package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.model.Product;
import com.example.demo.service.AccountService;
import com.example.demo.service.UserService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/admin/viewProduct")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "viewproduct"; 
    }
    @GetMapping("/admin/viewProduct/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "viewinforproduct";
    }


    
	    
	    @GetMapping("/login")
	    public String loginPage() {
	        return "login"; 
	    }

	        @PostMapping("/login")
	        public String login(HttpServletRequest request, Model model) {
	            String username = request.getParameter("name");
	            String password = request.getParameter("pass");
	            Account account = accountRepository.findByName(username);

	            if (account != null && account.getPass().equals(password)) {
	            	
	                HttpSession session = request.getSession();
	                session.setAttribute("loggedUser", account.getName());
	                session.setAttribute("userType", account.getType()); 

	                if ("admin".equals(account.getType())) {
	                    return "redirect:/admin";
	                } else if ("user".equals(account.getType())) {
	                    return "redirect:/user";
	                }
	            }

	            model.addAttribute("error", "Invalid username or password");
	            return "login";  
	        }
	        @PostMapping("/logout")
	        public String logout(HttpServletRequest request) {
	            HttpSession session = request.getSession();
	            session.invalidate();
	            return "redirect:/login"; 
	        }
	    @GetMapping("/admin")
	    public String adminPage(HttpSession session) {
	        String userType = (String) session.getAttribute("userType");

	        if ("admin".equals(userType)) {
	            return "admin"; 
	        }

	        return "redirect:/login"; 
	    }


    @GetMapping("/user")
    public String userPage() {
	            return "user"; 
	
    }
    @GetMapping("/products")
    public String productpage() {
        return "products";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("account", new Account());
        return "create_login";  
    }

	    @PostMapping("/create")
	    public String createAccount(@ModelAttribute Account account, Model model) {
	    	
	    	 if (account.getAccountId().isEmpty() || account.getName().isEmpty() || account.getPass().isEmpty()) {
	    	        model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
	    	        return "create_login";
	    	    }  
	        if (accountService.existsByAccountId(account.getAccountId()) || accountService.existsByAccountName(account.getName())) {
	            
	         model.addAttribute("errorMessage", "Account ID hoặc Account Name đã tồn tại. Vui lòng chọn giá trị khác.");
	            return "create_login"; 
	        }
	        account.setType("User");
	        accountService.createAccount(account);
	        model.addAttribute("congratulation", "Tạo tài khoản thành công");
	        return "login";  
	    }
}
