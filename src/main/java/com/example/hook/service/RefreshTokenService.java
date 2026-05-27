package com.example.hook.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.hook.exception.HookException;
import com.example.hook.model.RefreshToken;
import com.example.hook.model.User;
import com.example.hook.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.refreshTokenRepository =
                refreshTokenRepository;
    }

    // CREATE REFRESH TOKEN

    public RefreshToken createRefreshToken(
            User user
    ) {

        RefreshToken refreshToken =
                new RefreshToken();

        refreshToken.setUser(user);

        refreshToken.setToken(
                UUID.randomUUID().toString()
        );

        refreshToken.setCreatedAt(
                LocalDateTime.now()
        );

        refreshToken.setExpiryDate(
                LocalDateTime.now().plusDays(7)
        );

        return refreshTokenRepository
                .save(refreshToken);
    }

    // FIND TOKEN

    public RefreshToken findByToken(
            String token
    ) {

        return refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new HookException(
                                "Invalid refresh token"
                        ));
    }

    // VERIFY TOKEN EXPIRATION

    public RefreshToken verifyExpiration(
            RefreshToken token
    ) {

        if(token.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(token);

            throw new HookException(
                    "Refresh token expired"
            );
        }

        return token;
    }
    
    public RefreshToken verifyRefreshToken(
            String token
    ) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new HookException(
                                        "Invalid Refresh Token"
                                ));

        if(refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new HookException(
                    "Refresh Token Expired"
            );
        }

        return refreshToken;
    }	

    // DELETE USER TOKENS

    public void deleteByUser(User user) {

        refreshTokenRepository
                .deleteByUser(user);
    }

    // LOGOUT

    public void logout(String refreshToken){

        int deletedRows =
                refreshTokenRepository
                        .deleteToken(refreshToken);

        System.out.println(deletedRows);
    }
}