package com.example.hook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hook.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(HookException.class)
	public ResponseEntity<Map<String, Object>> handleHookException(
			HookException ex
			){
		Map<String, Object> error = new HashMap<>();
		
		error.put("timestamp", LocalDateTime.now());
		error.put("message", ex.getMessage());
		error.put("status",403);
		
		return new ResponseEntity<>(
				error,
				HttpStatus.FORBIDDEN
				);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)

	public ResponseEntity<ApiResponse<Map<String, String>>>
	handleValidationException(
	        MethodArgumentNotValidException ex
	) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult()
	            .getFieldErrors()
	            .forEach(error ->

	                errors.put(
	                        error.getField(),
	                        error.getDefaultMessage()
	                )
	            );

	    return new ResponseEntity<>(

	            new ApiResponse<>(
	                    false,
	                    "Validation Failed",
	                    errors
	            ),

	            HttpStatus.BAD_REQUEST
	    );
	}
	
}
