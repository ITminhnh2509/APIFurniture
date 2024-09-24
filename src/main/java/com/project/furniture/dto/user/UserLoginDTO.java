package com.project.furniture.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "username number cannot be blank")
    @JsonProperty("username")
    private String Username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}