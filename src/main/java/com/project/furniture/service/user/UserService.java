package com.project.furniture.service.user;


import com.project.furniture.model.user.User;
import com.project.furniture.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    @Override
    public List<User> listAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public String login(String username, String password) {
        return "Login successfully";
    }
}
