package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired private StudentService studentService;

    // update operation by id
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable("id") Long studentId, @RequestBody Student student) {
        return studentService.updateStudent(student, studentId);
    }

    // get list of students
    @GetMapping("/students")
    public List<Student> fetchDepartmentList() {
        return studentService.fetchStudentList();
    }

    // get student by id
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") Long studentId) {
        Optional<Student> student = studentService.getStudentById(studentId);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // delete operation by id
    @DeleteMapping("/students/{id}")
    public String deleteStudentById(@PathVariable("id") Long studentId) {
        studentService.DeleteStudentById(studentId);
        return "Deleted Successfully";
    }
}


