package com.project.furniture.response.category;

import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.response.BaseResponse;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse extends BaseResponse {
    private Long id;
    private String name;
    private Set<Product> products;

//    public static CategoryResponse fromCategory(Category category) {
//        CategoryResponse response = CategoryResponse.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .products(category.getProducts())
//                .build();
//        return response;
//    }
}
