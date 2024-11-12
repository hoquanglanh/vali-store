package com.example.store.controller;

import com.example.store.model.Product;
import com.example.store.repository.ProductRepository;
import com.example.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Endpoint trả về JSON cho tất cả sản phẩm
    @GetMapping("/api")
    @ResponseBody
    public List<Product> getAllProductsApi() {
        return productService.getAllProducts();
    }

    // Endpoint trả về JSON cho sản phẩm theo id
    @GetMapping("/api/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    
    @Autowired
    private ProductRepository repo;
    @GetMapping({"", "/products"})
    public String ShowProductList(Model model) {
        List<Product> products = repo.findAll();//Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("products", products);
        return "products";
    }
}
