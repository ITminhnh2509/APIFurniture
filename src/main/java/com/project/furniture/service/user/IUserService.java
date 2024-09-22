package com.project.furniture.service.user;



import com.project.furniture.dto.user.UserDTO;
import com.project.furniture.model.user.User;
import com.project.furniture.service.IService;

import java.util.List;

public interface IUserService extends IService<User, Long> {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password) throws  Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User getUserDetailsFromRefreshToken(String freshToken) throws Exception;
}
