package com.example.store.controller;

import com.example.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "checkout";
    }

    @PostMapping("/order/place")
    public String placeOrder(@RequestParam String customerName,
                             @RequestParam String customerEmail,
                             @RequestParam String shippingAddress) {
        orderService.placeOrder(customerName, customerEmail, shippingAddress);
        return "redirect:/products";
    }
}

