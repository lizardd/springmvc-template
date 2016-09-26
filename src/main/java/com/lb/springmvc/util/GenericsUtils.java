package com.lb.springmvc.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型工具类
 * 
 * @author libo
 *
 */
public class GenericsUtils {
	/**
	 * 通过反射,获得指定类的父类的泛型参数的实际类型. 如UserService extends BaseService<User>
	 * 
	 * @param clazz
	 *            需要反射的类,该类必须继承范型父类
	 * @param index
	 *            泛型参数所在索引,从0开始.
	 * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Class getSuperClassGenricType(Class clazz, int index) {
		// 得到泛型父类
		Type genType = clazz.getGenericSuperclass();
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
		// DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 通过反射,获得指定类的父类的第一个泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承泛型父类
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 获取一个对象上指定类型的所有域的集合
	 * 
	 * @param object
	 *            指定对象
	 * @param type
	 *            指定类型
	 * @return 域的集合
	 */
	public static List<Field> getFieldsByType(Object object, Class<?> type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 在指定对象上获取指定域
	 * 
	 * @param object
	 *            指定对象
	 * @param fieldName
	 *            域名
	 * @return 指定类型域
	 */
	public static Object getObjectByField(Object object, String fieldName) {
		Object result = null;
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			// 保存它的访问标识符
			boolean accessible = field.isAccessible();
			// 如何为私有，将其设置为可以访问
			field.setAccessible(true);
			// 获取域
			result = field.get(object);
			// 设置访问标识符
			field.setAccessible(accessible);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
