package org.example.sourdough.service.products;

import org.example.sourdough.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();
    public Product addProduct(Product product);
    public void deleteProduct(Long id);
    public Product updateProduct(Long id, Product updatedProduct);
    public List<Product> getProductsByCategory(Long categoryId);
    public List<Product> getProductsByAvailability(boolean isAvailable);
    public List<Product> getProductsByFeatured(boolean isFeatured);
    public List<Product> searchProducts(String keyword);
}
