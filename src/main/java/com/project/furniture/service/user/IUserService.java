package com.project.furniture.service.user;



import com.project.furniture.model.user.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    List<User> listAllUsers();
    User getUserByUsername(String username);
    String login(String username, String password);
}
