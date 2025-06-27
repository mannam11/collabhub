package com.tool.collabhub.auth.service;

import com.tool.collabhub.auth.dto.request.LoginRequest;
import com.tool.collabhub.auth.dto.request.SignupRequest;
import com.tool.collabhub.auth.dto.response.LoginResponse;
import com.tool.collabhub.auth.model.User;

import javax.security.auth.login.LoginException;
import java.util.Optional;

public interface UserService {
    public void signup(SignupRequest request);
    public LoginResponse login(LoginRequest request) throws LoginException;
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUserId(String userId);
    public LoginResponse refreshToken(String oldToken);
}
