package com.lb.springmvc.service.base;

import java.io.Serializable;
import java.util.List;

import com.lb.springmvc.dao.base.GenericDao;


/**
 * service层顶层接口，封装单表常用操作
 * 
 * @param <T> 实体类型
 * @param <M> 实体的DAO类型
 */
public interface BaseService<T, M extends GenericDao<T>>
{
	
	/**
	 * 获得所有实体类
	 * @return 
	 */
	public List<T> list();
	
	/**
	 * 删除一个或者多个实体对象
	 * 
	 * @param pkName 主键名称
	 * @param entityIds 实体对象
	 */
	public void delete(String pkName, Serializable ... entityIds);
	
	/**
	 * 删除一个或者多个实体对象的方法重载(主要用于级联删除)
	 * @param entityIds
	 */
	public void delete(Serializable ... entityIds);

	/**
	 * 通过实体id进行查找
	 * 
	 * @param entityId 实体id
	 * @return 查找到的实体对象
	 */
	public T find(Serializable entityId);

	/**
	 * 添加实体对象到数据库中
	 * 
	 * @param entity 待添加的实体对象
	 */
	public void add(T entity);

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public long getCount();

	/**
	 * 更新实体对象
	 * 
	 * @param entity 实体对象
	 */
	public void update(T entity);

	
	/**
	 * 根据object里的条件获取所有实体数据
	 * 
	 * @return 实体数据
	 */
	public List<T> getByObject(T object);
}
