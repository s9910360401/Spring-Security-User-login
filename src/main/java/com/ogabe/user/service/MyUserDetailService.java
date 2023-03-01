package com.ogabe.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ogabe.user.entity.UserVO;
import com.ogabe.user.repository.UserRepository;
import com.ogabe.user.security.UserPrincipal;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO uservo = userRepo.findByUseremail(username);
		if(uservo == null) {
			throw new UsernameNotFoundException("User not find!!");
		}
//		System.out.println(uservo.getUserstatusvo().getRolename());
		
		UserPrincipal userPrincipal = new UserPrincipal(uservo);
		
		return userPrincipal;
	}

}
