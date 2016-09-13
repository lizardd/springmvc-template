package com.lb.springmvc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class LoginUserUtil {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(LoginUserUtil.class);

	@SuppressWarnings("unchecked")
	public synchronized static void removeForLoginUserMap(String userName, String sessionId, ServletContext sc) {
		if (userName != null && sessionId != null) {
			Map<String, String> loginUserMap = (Map<String, String>) sc.getAttribute("loginUserMap");
			if (loginUserMap != null && sessionId.equals(loginUserMap.get(userName))) {
				loginUserMap.remove(userName);
				//logger.info("注销用户：" + userName);
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(df.format(new Date())+" 注销用户：" + userName);
				sc.setAttribute("loginUserMap", loginUserMap);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized static boolean isExistLoginUserMap(String userName, String sessionId,ServletContext sc) {
		// 所有的登录信息
		Map<String, String> loginUserMap = (Map<String, String>) sc.getAttribute("loginUserMap");
		boolean isExist = false;
		
		if (loginUserMap == null) {
			loginUserMap = new HashMap<String, String>();
		}
		
		for (String name : loginUserMap.keySet()) {
			// 判断是否已经保存该登录用户的信息，是否为同一个用户进行重复登录
			if (name.equals(userName) && !loginUserMap.get(name).equals(sessionId)) {
				isExist = true;
				break;
			}
			//TODO:
			if(!name.equals(userName) && loginUserMap.get(name).equals(sessionId)){
				loginUserMap.remove(name);
			}
		}

		if (isExist) {
			
		} else {// 该用户没有登录
			loginUserMap.put(userName, sessionId);
			sc.setAttribute("loginUserMap", loginUserMap);
		}
		return isExist;
	}

}
