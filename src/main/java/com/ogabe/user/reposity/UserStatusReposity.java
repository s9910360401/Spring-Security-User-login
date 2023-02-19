package com.ogabe.user.reposity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ogabe.user.entity.UserStatusVO;

@Repository
public interface UserStatusReposity extends JpaRepository<UserStatusVO, Integer> {

}
