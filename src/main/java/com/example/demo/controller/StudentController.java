package com.example.demo.controller;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.dtos.StudentDTO;
import com.example.demo.responses.ResponseWrapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<StudentDTO>>> fetchStudentList() {
        List<StudentDTO> students = studentService.fetchStudentList();
        ResponseWrapper<List<StudentDTO>> response = new ResponseWrapper<>("success", "Students fetched successfully", students);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<StudentDTO>> getById(@PathVariable("id") Long studentId) {
        Optional<StudentDTO> student = studentService.getStudentById(studentId);
        if (student.isPresent()) {
            ResponseWrapper<StudentDTO> response = new ResponseWrapper<>("success", "Student fetched successfully", student.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<StudentDTO> response = new ResponseWrapper<>("fail", "Student not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteStudentById(@PathVariable("id") Long studentId) {
        Optional<StudentDTO> student = studentService.getStudentById(studentId);
        if (student.isPresent()) {
            studentService.deleteStudentById(studentId);
            ResponseWrapper<String> response = new ResponseWrapper<>("success", "Deleted Successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<String> response = new ResponseWrapper<>("fail", "Student not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<StudentDTO>> updateStudent(@PathVariable("id") Long studentId, @RequestBody StudentDTO studentDTO) {
        Optional<StudentDTO> existingStudent = studentService.getStudentById(studentId);
        if (existingStudent.isPresent()) {
            StudentDTO updatedStudent = studentService.updateStudent(studentDTO, studentId);
            ResponseWrapper<StudentDTO> response = new ResponseWrapper<>("success", "Student updated successfully", updatedStudent);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<StudentDTO> response = new ResponseWrapper<>("fail", "Student not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}