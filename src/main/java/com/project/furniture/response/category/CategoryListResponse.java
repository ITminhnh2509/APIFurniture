package com.project.furniture.response.category;

import com.project.furniture.response.category.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class CategoryListResponse {
    private List<CategoryResponse> categoryResponseList;
    private int totalPages;

}
