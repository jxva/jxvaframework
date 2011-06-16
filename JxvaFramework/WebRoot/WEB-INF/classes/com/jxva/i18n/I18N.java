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
package com.jxva.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jxva.plugin.I18NPlugin;
import com.jxva.plugin.PluginException;

/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-02-18 09:04:48 by Jxva
 */
public class I18N {
	
	private static final Pattern PATTERN_ARRAY = Pattern.compile("\\{\\d+\\}");
	
	public I18N(){
		
	}
	
	public I18N(Locale locale){
		if(I18NPlugin.getLocale().equalsIgnoreCase("default")){
			Locale.setDefault(locale);
		}
	}

	/**
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public String getString(String key, String... params) {
		return getValueByParams(getValue(key),params);
	}
	
	/**
	 * 
	 * @param name
	 * @param key
	 * @param params
	 * @return
	 */
	public String getStringByName(String name,String key, String... params) {
		return getValueByParams(getValueByName(name,key),params);
	}
	
	private String getValueByParams(String value,String...params){
		if(params.length==0)return value;
		Matcher m =PATTERN_ARRAY.matcher(value);
		while(m.find()){
			final String result=m.group();
			int pos=Integer.parseInt(result.substring(1,result.lastIndexOf('}')));
			value=value.replaceAll("\\{"+pos+"\\}",params[pos]);
		}
		return value;
	}
	
	private String getValue(String key) {
		Map<String, ResourceBundle> resources = getResourceBundle();
		for (ResourceBundle resource : resources.values()) {
			if (resource.containsKey(key))
				return resource.getString(key);
		}
		return "";
	}

	private String getValueByName(String name, String key) {
		ResourceBundle resource = getResourceBundle().get(name);
		if (resource == null)return "";
		return resource.containsKey(key) ? resource.getString(key): "";
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String,ResourceBundle> getResourceBundle(){
		Map<String,String> resources = I18NPlugin.getResources();
		Map<String,ResourceBundle> map=new HashMap<String,ResourceBundle>(resources.size());
		for(String s:resources.keySet()){
			map.put(s,ResourceBundle.getBundle(resources.get(s),Locale.getDefault()));
		}
		return map;
	}
		
	public static void main(String[] args) throws PluginException {
		new I18NPlugin().initialize();
		System.out.println(new I18N().getString("test.title"));
		System.out.println(new I18N().getString("test.info","蒋赞","job"));
	}
}
