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
import com.ogabe.user.ultility.UserVONotFindException;


@Service
public class UserService {
	
	@Autowired
	UserRepository userrepo;
	@Autowired
	UserStatusRepository statusRepo;
	@Autowired
	PasswordEncoder passwodEncoder;
	
	public List <UserVO> getAllUser() {
		
		return userrepo.findAll();
	}
	
	
	public UserVO getUserById(Integer userid) {
		Optional<UserVO> uservo = userrepo.findById(userid);
		if(uservo.isPresent()) {
			return uservo.get();
		}
		return null;
	}
	
	public UserVO getUserByEmail(String useremail) {
		UserVO uservo = userrepo.findByUseremail(useremail);

		return uservo;
	}
	
	public void saveUser(UserVO uservo) {
		userrepo.save(uservo);
	}
	
	public UserVO login(String email, String pwd) {
		UserVO uservo = userrepo.findByUseremail(email);
		
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
	
		userrepo.save(temp);
		
		return temp;
	}
	
	public void adminUserEdit(Integer statusid, UserVO uservo) {
		
		if (statusid == uservo.getUserstatusvo().getStatusid()) {
			userrepo.save(uservo);
		}else {
			uservo.setUserstatusvo(statusRepo.findById(statusid).get());
		}
		
		userrepo.save(uservo);
	}
	
	public void updateResetPassword(String token, String email) throws UserVONotFindException {
		UserVO uservo = userrepo.findByUseremail(email);
		
		if(uservo != null) {
			uservo.setResetPasswordToken(token);
			userrepo.save(uservo);
		}else {
			throw new UserVONotFindException("查無  "+ email + " 此註冊信箱，請確認!!");
		}
	}
	
	public UserVO getUserByResetPasswordToken(String resetPasswordToken) {
		return userrepo.findByResetPasswordToken(resetPasswordToken);
	}
	
	public void resetPassword(UserVO uservo, String newPassword) {
		uservo.setUserpwd(passwodEncoder.encode(newPassword));
		uservo.setResetPasswordToken(null);
		userrepo.save(uservo);
	}

}
