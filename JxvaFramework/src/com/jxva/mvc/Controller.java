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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jxva.entity.Encoding;
import com.jxva.mvc.entity.BaseServlet;
import com.jxva.mvc.entity.View;
import com.jxva.mvc.util.ControllerUtil;
import com.jxva.mvc.util.TemplateUtil;
import com.jxva.plugin.MvcPlugin;
import com.jxva.plugin.SysPlugin;
import com.jxva.util.FileUtil;

/**
 * framework core controller<br>
 * 根据用户请求调用相应的业务处理类,并根据执行结果或注解值导航<br>
 * 需要在web.xml中配置
 * <pre>
 * &lt;servlet&gt;
 *   &lt;servlet-name&gt;Controller&lt;/servlet-name&gt;
 *     &lt;servlet-class&gt;com.jxva.mvc.Controller&lt;/servlet-class&gt;
 *   &lt;/servlet&gt;
 *   &lt;servlet-mapping&gt;
 *     &lt;servlet-name>Controller&lt;/servlet-name&gt;
 *     &lt;url-pattern>*.jv&lt;/url-pattern&gt;
 *  &lt;/servlet-mapping&gt;
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:00:53 by Jxva
 */
public class Controller extends BaseServlet{
	
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request,HttpServletResponse response)throws ServletException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//		response.setHeader("Pragma", "No-cache");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setDateHeader("Expires", 0);
		try{
			final String[] actionMethod=ControllerUtil.getActionAndMethod(request.getServletPath());
			final Class<?> action=MvcPlugin.getAction(actionMethod[0]);
			if(action==null){
				int lastPos=actionMethod[0].lastIndexOf('/')+1;
				View.printInfoWithHeaderAndFooter(response.getWriter(),"There is no Action mapped for namespace "+actionMethod[0].substring(0,lastPos)+" and action name '"+actionMethod[0].substring(lastPos)+"'.");
				return;
			}
			final Method method=action.getMethod(actionMethod[1]);
			if(response.isCommitted()){
				return;
			}else{
				if(method.getAnnotations().length==0){
					final String result=ControllerUtil.execute(action,actionMethod[1],request,response);
					if(result==null){
						View.printInfoWithHeaderAndFooter(response.getWriter(),action.getName()+'.'+actionMethod[1]+"() can't return null.");
					}else{
						View.printInfo(response.getWriter(),result);
					}
				}else{
					if(method.isAnnotationPresent(Forward.class)){
						Forward forward=method.getAnnotation(Forward.class);
						if(forward.cache()==0||request.getParameter(ControllerUtil.FORWARD_FLAG)!=null){//非缓存
							final String result=ControllerUtil.execute(action,actionMethod[1],request,response);
							forward(request,response,ControllerUtil.getValue(result,forward.value()));
						}else{//采用缓存
							File file=ControllerUtil.getCacheFile(request);
							if(file.exists()){
								if(forward.cache()>0){
									if(System.currentTimeMillis()-file.lastModified()>forward.cache()*1000){
										ControllerUtil.createForwardCache(request,file);
									}
								}
							}else{
								ControllerUtil.createForwardCache(request,file);
							}
							forward(request,response,SysPlugin.CACHE_PATH+file.getName());
						}
					}else if(method.isAnnotationPresent(Redirect.class)){
						final String result=ControllerUtil.execute(action,actionMethod[1],request,response);
						redirect(request,response,ControllerUtil.getValue(result,method.getAnnotation(Redirect.class).value()));
					}else if(method.isAnnotationPresent(Template.class)){
						Template template=method.getAnnotation(Template.class);
						if(template.cache()==0){//非缓存
							final String result=ControllerUtil.execute(action,actionMethod[1],request,response);
							View.printInfo(response.getWriter(),TemplateUtil.execute(ControllerUtil.getValue(result,template.value())));
						}else{//采用缓存
							File file=ControllerUtil.getCacheFile(request);
							if(file.exists()){
								if(template.cache()>0){
									if(System.currentTimeMillis()-file.lastModified()>template.cache()*1000){
										final String result=ControllerUtil.execute(action,actionMethod[1],request,response);
										FileUtil.write(file,TemplateUtil.execute(ControllerUtil.getValue(result,template.value())),Encoding.UTF_8);
									}
								}
							}else{
								final String result=ControllerUtil.execute(action,actionMethod[1],request,response);
								FileUtil.write(file,TemplateUtil.execute(ControllerUtil.getValue(result,template.value())),Encoding.UTF_8);
							}
							forward(request,response,SysPlugin.CACHE_PATH+file.getName());
						}
					}else if(method.isAnnotationPresent(Perform.class)){
						ControllerUtil.execute(action,actionMethod[1],request,response);
						return;
					}
				}
			}
		}catch(Exception e){
			try{
				e.printStackTrace();
				if(SysPlugin.DEBUG){
					View.printExceptionWithHeaderAndFooter(response.getWriter(),e);
				}else{
					forward(request,response,SysPlugin.ERROR_PAGE);
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
