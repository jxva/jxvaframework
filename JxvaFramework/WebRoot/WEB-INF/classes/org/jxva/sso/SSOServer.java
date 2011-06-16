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
package org.jxva.sso;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-08 11:30:05 by Jxva
 */
public interface SSOServer extends SSO {

	/**
	 * 用户登录检测
	 * @return
	 * 		用户名不正确:	username_incorrect
	 * 		密码不正确:		password_incorrect
	 * 		检测成功:		success
	 * @throws SSOException			
	 */
	public String check() throws SSOException;

	
	/**
	 * 客户端出错回调
	 * @return
	 * @throws SSOException
	 */
	public String callBack() throws SSOException;
	
	
	/**
	 * 用户注册
	 * @return
	 * 		失败时:
	 *			用户名不正确:	username_incorrect
	 * 			密码不正确:		password_incorrect
	 *			用户名已经存在：username_has_exist
	 *		成功时：success
	 * @throws SSOException
	 */
	public String register() throws SSOException;
	
	
	/**
	 * 修改用户基本信息
	 * @return success
	 * @throws SSOException
	 */
	public String updatePassword() throws SSOException;
}
