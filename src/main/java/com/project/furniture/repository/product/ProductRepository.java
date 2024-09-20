package com.project.furniture.repository.product;

import com.project.furniture.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
