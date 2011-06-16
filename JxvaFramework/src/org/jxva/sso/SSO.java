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
 *
 */
package org.jxva.sso;

/**
 * Single Sign-On Interface
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 14:16:06 by Jxva
 */
public interface SSO{
	
	/**
	 * 控制转向
	 * @return
	 * @throws SSOException
	 */
	public String control() throws SSOException;

	/**
	 * 登录
	 * @return
	 * 		用户名不正确:	username_incorrect
	 * 		密码不正确:		password_incorrect
	 * 		成功:			
	 * 			POST方式：	如果有参数backUrl，将直接转向backUrl，否则转向SSO默认页；
	 *			GET方式： 	${ssoid}
	 *@throws SSOException
	 */
	public String login() throws SSOException;

	/**
	 * 退出
	 * @return
	 * 		POST方式：如果有参数backUrl，将直接转向backUrl，否则转向SSO默认页；
	 *		GET方式： success
	 *@throws SSOException
	 */
	public String logout() throws SSOException;
	
	/**
	 * 客户端与服务器端的交互
	 * 交互接口主要是SSO客户端通过ssoid向SSO服务器端发出数据请求，
	 * SSO服务器端会将用户数据通过Base64加密返回给SSO客户端，
	 * 通过解密之后可以获得ssoid绑定的用户信息
	 * 
	 * @return
	 * 		失败：failed
	 *		成功：Base64.encode(ssoInfo)
	 *@throws SSOException
	 */
	public String verify() throws SSOException;
}
