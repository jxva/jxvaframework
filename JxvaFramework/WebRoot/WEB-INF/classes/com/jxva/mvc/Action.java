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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 业务处理类接口,mvc.xml配置文件中预定义操作时指定的处理类 <br>
 * 针对一个或多个业务操作,需要request,response的支持就要继承这个类 <br>
 * <b>子类常用的方法名如下:</b><br><br>
 * Views:
 * 		viewObject,addObject,editObject<br><br>
 * Services:
 * 		removeObject,updateObject,saveObject,showObject,listObject,searchObject<br>
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:42:06 by Jxva
 */
public abstract class Action {

	public static final String SUCCESS="success";
	public static final String ERROR="error";
	public static final String LOGIN="login";
	public static final String INPUT="input";
	public static final String NONE="none";
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected Form form;

	/**
	 * 由Controller控制中心自动初始化
	 * @param request
	 * @param response
	 */
	public Action init(final HttpServletRequest request,final HttpServletResponse response){
		this.request=request;
		this.response=response;
		this.session=request.getSession(true);
		this.form=new Form(request);
		return this;
	}
		
	/**
	 * 默认的执行方法,必须由子类实现
	 * @return 执行结果,通常返回SUCCESS常量
	 */
	public abstract String execute();	
}

