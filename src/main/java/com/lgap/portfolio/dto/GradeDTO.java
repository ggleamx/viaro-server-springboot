package com.lgap.portfolio.dto;

import com.lgap.portfolio.common.BaseDTO;
import com.lgap.portfolio.entity.Grade;

import java.time.LocalDateTime;

public class GradeDTO  extends BaseDTO<Grade, GradeDTO> {

    private Long id;
    private String name;

    private TeacherDTO teacher;
    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public GradeDTO(
            Long id, String name,
            Boolean status,
            TeacherDTO teacher,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.teacher = teacher;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;

    }



    public GradeDTO() {
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }



    public Long getId() {
        return id;
    }




    public TeacherDTO getTeacher() {
        return teacher;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }


    @Override
    public GradeDTO fromEntity(Grade entity) {
        return new GradeDTO(
            entity.getId(),
            entity.getName(),
            entity.getStatus(),
            new TeacherDTO().fromEntity(entity.getTeacher()),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }
}
