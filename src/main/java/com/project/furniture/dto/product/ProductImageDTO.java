package com.project.furniture.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.furniture.model.product.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    private Long productId;
    @Size(min = 5, max = 100, message = "Image URL must be between 5 and 100")
    @JsonProperty("image_url")
    private String imageURL;
//    private Product product;

}
