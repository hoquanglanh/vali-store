    package com.example.store.controller;

    import com.example.store.model.Product;
    import com.example.store.repository.ProductRepository;
    import com.example.store.service.ProductService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.http.ResponseEntity;
    import org.springframework.http.HttpStatus;

    import java.util.List;

    @Controller
    @RequestMapping("/products")
    public class ProductController {
        @Autowired
        private ProductService productService;

        @Autowired
        private ProductRepository repo;

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

        // Endpoint POST: Thêm sản phẩm mới qua API
        @PostMapping("/api")
        @ResponseBody
        public ResponseEntity<?> createProduct(@RequestBody Product newProduct) {
            try {
                // Validate dữ liệu đầu vào
                if (newProduct.getName() == null || newProduct.getName().isEmpty()) {
                    return new ResponseEntity<>("Product name is required", HttpStatus.BAD_REQUEST);
                }

                Product savedProduct = repo.save(newProduct);
                return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error creating product: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Endpoint PUT: Cập nhật sản phẩm theo ID
        @PutMapping("/api/{id}")
        @ResponseBody
        public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
            Product existingProduct = productService.getProductById(id);
            if (existingProduct != null) {
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setPrice(updatedProduct.getPrice());
                existingProduct.setQuantity(updatedProduct.getQuantity());
                existingProduct.setCategory(updatedProduct.getCategory());
                existingProduct.setDescription(updatedProduct.getDescription());
                return repo.save(existingProduct);
            } else {
                throw new RuntimeException("Product with ID " + id + " not found.");
            }
        }

        // Endpoint DELETE: Xóa sản phẩm theo ID
        @DeleteMapping("/api/{id}")
        @ResponseBody
        public String deleteProduct(@PathVariable Long id) {
            Product existingProduct = productService.getProductById(id);
            if (existingProduct != null) {
                repo.deleteById(id);
                return "Product with ID " + id + " has been deleted successfully.";
            } else {
                throw new RuntimeException("Product with ID " + id + " not found.");
            }
        }

        // Hiển thị tất cả sản phẩm
        @GetMapping({"", "/products"})
        public String ShowProductList(Model model) {
            List<Product> products = repo.findAll();
            model.addAttribute("products", products);
            // Thêm danh sách categories để hiển thị trong view
            model.addAttribute("categories", Product.ProductCategory.values());
            return "products";
        }

        // Hiển thị sản phẩm theo category
        @GetMapping("/category/{category}")
        public String showProductsByCategory(@PathVariable Product.ProductCategory category, Model model) {
            List<Product> products = repo.findByCategory(category);
            model.addAttribute("products", products);
            model.addAttribute("categories", Product.ProductCategory.values());
            model.addAttribute("currentCategory", category);
            return "products";
        }
    }