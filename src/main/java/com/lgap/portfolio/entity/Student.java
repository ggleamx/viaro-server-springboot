package com.lgap.portfolio.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "email", unique = true)
    @Email(message = "Email should be valid")
    @NotNull(message = "Email is mandatory")
    private String email;


    @Column(name = "firstName")
    @NotNull(message = "First name is mandatory")
    private String firstName;

    @Column(name = "lastName")
    @NotNull(message = "Last name is mandatory")
    private String lastName;


    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    @Column(name = "dateOfBirth")
    @NotNull(message = "Date of birth is mandatory")
    private LocalDate dateOfBirth;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private Boolean status = true;


    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        this.deletedAt = LocalDateTime.now();
    }


    public Student(){}
    public Student(long id, String firstName, String lastName, Gender gender, LocalDate dateOfBirth, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }


    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
    	this.email = email;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public LocalDateTime getDeletedAt() {
    	return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
    	this.deletedAt = deletedAt;
        this.status = false;
    }







    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +

                '}';
    }
}
