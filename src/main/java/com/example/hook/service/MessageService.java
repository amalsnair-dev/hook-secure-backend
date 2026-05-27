package com.example.hook.service;

import org.springframework.stereotype.Service;
import com.example.hook.model.User;
import com.example.hook.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import com.example.hook.dto.MessageRequest;
import com.example.hook.dto.MessageResponce;
import com.example.hook.dto.ViewMessageResponse;
import com.example.hook.exception.HookException;
import com.example.hook.model.Messages;
import com.example.hook.model.Token;
import com.example.hook.repository.MessageRepository;
import com.example.hook.repository.TokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class MessageService {
	
	private final MessageRepository messageRepository;
	private final TokenRepository tokenRepository;
	private final EmailService emailService;
	private final UserRepository userRepository;
	
	public MessageService(MessageRepository messageRepository, TokenRepository tokenRepository, EmailService emailService, UserRepository userRepository) {
		this.messageRepository = messageRepository;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
		this.userRepository = userRepository;
	}
	
	public MessageResponce createMessage(MessageRequest request) {
		
//		System.out.println(request.getContent());
		
		Messages messages = new Messages();
		
		messages.setSubject(request.getSubject());
		messages.setContent(request.getContent());
		
		messages.setExpiryTime(LocalDateTime.now().plusHours(request.getExpiryHours()));
		
		messages.setCreatedAt(LocalDateTime.now());
		messages.setUpdatedAt(LocalDateTime.now());
		
		Authentication authentication =
		        SecurityContextHolder
		                .getContext()
		                .getAuthentication();

		String email = authentication.getName();
		
		User user = userRepository.findByEmail(email)
		        .orElseThrow(() ->
		                new HookException("User not found"));
		
		messages.setUser(user);
		
				
		
		Messages savedMessages = messageRepository.save(messages);
		
		String generatedToken = UUID.randomUUID().toString();
		
		Token token = new Token();
		
		token.setToken(generatedToken);
		token.setMessage(savedMessages);
		token.setCreatedAt(LocalDateTime.now());
		
		token.setExpiresAt(savedMessages.getExpiryTime());
		
		tokenRepository.save(token);
		
		String hookUrl = "http://localhost:8081/messages/m/" + generatedToken;
		
		emailService.sendHookEmail(
		        request.getReceiverEmail(),
		        request.getSubject(),
		        hookUrl
		);
		
		return new MessageResponce(
		        savedMessages.getId(),
		        hookUrl
		);
		
	}
	
	public ViewMessageResponse viewMessage(String tokenValue) {
		Token token = tokenRepository.findByToken(tokenValue).orElseThrow(()-> new HookException("Invalid Hook URL"));
		
		System.out.println(token);

		
		Messages messages = token.getMessage();
		
		if(!messages.getIsActive()) {
			throw new HookException("message revoked");			
		}
		
		if(LocalDateTime.now().isAfter(messages.getExpiryTime())) {
			throw new HookException("message expired");
		}
		
		return new ViewMessageResponse(messages.getSubject(), messages.getContent());
	}
	
	public String revokeMessage(Long messageId) {
		
		Authentication authentication =
		        SecurityContextHolder
		                .getContext()
		                .getAuthentication();

		String email = authentication.getName();

		User loggedInUser =
		        userRepository.findByEmail(email)
		        .orElseThrow(() ->
		                new HookException("User not found"));
		
		
		Messages message = messageRepository
				.findById(messageId)
				.orElseThrow(()-> new HookException("Message not found"));
		
		if(!message.getUser().getId()
		        .equals(loggedInUser.getId())) {

		    throw new HookException(
		            "You are not allowed to revoke this message"
		    );
		}
		
		message.setIsActive(false);
		message.setUpdatedAt(LocalDateTime.now());
		
		
		messageRepository.save(message);
		
		return "Message revok Successfully";
		
	}
}
