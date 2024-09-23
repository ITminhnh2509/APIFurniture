package com.project.furniture.repository.product;

import com.project.furniture.model.product.Product;
import com.project.furniture.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findByNameContaining(String name);
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> findByPriceBetween(Long minPrice, Long maxPrice);
    @Query("SELECT p FROM Product p WHERE p.category = ?1")
    List<Product> findByCategory(Category category);
    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:category IS NULL OR p.category = :category)")
    List<Product> searchProducts(String name, Long minPrice, Long maxPrice, String category);
}
