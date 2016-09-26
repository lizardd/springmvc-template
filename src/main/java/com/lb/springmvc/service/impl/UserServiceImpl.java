package com.lb.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lb.springmvc.dao.UserDao;
import com.lb.springmvc.exception.BusinessException;
import com.lb.springmvc.model.User;
import com.lb.springmvc.service.UserService;
import com.lb.springmvc.service.base.impl.BaseServiceImpl;

@Service(value="userService")
public class UserServiceImpl extends BaseServiceImpl<User, UserDao> implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User findByUserNameAndPassword(String userName, String password) {
		List<User> users = userDao.getByObject(new User(userName,password));
		if(users == null || users.isEmpty()){
			return null;
		} else if(users.size()==1){
			return users.get(0);
		} else {
			throw new BusinessException("数据异常，存在 多条 同用户名与密码记录！");
		}
	}
}
