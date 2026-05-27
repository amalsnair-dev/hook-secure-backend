package com.example.hook.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
	private final JavaMailSender mailsender;
	
	public EmailService(JavaMailSender mailsender) {
		this.mailsender = mailsender;
	}
	
	public void sendHookEmail(
			String toEmail,
			String subject,
			String hookURL
			) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText("You receive a Hook message.\n\n" + "Open securely:\n" + hookURL);
		
		mailsender.send(message);
	}
}
