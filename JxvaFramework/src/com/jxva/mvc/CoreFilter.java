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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxva.entity.Encoding;
import com.jxva.plugin.Plugin;
import com.jxva.plugin.PluginException;

/**
 * framework core filter:plugin initialze,charset code,servlet context initialze
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:27:57 by Jxva
 */
public class CoreFilter implements Filter{
	
	private String encoding;
	
	public void destroy(){
		this.encoding = null;
		new Plugin().destroy();
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding =filterConfig.getInitParameter("encoding");
		if(encoding==null)encoding=Encoding.UTF_8;
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
			//PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this,req,resp, null, true,JspWriter.DEFAULT_BUFFER, true);
			try{
				ActionContext.setContext(request,response);
//			if(isUnFilter(request)){
	        	chain.doFilter(req, resp);
//	        	return;
//	        }else{
//				if(!new ManagerInfo(request).hasLogin()){
//				    resp.setCharacterEncoding("UTF-8");
//					resp.setContentType("text/html;charset=UTF-8");
//			 		PrintWriter out=resp.getWriter();
//			 		out.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
//			 		out.print("权限不够! <a href='"+request.getContextPath()+"/platform/login.zte' target='_top'>登录</a>");
//					out.close();
//					out.flush();
//					return;
//			 	}
//			    chain.doFilter(req,resp);
//	        }
			}finally{
				ActionContext.removeContext();
			}

	}
	
	/**
	 * 无需过滤的页面
	 * @param request
	 * @return
	 */
//    private boolean isUnFilter(HttpServletRequest request){
//    	if(request.getServletPath().indexOf("/platform/")==-1)return true;
//    	String[] urls		=new String[]{"index.jsp","validcode.jsp","login.zte","login!check.zte"};
//    	String[]unFilterExt	=new String[]{".jpg",".gif",".bmp",".js",".css",".png","",".chm",".htm",".html",".zip",".rar",".7z",".xml"}; 
//    	String tmp=request.getRequestURI();
//    	if(tmp.indexOf('.')==-1){
//    		return true;
//    	}else{
//    		if(Arrays.asList(unFilterExt).contains(tmp.substring(tmp.lastIndexOf('.'),tmp.length()))){
//	    		return true;
//	    	}
//    	}
//    	String url1 	= request.getServletPath();
//	 	url1=url1.substring(1,url1.length());
//	 	url1=url1.replaceAll("platform/","");
//	 	return Arrays.asList(urls).contains(url1);
//    }
}