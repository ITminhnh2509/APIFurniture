package com.project.furniture.model.user;

import com.project.furniture.model.BaseEntity;
import com.project.furniture.model.category.Category;
import com.project.furniture.model.order.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    private String username;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName().toUpperCase()));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
//public class User implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 100, nullable = false)
//    private String fullname;
//
//
//    @Column(length = 10, nullable = false)
//    private String phone_number;
//
//
//    @Column(length = 100, nullable = false)
//    private String address;
//
//    private boolean isActive = true;
//
//    private Date date_of_birth;
//
//    @Column(unique = true)
//    private String username;
//
//    @Column(length = 100, nullable = false)
//    private String password;
//
//    private int role;
//
//    private int facebook_account_id = 0;
//    private int google_account_id = 0;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Order> orderSet;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        String nameRole = "";
//        if(role == 0){
//            nameRole = "ADMIN";
//        } else {
//            nameRole = "USER";
//        }
//        return List.of(new SimpleGrantedAuthority(nameRole.toUpperCase()));
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
