/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jxva.mvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpCookie {
		
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	//private String domain	="127.0.0.1";
	private int maxage		=Integer.MAX_VALUE;
	private String path		="/";
	
	public HttpCookie(HttpServletRequest request,HttpServletResponse response){
		this.request=request;
		this.response=response;
	}
	
	//public void setDomain(String domain) {
	//	this.domain = domain;
	//}


	public void setMaxage(int maxage) {
		this.maxage = maxage;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	private void setValue(String name,String value,int maxage){
	    Cookie cookie = new Cookie(name,value);
		//cookie.setDomain(domain);
		cookie.setMaxAge(maxage);
		cookie.setValue(value);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
	
	/**
	 * 判断是否存在cookie
	 * @return
	 */
	public boolean hasExist(){
		return getCookies()!=null;
	}
	
	/**
	 * 判断单个cookie是否已经存在
	 * @param name
	 * @return
	 */
	public boolean hasExist(String name){
		return getValue(name)!=null;
	}
	
	/**
	 * 获取cookie
	 * @param name
	 * @return 存在:返回值 不存在:返回null
	 */
	public String getValue(String name){
		Cookie[] cookies = getCookies();
		if(cookies!=null){
			for (int i = 0; i <cookies.length; i++) {       
				if(cookies[i].getName().equals(name)){
					return cookies[i].getValue();
		        }
		    }
		}
		return null;
	}
	

	
	/**
	 * 增加cookie
	 * @param name
	 * @param value
	 */
	public void setValue(String name,String value){
		setValue(name,value,maxage);
	}
	
	/**
	 * 删除cookie
	 * @param name
	 */
	public void remove(String name){
		setValue(name,null,0);
	}
	
	/**
	 * 清除所有cookie
	 */
	public void clear(){
		Cookie[] cookies = getCookies();
		if(cookies!=null){
			for (int i = 0; i <cookies.length; i++) {    
				remove(cookies[i].getName());
		    }
		}
	}
	
	/**
	 * 获取所有Cookie
	 * @return
	 */
	public Cookie[] getCookies(){
		return request.getCookies();
	}

}
