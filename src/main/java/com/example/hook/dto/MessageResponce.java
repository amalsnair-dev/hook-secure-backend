package com.example.hook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponce {
	
	private Long messageId;
	
	private String hookUrl;

}
