package com.lgap.portfolio.controller;

import com.lgap.portfolio.dto.APIResponse;
import com.lgap.portfolio.entity.Teacher;
import com.lgap.portfolio.repository.TeacherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    private TeacherRepository TeacherRepository;

    @GetMapping("/teachers")
    public ResponseEntity getAllTeachers(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ) {

        try {
            Page<Teacher> TeacherPage = TeacherRepository.findAll(PageRequest.of(page, size, Sort.by("updatedAt").descending()));

            if (TeacherPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false, "No Teachers found", null, 404));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("teachers", TeacherPage.getContent());
            response.put("currentPage", TeacherPage.getNumber());
            response.put("totalItems", TeacherPage.getTotalElements());
            response.put("totalPages", TeacherPage.getTotalPages());

            return ResponseEntity.ok(new APIResponse<>(false, "List of Teachers retrieved successfully", response, 200));

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Database access error: " + e.getMessage(), null, 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<APIResponse<Teacher>> updateTeacher(
            @PathVariable Long id,
            @Valid
            @RequestBody Teacher TeacherUpdate) {
        try {
            Teacher originalTeacher = TeacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher with id " + id + " not found"));

            if(!originalTeacher.getStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Teacher is not active. select a different one", null, 400));
            }

            if (!originalTeacher.getEmail().equals(TeacherUpdate.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Email cannot be changed.", null, 400));
            }

            // Update the Teacher with the new data
            originalTeacher.setFirstName(TeacherUpdate.getFirstName());
            originalTeacher.setLastName(TeacherUpdate.getLastName());
            originalTeacher.setGender(TeacherUpdate.getGender());

            Teacher savedTeacher = TeacherRepository.save(originalTeacher);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse<>(false, "Teacher successfully updated", savedTeacher, 200));

        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse<>(true, re.getMessage(), null, 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "An unexpected error occurred. Please try again later.", null, 500));
        }
    }


    @PostMapping("/teachers")
    public ResponseEntity<APIResponse<Teacher>> createTeacher(
            @Valid
            @RequestBody Teacher Teacher) {
        try {
            // Validate Teacher if exists by email
            Teacher existingTeacher = TeacherRepository.findByEmail(Teacher.getEmail());



            if (existingTeacher != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new APIResponse<>(true, "A Teacher with that email already exists.", null, 409));
            }

            // Obligate the Teacher to be active
            Teacher.setStatus(true);

            Teacher savedTeacher = TeacherRepository.save(Teacher);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse<>(false, "Teacher successfully created", savedTeacher, 201));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse<>(true, "Data integrity violation: " + e.getMessage(), null, 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<APIResponse<Teacher>> deleteTeacher(@PathVariable Long id) {
        try {
            Optional<Teacher> TeacherOpt = TeacherRepository.findById(id);

            if (!TeacherOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse<>(true, "Teacher not found", null, 404));
            }

            if(!TeacherOpt.get().getStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Teacher is not active. ", null, 400));
            }

            Teacher Teacher = TeacherOpt.get();
            Teacher.setDeletedAt(LocalDateTime.now());
            TeacherRepository.save(Teacher);

            return ResponseEntity.ok()
                    .body(new APIResponse<>(false, "Teacher successfully deleted", null, 200));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


}