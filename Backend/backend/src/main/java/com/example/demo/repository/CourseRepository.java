package com.example.demo.repository;

import com.example.demo.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    // Custom method with JPQL query
    @Query("SELECT c FROM CourseEntity c WHERE c.courseName = :courseName")
    List<CourseEntity> findByCourseName(String courseName);
}
