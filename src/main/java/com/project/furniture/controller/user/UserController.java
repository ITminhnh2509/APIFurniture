package com.project.furniture.controller.user;

import com.project.furniture.dto.user.RefreshTokenDTO;
import com.project.furniture.dto.user.UserDTO;
import com.project.furniture.dto.user.UserLoginDTO;
import com.project.furniture.model.user.Token;
import com.project.furniture.model.user.User;
import com.project.furniture.response.ApiResponse;
import com.project.furniture.response.user.LoginResponse;
import com.project.furniture.service.token.TokenService;
import com.project.furniture.service.user.IUserService;
import com.project.furniture.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    @GetMapping("/list")
    public ResponseEntity<?> getAllUser(@RequestHeader("Authorization") String token) {
        log.debug("Authorization header: " + token); ApiResponse apiResponse = ApiResponse
                .builder()
                .data(userService.getAll())
                .message("Get all users")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try{
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errors);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Register successfull"+userDTO);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private boolean isMobileDevice(String userAgent) {
        // Kiểm tra User-Agent header để xác định thiết bị di động
        // Ví dụ đơn giản:
        return userAgent.toLowerCase().contains("mobile");
    }

    @PostMapping("/login1")
    public ResponseEntity<String> login1(
            @Valid @RequestBody UserLoginDTO userLoginDTO){
        return ResponseEntity.ok("login 1 success");
    }

    @PostMapping("/login2")
    public ResponseEntity<String> login2(){
        return ResponseEntity.ok("login 2 success");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO
    ) {
        try{
            String token = userService.login(userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword());
            // String userAgent=request.getHeader("User-Agent");
            String userAgent="abc";
            User user=userService.getUserDetailsFromToken(token);
            Token jwtToken=tokenService.addToken(user,token,isMobileDevice(userAgent));
            // Trả về token trong response
            return ResponseEntity.ok(
                    LoginResponse.builder()
                            .message("login success")
                            .token(jwtToken.getToken())
                            .refreshToken(jwtToken.getRefreshToken())
                            .tokenType(jwtToken.getTokenType())
                            .username(user.getUsername())
                            .roles(user.getAuthorities().stream().map(
                                    item->item.getAuthority()).toList())
                            .id(user.getId())
                            .build()
            );
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
        // .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
//                    .token(jwtToken.getToken())
//                    .tokenType(jwtToken.getTokenType())
//                    .refreshToken(jwtToken.getRefreshToken())
//                    .username(userDetail.getUsername())
//                    .roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
//                    .id(userDetail.getId())
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO
    ) {

        try {
            User user = userService.getUserDetailsFromRefreshToken(refreshTokenDTO.getRefreshToken());
            Token jwtToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), user);
            // Trả về token trong response
            return ResponseEntity.ok(
                    LoginResponse.builder()
                            .message("login success")
                            .token(jwtToken.getToken())
                            .refreshToken(jwtToken.getRefreshToken())
                            .tokenType(jwtToken.getTokenType())
                            .username(user.getUsername())
                            .roles(user.getAuthorities().stream().map(
                                    item -> item.getAuthority()).toList())
                            .id(user.getId())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @DeleteMapping("/login")
    public ResponseEntity<String> loginDelete() {
        try{
            return ResponseEntity.ok().body("delete successfully, some token");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
//@RestController
//    @RequestMapping("/api/user")
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService userService;

//
//    @GetMapping("/login")
//    public String login(){
//        return "User Login";
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result){
//        if (result.hasErrors()) {
//            List<String> listErro = result.getFieldErrors()
//                    .stream()
//                    .map(fieldError -> fieldError.getDefaultMessage())
//                    .toList();
//            ApiResponse apiResponse = ApiResponse
//                    .builder()
//                    .data(listErro)
//                    .message("Validation failed")
//                    .status(HttpStatus.BAD_REQUEST.value())
//                    .build();
//            return ResponseEntity.badRequest().body(apiResponse);
//        }
//        User user1 = userService.createUser(user);
//        ApiResponse apiResponse = ApiResponse
//                .builder()
//                .data(user1)
//                .status(HttpStatus.OK.value())
//                .message("Create user Success")
//                .build();
//        return ResponseEntity.ok().body(apiResponse);
//    }
//}
