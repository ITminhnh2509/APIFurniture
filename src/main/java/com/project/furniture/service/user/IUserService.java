package com.project.furniture.service.user;



import com.project.furniture.dto.user.UserDTO;
import com.project.furniture.model.user.User;
import com.project.furniture.service.IService;

import java.util.List;

public interface IUserService  {
    User createUser(User user) throws Exception;
    List<User> getAllUsers();
    User getUserByUsername(String username) throws Exception;
    String login(String username, String password) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User getUserDetailsFromRefreshToken(String freshToken) throws Exception;

}
