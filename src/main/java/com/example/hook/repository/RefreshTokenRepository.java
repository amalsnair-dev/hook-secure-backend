package com.example.hook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.hook.model.RefreshToken;
import com.example.hook.model.User;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    @Transactional
    @Modifying
    @Query("""
        DELETE FROM RefreshToken rt
        WHERE rt.token = :token
    """)
    int deleteToken(
            @Param("token") String token
    );
}