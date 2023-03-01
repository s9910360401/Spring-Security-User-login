package com.ogabe.user.model;

import lombok.Data;

@Data
public class UserModel {
	
	private String useremail;
	private String userpwd;
	private String username;
	private String usernickname;
	private String useraddress;
	private String usertel;
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsernickname() {
		return usernickname;
	}
	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}
	public String getUseraddress() {
		return useraddress;
	}
	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}
	public String getUsertel() {
		return usertel;
	}
	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}
	public UserModel(String useremail, String userpwd, String username, String usernickname, String useraddress,
			String usertel) {
		super();
		this.useremail = useremail;
		this.userpwd = userpwd;
		this.username = username;
		this.usernickname = usernickname;
		this.useraddress = useraddress;
		this.usertel = usertel;
	}
	public UserModel() {
		super();
	}
	

	
	
}
