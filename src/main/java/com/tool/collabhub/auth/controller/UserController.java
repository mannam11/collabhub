package com.tool.collabhub.auth.controller;

import com.tool.collabhub.auth.dto.request.LoginRequest;
import com.tool.collabhub.auth.dto.request.SignupRequest;
import com.tool.collabhub.auth.dto.response.LoginResponse;
import com.tool.collabhub.auth.service.UserService;
import com.tool.collabhub.auth.utils.AuthUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static int validityInDays = 7;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request){
        userService.signup(request);
        return new ResponseEntity<>("User with email "+request.getEmail() + " created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {

            LoginResponse response = userService.login(request);

            ResponseCookie cookie = ResponseCookie.from("rToken", response.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .path("/user/refreshToken")
                    .maxAge(Duration.ofDays(validityInDays))
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new LoginResponse(response.getAccessToken()));

        } catch (LoginException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String oldRefreshToken = AuthUtils.extractRefreshTokenFromCookie(request);

        if (oldRefreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token missing");
        }

        LoginResponse res = userService.refreshToken(oldRefreshToken);

        ResponseCookie cookie = ResponseCookie.from("rToken", res.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/user/refreshToken")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(res.getAccessToken()));
    }

}
