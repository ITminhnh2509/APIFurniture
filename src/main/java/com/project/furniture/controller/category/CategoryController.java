package com.project.furniture.controller.category;

import com.project.furniture.response.category.CategoryListResponse;
import com.project.furniture.response.category.CategoryResponse;
import com.project.furniture.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/furniture")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
//    @GetMapping("")
//    public ResponseEntity<?> getAllCategories(@RequestParam("page") int page,
//                                                                 @RequestParam("limit") int limit){
//        PageRequest pageRequest=PageRequest.of(
//                page,limit,
//                Sort.by("createdAt").descending()
//        );
//        Page<CategoryResponse> categoryResponsePage=categoryService.getAll(pageRequest);
//        int totalPages=categoryResponsePage.getTotalPages();
//        List<CategoryResponse> responseCategories=categoryResponsePage.getContent();
//        return ResponseEntity.ok(CategoryListResponse.builder()
//                .categoryResponseList(responseCategories)
//                .totalPages(totalPages)
//                .build());
//    }

//    @GetMapping("")
}
