package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/accounts")
public class AddController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getAllAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts";  
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Account account = accountService.getAccountById(id).orElse(null);
        model.addAttribute("account", account);
        return "edit_account"; 
    }

    @PostMapping("/edit/{id}")
    public String updateAccount(@PathVariable("id") String id, @ModelAttribute Account account, Model model) {

        if (account.getAccountId().isEmpty() || account.getName().isEmpty() || account.getPass().isEmpty()) {
            model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
            return "edit_account"; 
        }
        
        Account existingAccount = accountService.getAccountById(id).orElse(null);
        if (existingAccount == null) {
            model.addAttribute("errorMessage", "Tài khoản không tồn tại.");
            return "edit_account"; 
        }
        
        existingAccount.setAccountId(account.getAccountId());
        existingAccount.setName(account.getName());
        existingAccount.setPass(account.getPass());
        existingAccount.setType(account.getType());
        
        if (!existingAccount.getAccountId().equals(account.getAccountId())) {
            model.addAttribute("errorMessage", "Không thể thay đổi Account ID.");
            return "edit_account"; 
        }
        
        if (!existingAccount.getName().equals(account.getName()) && accountService.existsByAccountName(account.getName())) {
            model.addAttribute("errorMessage", "Account Name đã tồn tại. Vui lòng chọn giá trị khác.");
            return "edit_account"; 
        }
        // Proceed with updating the account if validation passes
        accountService.updateAccount(id, account);
        return "redirect:/admin/accounts";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("account", new Account());
        return "create_account_admin";  
    }

	    @PostMapping("/create")
	    public String createAccount(@ModelAttribute Account account, Model model) {
	    	
	    	 if (account.getAccountId().isEmpty() || account.getName().isEmpty() || account.getPass().isEmpty()) {
	    	        model.addAttribute("errorMessage", "Các trường không được để trống. Vui lòng nhập đầy đủ thông tin.");
	    	        return "create_account";
	    	    }  
	        if (accountService.existsByAccountId(account.getAccountId()) || accountService.existsByAccountName(account.getName())) {
	            
	         model.addAttribute("errorMessage", "Account ID hoặc Account Name đã tồn tại. Vui lòng chọn giá trị khác.");
	            return "create_account_admin"; 
	        }
	        accountService.createAccount(account);
	        model.addAttribute("congratulation", "Tạo tài khoản thành công");
	        return "login";  
	    }
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") String id) {
        accountService.deleteAccount(id);
        return "redirect:/admin/accounts";  
    }
}
