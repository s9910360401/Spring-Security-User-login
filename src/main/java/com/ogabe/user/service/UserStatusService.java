package com.ogabe.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ogabe.user.entity.UserStatusVO;
import com.ogabe.user.repository.UserStatusRepository;

@Service
public class UserStatusService {
	
	@Autowired
	UserStatusRepository userUserStatusRepository;

	public UserStatusVO findById(Integer statusid) {
		
		return userUserStatusRepository.findById(statusid).get();
	}


}
