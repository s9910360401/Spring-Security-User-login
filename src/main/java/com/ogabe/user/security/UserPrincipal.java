package com.ogabe.user.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ogabe.user.entity.UserStatusVO;
import com.ogabe.user.entity.UserVO;

public class UserPrincipal implements UserDetails{
	
	UserVO uservo;
	UserStatusVO statusvo;
	
	public UserPrincipal(UserVO uservo) {
		super();
		this.uservo = uservo;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singleton(new SimpleGrantedAuthority(uservo.getUserstatusvo().getRolename()));
	}
	
	public UserVO getUservo() {
		return uservo;
	}

	public void setUservo(UserVO uservo) {
		this.uservo = uservo;
	}

	@Override
	public String getPassword() {
		
		return uservo.getUserpwd();
	}

	@Override
	public String getUsername() {
		
		return uservo.getUseremail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
