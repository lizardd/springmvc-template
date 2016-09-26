package com.lb.springmvc.dao.impl;

import org.springframework.stereotype.Repository;

import com.lb.springmvc.dao.UserDao;
import com.lb.springmvc.dao.base.impl.GenericDaoImpl;
import com.lb.springmvc.model.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao{
	
}
