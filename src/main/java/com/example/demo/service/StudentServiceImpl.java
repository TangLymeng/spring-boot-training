package com.example.demo.service;

import com.example.demo.dtos.StudentDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<StudentDTO> fetchStudentList() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<StudentDTO> getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .map(this::convertToDTO);
    }

    @Override
    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO, Long studentId) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found for id: " + studentId));

        if (studentDTO.getStudentName() != null) {
            existingStudent.setStudentName(studentDTO.getStudentName());
        }
        if (studentDTO.getStudentEmail() != null) {
            Optional<Student> studentWithEmail = studentRepository.findByStudentEmail(studentDTO.getStudentEmail());
            if (studentWithEmail.isPresent() && !studentWithEmail.get().getStudentId().equals(studentId)) {
                throw new RuntimeException("Email is already in use by another student");
            }
            existingStudent.setStudentEmail(studentDTO.getStudentEmail());
        }
        if (studentDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(studentDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found for id: " + studentDTO.getDepartmentId()));
            existingStudent.setDepartment(department);
        }
        if (studentDTO.getStudentImageUrl() != null) {
            existingStudent.setStudentImageUrl(studentDTO.getStudentImageUrl());
        }

        Student updatedStudent = studentRepository.save(existingStudent);
        return convertToDTO(updatedStudent);
    }

    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getStudentId());
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setStudentEmail(student.getStudentEmail());
//        studentDTO.setDepartmentId(student.getDepartment().getDepartmentId());
        studentDTO.setStudentImageUrl(student.getStudentImageUrl());

        if (student.getDepartment() != null) {
            studentDTO.setDepartmentId(student.getDepartment().getDepartmentId());
        }
        return studentDTO;
    }

    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setStudentId(studentDTO.getId());
        student.setStudentName(studentDTO.getStudentName());
        student.setStudentEmail(studentDTO.getStudentEmail());
        if (studentDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(studentDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found for id: " + studentDTO.getDepartmentId()));
            student.setDepartment(department);
        }
        student.setStudentImageUrl(studentDTO.getStudentImageUrl());
        return student;
    }
}