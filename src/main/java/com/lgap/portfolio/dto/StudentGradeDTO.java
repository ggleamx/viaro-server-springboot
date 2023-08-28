package com.lgap.portfolio.dto;

import com.lgap.portfolio.common.BaseDTO;
import com.lgap.portfolio.entity.StudentGrade;

import java.time.LocalDateTime;

public class StudentGradeDTO  extends BaseDTO<StudentGrade, StudentGradeDTO> {
    private Long id;
    private StudentDTO student;
    private SimpleGradeDTO grade;

    private String section;

    private Boolean status;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;


    public StudentGradeDTO(Long id, StudentDTO student, SimpleGradeDTO grade,
                           Boolean status,
                           String section,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt
                          ) {
        this.id = id;
        this.student = student;
        this.grade = grade;
        this.status = status;
        this.section = section;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public StudentGradeDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }




    public SimpleGradeDTO getGrade() {
        return grade;
    }

    public void setSimpleGrade(SimpleGradeDTO grade) {
        this.grade = grade;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }


    @Override
    public StudentGradeDTO fromEntity(StudentGrade entity) {
        System.out.println(entity.getGrade().toString());
        return new StudentGradeDTO(
            entity.getId(),
            new StudentDTO().fromEntity(entity.getStudent()),
            new SimpleGradeDTO().fromEntity(entity.getGrade()),
            entity.getStatus(),
            entity.getSection(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()

        );
    }
}
