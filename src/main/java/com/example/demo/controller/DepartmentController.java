package com.example.demo.controller;

import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.responses.ResponseWrapper;
import com.example.demo.service.DepartmentService;
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
        DepartmentDTO savedDepartment = departmentService.saveDepartment(departmentDTO);
        ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("success", "Department saved successfully", savedDepartment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<DepartmentDTO>>> fetchDepartmentList() {
        List<DepartmentDTO> departments = departmentService.fetchDepartmentList();
        ResponseWrapper<List<DepartmentDTO>> response = new ResponseWrapper<>("success", "Departments fetched successfully", departments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<DepartmentDTO>> getById(@PathVariable("id") Long departmentId) {
        Optional<DepartmentDTO> department = departmentService.getDepartmentById(departmentId);
        if (department.isPresent()) {
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("success", "Department fetched successfully", department.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("fail", "Department not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteDepartmentById(@PathVariable("id") Long departmentId) {
        Optional<DepartmentDTO> department = departmentService.getDepartmentById(departmentId);
        if (department.isPresent()) {
            departmentService.deleteDepartmentById(departmentId);
            ResponseWrapper<String> response = new ResponseWrapper<>("success", "Deleted Successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<String> response = new ResponseWrapper<>("fail", "Department not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<DepartmentDTO>> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody DepartmentDTO departmentDTO) {
        Optional<DepartmentDTO> department = departmentService.getDepartmentById(departmentId);
        if (department.isPresent()) {
            DepartmentDTO updatedDepartment = departmentService.updateDepartment(departmentDTO, departmentId);
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("success", "Department updated successfully", updatedDepartment);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ResponseWrapper<DepartmentDTO> response = new ResponseWrapper<>("fail", "Department not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}