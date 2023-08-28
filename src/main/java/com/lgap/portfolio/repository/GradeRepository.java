package com.lgap.portfolio.repository;

import com.lgap.portfolio.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade,Long> {

   Grade findByName(String name);


}
