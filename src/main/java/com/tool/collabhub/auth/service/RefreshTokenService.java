package com.tool.collabhub.auth.service;

import com.tool.collabhub.auth.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    public void save(String refreshToken, String userId, int validityInDays);
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
    void rotate(String oldToken, String newToken, String userId,int validityInDays);
}
