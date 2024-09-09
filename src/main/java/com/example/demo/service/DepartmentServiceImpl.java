package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    // save
    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // update department by id
    @Override
    public Department updateDepartment(Department department, Long departmentId) {
        Department depDB = departmentRepository.findById(departmentId).get();
        if (department.getDepartmentName() != null) {
            depDB.setDepartmentName(department.getDepartmentName());
        }
        if (department.getDepartmentCode() != null) {
            depDB.setDepartmentCode(department.getDepartmentCode());
        }
        if (department.getDepartmentAddress() != null) {
            depDB.setDepartmentAddress(department.getDepartmentAddress());
        }
        return departmentRepository.save(depDB);
    }

    // get department
    @Override
    public List<Department> fetchDepartmentList() {
        return (List<Department>) departmentRepository.findAll();
    }

    // delete
    @Override
    public void DeleteDepartmentById(Long DepartmentById) {
        departmentRepository.deleteById(DepartmentById);
    }

    @Override
    public Optional<Department> getDepartmentById(Long DepartmentById) {
        return departmentRepository.findById(DepartmentById);
    }

}
