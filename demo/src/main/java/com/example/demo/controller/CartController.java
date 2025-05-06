
package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, RedirectAttributes redirectAttributes) {
        boolean success = cartService.addToCart(productId, quantity);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không đủ tồn kho hoặc lỗi xảy ra!");
        }
        return "redirect:/user/cart";
    }
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        boolean success = cartService.removeFromCart(productId);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm trong giỏ hàng!");
        }
        return "redirect:/user/cart";
    }
    @PostMapping("/increase")
    public String increaseQuantity(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        boolean success = cartService.increaseQuantity(productId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Tăng số lượng sản phẩm thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể tăng số lượng sản phẩm!");
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/decrease")
    public String decreaseQuantity(@RequestParam Long productId, RedirectAttributes redirectAttributes) {
        boolean success = cartService.decreaseQuantity(productId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "Giảm số lượng sản phẩm thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể giảm số lượng sản phẩm!");
        }
        return "redirect:/user/cart";
    }


    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        return "cart"; 
    }
}
