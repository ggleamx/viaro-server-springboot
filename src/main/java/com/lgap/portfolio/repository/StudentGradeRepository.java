package com.lgap.portfolio.repository;

import com.lgap.portfolio.entity.StudentGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentGradeRepository extends JpaRepository<StudentGrade,Long> {

    Optional<StudentGrade> findByStudentIdAndGradeId(Long studentId, Long gradeId);
    Optional<StudentGrade> findByStudentId(Long studentId);
}
