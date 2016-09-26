package com.lb.springmvc;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lb.springmvc.dao.UserDao;
import com.lb.springmvc.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext-common.xml")
public class DBTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/*@SuppressWarnings({ "unchecked" })
	@Test
	public void selectBySql(){
		List<Object[]> list = getSession().createSQLQuery("select * from test_user").list();
		if(list!=null && list.size()>0){
			for(Object[] o:list){
				System.out.println(Arrays.toString(o));
			}
		}
	}*/
	
	/*@SuppressWarnings({ "unchecked" })
	@Test
	public void selectByHql(){
		List<User> users= getSession().createQuery("from User").list();
		if(users!=null && users.size()>0){
			for(User o:users){
				System.out.println("username:" + o.getUserName());
			}
		}
	}*/
	
	
	@Autowired
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Test
	public void selectByHql2(){
		User query=new User();
		query.setUserId(1l);
		query.setUserName("test2");
		query.setPassword("test");
		List<User> users= userDao.getByObject(query);
		if(users!=null && users.size()>0){
			for(User u:users){
				System.out.println(u.getUserName());
			}
		} else {
			System.out.println(" users is null");
		}
	}
}
