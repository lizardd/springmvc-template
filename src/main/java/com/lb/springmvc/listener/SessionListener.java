package com.lb.springmvc.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.lb.springmvc.util.LoginUserUtil;  
  
  
public class SessionListener implements HttpSessionListener{  
  
    @SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(this.getClass());  
      
    public void sessionCreated(HttpSessionEvent event) {  
          
    }  
  
    public void sessionDestroyed(HttpSessionEvent event) {  
    	//在session销毁的时候 把loginUserMap中保存的键值对清除  
        String username= (String) event.getSession().getAttribute("loginUser");
        LoginUserUtil.removeForLoginUserMap(username, event.getSession().getId(), event.getSession().getServletContext());
    }
    
  
}  