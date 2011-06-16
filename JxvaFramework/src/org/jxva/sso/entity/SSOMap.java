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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jxva.sso.SSOData;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-08 10:41:25 by Jxva
 */
public class SSOMap {
	//注意:ssoMap必须为static类型,否则单点登录会登录失败
	//SSO存储<ssoId,SSOData>
	private static final ConcurrentMap<String, SSOData> ssoIdMap = new ConcurrentHashMap<String, SSOData>();
	//已登录用户
	//private static final ConcurrentMap<String,String>	userMap= new ConcurrentHashMap<String,String>();

	public static ConcurrentMap<String, SSOData> getSsoMap() {
		return ssoIdMap;
	}
}
