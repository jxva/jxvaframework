/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
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
package org.jxva.sso.entity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jxva.entity.Path;
import com.jxva.util.FileUtil;
import com.jxva.util.TplUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-03 14:54:29 by Jxva
 */
public class SSOTpl {
	
	private static final String SSO_TPL	=Path.APP_PATH+"tpl/sso.tpl";
	
	private static String composeClients(String paramUrl){
		StringBuilder sb=new StringBuilder();
		for(String client:SSOUtil.getClients()){
			sb.append("<img src='").append(client);
			if(client.lastIndexOf('?')==-1)sb.append('?');
			sb.append(paramUrl).append("' />\n");
		}
		return sb.toString();
	}
	
	private static String getSSOTplContent(){
		return FileUtil.read(new File(SSO_TPL));
	}
	
	private static String getSSOTplContentFromCache(){
		return getSSOTplContent();
	}
	
	public static String getLoginSsoTpl(String backUrl,String paramUrl){
		return getSsoTpl(backUrl,paramUrl);
	}
	
	public static String getLogoutSsoTpl(String backUrl,String paramUrl){
		return getSsoTpl(backUrl,paramUrl);
	}
	
	private static String getSsoTpl(String backUrl,String paramUrl){
		Map<String,String> map=new HashMap<String,String>(2);
		map.put("backUrl",backUrl);
		map.put("clients",composeClients(paramUrl));
		return TplUtil.replaceParams(getSSOTplContentFromCache(),map);
	}
}