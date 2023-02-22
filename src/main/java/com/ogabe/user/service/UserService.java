package com.ogabe.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ogabe.user.entity.UserStatusVO;
import com.ogabe.user.entity.UserVO;
import com.ogabe.user.model.UserModel;
import com.ogabe.user.repository.UserRepository;
import com.ogabe.user.repository.UserStatusRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository Userrepo;
	@Autowired
	UserStatusRepository statusRepo;
	@Autowired
	PasswordEncoder passwodEncoder;
	
	public List <UserVO> getAllUser() {
		
		return Userrepo.findAll();
	}
	
	
	public UserVO getUserById(Integer userid) {
		Optional<UserVO> uservo = Userrepo.findById(userid);
		if(uservo.isPresent()) {
			return uservo.get();
		}
		return null;
	}
	
	public UserVO getUserByEmail(String useremail) {
		UserVO uservo = Userrepo.findByUseremail(useremail);

		return uservo;
	}
	
	public void saveUser(UserVO uservo) {
		Userrepo.save(uservo);
	}
	
	public UserVO login(String email, String pwd) {
		UserVO uservo = Userrepo.findByUseremail(email);
		
		if (pwd.equals(uservo.getUserpwd())) {
			return uservo;
		}
		return null;
		
	}
	
	public UserVO register(UserModel usermodel) {
		UserVO temp = new UserVO();
		temp.setUseremail(usermodel.getUseremail());
		temp.setUsername(usermodel.getUsername());
		temp.setUserpwd(passwodEncoder.encode(usermodel.getUserpwd()));
		temp.setUsernickname(usermodel.getUsernickname());
		temp.setUsertel(usermodel.getUsertel());
		temp.setUseraddress(usermodel.getUseraddress());
		UserStatusVO status = statusRepo.findById(2).get();
		temp.setUserstatusvo(status);
	
		Userrepo.save(temp);
		
		return temp;
	}
	
	public void adminUserEdit(Integer statusid, UserVO uservo) {
		
		if (statusid == uservo.getUserstatusvo().getStatusid()) {
			Userrepo.save(uservo);
		}else {
			uservo.setUserstatusvo(statusRepo.findById(statusid).get());
		}
		
		Userrepo.save(uservo);
	}
	

}
