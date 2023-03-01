package com.ogabe.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service("emailService")
public class EmailService {
		
		@Autowired
	    private JavaMailSender javaMailSender;

	    @Async
	    public void sendEmail(SimpleMailMessage email) {
	        javaMailSender.send(email);
	    }
	    
	    @Async
	    public void sendEmail(String email, String resetPasswordLink, String sender, 
	    		String mailSubject, String mailContent) throws MessagingException {
	        MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);
	        
	        helper.setFrom(sender);
	        helper.setTo(email);
	        String subject = mailSubject;
	        String content = mailContent;
	        
	        helper.setSubject(subject);
	        helper.setText(content, true);
	        javaMailSender.send(message);
	    }
}

