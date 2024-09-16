package com.example.demo.controller;

import com.example.demo.dtos.DepartmentDTO;
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

    // Save operation
    @PostMapping("/departments")
    public ResponseEntity<Map<String, Object>> saveDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            DepartmentDTO savedDepartment = departmentService.saveDepartment(departmentDTO);
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

    // Read operation
    @GetMapping("/departments")
    public ResponseEntity<Map<String, Object>> fetchDepartmentList() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DepartmentDTO> departments = departmentService.fetchDepartmentList();
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

    // Read operation by id
    @GetMapping("/departments/{id}")
    public ResponseEntity<Map<String, Object>> getById(@Positive @PathVariable("id") Long departmentId) {
        Map<String, Object> response = new HashMap<>();
        Optional<DepartmentDTO> departmentDTO = departmentService.getDepartmentById(departmentId);
        return departmentDTO.map(dep -> {
                    response.put("status", "success");
                    response.put("message", "Department fetched successfully");
                    response.put("data", dep);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    response.put("status", HttpStatus.NOT_FOUND.value());
                    response.put("message", "Department not found with id: " + departmentId);
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }

    // Delete operation by id
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Map<String, Object>> deleteDepartmentById(@Positive @PathVariable("id") Long departmentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            departmentService.deleteDepartmentById(departmentId);
            response.put("status", "success");
            response.put("message", "Deleted Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", "Failed to delete department with id: " + departmentId);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update operation by id
    @PutMapping("/departments/{id}")
    public ResponseEntity<Map<String, Object>> updateDepartment(@Positive @PathVariable("id") Long departmentId,
                                                                @RequestBody DepartmentDTO departmentDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO, departmentId);
            response.put("status", "success");
            response.put("message", "Department updated successfully");
            response.put("data", updatedDepartment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "fail");
            response.put("message", "Failed to update department");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}