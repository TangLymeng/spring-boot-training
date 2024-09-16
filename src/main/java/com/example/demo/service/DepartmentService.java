package com.example.demo.service;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long departmentId);
    List<DepartmentDTO> fetchDepartmentList();
    void deleteDepartmentById(Long departmentId);
    Optional<DepartmentDTO> getDepartmentById(Long departmentId);
}