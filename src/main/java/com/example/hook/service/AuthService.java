package com.example.hook.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hook.dto.LoginRequest;
import com.example.hook.dto.LoginResponse;
import com.example.hook.dto.RefreshRequest;
import com.example.hook.dto.RefreshResponse;
import com.example.hook.dto.RegisterRequest;
import com.example.hook.exception.HookException;
import com.example.hook.model.RefreshToken;
import com.example.hook.model.User;
import com.example.hook.repository.UserRepository;
import com.example.hook.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService,
            BCryptPasswordEncoder encoder,
            RefreshTokenService refreshTokenService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.refreshTokenService =
                refreshTokenService;
    }

    public String register(RegisterRequest request) {

        if(userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            throw new HookException("Email already exist");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                encoder.encode(request.getPassword())
        );

        user.setRole("ROLE_USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(
            LoginRequest request
    ) {

        User user = userRepository.findByEmail(
                request.getEmail())
                .orElseThrow(() ->
                        new HookException(
                                "Invalid Email"
                        ));

        if(!encoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new HookException(
                    "Invalid Password"
            );
        }

        // ACCESS TOKEN

        String accessToken =
                jwtService.generateToken(
                        user.getEmail()
                );

        // REFRESH TOKEN

        String refreshToken =
                refreshTokenService
                        .createRefreshToken(user)
                        .getToken();

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getRole(),
                user.getEmail()
        );
    }
    
    public RefreshResponse refreshToken(

            RefreshRequest request
    ) {

        RefreshToken refreshToken =
                refreshTokenService
                        .verifyRefreshToken(
                                request.getRefreshToken()
                        );

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new RefreshResponse(
                accessToken
        );
    }
}