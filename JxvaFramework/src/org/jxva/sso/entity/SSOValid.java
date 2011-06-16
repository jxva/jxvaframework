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


import org.jxva.sso.SSODefine;
import org.jxva.sso.model.SsoUser;

import com.jxva.entity.MD5;
import com.jxva.util.StringUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-03 17:58:52 by Jxva
 */
public class SSOValid {
	
	public String check(SsoUser ssoUser,String username,String password){
		if(ssoUser==null)return SSODefine.USERNAME_INCORRECT;
		if(StringUtil.isEmpty(password))return SSODefine.PASSWORD_INCORRECT;
		if(!ssoUser.getPassword().equalsIgnoreCase(MD5.encrypt(password))){
			return SSODefine.PASSWORD_INCORRECT;
		}else{
			return SSODefine.SUCCESS;
		}
	}
}
