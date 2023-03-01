package com.ogabe.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ogabe.user.entity.UserVO;

@Repository
public interface UserRepository extends JpaRepository<UserVO, Integer> {
	UserVO findByUseremail(String email);
	UserVO findByResetPasswordToken(String token);
}
