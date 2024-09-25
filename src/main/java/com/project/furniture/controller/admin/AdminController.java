package com.project.furniture.controller.admin;

import com.project.furniture.response.ApiResponse;
import com.project.furniture.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
public class AdminController {
    private final ProductService productService;

    @GetMapping("/")
    public String getAllProducts1() {
     return "index";
    }

}
