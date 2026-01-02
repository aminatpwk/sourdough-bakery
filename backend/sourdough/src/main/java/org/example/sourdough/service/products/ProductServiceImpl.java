package org.example.sourdough.service.products;

import org.example.sourdough.exception.InvalidOperationException;
import org.example.sourdough.exception.ResourceNotFoundException;
import org.example.sourdough.model.Product;
import org.example.sourdough.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        validateProduct(updatedProduct);
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
        if(categoryId == null) {
            throw new InvalidOperationException("Category ID cannot be null");
        }
        return productRepository.findByCategory(categoryId);
    }

    public List<Product> getProductsByAvailability(boolean isAvailable){
        return productRepository.findByAvailability(isAvailable);
    }

    public List<Product> getProductsByFeatured(boolean isFeatured){
        return productRepository.findByFeatured(isFeatured);
    }

    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidOperationException("Search keyword cannot be empty");
        }
        return productRepository.searchByKeyword(keyword.trim());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidOperationException("Product name is required");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException("Product price must be greater than zero");
        }

        if (product.getCategory_id() == null) {
            throw new InvalidOperationException("Product category is required");
        }

        if (product.getPrep_time_hours() < 0) {
            throw new InvalidOperationException("Preparation time cannot be negative");
        }
    }
}
