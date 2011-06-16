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
package org.jxva.i18n;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import com.jxva.i18n.I18N;
import com.jxva.plugin.I18NPlugin;
import com.jxva.plugin.PluginException;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-11 14:30:49 by Jxva
 */
public class Test {

	/**
	 * @param args
	 * @throws PluginException 
	 */
	public static void main(String[] args) throws PluginException {
		new I18NPlugin().initialize();
		System.out.println(new I18N().getString("test.title"));
		System.out.println(new I18N().getString("test.info"));
	}
	
	public static void test(){
		System.out.println(Locale.getDefault());
		Locale locale=new Locale("zh","CN");
		Locale.setDefault(locale);
		ResourceBundle res= ResourceBundle.getBundle("jxva-i18n");
		Enumeration<String> keys=res.getKeys();
		while(keys.hasMoreElements()){
			String key=keys.nextElement();
			System.out.println(key+"="+res.getString(key));
		}
		System.out.println(Locale.getDefault());
		System.out.println(res.containsKey("dd"));
	}
}
