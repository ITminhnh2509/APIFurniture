package com.project.furniture.response.user;

import com.project.furniture.model.order.Order;
import com.project.furniture.model.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullname;
    private String phone_number;
    private String address;
    private boolean isActive = true;
    private Date date_of_birth;
    private String username;
    private String password;
    private int role;
    private int facebook_account_id = 0;
    private int google_account_id = 0;
    private List<Order> orderSet;

    public static UserResponse fromUser (User user){
        UserResponse response = UserResponse
                .builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .phone_number(user.getPhone_number())
                .address(user.getAddress())
                .isActive(user.isActive())
                .date_of_birth(user.getDate_of_birth())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .facebook_account_id(user.getFacebook_account_id())
                .google_account_id(user.getGoogle_account_id())
                .orderSet(user.getOrderSet())
                .build();
        return response;
    }
}
