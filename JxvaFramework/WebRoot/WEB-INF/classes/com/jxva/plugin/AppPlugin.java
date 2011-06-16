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
import java.util.HashMap;
import java.util.Map;

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.log.Logger;
import com.jxva.util.UtilException;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 * 应用系统配置信息,用来装载配置文件jxva-app.xml中的信息,
 * 需要在jxva.xml中增加如下语句,以在应用启动时进行初始化
 * <pre>
 * &lt;plugins&gt;
 *   ...
 *   &lt;plugin class="com.jxva.plugin.AppPlugin"/&gt;
 * &lt;/plugins&gt;
 * </pre>
 * jxva-app.xml的格式如下:
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;jxva&gt;
 *   &lt;app-config name="jxva"&gt;
 *     &lt;para name="DB"&gt;jdbc/jxva&lt;/para&gt;
 *   &lt;/app-config&gt;
 * &lt;jxva&gt;
 * </pre>
 * 每个&lt;app-config&gt;项代表一个应用系统的配置信息,由其name属性指定的应用系统的相关信息
 * 都应该在该&lt;app-config&gt;内给出,可以有多个&lt;app-config&gt;项,
 * 每个&lt;app-config&gt;项中,可以配置多个&lt;para&gt;项
 * &lt;para&gt;项的name属性指定了该参数的名字,其文本(如上面的“jdbc/jxva”)表示该参数的值
 * 可以用如下的方法得到某个参数的值:
 * <pre>String para=AppPlugin.getPara("jxva","DB");</pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:43:11 by Jxva
 */
public class AppPlugin implements Pluginable{
	
	private static final Logger log=Logger.getLogger(AppPlugin.class);
	private static Map<String,String> appConfigMap=new HashMap<String,String>();
		
	@Override
	public void initialize() throws PluginException{
		try{
			appConfigMap.clear();
			log.info("App initializing... ");
			Element root = XmlUtil.getDocument(new File(Path.CLASS_PATH+"jxva-app.xml"),Encoding.UTF_8).getRootElement();
	        for(int i=0;i<root.getChildCount();i++){
	        	Element app=root.getElement(i);
	        	if(app!=null){
		        	String appName=app.getAttributeValue("name");
		        	for(int j=0;j<app.getChildCount();j++){
		        		Element para=app.getElement(j);
		        		if(para!=null){
			        		String paraName=para.getAttributeValue("name");
			        		String paraValue=para.getText();
			        		String key=appName+'.'+paraName;
			        		AppPlugin.appConfigMap.put(key,paraValue);
		        		}
		        	}
	        	}
	        }
		}catch(UtilException e){
			throw new PluginException(e);
		}
	}
	
	@Override
	public void dispose(){
		appConfigMap.clear();
		appConfigMap=null;
	}
	
	public static String getPara(String appName,String paraName){
		//Assert.notNull(appName,"appName must not be null");
		//Assert.notNull(paraName,"paramName must not be null");
		return appConfigMap.get(appName+'.'+paraName);
	}
}
