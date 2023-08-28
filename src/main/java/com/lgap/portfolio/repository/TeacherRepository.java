package com.lgap.portfolio.repository;

import com.lgap.portfolio.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

   Teacher findByEmail(String email);


}
