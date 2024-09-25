package com.project.furniture.controller.admin;

import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/cate")
public class CateAdminController {
    private final CategoryService categoryService;
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Category category) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(categoryService.save(category))
                .message("add successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(categoryService.getAll())
                .message("get all successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        categoryService.remove(id);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .message("delete successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Category category, BindingResult result) {
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage()).toList();
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(errors)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        Category product1 = categoryService.update(id, category);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(product1)
                .message("update successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }


}
