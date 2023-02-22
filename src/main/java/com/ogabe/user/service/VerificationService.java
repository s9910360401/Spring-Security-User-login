package com.ogabe.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogabe.user.entity.VerificationToken;
import com.ogabe.user.repository.VerificationRepository;

@Service
public class VerificationService {
	
	@Autowired
	VerificationRepository verificationRepository;
	
	public void saveToken(VerificationToken verificationToken) {
		verificationRepository.save(verificationToken);
	}

	public VerificationToken findByConfirmationToken(String verificationToken) {
		
		return verificationRepository.findByConfirmationToken(verificationToken);
	}



}
