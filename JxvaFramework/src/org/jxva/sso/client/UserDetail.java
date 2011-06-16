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
package org.jxva.sso.client;

/**
 * 用户信息过度类
 * 通过CoreFilter来注入UserUtil信息，省掉了Servlet容器的依赖性
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-29 13:07:48 by Jxva
 */
public class UserDetail {
	
	private static ThreadLocal<UserUtil> threadLocal=new ThreadLocal<UserUtil>();

	public static void setUserUtil(UserUtil userUtil) {
		threadLocal.set(userUtil);
	}

	public static UserUtil getUserUtil() {
		return threadLocal.get();
	}
	
	public static void remove(){
		threadLocal.remove();
	}
}
