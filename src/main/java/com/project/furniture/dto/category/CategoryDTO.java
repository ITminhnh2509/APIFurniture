package com.project.furniture.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Category name can not be empty")
    private String name;
}
