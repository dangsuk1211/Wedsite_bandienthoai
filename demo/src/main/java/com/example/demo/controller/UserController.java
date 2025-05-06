package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/accounts")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/infor/{accountuserid}")
    public String checkAccountUser(@PathVariable String accountuserid, Model model) {
    	

    	Optional<User> user = userRepository.findByAccountuserid(accountuserid);

    	 if (!user.isPresent()) {
    	        
    	        User newUser = new User();
    	        newUser.setAccountuserid(accountuserid); 
    	        model.addAttribute("user", newUser);
    	        model.addAttribute("errorMessage", "Không tìm thấy User liên kết với Account này. Vui lòng nhập thông tin mới.");
    	        return "inforUser2"; 
    	    }


        model.addAttribute("user", user.get());
        return "inforUser"; 
    }
    
    @PostMapping("/infor/{accountuserid}")
    public String updateUser(@PathVariable("accountuserid") String accountuserid, @ModelAttribute User user, Model model) {
    try {	
    	if (user.getId().isEmpty() || user.getFullname().isEmpty() || user.getPhone().isEmpty() || user.getAddress().isEmpty()
    			|| user.getDepartment().isEmpty() || user.getAccountuserid().isEmpty()) {
	        model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
	        return "inforUser";
	    }  
        User updatedUser = userService.updateUser(accountuserid, user);
        model.addAttribute("successMessage", "Cập nhật thông tin thành công");
        return "redirect:/admin/accounts";
    }
     catch (Exception e) {
        model.addAttribute("errorMessage", "Phòng ban không tồn tại.");
        return "inforUser";
    }
    }
    
    @GetMapping("/infor2/{accountuserid}")
  public String checkAccountUser2(@PathVariable String accountuserid, Model model) {
    	
    	        return "inforUser2"; 
    	    }

    @PostMapping("/infor2/{accountuserid}")
    public String createNewUser(@PathVariable String accountuserid, @ModelAttribute User user, Model model) {
        try {
       
            if (user.getId().isEmpty() || user.getFullname().isEmpty() || user.getPhone().isEmpty() || 
                user.getAddress().isEmpty() || user.getDepartment().isEmpty()) {
                model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
                return "inforUser2";
            }
            if (userService.existsById(user.getId())) {
                model.addAttribute("errorMessage", "ID đã tồn tại. Vui lòng chọn giá trị khác.");
                return "inforUser2";
            }

            if (userService.existsByAccountUserId(user.getAccountuserid())) {
                model.addAttribute("errorMessage", "Account User ID đã liên kết với User khác.");
                return "inforUser2";
            }

            userService.createUser(user);
            model.addAttribute("successMessage", "Tạo User mới thành công!");
            return "redirect:/admin/accounts"; 

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Phòng ban không tồn tại.");
            return "inforUser2";
        }
    }

    
}
