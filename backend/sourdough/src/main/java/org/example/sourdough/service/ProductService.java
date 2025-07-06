package org.example.sourdough.service;

import org.example.sourdough.model.Product;
import org.example.sourdough.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory_id(updatedProduct.getCategory_id());
        existingProduct.setImage_url(updatedProduct.getImage_url());
        existingProduct.setPrep_time_hours(updatedProduct.getPrep_time_hours());
        existingProduct.setIs_available(updatedProduct.isIs_available());
        existingProduct.setIs_featured(updatedProduct.isIs_featured());
        return productRepository.save(existingProduct);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory(categoryId);
    }
    public List<Product> getProductsByAvailability(boolean isAvailable){
        return productRepository.findByAvailability(isAvailable);
    }
}
