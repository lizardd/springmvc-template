package com.lb.springmvc.dao.base.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lb.springmvc.dao.base.GenericDao;
import com.lb.springmvc.util.GenericsUtils;

/**
 * <code>GenericDaoImpl</code> 泛型DAO接口的实现类
 * 
 * @param <T>
 *            类型参数
 */
@Repository
public class GenericDaoImpl<T> implements GenericDao<T> {

	/**
	 * <code>entityClass</code> 用于获取实现的真实类型
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 由spring注入sessionFactory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取当前Session instance
	 * 
	 * @return session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @Title: getEntityName
	 * @Description: 返回的源代码中的基础类的简单名称 定义泛型方法时，必须在返回值前边加一个
	 *               <T>，来声明这是一个泛型方法，持有一个泛型T，然后才可以用泛型T作为方法的返回值。
	 * @return
	 * @return: String
	 */
	private <E> String getEntityName() {
		return this.entityClass.getSimpleName();
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
		return getSession().createQuery("FROM " + getEntityName()).list();
	}

	/**
	 * 删除一个或者多个实体对象的方法重载(主要用于级联删除)
	 * 
	 * @param entityIds
	 */
	public void delete(Serializable... entityIds) {
		Session session = getSession();
		for (int i = 0; i < entityIds.length; i++) {
			Object object = find(entityIds[i]);
			session.delete(object);
		}
	}

	/**
	 * 删除一个或者多个实体对象
	 * 
	 * @param keyName
	 *            条件名称
	 * @param entityIds
	 *            条件值
	 */
	public void delete(String keyName, Serializable... entityIds) {
		if (entityIds != null && entityIds.length != 0) {
			String hql = "DELETE FROM " + getEntityName() + " WHERE " + keyName + " IN(:ids)";
			Query query = getSession().createQuery(hql);
			query.setParameterList("ids", entityIds);
			query.executeUpdate();
		}
	}

	/**
	 * 通过实体id进行查找
	 * 
	 * @param entityId
	 *            实体id
	 * @return 查找到的实体对象
	 */
	@SuppressWarnings("unchecked")
	public T find(Serializable entityId) {
		if (entityId == null) {
			throw new RuntimeException();
		}
		return (T) getSession().get(this.entityClass, entityId);
	}

	/**
	 * 添加实体对象到数据库中
	 * 
	 * @param entity
	 *            待添加的实体对象
	 */
	public void save(T entity) {
		Session session = getSession();
		session.persist(entity);
		session.flush();
		session.clear();
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public long getCount() {
		return (Long) getSession().createQuery("select count(*) from " + getEntityName() + " o").uniqueResult();
	}

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void update(T entity) {
		Session session = getSession();
		session.merge(entity);
		session.flush();
		session.clear();
	}

	/**
	 * 根据object条件获取全部数据
	 * 
	 * @return 全体数据
	 */
	@SuppressWarnings("unchecked")
	public List<T> getByObject(T object) {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		criteria.add(Example.create(object));
		return (List<T>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getForeignKey() {
		SessionFactory factory = getSessionFactory();
		Map<String, String> map = new HashMap<String, String>();
		AbstractEntityPersister classMetadata = (AbstractEntityPersister) factory.getClassMetadata(this.entityClass);
		String tableNameAll = classMetadata.getTableName().toUpperCase();
		String[] tableNames = tableNameAll.split("\\.");
		if (tableNames.length > 1) {
			String tableName = tableNames[1];
			String hql = "select c.table_name,d.column_name from user_constraints a "
					+ "left join user_constraints c on c.r_constraint_name=a.constraint_name "
					+ "left join user_cons_columns d  on c.constraint_name=d.constraint_name "
					+ "where  a.constraint_type='P'  and  a.table_name='" + tableName + "' order by a.table_name";
			Query query = getSession().createSQLQuery(hql);
			List<Object[]> list = query.list();
			if (list != null) {
				for (Object[] obj : list) {
					if (obj[0] != null && obj[1] != null) {
						map.put(obj[0].toString(), obj[1].toString());
					}
				}
			}
		}
		return map;
	}

	public int hasTableRecords(String tableName, String FK, Serializable... entityIds) {
		String hql = "select " + FK + " from " + tableName + " where " + FK + " in(:ids)";
		Query query = getSession().createSQLQuery(hql);
		query.setParameterList("ids", entityIds);
		if (query.list() != null) {
			return query.list().size();
		} else {
			return 0;
		}
	}
}
