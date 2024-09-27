package com.project.furniture.controller.product;

import com.project.furniture.dto.product.ProductImageDTO;
import com.project.furniture.model.category.Category;
import com.project.furniture.model.product.Product;
import com.project.furniture.model.product.ProductImage;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.response.product.ProductListResponse;
import com.project.furniture.response.product.ProductResponse;
import com.project.furniture.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("api/furniture")
@CrossOrigin(origins = "http://localhost:3001")
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
//@GetMapping("")
//public ResponseEntity<ProductListResponse> getAllProduct(
//        @RequestParam("page") int page,
//        @RequestParam("limit") int limit
//){
//    Pageable pageRequest=PageRequest.of(
//            page,limit,
//            Sort.by("createdAt").descending()
//    );
//
//    Page<ProductResponse> productPage=productService.getAll(pageRequest);
//    int totalPages=productPage.getTotalPages();
//    List<ProductResponse> responseProducts=productPage.getContent();
//    return ResponseEntity.ok(ProductListResponse.builder()
//            .productResponseList(responseProducts)
//            .totalPages(totalPages)
//            .build());
//}
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
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/price")
    public ResponseEntity<?> getProductByPrice(@RequestParam Long minPrice, @RequestParam Long maxPrice) {
        List<Product> products = productService.findByPriceBetween(minPrice, maxPrice);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(products)
                .status(HttpStatus.OK.value())
                .message("get successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/category")
    public ResponseEntity<?> getProductByCategory(@RequestParam Category category) {
        List<Product> products = productService.findByCategory(category);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(products)
                .message("get successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String name, @RequestParam Long minPrice, @RequestParam Long maxPrice) {
        List<Product> products = productService.searchProducts(name, minPrice, maxPrice);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(products)
                .message("get successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
//    @PostMapping("/uploadimage/{id}")
//    public ResponseEntity<?> saveProductImage(@PathVariable Long id,
//                                              @ModelAttribute("files") List<MultipartFile> files) throws IOException {
//        List<ProductImage> productImages = new ArrayList<>();
//        int count = 0;
//        for (MultipartFile file : files) {
//            if(file!=null){
//                if(file.getSize()==0){
//                    count++;
//                    continue;
//                }
//                String filename = storeFile(file);
//                ProductImageDTO productImagedto = ProductImageDTO.builder()
//                        .image_url(filename)
//                        .build();
//                ProductImage productImage = productService.create(id, productImagedto);
//                productImages.add(productImage);
//            }
//        }
//
//        if(count==1){
//            throw new IllegalArgumentException("File is empty");
//        }
//        ApiResponse apiResponse = ApiResponse.builder()
//                .data(productImages)
//                .message("upload successful")
//                .status(HttpStatus.OK.value())
//                .build();
//        return ResponseEntity.ok(apiResponse);
//    }
//    private String storeFile(MultipartFile file) throws IOException {
//
//        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
//
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;
//
//        Path uploadDir = Paths.get("upload");
//
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectories(uploadDir);
//        }
//        Path filePath = Paths.get(uploadDir.toString(),uniqueFileName);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        return uniqueFileName;
//    }
@PostMapping("/uploadimage/{id}")
public ResponseEntity<?> saveProductImage(@PathVariable("id") Long id,
                                          @RequestParam("files") List<MultipartFile> files) throws IOException {
    if (files == null || files.isEmpty()) {
        throw new IllegalArgumentException("No files uploaded");
    }
    System.out.println("Received files count: " + files.size());

    List<ProductImage> productImages = new ArrayList<>();
    int count = 0;

    for (MultipartFile file : files) {
        if (file.isEmpty()) {
            count++;
            continue;
        }
        String filename = storeFile(file); // Store file and return its name
        ProductImageDTO productImageDTO = ProductImageDTO.builder()
                .imageURL(filename)
                .build();
        ProductImage productImage = productService.create(id, productImageDTO);
        productImages.add(productImage);
    }

    if (count == files.size()) {
        throw new IllegalArgumentException("All uploaded files are empty");
    }

    ApiResponse apiResponse = ApiResponse.builder()
            .data(productImages)
            .message("Upload successful")
            .status(HttpStatus.OK.value())
            .build();
    return ResponseEntity.ok(apiResponse);

}

    private String storeFile(MultipartFile file) throws IOException {

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

        Path uploadDir = Paths.get("upload");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path filePath = Paths.get(uploadDir.toString(),uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @GetMapping("/getimages/{imageName}")
    public ResponseEntity<?> getStudentImage(@PathVariable("imageName") String imageName) {
        try {
            Path filePath = Paths.get("upload/"+imageName);
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
            else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/images/{id}")
    public ResponseEntity<?> getProductImageById(@PathVariable Long id) {
        List<ProductImage> productImages = productService.getAllImage(id); // Implement this service method
        if (productImages.isEmpty()) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .data(null)
                    .message("No data found")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        ApiResponse apiResponse = ApiResponse.builder()
                .data(productImages)
                .message("Data found")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
//    @GetMapping()
//    public ResponseEntity<?>
}
