package com.ogabe.user.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ogabe.user.entity.UserVO;

@Repository
public interface UserReposity extends JpaRepository<UserVO, Integer> {
	UserVO findByUseremail(String email);
}
