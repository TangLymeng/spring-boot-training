package com.example.demo.service;

import com.example.demo.dtos.StudentDTO;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentDTO> fetchStudentList();
    Optional<StudentDTO> getStudentById(Long studentId);
    void deleteStudentById(Long studentId);
    StudentDTO updateStudent(StudentDTO studentDTO, Long studentId);
}