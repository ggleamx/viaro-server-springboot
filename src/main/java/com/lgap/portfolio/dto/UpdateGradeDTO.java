package com.lgap.portfolio.dto;

public class UpdateGradeDTO {
    private String name;
    private Long teacher_id; // este es el ID del profesor que quieres actualizar

    public UpdateGradeDTO(String name, Long teacher_id) {
        this.name = name;
        this.teacher_id = teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeacherId() {
        return teacher_id;
    }

    public void setTeacherId(Long teacher_id) {
        this.teacher_id = teacher_id;
    }


    @Override
    public String toString() {
        return "GradeUpdateDTO{" +
                "name='" + name + '\'' +
                ", teacher_id=" + teacher_id +
                '}';
    }
}