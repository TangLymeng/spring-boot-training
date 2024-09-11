package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    // update
    Student updateStudent(Student student, Long studentId);

    // read
    List<Student> fetchStudentList();

    // delete
    void DeleteStudentById(Long StudentById);

    // getById
    Optional<Student> getStudentById(Long StudentById);
}
