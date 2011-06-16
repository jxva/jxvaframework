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
package org.jxva.sso;

/**
 * 公共信息,返回给SSO客户端的数据
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-03 13:36:53 by Jxva
 */
public interface SSODefine {
	
	//成功(用于所有)
	public static final String SUCCESS	="success";
	//失败(用于所有)
	public static final String FAILED	="failed";
	//出错(用于所有)
	public static final String ERROR	="error";
		
	//用户名不正确(用于注册/登录)
	public static final String USERNAME_INCORRECT="username_incorrect";
	
	//密码不正确(用于注册/登录/修改登录)
	public static final String PASSWORD_INCORRECT="password_incorrect";
	
	//用户不合法
	public static final String USERNAME_NOT_VALID="username_not_valid";
	
	//密码不合法
	public static final String PASSWORD_NOT_VALID="password_not_valid";
	
	//用户名不存在(用于登录)
	public static final String USERNAME_NOT_EXIST="username_not_exist";
	
	//用户名已经存在(用于注册)
	public static final String USERNAME_HAS_EXIST="username_has_exist";
	
	//用户已经处于登录状态(用于校验)
	public static final String USER_HAS_LOGIN="user_has_login";
	
	//用户未登录状态(用于校验)
	public static final String USER_NOT_LOGIN="user_not_login";
	
	public static final int USERNAME_MIN_LENGTH=4;
	
	public static final int USERNAME_MAX_LENGTH=16;
	
	public static final int PASSWORD_MIN_LENGTH=6;
	
	public static final int PASSWORD_MAX_LENGTH=16;
	
	
	//==================================================================================
	
	//Platform for Privacy Preferences
	public static final String P3P="CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"";
	
	public static final String SSOINFO		= "ssoinfo";
	
	public static final String SSOID		= "ssoid";

	public static final String SSOUSER		= "ssouser";
	
	//用户名
	public static final String USERNAME		="username";
	
	//用户密码
	public static final String PASSWORD		="password";
	
	
	//返回的页面
	public static final String BACK_URL		="backUrl";
	
	public static final String POST			="post";
	
	public static final String GET			="get";
	
	public static final String METHOD		="method";
	
	public static final String LOGIN		="login";
	public static final String LOGOUT		="logout";
	public static final String CONTROL		="control";
	public static final String VERIFY		="verify";
}
