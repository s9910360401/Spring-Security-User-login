package com.ogabe.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ogabe.user.entity.UserStatusVO;
import com.ogabe.user.entity.UserVO;
import com.ogabe.user.model.UserModel;
import com.ogabe.user.reposity.UserReposity;
import com.ogabe.user.reposity.UserStatusReposity;


@Service
public class UserService {
	
	@Autowired
	UserReposity repo;
	@Autowired
	UserStatusReposity statusRepo;
	@Autowired
	PasswordEncoder passwodEncoder;
	
	public List <UserVO> getAllUser() {
		
		return repo.findAll();
	}
	
	
	public UserVO getUserById(Integer userid) {
		Optional<UserVO> uservo = repo.findById(userid);
		if(uservo.isPresent()) {
			return uservo.get();
		}
		return null;
	}
	
	public UserVO getUserByEmail(String useremail) {
		UserVO uservo = repo.findByUseremail(useremail);

		return uservo;
	}
	
	public void saveUser(UserVO uservo) {
		repo.save(uservo);
	}
	
	public UserVO login(String email, String pwd) {
		UserVO uservo = repo.findByUseremail(email);
		
		if (pwd.equals(uservo.getUserpwd())) {
			return uservo;
		}
		return null;
		
	}
	
	public void register(UserModel usermodel) {
		UserVO temp = new UserVO();
		temp.setUseremail(usermodel.getUseremail());
		temp.setUsername(usermodel.getUsername());
		temp.setUserpwd(passwodEncoder.encode(usermodel.getUserpwd()));
		temp.setUsernickname(usermodel.getUsernickname());
		temp.setUsertel(usermodel.getUsertel());
		temp.setUseraddress(usermodel.getUseraddress());
		UserStatusVO status = statusRepo.findById(2).get();
		temp.setUserstatusvo(status);
	
		repo.save(temp);
	}
	
	public void adminUserEdit(Integer statusid, UserVO uservo) {
		
		if (statusid == uservo.getUserstatusvo().getStatusid()) {
			repo.save(uservo);
		}else {
			uservo.setUserstatusvo(statusRepo.findById(statusid).get());
		}
		
		repo.save(uservo);
	}
	

}
