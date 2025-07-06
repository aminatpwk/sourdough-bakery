package org.example.sourdough.repository;

import org.example.sourdough.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category_id = :categoryId")
    List<Product> findByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.is_available = :isAvailable")
    List<Product> findByAvailability(@Param("isAvailable") boolean isAvailable);

    @Query("SELECT p FROM Product p WHERE p.is_featured = :isFeatured")
    List<Product> findByFeatured(@Param("isFeatured") boolean isFeatured);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);
}
