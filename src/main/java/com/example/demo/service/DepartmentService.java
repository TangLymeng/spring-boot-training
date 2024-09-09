package com.example.demo.service;
import com.example.demo.entity.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    // save
    Department saveDepartment(Department department);

    // update
    Department updateDepartment(Department department, Long departmentId);

    // read
    List<Department> fetchDepartmentList();

    // delete
    void DeleteDepartmentById(Long DepartmentById);

    // getById
    Optional<Department> getDepartmentById(Long DepartmentById);

}
