package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(String id) {
        return departmentRepository.findById(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(String id, Department department) {
        if (departmentRepository.existsById(id)) {
            department.setIdDepartment(id);
            return departmentRepository.save(department);
        }
        return null;
    }
    public boolean existsByIdDepartment(String IdDepartment) {
        return departmentRepository.existsById(IdDepartment);
    }
    public boolean existsByDepartmentName(String name) {
        return departmentRepository.existsByName(name);
    }
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }
}
