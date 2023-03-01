package com.ogabe.user.entity;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="user02")
public class UserVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_ID")
	private Integer userid;
	
	@Column(name="user_email")
	private String useremail;
	
	@Column(name="user_pwd")
	private String userpwd;
	
	@Column(name="user_name")
	private String username;
	
	@Column(name="user_nickname")
	private String usernickname;
	
	@Column(name="user_address")
	private String useraddress;
	
	@Column(name="user_tel")
	private String usertel;
	
	@Column(name="user_vip_level_id")
	private Integer viplevelid = 1;
	
	@CreationTimestamp
	@Column(name="user_datetime", nullable = false, updatable = false, insertable = false)
	private Date userregidate;
	
    @ManyToOne
    @JoinColumn(name="statusid")
	private UserStatusVO userstatusvo;
	
	@Column(name="user_pic")
	@Lob
	private byte[] userpic;
	
	@Column(name="reset_password_token")
	private String resetPasswordToken;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

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

	public Integer getViplevelid() {
		return viplevelid;
	}

	public void setViplevelid(Integer viplevelid) {
		this.viplevelid = viplevelid;
	}

	public Date getUserregidate() {
		return userregidate;
	}

	public void setUserregidate(Date userregidate) {
		this.userregidate = userregidate;
	}

	public UserStatusVO getUserstatusvo() {
		return userstatusvo;
	}

	public void setUserstatusvo(UserStatusVO userstatusvo) {
		this.userstatusvo = userstatusvo;
	}

	public byte[] getUserpic() {
		return userpic;
	}

	public void setUserpic(byte[] userpic) {
		this.userpic = userpic;
	}
	
	

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}



	public UserVO(Integer userid, String useremail, String userpwd, String username, String usernickname,
			String useraddress, String usertel, Integer viplevelid, Date userregidate, UserStatusVO userstatusvo,
			byte[] userpic, String resetPasswordToken) {
		super();
		this.userid = userid;
		this.useremail = useremail;
		this.userpwd = userpwd;
		this.username = username;
		this.usernickname = usernickname;
		this.useraddress = useraddress;
		this.usertel = usertel;
		this.viplevelid = viplevelid;
		this.userregidate = userregidate;
		this.userstatusvo = userstatusvo;
		this.userpic = userpic;
		this.resetPasswordToken = resetPasswordToken;
	}

	public UserVO() {
		super();
	}
	
	
	
}	
	
	
	
