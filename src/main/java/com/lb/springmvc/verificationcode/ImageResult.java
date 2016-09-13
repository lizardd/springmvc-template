package com.lb.springmvc.verificationcode;

import java.util.Set;

public class ImageResult {
	
	private String name;//文件名
	private Set<Integer> keySet;
	private String uniqueKey;//唯一码
	private String tip;//验证图片名（需要选中的图片）
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Integer> getKeySet() {
		return keySet;
	}
	public void setKeySet(Set<Integer> keySet) {
		this.keySet = keySet;
	}
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniquKey) {
		this.uniqueKey = uniquKey;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
}
