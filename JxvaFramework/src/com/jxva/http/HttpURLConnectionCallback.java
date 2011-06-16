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
package com.jxva.http;

import java.net.HttpURLConnection;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-01 14:09:44 by Jxva
 */
public interface HttpURLConnectionCallback<T> {
	
	/**
	 * conn.setConnectTimeout(30000); //设置连接主机超时（单位：毫秒）
	 * conn.setReadTimeout(30000);	  //设置从主机读取数据超时（单位：毫秒）
	 * 
	 * @param conn
	 * @return
	 * @throws HttpException
	 */
	T doInConnection(HttpURLConnection conn) throws HttpException;
	
}
