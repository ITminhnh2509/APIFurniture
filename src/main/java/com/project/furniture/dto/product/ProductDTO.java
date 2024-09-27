package com.project.furniture.dto.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.ProductImage;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Price must not be empty")
    private Long price;
    private String description;
    private boolean isActive;
    private Category category;
    private Long id;

    private List<ProductImage> productImages;

    public ProductDTO(Long id, String name, Long price, String description, boolean isActive, Category category) {
       this.id=id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
        this.category = category;
    }
}
