package com.project.furniture.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "Username can not be blank")
    private String username;
    @NotBlank(message = "Username can not be blank")
    private String password;
}
