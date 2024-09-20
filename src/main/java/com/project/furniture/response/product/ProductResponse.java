package com.project.furniture.response.product;

import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.model.product.ProuductImage;
import com.project.furniture.response.BaseResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private boolean isActive;
    private Category category;
    private List<ProuductImage> productImages;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse response = ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .isActive(product.isActive())
                .category(product.getCategory())
                .productImages(product.getProductImages())
                .build();
        return response;
    }
}
