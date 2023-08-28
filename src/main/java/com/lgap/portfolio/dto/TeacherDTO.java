package com.lgap.portfolio.dto;

import com.lgap.portfolio.common.BaseDTO;
import com.lgap.portfolio.entity.Teacher;

public class TeacherDTO extends BaseDTO<Teacher,TeacherDTO> {
    private Long id;
    private String firstName;
    private String lastName;

    public TeacherDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public TeacherDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public TeacherDTO serialize(Teacher teacher) {
        return new TeacherDTO(
            teacher.getId(),
            teacher.getFirstName(),
            teacher.getLastName()
        );
    }


    @Override
    public TeacherDTO fromEntity(Teacher entity) {
        return new TeacherDTO(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName()
        );
    }
}
