package com.project.furniture.controller.admin;

import com.project.furniture.dto.product.ProductImageDTO;
import com.project.furniture.model.product.Product;

import com.project.furniture.model.product.ProductImage;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/product")
public class ProductAdminController {
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
    private final String UPLOAD_DIR = "uploads/";


@PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<ApiResponse> uploads3(@PathVariable Long id, @ModelAttribute("files") MultipartFile[] files) throws IOException {
    List<ProductImage> productImages = new ArrayList<>();
    int emptyFileCount = 0;

    for (MultipartFile file : files) {
        if (file != null && file.getSize() > 0) {
            String fileName = storeFile(file);
            ProductImageDTO productImageDTO = ProductImageDTO.builder()
                    .image_url(fileName)
                    .build();
            ProductImage productImage = productService.saveProductImage(id, productImageDTO);
            productImages.add(productImage);
        } else {
            emptyFileCount++;
        }
    }

    if (emptyFileCount == files.length) {
        throw new IllegalArgumentException("Không có file nào được chọn để tải lên.");
    }

    ApiResponse apiResponse = ApiResponse.builder()
            .data(productImages)
            .status(HttpStatus.OK.value())
            .message("Upload thành công")
            .build();

    return ResponseEntity.ok(apiResponse);
}
    private String storeFile(MultipartFile file) throws IOException{
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName= UUID.randomUUID().toString()+"-"+fileName;
        Path uploadDir= Paths.get("upload");
        if(!Files.exists(uploadDir))
            Files.createDirectory(uploadDir);
        Path destination=Paths.get(uploadDir.toString(),uniqueFileName);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }


}
