package com.lgap.portfolio.controller;

import com.lgap.portfolio.dto.APIResponse;
import com.lgap.portfolio.dto.StudentGradeDTO;
import com.lgap.portfolio.entity.Grade;
import com.lgap.portfolio.entity.Student;
import com.lgap.portfolio.entity.StudentGrade;
import com.lgap.portfolio.repository.GradeRepository;
import com.lgap.portfolio.repository.StudentGradeRepository;
import com.lgap.portfolio.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class StudentGradeController {
    @Autowired
    StudentGradeRepository studentGradeRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students_grades")
    public ResponseEntity getAllStudentGrades(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        try {

            Page<StudentGrade> studentGradePage = studentGradeRepository.findAll(PageRequest.of(page, size, Sort.by("updatedAt").descending()));

            if (studentGradePage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false, "No students grades found", null, 404));
            }


            Map<String, Object> response = new HashMap<>();

            List<StudentGradeDTO> dtos = studentGradePage.getContent().stream()
                    .map(sg -> {
                        StudentGradeDTO dto = new StudentGradeDTO().fromEntity(sg);
                        return dto;
                    }).collect(Collectors.toList());

            response.put("students_grades", dtos);
            response.put("currentPage", studentGradePage.getNumber());
            response.put("totalItems", studentGradePage.getTotalElements());
            response.put("totalPages", studentGradePage.getTotalPages());

            return ResponseEntity.ok(new APIResponse<>(false, "List of grades retrieved successfully", response, 200));

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Database access error: " + e.getMessage(), null, 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }

    @PostMapping("/students_grades")
    public ResponseEntity<APIResponse> createStudentGrade(
            @Valid
            @RequestBody Map<String, Object> request) {


        try {
            Long studentId = Long.valueOf((Integer) request.get("student_id"));
            Long gradeId = Long.valueOf((Integer) request.get("grade_id"));
            String section = (String) request.get("section");

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            Optional<Grade> optionalGrade = gradeRepository.findById(gradeId);

            if(!optionalStudent.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Student does not exist", null, 400));
            }

            if(!optionalGrade.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Grade does not exist", null, 400));
            }

            Optional<StudentGrade> existingStudentGrade = studentGradeRepository.findByStudentId(studentId);


            if (existingStudentGrade.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new APIResponse<>(true, "A student grade  already exists. Remove the student grade and try again.", null, 409));
            }



            // validate that the student is not already enrolled in any grade


            StudentGrade studentGrade = new StudentGrade();
            studentGrade.setStudent(optionalStudent.get());
            studentGrade.setGrade(optionalGrade.get());
            studentGrade.setSection(section);

            StudentGrade savedStudentGrade = studentGradeRepository.save(studentGrade);

            StudentGradeDTO dto = new StudentGradeDTO().fromEntity(savedStudentGrade);
            Map<String, Object> response = new HashMap<>();

            response.put("student_grade", dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse<>(false, "Student grade successfully created", response, 201));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


    @PutMapping("/students_grades/{id}")
    public ResponseEntity<APIResponse> updateStudentGrade(
            @PathVariable Long id,
            @Valid
            @RequestBody Map<String, Object> request) {
        try {
            Optional<StudentGrade> studentGradeOpt = studentGradeRepository.findById(id);
            if (!studentGradeOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse<>(true, "StudentGrade not found", null, 404));
            }

            Long studentId = Long.valueOf((Integer) request.get("student_id"));
            Long gradeId = Long.valueOf((Integer) request.get("grade_id"));
            String section = (String) request.get("section");

            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            Optional<Grade> optionalGrade = gradeRepository.findById(gradeId);

            if (!optionalStudent.isPresent() || !optionalGrade.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Student or Grade does not exist", null, 400));
            }

            // validate that the student is not already enrolled in any grade
            if(studentGradeRepository.findByStudentId(studentId).isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new APIResponse<>(true, "A student grade  already exists. Remove the student grade and try again.", null, 409));
            }


            StudentGrade studentGrade = studentGradeOpt.get();
            studentGrade.setStudent(optionalStudent.get());
            studentGrade.setGrade(optionalGrade.get());
            studentGrade.setSection(section);

            StudentGrade savedStudentGrade = studentGradeRepository.save(studentGrade);

            StudentGradeDTO dto = new StudentGradeDTO().fromEntity(savedStudentGrade);


            Map<String, Object> response = new HashMap<>();

            response.put("student_grade", dto);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse<>(false, "StudentGrade successfully updated", response, 200));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }

    @DeleteMapping("/students_grades/{id}")
    public ResponseEntity<APIResponse<StudentGrade>> deleteStudentGrade(@PathVariable Long id) {
        try {
            Optional<StudentGrade> studentGradeOpt = studentGradeRepository.findById(id);

            if (!studentGradeOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse<>(true, "StudentGrade not found", null, 404));
            }

            studentGradeRepository.delete(studentGradeOpt.get());

            return ResponseEntity.ok()
                    .body(new APIResponse<>(false, "StudentGrade successfully deleted", null, 200));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }



}