package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AccountRepository;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class AccountUserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/viewProduct")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "viewproduct_user"; 
    }
	    
	    @GetMapping("/viewProduct/{id}")
	    public String viewProduct(@PathVariable("id") Long id, Model model) {
	        Product product = productService.findById(id);
	        model.addAttribute("product", product);
	        return "viewinforproduct_user";
	    }
	    
	    @GetMapping("/account")
	    public String viewAccount(HttpServletRequest request, Model model) {
	        HttpSession session = request.getSession();
	        String username = (String) session.getAttribute("loggedUser");
	        if (username == null) {
	            return "redirect:/login"; 
	        }

	        Account account = accountRepository.findByName(username);
	        model.addAttribute("account", account); 

	        return "useraccount"; 
	    }
	    @GetMapping("/account/edit/{id}")
	    public String showEditForm(@PathVariable("id") String id, Model model) {
	        Account account = accountService.getAccountById(id).orElse(null);
	        model.addAttribute("account", account);
	        return "edit_account_user"; 
	    }

	    @PostMapping("/account/edit/{id}")
	    public String updateAccount(@PathVariable("id") String id, @ModelAttribute Account account, Model model) {

	        if (account.getAccountId().isEmpty() || account.getName().isEmpty() || account.getPass().isEmpty()) {
	            model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
	            return "edit_account"; 
	        }
	        
	        Account existingAccount = accountService.getAccountById(id).orElse(null);
	        if (existingAccount == null) {
	            model.addAttribute("errorMessage", "Tài khoản không tồn tại.");
	            return "edit_account_user"; 
	        }
	        
	        existingAccount.setAccountId(account.getAccountId());
	        existingAccount.setName(account.getName());
	        existingAccount.setPass(account.getPass());
	        existingAccount.setType(account.getType());
	        
	        if (!existingAccount.getAccountId().equals(account.getAccountId())) {
	            model.addAttribute("errorMessage", "Không thể thay đổi Account ID.");
	            return "edit_account_user"; 
	        }
	        
	        if (!existingAccount.getName().equals(account.getName()) && accountService.existsByAccountName(account.getName())) {
	            model.addAttribute("errorMessage", "Account Name đã tồn tại. Vui lòng chọn giá trị khác.");
	            return "edit_account_user"; 
	        }
	        accountService.updateAccount(id, account);
	        return "redirect:/user/account";
	    }
	    @GetMapping("/account/infor/{accountuserid}")
	    public String checkAccountUser(@PathVariable String accountuserid, Model model) {
	    	

	    	Optional<User> user = userRepository.findByAccountuserid(accountuserid);

	    	 if (!user.isPresent()) {
	    	        
	    	        User newUser = new User();
	    	        newUser.setAccountuserid(accountuserid); 
	    	        model.addAttribute("user", newUser);
	    	        model.addAttribute("errorMessage", "Không tìm thấy User liên kết với Account này. Vui lòng nhập thông tin mới.");
	    	        return "inforuser2_user"; 
	    	    }


	        model.addAttribute("user", user.get());
	        return "inforuser_user"; 
	    }
	    
	    @PostMapping("/account/infor/{accountuserid}")
	    public String updateUser(@PathVariable("accountuserid") String accountuserid, @ModelAttribute User user, Model model) {
	    try {	
	    	if (user.getId().isEmpty() || user.getFullname().isEmpty() || user.getPhone().isEmpty() || user.getAddress().isEmpty()
	    			|| user.getDepartment().isEmpty() || user.getAccountuserid().isEmpty()) {
		        model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
		        return "inforuser_user";
		    }  
	        User updatedUser = userService.updateUser(accountuserid, user);
	        model.addAttribute("successMessage", "Cập nhật thông tin thành công");
	        return "redirect:/user/accounts";
	    }
	     catch (Exception e) {
	        model.addAttribute("errorMessage", "Phòng ban không tồn tại.");
	        return "inforuser_user";
	    }
	    }
	    
	    @GetMapping("/account/infor2/{accountuserid}")
	  public String checkAccountUser2(@PathVariable String accountuserid, Model model) {
	    	
	    	        return "inforuser2_user"; 
	    	    }

	    @PostMapping("/account/infor2/{accountuserid}")
	    public String createNewUser(@PathVariable String accountuserid, @ModelAttribute User user, Model model) {
	        try {
	       
	            if (user.getId().isEmpty() || user.getFullname().isEmpty() || user.getPhone().isEmpty() || 
	                user.getAddress().isEmpty() || user.getDepartment().isEmpty()) {
	                model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
	                return "inforuser2_user";
	            }
	            if (userService.existsById(user.getId())) {
	                model.addAttribute("errorMessage", "ID đã tồn tại. Vui lòng chọn giá trị khác.");
	                return "inforuser2_user";
	            }

	            if (userService.existsByAccountUserId(user.getAccountuserid())) {
	                model.addAttribute("errorMessage", "Account User ID đã liên kết với User khác.");
	                return "inforuser2_user";
	            }

	            userService.createUser(user);
	            model.addAttribute("successMessage", "Tạo User mới thành công!");
	            return "redirect:/user/accounts"; 

	        } catch (Exception e) {
	            model.addAttribute("errorMessage", "Phòng ban không tồn tại.");
	            return "inforuser2_user";
	        }
	    }

	}


