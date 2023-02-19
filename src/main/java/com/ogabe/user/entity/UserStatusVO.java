package com.ogabe.user.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="user_status")
public class UserStatusVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer statusid;
	@Column
	private String rolename;

	@OneToMany(mappedBy="userstatusvo", cascade=CascadeType.ALL)
	private Set<UserVO> uservo;

	public Integer getStatusid() {
		return statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Set<UserVO> getUservo() {
		return uservo;
	}

	public void setUservo(Set<UserVO> uservo) {
		this.uservo = uservo;
	}

	public UserStatusVO(Integer statusid, String rolename, Set<UserVO> uservo) {
		super();
		this.statusid = statusid;
		this.rolename = rolename;
		this.uservo = uservo;
	}

	public UserStatusVO() {
		super();
	}
	
	
	
}
