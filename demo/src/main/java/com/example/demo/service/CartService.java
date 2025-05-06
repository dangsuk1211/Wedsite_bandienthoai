
package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public boolean addToCart(Long productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            return false;
        }

        Product product = productOptional.get();

        if (product.getStock() < quantity) {
            return false; 
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        Cart cartItem = cartRepository.findByProductId(productId)
                .orElse(new Cart(product, 0));
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartRepository.save(cartItem);

        return true;
    }

    public List<Cart> getCartItems() {
        return cartRepository.findAll();
    }
    public boolean removeFromCart(Long productId) {
    Optional<Cart> cartItemOptional = cartRepository.findByProductId(productId);

    if (cartItemOptional.isPresent()) {
        Cart cartItem = cartItemOptional.get();
        Product product = cartItem.getProduct();
        product.setStock(product.getStock() + cartItem.getQuantity());
        productRepository.save(product);

        cartRepository.delete(cartItem);

        return true;
    }
    return false; 
}

public boolean increaseQuantity(Long productId) {
    Optional<Cart> cartItemOptional = cartRepository.findByProductId(productId);
    if (cartItemOptional.isPresent()) {
        Cart cartItem = cartItemOptional.get();
        Product product = cartItem.getProduct();
        if (product.getStock() > 0) { // Kiểm tra tồn kho
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            product.setStock(product.getStock() - 1); 
            productRepository.save(product);
            cartRepository.save(cartItem);
            return true;
        }
    }
    return false;
}

public boolean decreaseQuantity(Long productId) {
    Optional<Cart> cartItemOptional = cartRepository.findByProductId(productId);
    if (cartItemOptional.isPresent()) {
        Cart cartItem = cartItemOptional.get();
        Product product = cartItem.getProduct();
        int currentQuantity = cartItem.getQuantity();
        if (currentQuantity > 1) {
            cartItem.setQuantity(currentQuantity - 1);
            product.setStock(product.getStock() + 1); 
            productRepository.save(product);
            cartRepository.save(cartItem);
        } else {
            cartRepository.delete(cartItem);
            product.setStock(product.getStock() + 1);
            productRepository.save(product);
        }
        return true;
    }
    return false;
}

}
