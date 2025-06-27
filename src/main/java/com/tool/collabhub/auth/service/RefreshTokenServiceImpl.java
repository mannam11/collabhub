package com.tool.collabhub.auth.service;

import com.tool.collabhub.auth.model.RefreshToken;
import com.tool.collabhub.auth.repository.RefreshTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void save(String refreshToken, String userId,int validityInDays) {

        RefreshToken token = RefreshToken.builder()
                .refreshToken(refreshToken)
                .createdOn(LocalDateTime.now())
                .userId(userId)
                .expiresAt(LocalDateTime.now().plusDays(validityInDays))
                .build();

        refreshTokenRepository.save(token);

        log.info("RefreshToken created for user with id : {}",token.getUserId());
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByRefreshToken(token);

        log.info("RefreshToken deleted {}", token);
    }

    @Override
    public void rotate(String oldToken, String newToken, String userId,int validityInDays) {
        deleteByToken(oldToken);
        save(newToken, userId,validityInDays);

        log.info("Old token {} rotated with new token {}",oldToken,newToken);
    }
}
