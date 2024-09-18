package com.example.demo.controller;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.responses.ResponseWrapper;
import com.example.demo.service.DepartmentService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<DepartmentDTO>> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        try {
            DepartmentDTO savedDepartment = departmentService.saveDepartment(departmentDTO);
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("success", "Department saved successfully", savedDepartment);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<DepartmentDTO>>> fetchDepartmentList() {
        try {
            List<DepartmentDTO> departments = departmentService.fetchDepartmentList();
            ResponseWrapper<List<DepartmentDTO>> response = new ResponseWrapper<>("success", "Departments fetched successfully", departments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ResponseWrapper<List<DepartmentDTO>> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<DepartmentDTO>> getById(@Positive @PathVariable("id") Long departmentId) {
        try {
            Optional<DepartmentDTO> department = departmentService.getDepartmentById(departmentId);
            if (department.isPresent()) {
                ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("success", "Department fetched successfully", department.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("fail", "Department not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteDepartmentById(@Positive @PathVariable("id") Long departmentId) {
        try {
            Optional<DepartmentDTO> department = departmentService.getDepartmentById(departmentId);
            if (department.isPresent()) {
                departmentService.deleteDepartmentById(departmentId);
                ResponseWrapper<String> response = new ResponseWrapper<>("success", "Deleted Successfully", null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ResponseWrapper<String> response = new ResponseWrapper<>("fail", "Department not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ResponseWrapper<String> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<DepartmentDTO>> updateDepartment(@Positive @PathVariable("id") Long departmentId, @RequestBody DepartmentDTO departmentDTO) {
        try {
            Optional<DepartmentDTO> department = departmentService.getDepartmentById(departmentId);
            if (department.isPresent()) {
                DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO, departmentId);
                ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("success", "Department updated successfully", updatedDepartment);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("fail", "Department not found", null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("fail", "An unexpected error occurred", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}