package com.example.hook.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hook.dto.ApiResponse;
import com.example.hook.dto.MessageRequest;
import com.example.hook.dto.MessageResponce;
import com.example.hook.dto.ViewMessageResponse;
import com.example.hook.model.Messages;
import com.example.hook.service.MessageService;


@RestController
@RequestMapping("/messages")
public class MessageController {
	
	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping
	public ApiResponse<MessageResponce> createMessage(
	        @RequestBody MessageRequest request
	) {

	    MessageResponce response =
	            messageService.createMessage(request);

	    return new ApiResponse<>(
	            true,
	            "Message created successfully",
	            response
	    );
	}
	
	@GetMapping("/m/{token}")
	public ApiResponse<ViewMessageResponse> viewMessage(
	        @PathVariable String token
	) {

	    ViewMessageResponse response =
	            messageService.viewMessage(token);

	    return new ApiResponse<>(
	            true,
	            "Message fetched successfully",
	            response
	    );
	}
	
	
	@PutMapping("/revoke/{id}")
	public ApiResponse<String> revokeMessage(
	        @PathVariable Long id
	) {

	    String response =
	            messageService.revokeMessage(id);

	    return new ApiResponse<>(
	            true,
	            response,
	            null
	    );
	}
}
