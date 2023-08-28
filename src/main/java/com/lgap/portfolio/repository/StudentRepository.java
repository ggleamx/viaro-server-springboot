package com.lgap.portfolio.repository;

import com.lgap.portfolio.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findByEmail(String email);


}
