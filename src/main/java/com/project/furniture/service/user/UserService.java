package com.project.furniture.service.user;


import com.project.furniture.config.JwtToken;
import com.project.furniture.repository.user.UserRepository;
import com.project.furniture.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    @Override
    public User createUser(User user) throws Exception{
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username is already taken.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

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
}