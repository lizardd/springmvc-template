package com.lb.springmvc.service.base.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lb.springmvc.dao.base.GenericDao;
import com.lb.springmvc.exception.BusinessException;
import com.lb.springmvc.service.base.BaseService;
import com.lb.springmvc.util.GenericsUtils;

/**
 * <code>BaseServiceImpl</code> service顶层接口实现类
 * 
 * @param <T>
 *            类型参数
 * @param <M>
 *            实体的DAO类型
 */
@Transactional
public class BaseServiceImpl<T, M extends GenericDao<T>> implements BaseService<T, M> {

	/**
	 * 获取dao的真实类型
	 * 
	 * @return 真实类型
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public M getGeniricDao() {
		// 获取M的真实类型
		Class clazz = GenericsUtils.getSuperClassGenricType(getClass(), 1);
		// 获取BaseServiceImpl中类型为M的所有域
		List<Field> fields = GenericsUtils.getFieldsByType(this, clazz);
		// 根据域名获取域
		return (M) GenericsUtils.getObjectByField(this, fields.get(0).getName());
	}

	/**
	 * 删除一个或者多个实体对象
	 * @param pkName
	 *            主键名称
	 * @param entityIds
	 *            实体对象
	 */
	public void delete(String pkName, Serializable... entityIds) throws BusinessException {
		if (foreignHaveRecord(entityIds)) {
			getGeniricDao().delete(pkName, entityIds);
		} else {
			throw new BusinessException("删除错误:包含子记录");
		}
	}

	/**
	 * 删除一个或者多个实体对象的方法重载(主要用于级联删除)
	 * 
	 * @param entityIds
	 */
	public void delete(Serializable... entityIds) throws BusinessException {
		if (foreignHaveRecord(entityIds)) {
			getGeniricDao().delete(entityIds);
		} else {
			throw new BusinessException("删除错误:包含子记录");
		}
	}

	/**
	 * 通过实体id进行查找
	 * 
	 * @param entityId
	 *            实体id
	 * @return 查找到的实体对象
	 */
	public T find(Serializable entityId) {
		return getGeniricDao().find(entityId);
	}

	/**
	 * 添加实体对象到数据库中
	 * 
	 * @param entity
	 *            待添加的实体对象
	 */
	public void add(T entity) {
		getGeniricDao().save(entity);
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public long getCount() {
		return getGeniricDao().getCount();
	}

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void update(T entity) {
		getGeniricDao().update(entity);
	}

	public List<T> getByObject(T object) {
		return getGeniricDao().getByObject(object);
	}

	public List<T> list() {
		return getGeniricDao().list();
	}

	public Boolean foreignHaveRecord(Serializable... entityIds) {
		Map<String, String> map = getGeniricDao().getForeignKey();
		for (String str : map.keySet()) {
			int index = getGeniricDao().hasTableRecords(str, map.get(str), entityIds);
			if (index > 0) {
				return false;
			}
		}
		return true;
	}

}
