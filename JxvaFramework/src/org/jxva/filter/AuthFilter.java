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
package org.jxva.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class AuthFilter implements Filter{
    protected FilterConfig filterConfig;

    public void init(FilterConfig config) throws ServletException{
       this.filterConfig=config;
    }

    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws
        IOException,ServletException{
    	
    	//request.setCharacterEncoding("UTF-8");
    	
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
    	
	    CharResponseWrapper wrapper=new CharResponseWrapper((HttpServletResponse)response);
	 	chain.doFilter(request,wrapper);
	    String resStr=wrapper.toString();
	 	//HttpServletRequest httpreq = (HttpServletRequest) request;
	 	//HttpServletRequest httpresp = (HttpServletRequest) response;
		

	 	if(resStr.indexOf("<simpleAuth>")>-1){
			//if (jinfo.getInfo() != null)
			//	out.print(resStr.replaceAll("<simpleAuth>", "<a href=\"../servlet/Controller?act=jxva-blog-build&ops=welcome&userid="+jinfo.getUserId()+"\">"+jinfo.getUserId()+"</a> &nbsp;  &nbsp; <a href=\"/Forum/servlet/Controller?act=jxva-forum-build&ops=logout\">退 出</a>"));
 			//else
				out.print(resStr.replaceAll("<simpleAuth>", "<a href=\"../servlet/Controller?act=jxva-build&ops=register\">注 册</a>  &nbsp; &nbsp; <a href=\"javascript:userLogin()\">登 录</a>"));
		}else{
		  	out.print(resStr);
		}
	    out.close();
		out.flush();
     }

     public void destroy(){
       this.filterConfig=null;
     }

     public void setFilterConfig(final FilterConfig filterConfig){
        this.filterConfig=filterConfig;
     }
}