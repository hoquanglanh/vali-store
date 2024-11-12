package com.example.store.controller;

import com.example.store.model.CartItem;
import com.example.store.model.Product;
import com.example.store.service.CartService;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Model model) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            cartService.addProductToCart(product, quantity);
        }
        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        return "cart";
    }

    @GetMapping
    public String redirectToCartView() {
        return "redirect:/cart/view";
    }

    // Endpoint mới trả về JSON
    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }
}
