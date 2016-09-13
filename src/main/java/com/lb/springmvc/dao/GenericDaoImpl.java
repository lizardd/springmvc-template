package com.lb.springmvc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <code>GenericDaoImpl</code> 泛型DAO接口的实现类
 * 
 * @param <T> 类型参数
 */
@Repository
public class GenericDaoImpl<T> implements GenericDao<T>
{

	/**
	 * <code>entityClass</code> 用于获取实现的真实类型
	 */
	//protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	/**
	 * 由spring注入sessionFactory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取当前Session instance
	 * 
	 * @return session
	 */
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public long getCount()
	{
		return (Long) getSession().createQuery("select count(*) from User o").uniqueResult();
	}

	
}
