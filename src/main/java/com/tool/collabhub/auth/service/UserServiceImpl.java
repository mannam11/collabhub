package com.tool.collabhub.auth.service;

import com.tool.collabhub.auth.dto.request.LoginRequest;
import com.tool.collabhub.auth.dto.request.SignupRequest;
import com.tool.collabhub.auth.dto.response.LoginResponse;
import com.tool.collabhub.auth.enums.Role;
import com.tool.collabhub.auth.exception.SignupException;
import com.tool.collabhub.auth.model.RefreshToken;
import com.tool.collabhub.auth.model.User;
import com.tool.collabhub.auth.repository.UserRepository;
import com.tool.collabhub.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static int validityInDays = 7;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void signup(SignupRequest request) {

        SignupRequest.validate(request);

        Optional<User> optionalUser = findByEmail(request.getEmail());

        if(optionalUser.isPresent()){
            throw new SignupException("User with email " + request.getEmail() + " already exists");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(Role.ROLE_USER))
                .createdOn(LocalDateTime.now())
                .build();

        userRepository.save(newUser);

        log.info("User with email : {} signed up successfully", request.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) throws LoginException {

        LoginRequest.validate(request);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(user.getId(),user.getEmail());
        String refreshToken = UUID.randomUUID().toString();

        refreshTokenService.save(refreshToken, user.getId(),validityInDays);

        log.info("Login successful for user with id : {}",user.getId());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public LoginResponse refreshToken(String oldToken) {

        Optional<RefreshToken> optional = refreshTokenService.findByToken(oldToken);

        if(optional.isEmpty()){
            return null;
        }

        RefreshToken stored = optional.get();

        if (stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenService.deleteByToken(oldToken);
            throw new RuntimeException("Refresh token expired");
        }

        User user =findByUserId(stored.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        String newAccessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String newRefreshToken = UUID.randomUUID().toString();

        refreshTokenService.rotate(oldToken, newRefreshToken, stored.getUserId(),validityInDays);

        log.info("New accessToken and refreshToken is created for user with id {}", user.getId());
        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
