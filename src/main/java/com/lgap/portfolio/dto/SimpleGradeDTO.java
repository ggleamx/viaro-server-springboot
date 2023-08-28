package com.lgap.portfolio.dto;

import com.lgap.portfolio.common.BaseDTO;
import com.lgap.portfolio.entity.Grade;

public class SimpleGradeDTO extends BaseDTO<Grade,SimpleGradeDTO> {

    private Long id;
    private String name;

    private TeacherDTO teacher;

    public SimpleGradeDTO(Long id, String name, TeacherDTO teacher) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
    }

    public SimpleGradeDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public SimpleGradeDTO fromEntity(Grade entity) {
        return new SimpleGradeDTO(
            entity.getId(),
            entity.getName(),
            new TeacherDTO().fromEntity(entity.getTeacher())
        );
    }
}
