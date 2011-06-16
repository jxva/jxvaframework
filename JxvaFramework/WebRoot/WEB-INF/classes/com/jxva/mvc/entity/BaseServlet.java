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

package com.jxva.mvc.entity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxva.util.UrlUtil;

/**
 * Base Servlet
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:00:23 by Jxva
 */
public abstract class BaseServlet extends HttpServlet{	

	private static final long serialVersionUID = 2075123740321238504L;

	public BaseServlet() {
	}	
	public void goGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {

		doPost(request,response);
	}	
	protected void forward(HttpServletRequest request,HttpServletResponse response, String url)throws IOException, ServletException {
		if(response.isCommitted())return;
		getServletContext().getRequestDispatcher(url).forward(request,response);
	}
	
	protected void redirect(HttpServletRequest request,HttpServletResponse response,String url) throws IOException, ServletException {
		if(UrlUtil.isExternalUrl(url)){
			response.sendRedirect(url);
		}else{
			response.sendRedirect(request.getContextPath()+url);
		}	
	}
	
	protected void include(HttpServletRequest request,HttpServletResponse response,String url) throws IOException, ServletException {
		//response.getWriter().flush();
		getServletContext().getRequestDispatcher(url).include(request, response);
	}
}