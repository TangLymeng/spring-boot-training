package com.example.demo.service;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.dtos.StudentDTO;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    @Override
    public List<DepartmentDTO> fetchDepartmentList() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DepartmentDTO> getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(this::convertToDTO);
    }

    @Override
    public void deleteDepartmentById(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long departmentId) {
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found for id: " + departmentId));

        if (departmentDTO.getName() != null) {
            existingDepartment.setDepartmentName(departmentDTO.getName());
        }
        if (departmentDTO.getAddress() != null) {
            existingDepartment.setDepartmentAddress(departmentDTO.getAddress());
        }
        if (departmentDTO.getCode() != null) {
            existingDepartment.setDepartmentCode(departmentDTO.getCode());
        }

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return convertToDTO(updatedDepartment);
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(department.getDepartmentId());
        departmentDTO.setName(department.getDepartmentName());
        departmentDTO.setAddress(department.getDepartmentAddress());
        departmentDTO.setCode(department.getDepartmentCode());
        departmentDTO.setStudents(department.getStudents().stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList()));
        return departmentDTO;
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setDepartmentId(departmentDTO.getId());
        department.setDepartmentName(departmentDTO.getName());
        department.setDepartmentAddress(departmentDTO.getAddress());
        department.setDepartmentCode(departmentDTO.getCode());
        return department;
    }

    private StudentDTO convertToStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getStudentId());
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setStudentEmail(student.getStudentEmail());
        studentDTO.setDepartmentId(student.getDepartment().getDepartmentId());
        studentDTO.setStudentImageUrl(student.getStudentImageUrl());
        return studentDTO;
    }
}