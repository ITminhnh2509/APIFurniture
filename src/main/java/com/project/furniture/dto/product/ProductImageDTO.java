package com.project.furniture.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.furniture.model.product.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @NotEmpty(message = "Insert image is empty")
    @Size(min = 5, max = 100, message = "Image URL must be between 5 and 100")
    @JsonProperty("image_url")
    private String image_url;
//    private Product product;
    @JsonProperty("product_id")
    private Long productId;
}
