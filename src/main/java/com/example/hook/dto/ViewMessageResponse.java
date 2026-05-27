package com.example.hook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewMessageResponse {
	
	private String subject;
	private String content;

}
