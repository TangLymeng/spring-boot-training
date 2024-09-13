package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // save operation
    @PostMapping("/departments")
    public ResponseEntity<Map<String, Object>> saveDepartment(@Valid @RequestBody Department department) {
        Map<String, Object> response = new HashMap<>();
        try {
            Department savedDepartment = departmentService.saveDepartment(department);
            response.put("status", "success");
            response.put("message", "Department saved successfully");
            response.put("data", savedDepartment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", "Failed to save department");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // read operation
    @GetMapping("/departments")
    public ResponseEntity<Map<String, Object>> fetchDepartmentList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Department> departments = departmentService.fetchDepartmentList();
            response.put("status", "success");
            response.put("message", "Departments fetched successfully");
            response.put("data", departments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", "Failed to fetch departments");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // read operation by id
    @GetMapping("/departments/{id}")
    public ResponseEntity<Object> getById(@Positive @PathVariable("id") Long departmentId) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        return department.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", HttpStatus.NOT_FOUND.value());
                    response.put("message", "Department not found with id: " + departmentId);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }

    // delete operation by id
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Map<String, Object>> deleteDepartmentById(@Positive @PathVariable("id") Long departmentId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        if (department.isPresent()) {
            try {
                departmentService.DeleteDepartmentById(departmentId);
                response.put("status", "success");
                response.put("message", "Deleted Successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("status", "fail");
                response.put("message", "Failed to delete department with id: " + departmentId);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("status", "fail");
            response.put("message", "Department not found with id: " + departmentId);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // update operation by id
    @PutMapping("/departments/{id}")
    public ResponseEntity<Map<String, Object>> updateDepartment(@Positive @PathVariable("id") Long departmentId, @RequestBody Department department) {
        Map<String, Object> response = new HashMap<>();
        Optional<Department> departmentDb = departmentService.getDepartmentById(departmentId);
        if (departmentDb.isPresent()) {
            try {
                Department updatedDepartment = departmentService.updateDepartment(department, departmentId);
                response.put("status", "success");
                response.put("message", "Department updated successfully");
                response.put("data", updatedDepartment);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("status", "fail");
                response.put("message", "Failed to update department");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.put("status", "fail");
            response.put("message", "Department not found with id: " + departmentId);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
