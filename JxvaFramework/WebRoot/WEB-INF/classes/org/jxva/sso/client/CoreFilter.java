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
package org.jxva.sso.client;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxva.plugin.PluginException;
import com.jxva.plugin.Plugin;

/**
 * 核心过滤器,整合 字符编码、单点登录、用户会话 三者功能
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:27:57 by Jxva
 */
public class CoreFilter implements Filter{
	
	private String encoding;
	private String ssoserver;
	
	public void destroy(){
		this.encoding = null;
		this.ssoserver= null;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding =filterConfig.getInitParameter("encoding");
		if(encoding==null)new ServletException("parameter encoding can't is empty.");
		ssoserver =filterConfig.getInitParameter("ssoserver");
		if(ssoserver!=null)SSOUtil.setSsoServer(ssoserver);
		try {
			new Plugin().initialize();
		} catch (PluginException e) {
			throw new ServletException(e);
		}
	}
	
	
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain)throws IOException, ServletException {
		req.setCharacterEncoding(encoding);
		HttpServletRequest request = (HttpServletRequest)req;
		 HttpServletResponse response = (HttpServletResponse)resp;
		if(ssoserver!=null){//如果ssoserver未设置时,将不调用SSO
		    request.setAttribute("ssoserver",ssoserver);
			UserUtil userUtil=new UserUtil().initByCoreFilter(request,response);
			if(userUtil.hasLogin()){//如果用户已经登录了,将用户基本信息存入会话状态中
				request.setAttribute("hasLogin",true);
				//request.setAttribute("user",userUtil.getUser());//User对象
    			request.setAttribute("userId",userUtil.getUser().getUserId());
    			request.setAttribute("username",userUtil.getUser().getUsername());
    			request.setAttribute("nickname",userUtil.getUser().getNickname());
			}
			UserDetail.setUserUtil(userUtil);
		}
		try{
			chain.doFilter(request,response);
		}finally{
			UserDetail.remove();
		}
	}
}