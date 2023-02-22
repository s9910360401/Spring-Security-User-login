package com.ogabe.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ogabe.user.entity.UserStatusVO;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatusVO, Integer> {

}
