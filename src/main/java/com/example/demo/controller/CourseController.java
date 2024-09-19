package com.example.demo.controller;

import com.example.demo.dtos.CourseDTO;
import com.example.demo.responses.ResponseWrapper;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<CourseDTO>> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourse = courseService.saveCourse(courseDTO);
        ResponseWrapper<CourseDTO> response = new ResponseWrapper<>("success", "Course created successfully", savedCourse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CourseDTO>> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        try {
            Optional<CourseDTO> existingCourse = courseService.getCourseById(id);
            if (existingCourse.isPresent()) {
                CourseDTO updatedCourse = courseService.updateCourse(courseDTO, id);
                ResponseWrapper<CourseDTO> response = new ResponseWrapper<>("success", "Course updated successfully", updatedCourse);
                return ResponseEntity.ok(response);
            } else {
                ResponseWrapper<CourseDTO> response = new ResponseWrapper<>("fail", "Course not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ResponseWrapper<CourseDTO> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<CourseDTO>> getCourseById(@PathVariable Long id) {
        try {
            Optional<CourseDTO> courseDTO = courseService.getCourseById(id);
            if (courseDTO.isPresent()) {
                ResponseWrapper<CourseDTO> response = new ResponseWrapper<>("success", "Course fetched successfully", courseDTO.get());
                return ResponseEntity.ok(response);
            } else {
                ResponseWrapper<CourseDTO> response = new ResponseWrapper<>("fail", "Course not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ResponseWrapper<CourseDTO> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<CourseDTO>>> getAllCourses() {
        try {
            List<CourseDTO> courses = courseService.getAllCourses();
            ResponseWrapper<List<CourseDTO>> response = new ResponseWrapper<>("success", "Courses fetched successfully", courses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseWrapper<List<CourseDTO>> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteCourse(@PathVariable Long id) {
        try {
            Optional<CourseDTO> courseDTO = courseService.getCourseById(id);
            if (courseDTO.isPresent()) {
                courseService.deleteCourse(id);
                ResponseWrapper<String> response = new ResponseWrapper<>("success", "Course deleted successfully", null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ResponseWrapper<String> response = new ResponseWrapper<>("fail", "Course not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ResponseWrapper<String> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}