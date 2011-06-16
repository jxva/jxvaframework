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

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.jxva.dao.DAOFactory;
import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.log.Logger;
import com.jxva.util.UtilException;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 * 系统MVC框架初始化实现类<br>
 * 需要在web.xml中配置以随应用启动时初始化
 * <pre>
 * &lt;servlet&gt;
 *   &lt;servlet-name&gt;Plugin&lt;/servlet-name&gt;
 *   &lt;servlet-class&gt;com.jxva.mvc.Initializers&lt;/servlet-class&gt;
 *   &lt;load-on-startup&gt;1&lt;/load-on-startup&gt;
 * &lt;/servlet
 * 
 * <listener>
	<listener-class>
		com.jxva.plugin.Plugin
	</listener-class>
   </listener>
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:06:49 by Jxva
 */
public class Plugin extends HttpServlet implements ServletContextListener,Pluginable{	
	
	private static final long serialVersionUID = 1L;
	private static boolean initialized=false;
	private static final Logger log=Logger.getLogger(Plugin.class);
	private static Element root;

	static{
		try{
			root =XmlUtil.getDocument(new File(Path.CLASS_PATH+"jxva.xml"),Encoding.UTF_8).getRootElement();
			System.setProperty("sun.reflect.noCaches","false");
		}catch(UtilException e){
			log.trace(e);
			throw e;
		}
	}

	public static Element getRoot(){
		return root;
	}

	
	public void dispose(){
		if(SysPlugin.USE_DAO){
			DAOFactory.getInstance().destroy();
		}
	}
	
	public void initialize()throws PluginException{
		if(!initialized){
			reInitialize();
		}
	}
	
	public void reInitialize()throws PluginException{
		try{
			log.info("");
			log.info("--------------------------------------------------");
			log.info("                                                  ");
			log.info("      Welcome to use the Jxva Framework           ");
			log.info("                                                  ");
			log.info("--------------------------------------------------");
			Element els=root.getElement("plugins");
			for(int i=0;i<els.getChildCount();i++){
				Element ele=els.getElement(i);
				if(ele!=null){
					String pluginClass=ele.getAttributeValue("class");
					log.info(pluginClass+" initializing...");
					Pluginable pluginable=(Pluginable)Class.forName(pluginClass).newInstance();
					pluginable.initialize();
				}
			}
			initialized=true;
		}catch(Exception e){
			throw new PluginException(e);
		}
	}

	/**
	 * for ServletContextListener
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		dispose();		
	}

	/**
	 * for ServletContextListener
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			initialize();
		} catch (PluginException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * for servlet
	 */
	public void init()throws ServletException{
		try {
			initialize();
		} catch (PluginException e) {
			throw new ServletException(e);
		}
	}
	
	/**
	 * for servlet
	 */
	public void destroy(){
		dispose();
	}
}
