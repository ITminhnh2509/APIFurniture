package com.project.furniture.dto.product;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.ProuductImage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private List<ProuductImage> productImages;


}
