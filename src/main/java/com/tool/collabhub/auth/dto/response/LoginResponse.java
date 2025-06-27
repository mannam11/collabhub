package com.tool.collabhub.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String accessToken;

    @NonNull
    private String refreshToken;

    public LoginResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
