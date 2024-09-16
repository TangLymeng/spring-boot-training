package com.example.demo.service;

import com.example.demo.dtos.DepartmentDTO;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);
    List<DepartmentDTO> fetchDepartmentList();
    Optional<DepartmentDTO> getDepartmentById(Long departmentId);
    void deleteDepartmentById(Long departmentId);
    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long departmentId);
}