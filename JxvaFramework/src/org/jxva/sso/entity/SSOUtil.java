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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxva.plugin.SSOPlugin;
import org.jxva.sso.SSOData;
import org.jxva.sso.model.SsoUser;

import com.jxva.entity.Base64;
import com.jxva.util.CharUtil;
import com.jxva.util.RandomUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-02 15:15:57 by Jxva
 */
public class SSOUtil {
	
	private static final List<String> clients=new ArrayList<String>();
	
	private static final Map<String,SsoUser> ssoUsers=new HashMap<String,SsoUser>();
	
	/**
	 * 单点登录客户端
	 * @return
	 */
	public static List<String> getClients() {
		return clients;
	}
	
	public static Map<String,SsoUser> getSsoUsersByCache(){
		return ssoUsers;
	}
	
	/**
	 * 
	 * @param backUrl
	 * @return
	 */
	public static String getBackUrl(String backUrl){
		return backUrl==null||backUrl.equals("")?SSOPlugin.getHomePage():backUrl;
	}
	
	public static String getSsoInfo(SsoUser ssoUser){
		StringBuilder sb=new StringBuilder();
		sb.append(ssoUser.getUserId());
		sb.append('?');
		sb.append(ssoUser.getUsername());
		return Base64.encode(sb.toString());
	}
	
	/**
	 * 以POST方式登录成功之后的处理
	 * @param ssoUser
	 */
	public static void saveToSsoMap(SsoUser ssoUser,String ssoId){
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		SSOData ssoData=new SSOData();
		ssoData.setLoginTime(ts);
		ssoData.setSsoId(ssoId);
		ssoData.setSsoInfo(getSsoInfo(ssoUser));
		ssoData.setSsoUser(ssoUser);
		ssoData.setUpdateTime(ts);
		ssoData.setVisitCount(1);
		SSOMap.getSsoMap().put(ssoId,ssoData);
	}
	
	/**
	 * 获取自动注册的信息
	 * @return ${username}?${password}
	 */
	public static String getAutoRegitsterInfo(){
		StringBuilder sb=new StringBuilder();
		sb.append(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,6));
		sb.append('?');
		sb.append(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,8));
		return sb.toString();
	}
	
	public static int getUserId(String ssoId){
		return getSsoUserBySsoId(ssoId).getUserId();
	}
	
	public static String getUsername(String ssoId){
		return getSsoUserBySsoId(ssoId).getUsername();
	}
	
	public static SsoUser getSsoUserBySsoId(String ssoId){
		SSOData ssoData=SSOMap.getSsoMap().get(ssoId);
		if(ssoData==null)return new SsoUser();
		return ssoData.getSsoUser();
	}
	
	/**
	 * 将int型数组转换为字符串
	 * @param ids
	 * @return
	 * 		如: int[] ids={2,6,8,13};
	 * 			String str=toString(ids);
	 * 			str: 2,6,8,13
	 */
	public static String toString(int[] ids){
		if(ids.length==0)return "";
		StringBuilder sb=new StringBuilder();
		for(int i:ids){
			sb.append(i).append(',');
		}
		return sb.toString().substring(0,sb.length()-1);
	}
}
