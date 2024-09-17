package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositories: Provide data access methods.
public interface CourseRepository extends JpaRepository<Course, Long> {
}