package com.lb.springmvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lb.springmvc.dao.UserDao;
import com.lb.springmvc.model.User;
import com.lb.springmvc.service.UserService;

@Service(value="userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User findByUserNameAndPassword(String userName, String password) {
		return (User) userDao.getSession()
				.createQuery("from User o where o.userName =:userName and o.password =:password")
				.setString("userName", userName)
				.setString("password", password)
				.uniqueResult();
	}
	
	public long getUsersCount() {
		return userDao.getCount();
	}
	
}
