package com.project.furniture.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.furniture.model.user.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @JsonProperty("username")
    @NotBlank(message = "Username cannot be blank")
    private String Username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @JsonProperty("retype_password")
    private String retypePassword;
    @NotNull(message="Role id is required")
    private Role role;

}