package com.project.furniture.controller.product;

import com.project.furniture.model.product.Product;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.response.product.ProductListResponse;
import com.project.furniture.response.product.ProductResponse;
import com.project.furniture.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/furniture")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(productService.getAll())
                .message("get all successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> products = productService.getAll(pageable);
        ProductListResponse productListResponse = ProductListResponse
                .builder()
                .productResponseList(products.getContent())
                .totalPages(products.getTotalPages())
                .build();
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(productListResponse)
                .message("get all successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(productService.save(product))
                .message("add successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.remove(id);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .message("delete successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product, BindingResult result) {
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
        Product product1 = productService.update(id, product);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(product1)
                .message("update successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name) {

        List<Product> products = productService.findByName(name);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(products)
                .message("get successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/price")
    public ResponseEntity<?> getProductByPrice(@RequestParam Long minPrice, @RequestParam Long maxPrice) {
        List<Product> products = productService.findByPriceBetween(minPrice, maxPrice);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(products)
                .message("get successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
//    @GetMapping("/category")
//    public ResponseEntity<?> getProductByCategory(@RequestParam String category) {
//        List<Product> products = productService.findByCategory(category);
//        ApiResponse apiResponse = ApiResponse
//                .builder()
//                .data(products)
//                .message("get successfully")
//                .build();
//        return ResponseEntity.ok(apiResponse);
//    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String name, @RequestParam Long minPrice, @RequestParam Long maxPrice, @RequestParam String category) {
        List<Product> products = productService.searchProducts(name, minPrice, maxPrice, category);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(products)
                .message("get successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
