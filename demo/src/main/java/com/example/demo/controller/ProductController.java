package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products"; 
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "create_product";  
    }

	    @PostMapping("/create")
	    public String createProduct(@ModelAttribute Product product) {
	    	
	        productService.createProduct(product);
	        return "redirect:/admin/products";  
	    }
	    
	    @GetMapping("/view/{id}")
	    public String viewProduct(@PathVariable("id") Long id, Model model) {
	        Product product = productService.findById(id);
	        model.addAttribute("product", product);
	        return "infor_product";
	    }

	    @GetMapping("/delete/{id}")
	    public String deleteProduct(@PathVariable("id") Long id) {
	        productService.deleteProduct(id);
	        return "redirect:/admin/products";  
	    }

	    @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable("id") long id, Model model) {
	        Product product = productService.getProductById(id).orElse(null);
	        model.addAttribute("product", product);
	        return "edit_product"; 
	    }

	    @PostMapping("/edit/{id}")
	    public String updateAccount(@PathVariable("id") long id, @ModelAttribute Product product, Model model) {
	    	
	        productService.updateProduct(id, product);
	        return "redirect:/admin/products";
	    }
}
