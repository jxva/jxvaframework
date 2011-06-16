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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.log.Logger;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 * 预定义Action操作配置信息,用来装载MVC module xml中的配置信息<br>
 * 需要在jxva.xml中增加如下语句,以在应用启动时进行初始化
 * <pre>
 * &lt;initializers&gt;
 *   ...
 *   &lt;initializer init-handler="com.jxva.mvc.MvcInitializer"/&gt;
 * &lt;/initializers&gt;
 * </pre>
 * jxva-web.xml的格式如下:
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;jxva&gt;
 *   &lt;package namespace="default"&gt;
 *     &lt;action name="blog"       class="com.jxva.web.action.BlogAction"/&gt;
 *     &lt;action name="guestbook"  class="com.jxva.web.action.GuestBookAction"/&gt;
 *   &lt;/package&gt;
 *   &lt;package namespace="other"&gt;
 *     &lt;action name="other"      class="com.your.website.OtherAction"/&gt;
 *     ...
 *     &lt;/package&gt;
 * &lt;/jxva&gt;
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:12:03 by Jxva
 */
public class MvcPlugin implements Pluginable{	
		
	private static final Logger log=Logger.getLogger(MvcPlugin.class);
	private static final List<String> mvcs=new ArrayList<String>();
	private static final Map<String,Class<?>> actions=new HashMap<String,Class<?>>();
	
	static List<String> getMvcs(){
		return mvcs;
	}
		
	public static Class<?> getAction(String name){
		return actions.get(name);
	}
	
	public void initialize() throws PluginException{
		try{
			actions.clear();
			for(String mvc:mvcs){
	      		log.info("Mvc Module '"+mvc+"' initializing... ");
				Element root =XmlUtil.getDocument(new File(Path.CLASS_PATH+mvc),Encoding.UTF_8).getRootElement();
		       
		        for(int i=0;i<root.getChildCount();i++){
		        	Element _package=root.getElement(i);
		        	if(_package!=null){
		        		String namespace = _package.getAttributeValue("namespace");   
		        		namespace=(namespace.equals("default")||namespace.equals("")||namespace.equals("/")?"":'/'+namespace);
		        		
			        	for(int j=0;j<_package.getChildCount();j++){
			        		Element actionElement=_package.getElement(j);
			        		if(actionElement!=null){
				        		actions.put(namespace+'/'+actionElement.getAttributeValue("name"),Class.forName(actionElement.getAttributeValue("class")));
			        		}
				        }
		        	}
		        }
			}
			Class<?> framework=actions.get("/framework");
			if(framework==null){
				actions.put("/framework",Class.forName("com.jxva.mvc.FrameworkAction"));
			}
		}catch(Exception e){
			throw new PluginException(e);
		}
	}
	
	public void dispose(){}
}
