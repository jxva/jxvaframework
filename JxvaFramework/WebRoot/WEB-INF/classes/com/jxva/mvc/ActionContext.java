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
 */
package com.jxva.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-07-23 14:05:57 by Jxva
 */
public class ActionContext {
	
	public static final String PAGE_CONTEXT="pageContext";
	public static final String REQUEST="request";
	public static final String RESPONSE="response";
	public static final String SESSION="session";
	public static final String APPLICATION="application";

	private static final String TAG="tag";
	
	private static ThreadLocal<ConcurrentMap<String, Object>> THREADLOCAL = new ThreadLocal<ConcurrentMap<String, Object>>() {
		protected synchronized ConcurrentMap<String, Object> initialValue() {
			return new ConcurrentHashMap<String, Object>();
		}
	};
	
	public static void setContext(HttpServletRequest request,HttpServletResponse response) {
		ConcurrentMap<String,Object> map=THREADLOCAL.get();
		map.put(REQUEST,request);
		map.put(RESPONSE,response);
		final Map<String,Object> context=new HashMap<String,Object>();
		context.put(ActionContext.REQUEST,request);
		context.put(ActionContext.SESSION,request.getSession(true));
		map.put(TAG,context);
	}
	
	public static void removeContext(){
		THREADLOCAL.remove();
	}
	
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest)THREADLOCAL.get().get(REQUEST);
	}
	
	public static HttpServletResponse getResponse() {
		return (HttpServletResponse)THREADLOCAL.get().get(RESPONSE);
	}
	
	public static HttpSession getSession(){
		return getRequest().getSession(true);
	}
	
	public static HttpCookie getCookie(){
		Map<String,Object> map=THREADLOCAL.get();
		return new HttpCookie((HttpServletRequest)map.get(REQUEST),(HttpServletResponse)map.get(RESPONSE));
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getTagContext(){
		return (Map<String,Object>)THREADLOCAL.get().get(TAG);
	}
}
