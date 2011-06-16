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
package org.jxva.sso.client;


import com.jxva.entity.Encoding;
import com.jxva.http.HttpTransfer;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-25 15:38:34 by Jxva
 */
public class SSOUtil {
	
	private static String SSO_SERVER;
	
	/**
	 * 由CoreFilter注入
	 * @param ssoserver
	 */
	public static void setSsoServer(String ssoserver){
		SSO_SERVER=ssoserver;
	}
	
	public static String getSsoServerUrl(){
		return SSO_SERVER;
	}
	
	public static String getSsoServerVerifyUrl(String ssoid){
		return SSO_SERVER+"!verify.zte?ssoid="+ssoid;
	}
	
	public static String getSsoServerLogoutUrl(String ssoid){
		return SSO_SERVER+"!logout.zte?ssoid="+ssoid;
	}
	
	public static String getSsoServerCheckUrl(String username,String password){
		return SSO_SERVER+"!check.zte?username="+username+"&password="+password;
	}
	
	public static String getSsoServerLoginUrl(String backUrl){
		if(backUrl!=null){
			return SSO_SERVER+"!login.zte?backUrl="+backUrl;
		}else{
			return SSO_SERVER+"!login.zte";
		}
	}
	
	public static String verify(String ssoid){
		return getRemoteInfo(getSsoServerVerifyUrl(ssoid));
	}
	
	public static String logout(String ssoid){
		return getRemoteInfo(getSsoServerLogoutUrl(ssoid));
	}
	
	public static String check(String username,String password){
		return getRemoteInfo(getSsoServerCheckUrl(username,password));
	}
		
	private static String getRemoteInfo(String remoteUrl){
		return new HttpTransfer(remoteUrl).get(Encoding.UTF_8);
	}
}
