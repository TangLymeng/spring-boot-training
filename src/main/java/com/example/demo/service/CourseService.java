package com.example.demo.service;

import com.example.demo.dtos.CourseDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    CourseDTO saveCourse(CourseDTO courseDTO);
    CourseDTO updateCourse(CourseDTO courseDTO, Long courseId);
    Optional<CourseDTO> getCourseById(Long courseId);
    List<CourseDTO> getAllCourses();
    void deleteCourse(Long courseId);
}