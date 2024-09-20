package com.project.furniture.response.category;

import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.response.BaseResponse;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse extends BaseResponse {
    private Long id;
    private String name;
    private List<Product> products;

    public static CategoryResponse fromCategory(Category category) {
        CategoryResponse response = CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .products(category.getProducts())
                .build();
        return response;
    }
}
