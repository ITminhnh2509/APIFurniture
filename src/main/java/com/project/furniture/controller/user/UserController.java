package com.project.furniture.controller.user;

import com.project.furniture.model.user.User;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/list")
    public ResponseEntity<?> getAllUser(){
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(userService.listAllUsers())
                .message("Get all users")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/login")
    public String login(){
        return "User Login";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()) {
            List<String> listErro = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            ApiResponse apiResponse = ApiResponse
                    .builder()
                    .data(listErro)
                    .message("Validation failed")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
            return ResponseEntity.badRequest().body(apiResponse);
        }
        User user1 = userService.createUser(user);
        ApiResponse apiResponse = ApiResponse
                .builder()
                .data(user1)
                .status(HttpStatus.OK.value())
                .message("Create user Success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
