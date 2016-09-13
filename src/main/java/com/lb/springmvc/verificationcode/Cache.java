package com.lb.springmvc.verificationcode;

import java.util.HashMap;
import java.util.Map;

public final class Cache {
	private static Map<String,ImageResult> cache = new HashMap<String, ImageResult>();
	
	public static void put(String note,ImageResult ir){
		cache.put(note, ir);
	}
	
	public static ImageResult get(String note){
		return cache.get(note);
	}
	
	public static void remove(String note){
		cache.remove(note);
	}
}
