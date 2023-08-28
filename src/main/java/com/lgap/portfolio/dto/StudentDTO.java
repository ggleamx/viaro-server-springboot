package com.lgap.portfolio.dto;

import com.lgap.portfolio.common.BaseDTO;
import com.lgap.portfolio.entity.Student;

public class StudentDTO  extends BaseDTO<Student, StudentDTO> {

    private Long id;
    private String firstName;
    private String lastName;

    public StudentDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public StudentDTO() {
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


    @Override
    public StudentDTO fromEntity(Student entity) {
        return new StudentDTO(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName()
        );
    }
}
