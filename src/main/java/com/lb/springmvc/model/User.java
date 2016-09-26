package com.lb.springmvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="test_user")
public class User {
	
	/** 用户ID **/
	@Id
	@GeneratedValue(generator = "TEST_USER_SEQUENCE", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "TEST_USER_SEQUENCE", sequenceName = "TEST_USER_SEQUENCE")
	@Column(name = "USER_ID", unique = true, nullable = false)
	private Long userId;
	
	/** 用户名  **/
	@Column(name = "USER_NAME")
	private String userName;
	
	/** 用户密码 **/
	@Column(name = "USER_PASSWORD")
	private String password;
	
	public User() {
	}
	
	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passWord) {
		this.password = passWord;
	}
}
