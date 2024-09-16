package com.example.demo.service;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.entity.Department;
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

    // Save
    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setDepartmentName(departmentDTO.getName());
        department.setDepartmentAddress(departmentDTO.getAddress());
        department.setDepartmentCode(departmentDTO.getCode());

        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    // Update
    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long departmentId) {
        Department depDB = departmentRepository.findById(departmentId).orElseThrow();
        depDB.setDepartmentName(departmentDTO.getName());
        depDB.setDepartmentAddress(departmentDTO.getAddress());
        depDB.setDepartmentCode(departmentDTO.getCode());

        Department updatedDepartment = departmentRepository.save(depDB);
        return convertToDTO(updatedDepartment);
    }

    // Fetch all departments
    @Override
    public List<DepartmentDTO> fetchDepartmentList() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Delete
    @Override
    public void deleteDepartmentById(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new IllegalArgumentException("Department not found with id: " + departmentId);
        }
        departmentRepository.deleteById(departmentId);
    }

    // Get by ID
    @Override
    public Optional<DepartmentDTO> getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .map(this::convertToDTO);
    }

    // Helper method to convert Department to DepartmentDTO
    private DepartmentDTO convertToDTO(Department department) {
        return new DepartmentDTO(department.getDepartmentId(), department.getDepartmentName(),
                department.getDepartmentAddress(), department.getDepartmentCode());
    }
}