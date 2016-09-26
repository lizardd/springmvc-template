package com.lb.springmvc.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;

/**
 * 泛型DAO接口
 * 
 * @author Administrator
 * @param <T>
 */
public interface GenericDao<T> {

	public SessionFactory getSessionFactory();
	
	/**
	 * 获得所有实体类
	 * 
	 * @return
	 */
	public List<T> list();

	/**
	 * 删除一个或者多个实体对象的方法重载(主要用于级联删除)
	 * 
	 * @param entityIds
	 */
	public void delete(Serializable... entityIds);

	/**
	 * 删除一个或者多个实体对象
	 * @param keyName
	 *            条件名称
	 * @param entityIds
	 *            条件值
	 */
	public void delete(String keyName, Serializable... entityIds);
	
	/**
	 * 通过实体id进行查找
	 * @param entityId
	 *            实体id
	 * @return 查找到的实体对象
	 */
	public T find(Serializable entityId) throws RuntimeException;

	/**
	 * 添加实体对象到数据库中
	 * @param entity
	 *            待添加的实体对象
	 */
	public void save(T entity);

	/**
	 * 获取总记录数
	 * @return 总记录数
	 */
	public long getCount();
	
	/**
	 * 更新实体对象
	 * @param entity
	 *            实体对象
	 */
	public void update(T entity);
	
	/**
	 * 根据object条件获取全部数据
	 * @return 全体数据
	 */
	public List<T> getByObject(T object);
}
