package com.lb.springmvc.controller;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lb.springmvc.model.User;
import com.lb.springmvc.service.UserService;
import com.lb.springmvc.util.LoginUserUtil;
import com.lb.springmvc.verificationcode.Cache;
import com.lb.springmvc.verificationcode.Image;
import com.lb.springmvc.verificationcode.ImageResult;


@Controller
public class IndexController {
	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Resource(name="userService")  
    private UserService userService;  
	
	@RequestMapping(value = "toLogin")
	public String toLogin(Model model,HttpServletRequest request,HttpServletResponse response,String msg) {
		model.addAttribute("msg",msg);
		try {
			ImageResult ir = Image.generateImage();
			model.addAttribute("file",ir.getName());
			model.addAttribute("tip",ir.getTip());
			Cache.put(ir.getUniqueKey(),ir);
			Cookie cookie = new Cookie("note",ir.getUniqueKey());
			response.addCookie(cookie);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "login";
	}
	
	@RequestMapping(value = "logout")
	public String logout(Model model,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null){
			String username = (String) session.getAttribute("loginUser");
			LoginUserUtil.removeForLoginUserMap(username, request.getSession(false).getId(), request.getSession().getServletContext());
			session.removeAttribute("loginUser");
		}
		return "redirect:/toLogin.action";
	}

	@RequestMapping(value = "login")
	public String login(Model model, HttpServletRequest request, String username,String password,String location) {
		HttpSession session = request.getSession(false);
		if(session == null ){
			model.addAttribute("msg", "异常，请重新登录！");
			return "redirect:/toLogin.action";
		}
		
		Cookie[] cookies = request.getCookies();
		Cookie note = null;
		for(Cookie cookie : cookies){
			if(Objects.equals(cookie.getName(), "note")){
				note = cookie;
				break;
			}
		}
		if(null == note){
			model.addAttribute("msg", "验证码错误！");
			return "redirect:/toLogin.action";
		}
		ImageResult ir = Cache.get(note.getValue());
		if(null == location || "".equals(location) || ir == null || !validate(location,ir)){
			model.addAttribute("msg", "验证码错误！");
			Cache.remove(note.getName());
			return "redirect:/toLogin.action";
		}
		
		boolean isExist = LoginUserUtil.isExistLoginUserMap(username, session.getId(), session.getServletContext());

		if (isExist) {
			model.addAttribute("msg", "抱歉，该用户已登录！");
			return "redirect:/toLogin.action";
		} else {
			// 该用户没有登录
			User user = userService.findByUserNameAndPassword(username, password);
			if(user == null){
				model.addAttribute("msg", "用户名或密码错误！");
				return "redirect:/toLogin.action";
			} else {
				request.getSession(false).setAttribute("loginUser", username);
				model.addAttribute("msg", "登录成功！");
				logger.info("用户："+username+"登录成功！");
			}
		}
		model.addAttribute("username", username);
		return "redirect:/index.action";
	}
	
	private boolean validate(String locationString, ImageResult ir) {
		String[] resultArray = locationString.split(";");
		int[][] array = new int[resultArray.length][2];
		for(int i = 0;i < resultArray.length;i++){
			String[] temp = resultArray[i].split(",");
			array[i][0] = Integer.parseInt(temp[0]) + 150 + 10;
			array[i][1] = Integer.parseInt(temp[1]) + 10;
		}
		for(int i = 0;i<array.length;i++){
			int location = location(array[i][1],array[i][0]);
			if(!ir.getKeySet().contains(location)){
				return false;
			}
		}
		return true;
	}

	private static int location(int x, int y) {
		if(y >= 0 && y < 75){
			return xLocation(x);
		} else if(y > 75 && y <= 150){
			return xLocation(x) + 4;
		} else {
			 return -1;
		}
	}

	private static int xLocation(int x) {
		if(x >=0 && x < 75){
			return 0;
		} else if(x >= 75 && x < 150){
			return 1;
		} else if(x >= 150 && x < 225){
			return 2;
		} else if(x >= 225 && x <= 300){
			return 3;
		} else {
			return -1;
		}
	}

	@RequestMapping(value = "index")
	public String index(Model model, HttpServletRequest request, String username,String msg) {
		if(request.getSession(false).getAttribute("loginUser")==null){
			model.addAttribute("msg", "未登录！");
			return "redirect:/toLogin.action";
		}
		model.addAttribute("username", username);
		model.addAttribute("msg", msg);
		return "index";
	}
}
