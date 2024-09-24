package com.project.furniture.service.user;


import com.project.furniture.config.JwtToken;
import com.project.furniture.dto.user.UserDTO;
import com.project.furniture.exception.DataNotFoundException;
import com.project.furniture.exception.PermissionDenyException;
import com.project.furniture.model.user.Role;
import com.project.furniture.repository.user.RoleRepository;
import com.project.furniture.repository.user.UserRepository;
import com.project.furniture.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    @Override
    public User createUser(User user) throws Exception{
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username is already taken.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        return userRepository.save(user);
    }


//    @Override
//    public User createUser(User userDTO) throws Exception {
//        String Username = userDTO.getUsername();
//        if(userRepository.existsByUsername(Username)) {
//            throw new DataNotFoundException("Username " + Username + " already exists");
//        }
//        Role role =roleRepository.findById(userDTO.getRole().getId())
//                .orElseThrow(() -> new DataNotFoundException("Role not found"));
//        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
//            throw new PermissionDenyException("You cannot register an admin account");
//        }
//
//        User newUser = User
//                .builder()
//                .username(userDTO.getUsername())
//                .password(userDTO.getPassword())
//                .build();
//        newUser.setRole(role);
//
//        return userRepository.save(newUser);
//    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) throws Exception{
        return userRepository.findByUsername(username);
    }

    @Override
    public String login(String username, String password) throws Exception{
        User user = userRepository.findByUsername(username);
        if (user==null) {
            throw new Exception("invalid username");
        }

        UsernamePasswordAuthenticationToken authentication = new
                UsernamePasswordAuthenticationToken(user, password, new ArrayList<>());
        authenticationManager.authenticate(authentication);
        return jwtToken.generateToken(user);


    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        return null;
    }

    @Override
    public User getUserDetailsFromRefreshToken(String freshToken) throws Exception {
        return null;
    }
}