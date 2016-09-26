package com.lb.springmvc.service;

import com.lb.springmvc.dao.UserDao;
import com.lb.springmvc.model.User;
import com.lb.springmvc.service.base.BaseService;

public interface UserService extends BaseService<User, UserDao> {

	public User findByUserNameAndPassword(String userName,String password);
	
}
