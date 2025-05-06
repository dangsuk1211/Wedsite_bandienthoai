package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class departmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/department")
    public String getAllDepartments(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "departments";  
    }
 @GetMapping("/department/create")
    public String showCreateForm(Model model) {
        model.addAttribute("department", new Department());
        return "create_department";  
    }

	    @PostMapping("/department/create")
	    public String createDepartment(@ModelAttribute Department department, Model model) {
	    	
	    	 if (department.getIdDepartment().isEmpty() || department.getName().isEmpty() ) {
	    	        model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
	    	        return "create_department";
	    	    }
	    	    
	    	
	        if (departmentService.existsByIdDepartment(department.getIdDepartment())) {
	            
	         model.addAttribute("errorMessage", " ID đã tồn tại.");
	            return "create_department"; 
	        }
	        departmentService.createDepartment(department);
	        return "redirect:/admin/department";  
	    }
   @GetMapping("/department/delete/{id}")
    public String deleteDepartment(@PathVariable("id") String id) {
        departmentService.deleteDepartment(id);
        return "redirect:/admin/department";  
    }

@GetMapping("/department/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Department department = departmentService.getDepartmentById(id).orElse(null);
        model.addAttribute("department", department);
        return "edit_department"; 
    }

    @PostMapping("/department/edit/{id}")
    public String updateDepartment(@PathVariable("id") String id, @ModelAttribute Department department, Model model) {

       if (department.getIdDepartment().isEmpty() || department.getName().isEmpty() ) {
	    	        model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
	    	        return "edit_department";
	    	    }

        Department existingDepartment = departmentService.getDepartmentById(id).orElse(null);
        if (existingDepartment == null) {
            model.addAttribute("errorMessage", "Phòng ban không tồn tại.");
            return "edit_department";
        }
        
        existingDepartment.setIdDepartment(department.getIdDepartment());
        existingDepartment.setName(department.getName());
        
        if (!existingDepartment.getIdDepartment().equals(department.getIdDepartment())) {
            model.addAttribute("errorMessage", "Không thể thay đổi ID.");
            return "edit_department";
        }
        departmentService.updateDepartment(id, department);
        return "redirect:/admin/department";
    }



}
