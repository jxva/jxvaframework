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
package com.jxva.license;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-02 09:11:47 by Jxva
 */
public class Network {
	
	public static final String DEFAULT_HOST_NAME	="localhost";
	public static final String DEFAULT_HOST_ADDRESS	="127.0.0.1";
	
	public static String getHostName(){
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return DEFAULT_HOST_NAME;
		}
	}
	
	public static String getHostAddress(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return DEFAULT_HOST_ADDRESS;
		}
	}
}
