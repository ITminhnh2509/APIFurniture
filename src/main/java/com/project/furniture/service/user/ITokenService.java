package com.project.furniture.service.user;

import com.project.furniture.model.user.Token;
import com.project.furniture.model.user.User;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken,User user) throws Exception;
}