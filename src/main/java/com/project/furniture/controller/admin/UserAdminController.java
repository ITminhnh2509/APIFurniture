package com.project.furniture.controller.admin;

import com.project.furniture.response.ApiResponse;
import com.project.furniture.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/user")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> index() {
        ApiResponse apiResponse=ApiResponse.builder()
                .data(userService.getAllUsers())
                .message("get all successfully")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

}
