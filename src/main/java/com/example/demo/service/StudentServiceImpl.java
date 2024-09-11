package com.example.demo.service;

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
        Student stuDB = studentRepository.findById(studentId).
                orElseThrow(() -> new RuntimeException("Student not found for id: " + studentId));
        if (student.getDepartment() != null) {
            Department department = departmentService.getDepartmentById(student.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found for id: " + student.getDepartment().getDepartmentId()));
            stuDB.setDepartment(department);
        }
        if (student.getStudentName() != null) {
            stuDB.setStudentName(student.getStudentName());
        }
        return studentRepository.save(stuDB);
    }
}

