package com.lb.springmvc.util;

public class FileUtils {
	
	private static final String SYSTEMPATH = new FileUtils().getClass().getResource("/").getFile().toString();
	
	public static String getSystemPath(){
		return SYSTEMPATH.replace("WEB-INF/classes/", "");
	}
	
}
