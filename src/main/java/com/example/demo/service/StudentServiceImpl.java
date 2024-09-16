package com.example.demo.service;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final DepartmentService departmentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public List<Student> fetchStudentList() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public void DeleteStudentById(Long StudentById) {
        studentRepository.deleteById(StudentById);
    }

    @Override
    public Optional<Student> getStudentById(Long StudentById) {
        return studentRepository.findById(StudentById);
    }

    @Override
    public Student updateStudent(Student student, Long studentId) {
        // Retrieve the existing student
        Student stuDB = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found for id: " + studentId));

        // Check if a new department is provided in the student DTO
        if (student.getDepartment() != null && student.getDepartment().getDepartmentId() != null) {
            // Get the department using the new DepartmentService
            DepartmentDTO departmentDTO = departmentService.getDepartmentById(student.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found for id: " + student.getDepartment().getDepartmentId()));

            // Set the department in the student entity
            Department department = new Department(); // Create a new Department instance
            department.setDepartmentId(departmentDTO.getId());
            department.setDepartmentName(departmentDTO.getName());
            department.setDepartmentAddress(departmentDTO.getAddress());
            department.setDepartmentCode(departmentDTO.getCode());

            stuDB.setDepartment(department);
        }

        // Update the student name if provided
        if (student.getStudentName() != null) {
            stuDB.setStudentName(student.getStudentName());
        }

        // Update the student email if provided
        if (student.getStudentEmail() != null) {
            stuDB.setStudentEmail(student.getStudentEmail());
        }

        // Save and return the updated student
        return studentRepository.save(stuDB);
    }}

