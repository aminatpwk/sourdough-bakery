package org.example.sourdough.controller;

import org.example.sourdough.model.Product;
import org.example.sourdough.service.products.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        Product product = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<Product> getProducts(@PathVariable Long categoryId) {
        if(categoryId != null) {
            return productService.getProductsByCategory(categoryId);
        }else{
            return productService.getAllProducts();
        }
    }

    @GetMapping("/availability")
    public List<Product> getProductsByAvailability(@RequestParam boolean available) {
        return productService.getProductsByAvailability(available);
    }

    @GetMapping("/featured")
    public List<Product> getProductsByFeatured(@RequestParam boolean featured) {
        return productService.getProductsByFeatured(featured);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String q){
        return productService.searchProducts(q);
    }
}
