package com.lgap.portfolio.controller;

import com.lgap.portfolio.dto.APIResponse;
import com.lgap.portfolio.dto.GradeDTO;
import com.lgap.portfolio.dto.UpdateGradeDTO;
import com.lgap.portfolio.entity.Grade;
import com.lgap.portfolio.entity.Teacher;
import com.lgap.portfolio.repository.GradeRepository;
import com.lgap.portfolio.repository.TeacherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class GradeController {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping("/grades")
    public ResponseEntity getAllGrades(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        try {

            Page<Grade> gradePage = gradeRepository.findAll(PageRequest.of(page, size, Sort.by("updatedAt").descending()));

            if (gradePage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false, "No grades found", null, 404));
            }


            List<GradeDTO> dtos  = gradePage.getContent().stream()
                    .map(sg -> new GradeDTO().fromEntity(sg)
                    ).collect(Collectors.toList());


            Map<String, Object> response = new HashMap<>();
            response.put("grades", dtos);
            response.put("currentPage", gradePage.getNumber());
            response.put("totalItems", gradePage.getTotalElements());
            response.put("totalPages", gradePage.getTotalPages());

            return ResponseEntity.ok(new APIResponse<>(false, "List of grades retrieved successfully", response, 200));

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Database access error: " + e.getMessage(), null, 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


    @PutMapping("/grades/{id}")
    public ResponseEntity<APIResponse> updateGrade(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateGradeDTO gradeUpdateDTO) {
        try {
            Grade originalGrade = gradeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Grade with id " + id + " not found"));


            if(!originalGrade.getStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Grade is not active.", null, 400));
            }


            if (gradeUpdateDTO.getName() != null) {
                originalGrade.setName(gradeUpdateDTO.getName());
            }

            if (gradeUpdateDTO.getTeacherId() != null) {
                Teacher newTeacher = teacherRepository.findById(gradeUpdateDTO.getTeacherId())
                        .orElseThrow(() -> new RuntimeException("Teacher with id " + gradeUpdateDTO.getTeacherId() + " not found"));

                originalGrade.setTeacher(newTeacher);
            }

            Grade existingGrade = gradeRepository.findByName(originalGrade.getName());

            if (existingGrade != null && existingGrade.getId() != originalGrade.getId()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new APIResponse<>(true, "A grade with that name already exists.", null, 409));
            }


            Grade savedGrade = gradeRepository.save(originalGrade);

            GradeDTO dto = new GradeDTO().fromEntity(savedGrade);

            Map<String, Object> response = new HashMap<>();
            response.put("grade", dto);



            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse<>(false, "Grade successfully updated", response, 200));

        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse<>(true, re.getMessage(), null, 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "An unexpected error occurred. Please try again later.", null, 500));
        }
    }



    @PostMapping("/grades")
    public ResponseEntity<APIResponse> createGrade(
            @Valid
            @RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            Long teacherId = Long.valueOf((Integer) request.get("teacher_id"));

            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
            if(!optionalTeacher.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Teacher does not exist", null, 400));
            }

            Grade grade = new Grade();
            grade.setName(name);
            grade.setTeacher(optionalTeacher.get());
            grade.setStatus(true);

            Grade existingGrade = gradeRepository.findByName(grade.getName());


            if (existingGrade != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new APIResponse<>(true, "A grade with that name already exists.", null, 409));
            }

            Grade savedGrade = gradeRepository.save(grade);


            Map<String, Object> response = new HashMap<>();

            response.put("grade", new GradeDTO().fromEntity(savedGrade));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse<>(false, "Grade successfully created", response, 201));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


    @DeleteMapping("/grades/{id}")
    public ResponseEntity<APIResponse<Grade>> deleteGrade(@PathVariable Long id) {
        try {
            Optional<Grade> gradeOpt = gradeRepository.findById(id);

            if (!gradeOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse<>(true, "Grade not found", null, 404));
            }

            if(!gradeOpt.get().getStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Grade is not active.", null, 400));
            }

            Grade grade = gradeOpt.get();
            grade.setDeletedAt(LocalDateTime.now());
            gradeRepository.save(grade);



            return ResponseEntity.ok()
                    .body(new APIResponse<>(false, "Grade successfully deleted", null, 200));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


}