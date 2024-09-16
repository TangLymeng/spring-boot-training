package com.example.demo.service;

import com.example.demo.dtos.CourseDTO;
import com.example.demo.entity.Course;
import com.example.demo.entity.Department;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public CourseDTO saveCourse(CourseDTO courseDTO) {
        Optional<Department> department = departmentRepository.findById(courseDTO.getDepartmentId());
        if (department.isPresent()) {
            Course course = Course.builder()
                    .courseName(courseDTO.getCourseName())
                    .courseCode(courseDTO.getCourseCode())
                    .department(department.get())
                    .build();
            course = courseRepository.save(course);
            courseDTO.setCourseId(course.getCourseId());
            return courseDTO;
        } else {
            throw new RuntimeException("Department not found");
        }
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found for id: " + courseId));
        course.setCourseName(courseDTO.getCourseName());
        course.setCourseCode(courseDTO.getCourseCode());
        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    @Override
    public Optional<CourseDTO> getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .map(this::convertToDTO);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    private CourseDTO convertToDTO(Course course) {
        return CourseDTO.builder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .courseCode(course.getCourseCode())
                .departmentId(course.getDepartment().getDepartmentId())
                .build();
    }
}