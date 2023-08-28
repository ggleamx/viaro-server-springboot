package com.lgap.portfolio.controller;

import com.lgap.portfolio.dto.APIResponse;
import com.lgap.portfolio.entity.Student;
import com.lgap.portfolio.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public ResponseEntity getAllStudents(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
            ) {

        try {
            Page<Student> studentPage = studentRepository.findAll(PageRequest.of(page, size, Sort.by("updatedAt").descending()));

            if (studentPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false, "No students found", null, 404));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("students", studentPage.getContent());
            response.put("currentPage", studentPage.getNumber());
            response.put("totalItems", studentPage.getTotalElements());
            response.put("totalPages", studentPage.getTotalPages());

            return ResponseEntity.ok(new APIResponse<>(false, "List of students retrieved successfully", response, 200));

        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Database access error: " + e.getMessage(), null, 500));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<APIResponse<Student>> updateStudent(
            @PathVariable Long id,
            @Valid
            @RequestBody Student studentUpdate) {
        try {
            Student originalStudent = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student with id " + id + " not found"));

            if(!originalStudent.getStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Student is not active.", null, 400));
            }

            if (!originalStudent.getEmail().equals(studentUpdate.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Email cannot be changed.", null, 400));
            }

            // Update the student with the new data
            originalStudent.setFirstName(studentUpdate.getFirstName());
            originalStudent.setLastName(studentUpdate.getLastName());
            originalStudent.setDateOfBirth(studentUpdate.getDateOfBirth());
            originalStudent.setGender(studentUpdate.getGender());

            Student savedStudent = studentRepository.save(originalStudent);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse<>(false, "Student successfully updated", savedStudent, 200));

        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse<>(true, re.getMessage(), null, 404));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "An unexpected error occurred. Please try again later.", null, 500));
        }
    }


    @PostMapping("/students")
    public ResponseEntity<APIResponse<Student>> createStudent(
            @Valid
            @RequestBody Student student) {
        try {
            // Validate student if exists by email
            Student existingStudent = studentRepository.findByEmail(student.getEmail());



            if (existingStudent != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new APIResponse<>(true, "A student with that email already exists.", null, 409));
            }

            // Obligate the student to be active
            student.setStatus(true);

            Student savedStudent = studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new APIResponse<>(false, "Student successfully created", savedStudent, 201));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse<>(true, "Data integrity violation: " + e.getMessage(), null, 400));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


    @DeleteMapping("/students/{id}")
    public ResponseEntity<APIResponse<Student>> deleteStudent(@PathVariable Long id) {
        try {
            Optional<Student> studentOpt = studentRepository.findById(id);

            if (!studentOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new APIResponse<>(true, "Student not found", null, 404));
            }

            if(!studentOpt.get().getStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse<>(true, "Student is not active.", null, 400));
            }

            Student student = studentOpt.get();
            student.setDeletedAt(LocalDateTime.now());
            studentRepository.save(student);

            return ResponseEntity.ok()
                    .body(new APIResponse<>(false, "Student successfully deleted", null, 200));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse<>(true, "Internal server error: " + e.getMessage(), null, 500));
        }
    }


}