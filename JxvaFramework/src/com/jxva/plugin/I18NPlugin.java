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
import java.util.Locale;
import java.util.Map;

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.log.Logger;
import com.jxva.util.UtilException;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 * Internationalization Resources Initialization
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-13 10:47:45 by Jxva
 */
public class I18NPlugin implements Pluginable {
	
	private static final Logger log=Logger.getLogger(I18NPlugin.class);
	private static Map<String,String> resources=new HashMap<String,String>();
	private static String locale;
	
	public static Map<String,String> getResources(){
		return resources;
	}
	
	public static String getLocale(){
		return locale;
	}
	
	public void initialize() throws PluginException {
		try{
			resources.clear();
			Element root = XmlUtil.getDocument(new File(Path.CLASS_PATH+"jxva-i18n.xml"),Encoding.UTF_8).getRootElement();
			Element resourcesElement=root.getElement("resources");
			locale=resourcesElement.getAttributeValue("locale");
			if(!locale.equalsIgnoreCase("default")){
				String[] localeValueArray=locale.split("\\_");
				Locale newLocale=new Locale(localeValueArray[0],localeValueArray[1]);
				Locale.setDefault(newLocale);
			}
			log.info("i18n locale is "+Locale.getDefault().toString());
	        for(int i=0;i<resourcesElement.getChildCount();i++){
	        	Element resource=resourcesElement.getElement(i);
	        	if(resource!=null){
		        	resources.put(resource.getAttributeValue("name"),resource.getText());
		        	log.info("i18n resource file '"+resource.getText()+".properties' initializing");
	        	}
	        }
		}catch(UtilException e){
			throw new PluginException(e);
		}

	}

	public void dispose(){
		resources.clear();
		resources=null;
	}
}
