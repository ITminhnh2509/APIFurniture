package com.project.furniture.service.product;

import com.project.furniture.dto.product.ProductImageDTO;
import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.model.product.ProductImage;
import com.project.furniture.service.IService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService  extends IService<Product,Long> {
//    Page<ProductResponse> getAllProducts(Pageable pageable);
    List<Product> findByName(String name);
    Page<Product> getAll(Pageable pageable);
    List<Product> findByPriceBetween(Long minPrice, Long maxPrice);
    List<Product> findByCategory(Category category);
    List<Product> searchProducts(@NotEmpty String name, @NotEmpty Long minPrice, @NotEmpty Long maxPrice);
//    Page<ProductResponse> searchProducts(@NotEmpty String name, @NotEmpty String minPrice, @NotEmpty String maxPrice, @NotEmpty String category, Pageable pageable);


    List<ProductImage> getAll(Long id);
    ProductImage getProductImageById(Long id);
//    ProductImage create(Long id,ProductImageDTO productImage);
//    ProductImage update(Long id, ProductImageDTO productImage);
    void removeProductImage(Long id);
}
