package com.tool.collabhub.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.auth.login.LoginException;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;

    public static void validate(LoginRequest request) throws LoginException {
        if(request.getEmail() == null || request.getEmail().trim().isEmpty()){
            throw new LoginException("Email should not be empty");
        }

        if(request.getPassword() == null || request.getPassword().trim().isEmpty()){
            throw new LoginException("Password should not be empty");
        }
    }
}
