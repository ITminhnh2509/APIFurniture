package com.project.furniture.dto.product;

import com.project.furniture.model.product.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    @NotEmpty(message = "Insert image is empty")
    private String image_url;
    private Product product;
}
