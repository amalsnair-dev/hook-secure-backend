package com.example.hook.dto;

import lombok.Data;

@Data
public class MessageRequest {
	private String subject;
	private String content;
	private Integer expiryHours;
	
	private String receiverEmail;

}
