package com.tool.collabhub.auth.dto.request;

import com.tool.collabhub.auth.exception.SignupException;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String fullName;

    @Email
    private String email;
    private String password;

    public static void validate(SignupRequest request){

        if(request.getEmail() == null || request.getEmail().trim().isEmpty()){
            throw new SignupException("Email should not be empty");
        }

        if(request.getPassword() == null || request.getPassword().trim().isEmpty()){
            throw new SignupException("Password should not be empty");
        }

        if(request.getFullName() == null || request.getFullName().trim().isEmpty()){
            throw new SignupException("Name should not be empty");
        }

    }

}
