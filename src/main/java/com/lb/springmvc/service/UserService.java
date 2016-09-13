package com.lb.springmvc.service;

import com.lb.springmvc.model.User;

public interface UserService {

	public User findByUserNameAndPassword(String userName,String password);
	
	public long getUsersCount(); 
}
