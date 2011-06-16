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
package com.jxva.plugin;

import com.jxva.dao.DAOFactory;
import com.jxva.log.Logger;
import com.jxva.util.UtilException;
import com.jxva.xml.Element;

/**
 * 系统核心配置信息,用来装载jxva.xml中的配置信息<br>
 * 以在应用启动时进行初始化<br>
 * jxva.xml文档内容如下:
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;jxva&gt;	
 *   &lt;sysconfig debug="true"&gt;
 *     &lt;error-page&gt;/pub/error.htm&lt;/error-page&gt;	
 *     &lt;resubmit-page&gt;/pub/resubmit.htm&lt;/resubmit-page&gt;
 *   &lt;/sysconfig&gt;
 *		
 *   &lt;mvc use-dao="true"&gt;
 *     &lt;invoke-class&gt;default&lt;/invoke-class&gt;
 *     &lt;modules&gt;
 *       &lt;module remark="jxva-mvc"&gt;jxva-mvc.xml&lt;/module&gt;
 *     &lt;/modules&gt;
 *   &lt;/mvc&gt;
 *		
 *   &lt;!-- 非必须配置,如果你不使用单点登录服务器,可以将此段删除 --&gt;
 *   &lt;ssoserver&gt;
 *   &lt;!-- 单点登录服务器地址 --&gt;
 *     &lt;server&gt;http://sso.jxva.com/&lt;/server&gt;
 *     &lt;!-- 服务器验证类,必须实现com.jxva.sso.SSOInvoke接口 --&gt;
 *     &lt;sso-invoke&gt;com.jxva.sso.SSOImpl&lt;/sso-invoke&gt;
 *     &lt;!-- 登录页面 --&gt;
 *     &lt;login-page&gt;/login.jsp&lt;/login-page&gt;
 *     &lt;!-- 登录成功之后默认返回的页面 --&gt;
 *     &lt;home-page&gt;/index.jsp&lt;/home-page&gt;
 *     &lt;!-- 超时时间,以分为单位 --&gt;
 *     &lt;time-out&gt;&lt;/time-out&gt;
 *     &lt;!-- 多长时间用户没有活动表示用户为“非活动用户”,以分为单位 --&gt;
 *     &lt;active-time&gt;&lt;/active-time&gt;
 *     &lt;!-- 
 *       单点登录客户端
 *         1. 仅配置跨域客户端,非跨域客户端无需配置；
 *         2. 支持各种B/S语言,不限于JAVA；
 *         3. client:为你客户端需要监听的页面
 *      --&gt;
 *     &lt;clients&gt;
 *       &lt;client&gt;http://www.jxva.org/&lt;/client&gt;
 *       &lt;client&gt;http://www.jxva.net/&lt;/client&gt;
 *     &lt;/clients&gt;
 *   &lt;/ssoserver&gt;	
 *			
 *   &lt;initializers&gt;
 *     &lt;initializer init-handler="com.jxva.mvc.SysInitializer"/&gt;
 *     &lt;initializer init-handler="com.jxva.mvc.MvcInitializer"/&gt;
 *   &lt;/initializers&gt;
 * &lt;/jxva&gt;
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:35:29 by Jxva
 */
public class SysPlugin implements Pluginable{	
	
	private static final Logger log=Logger.getLogger(SysPlugin.class);
	
	/**
	 * 是否处于调试状态
	 */
	public static boolean DEBUG;
	
	/**
	 * 错误页面
	 * 当处于非调试状态,抛出异常时将转向此页
	 */
	public static String ERROR_PAGE;
	
	/**
	 * 主要用于Forward与Template的临时文件缓存
	 */
	public static String CACHE_PATH;
	
	//在action中的变量是否自动注入
	//private static boolean autoInject;
	//在action中的参数是否自动拦截
	//private static boolean paramIntercept;
	
	/**
	 * MVC中是否需要使用DAO,主要用于DAO自动关闭
	 */
	public static boolean USE_DAO;
		
	
	/**
	 * 从配置文件装载系统配置信息
	 */
	public void initialize()throws PluginException{
		try{
			Element sysconfig=Plugin.getRoot().getElement("sysconfig");
	      	DEBUG=Boolean.valueOf(sysconfig.getAttributeValue("debug"));
	      	ERROR_PAGE=sysconfig.getElementText("error-page");
	      	CACHE_PATH=sysconfig.getElementText("cache-path");
	      	
	      	MvcPlugin.getMvcs().clear();
	      	Element mvc=Plugin.getRoot().getElement("mvc");
	      	
	      	//autoInject=Boolean.valueOf(mvc.getAttributeValue("auto-inject"));
	      	//paramIntercept=Boolean.valueOf(mvc.getAttributeValue("param-intercept"));
	      	String _useDao=mvc.getAttributeValue("use-dao");
	      	USE_DAO=!(_useDao==null||_useDao.equalsIgnoreCase("false"));
	      	if(USE_DAO){
	      		DAOFactory.getInstance().createDAO().close();
	      	}
	      	Element modules=mvc.getElement("modules");
	      	for(int i=0;i<modules.getChildCount();i++){
		      	Element module=(Element)modules.getElement(i);
		      	if(module!=null){
		      		MvcPlugin.getMvcs().add(module.getText());
		      	}
	      	}   
	      	
		}catch(UtilException e){
			throw new PluginException(e);
		}
	}
	
	public void dispose(){}
}

