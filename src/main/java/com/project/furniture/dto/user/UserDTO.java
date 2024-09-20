package com.project.furniture.dto.user;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullname;
    private String phone_number;
    private String address;
    private boolean isActive = true;
    private Date date_of_birth;
    private int role;
    private int facebook_account_id = 0;
    private int google_account_id = 0;
}
