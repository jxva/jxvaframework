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
package com.jxva.util;

import java.net.InetAddress;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-08 13:59:59 by Jxva
 */
public abstract class UrlUtil {


	/**
	 * 通过url获取主机地址,包括端口号
	 * @param url url地址
	 * @return 
	 *   example:<br>
	 *     http://localhost/* 			-> localhost <br>
	 *     http://127.0.0.1:8080/* 		-> 127.0.0.1:8080 <br>
	 *     http://www.test.com:8080/* 	-> www.test.com:8080 <br>	
	 * @throws UtilException
	 */
	public static String getHostAddr(String url)throws UtilException{
		int flagPos=url.indexOf("://");
		if(flagPos==-1)throw new UtilException(url +" is invalid url.");			
		url=url.substring(flagPos+3);
		int virgulePos=url.indexOf('/');
		return virgulePos==-1?url:url.substring(0,virgulePos);
	}
	
	
	/**
	 * 通过url获取主机名
	 * @param url url地址
	 * @return
	 * 	 example:<br>
	 *     http://localhost/* 			-> localhost <br>
	 *     http://127.0.0.1:8080/* 		-> 127.0.0.1 <br>
	 *     http://www.test.com:8080/* 	-> www.test.com <br>
	 */
	public static String getHostName(String url){
		String hostAddr=getHostAddr(url);
		int colonPos=hostAddr.indexOf(':');
		return colonPos==-1?hostAddr:hostAddr.substring(0,colonPos);
	}
	
	/**
	 * 通过url获取主机端口号
	 * @param url url地址
	 * @return
	 * 	 example:<br>
	 *     http://localhost/* 			-> {@link StringUtil.EMPTY} <br>
	 *     http://127.0.0.1:8080/* 		-> 8080 <br>
	 *     http://www.test.com:8080/* 	-> 8080 <br>	
	 */
	public static String getHostPort(String url){
		String hostAddr=getHostAddr(url);
		int colonPos=hostAddr.indexOf(':');
		return colonPos==-1?"":hostAddr.substring(colonPos+1);
	}
	
	
	/**
	 * 判断url的主机名是否是IP地址
	 * @param url url地址
	 * @return
	 * 	 example:<br>
	 *     http://localhost/* 			-> false <br>
	 *     http://127.0.0.1:8080/* 		-> true  <br>
	 *     http://www.test.com:8080/* 	-> false <br>
	 */
	public static boolean isIp(String url){
		try{
			String hostName=getHostName(url);
			Integer.valueOf(hostName.substring(hostName.lastIndexOf('.')+1));
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 通过url获取域
	 * @param url url地址
	 * @return
	 *   example:<br>
	 *     http://localhost/* 			-> localhost <br>
	 *     http://127.0.0.1:8080/* 		-> 127.0.0.1 <br>
	 *     http://www.test.com:8080/* 	-> .test.com <br>	
	 */
	public static String getDomain(String url){
		String hostName=getHostName(url);
		if(isIp(url)){
			return hostName;
		}else{
			int dotPos=hostName.indexOf('.');
			return dotPos==-1?hostName:hostName.substring(dotPos,hostName.length());
		}
	}
	
	/**
	 * 判断是否是系统外部地址
	 * @param url url地址
	 * @return 是:true 否:false
	 */
	public static boolean isExternalUrl(String url){
		return url.startsWith("http://")||url.startsWith("https://")||url.startsWith("ftp://")||url.equals("about:blank");
	}
	
	/**
	 * IP地址转化成long型数的算法
	 * @param ip
	 * @return
	 */
	public static long getIP(InetAddress ip) {  
	     byte[] b = ip.getAddress();  
	     return b[0] << 24L & 0xff000000L | b[1] << 16L & 0xff0000L  | b[2] << 8L & 0xff00 | b[3] << 0L & 0xff;  
	} 
	
	public static void main(String args[]){
		String url="http://localhost/fdsa";
		System.out.println(getHostAddr(url));
		System.out.println(getHostName(url));
		System.out.println(isIp(url));
		System.out.println(getDomain(url));
		System.out.println(isExternalUrl(url));
		System.out.println(getHostPort(url));
	}
}
