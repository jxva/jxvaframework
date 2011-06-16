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
package org.jxva.plugin;

import java.io.File;

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.plugin.PluginException;
import com.jxva.plugin.Pluginable;
import com.jxva.util.UtilException;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 * 单点登录核心配置信息,用来装载jxva-sso.xml中的配置信息<br>
 * 以在应用启动时进行初始化<br>
 * jxva-sso.xml文档内容如下:
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;jxva&gt;	
 * 	&lt;!-- 单点登录服务器地址 --&gt;
 * 	&lt;server&gt;http://sso.jxva.com/&lt;/server&gt;
 * 	&lt;!-- 服务器验证类,必须实现com.jxva.sso.SSOInvoke接口 --&gt;
 * 	&lt;sso-invoke&gt;com.jxva.sso.SSOInvoke&lt;/sso-invoke&gt;
 * 	&lt;!-- 登录页面 --&gt;
 * 	&lt;login-page&gt;/login.jsp&lt;/login-page&gt;
 * 	&lt;!-- 登录成功之后默认返回的页面 --&gt;
 * 	&lt;home-page&gt;/index.jsp&lt;/home-page&gt;
 * 	&lt;!-- 超时时间,多长时间用户没有活动表示用户为“非活动用户”，以分为单位 --&gt;
 * 	&lt;time-out&gt;30&lt;/time-out&gt;
 * 	&lt;!-- 服务器活动时间，以分为单位 --&gt;
 * 	&lt;active-time&gt;5&lt;/active-time&gt;
 * 	&lt;!-- 
 * 		单点登录客户端
 * 		1. 仅配置跨域客户端，非跨域客户端无需配置；
 * 		2. 支持各种B/S语言，不限于JAVA；
 * 		3. client:为你客户端需要监听的页面
 * 	--&gt;
 * 	&lt;clients&gt;
 * 		&lt;client&gt;http://www.jxva.org/&lt;/client&gt;
 * 		&lt;client&gt;http://www.jxva.net/&lt;/client&gt;
 * 	&lt;/clients&gt;
 * &lt;/jxva&gt;
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 14:28:36 by Jxva
 */
public class SSOPlugin implements Pluginable {
	
	private static String server;
	private static String ssoInvoke;
	private static String loginPage;
	private static String homePage;
	private static Integer timeOut;
	private static Integer activeTime;

	
	/**
	 * 单点登录服务器
	 * @return
	 */
	public static String getServer(){
		return server;
	}
	
	/**
	 * 单点业务登录调用类
	 * @return
	 */
	public static String getSsoInvoke(){
		return ssoInvoke;
	}

	/**
	 * 单点登录页面
	 * @return
	 */
	public static String getLoginPage() {
		return loginPage;
	}

	/**
	 * 登录成功之后默认的返回页面
	 * @return
	 */
	public static String getHomePage() {
		return homePage;
	}

	/**
	 * 超时时间,以分钟为单位
	 * @return
	 */
	public static Integer getTimeOut() {
		return timeOut;
	}

	/**
	 * 活动时长,以分钟为单位
	 * @return
	 */
	public static Integer getActiveTime() {
		return activeTime;
	}

	
	
	public void initialize() throws PluginException {
		try{
			Element root =XmlUtil.getDocument(new File(Path.CLASS_PATH+"jxva-sso.xml"),Encoding.UTF_8).getRootElement();
			
			server		=root.getElement("server").getText();
			ssoInvoke	=root.getElement("sso-invoke").getText();
			loginPage	=root.getElement("login-page").getText();
			homePage	=root.getElement("home-page").getText();
			timeOut		=Integer.valueOf(root.getElement("time-out").getText());
			activeTime	=Integer.valueOf(root.getElement("active-time").getText());
	
			Element el=root.getElement("clients");
	      	for(int i=0;i<el.getChildCount();i++){
	      		Element e=el.getElement(i);
	      		if(e!=null){
	      			//SSOUtil.getClients().add(e.getText());
	      		}
	      	}
		}catch(UtilException e){
			throw new PluginException(e);
		}
	}
	
	public void dispose(){}
}
