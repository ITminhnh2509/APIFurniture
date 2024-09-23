package com.project.furniture.service.product;

import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.model.product.ProuductImage;
import com.project.furniture.response.product.ProductResponse;
import com.project.furniture.service.IService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService  extends IService<Product,Long> {
//    Page<ProductResponse> getAllProducts(Pageable pageable);
    List<Product> findByName(String name);
    List<Product> findByPriceBetween(Long minPrice, Long maxPrice);
    List<Product> findByCategory(Category category);
    List<Product> searchProducts(@NotEmpty String name, @NotEmpty Long minPrice, @NotEmpty Long maxPrice, @NotEmpty String category);
//    Page<ProductResponse> searchProducts(@NotEmpty String name, @NotEmpty String minPrice, @NotEmpty String maxPrice, @NotEmpty String category, Pageable pageable);
}
