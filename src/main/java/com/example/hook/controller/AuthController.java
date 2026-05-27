package com.example.hook.controller;

import com.example.hook.service.RefreshTokenService;
import jakarta.validation.Valid;
import com.example.hook.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hook.dto.ApiResponse;
import com.example.hook.dto.LoginRequest;
import com.example.hook.dto.LoginResponse;
import com.example.hook.dto.RefreshRequest;
import com.example.hook.dto.RefreshResponse;
import com.example.hook.dto.RegisterRequest;
import com.example.hook.dto.LogoutRequest;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
	}
	
	@PostMapping("/register")

	public ApiResponse<String> register(

	        @Valid
	        @RequestBody RegisterRequest request
	) {

	    String response = authService.register(request);

	    return new ApiResponse<>(
	            true,
	            "Success",
	            response
	    );
	}
	
	@PostMapping("/login")

	public ApiResponse<LoginResponse> login(

	        @Valid
	        @RequestBody LoginRequest request
	) {

	    LoginResponse responce = authService.login(request);

	    return new ApiResponse<>(
	            true,
	            "Login Successful",
	            responce
	    );
	}
	
	@PostMapping("/refresh")

	public ApiResponse<RefreshResponse> refreshToken(

	        @Valid
	        @RequestBody RefreshRequest request
	) {

	    RefreshResponse response =
	            authService.refreshToken(request);

	    return new ApiResponse<>(
	            true,
	            "Access Token Refreshed",
	            response
	    );
	}

	@PostMapping("/logout")

	public ApiResponse<String> logout(

			@RequestBody LogoutRequest request
	) {


		System.out.println(request.getRefreshToken());

		refreshTokenService.logout(
				request.getRefreshToken()
		);

		return new ApiResponse<>(
				true,
				"Logout Successful",
				null
		);
	}
}
