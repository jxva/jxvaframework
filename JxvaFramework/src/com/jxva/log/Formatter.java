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
package com.jxva.log;

/**
 * 日志格式化输出
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:41:40 by Jxva
 */
public class Formatter {
	
	public  static String format(LogRecord record) {
		StringBuilder sb=new StringBuilder(64);
		sb.append(record.getDatetime().toString().substring(11,19)).append(' ');
		sb.append(fillWhitespace(record.getLevel().toString().toLowerCase(),6)).append(':');
		//sb.append("[").append(record.getClassName()).append("] -> ");
		sb.append(record.getMsg()).append("\r\n");
		return sb.toString();
	}
	
	private static String fillWhitespace(String s,int length){
		int srcLength=s.length();
		if(srcLength>=length)return s;
		StringBuffer sb=new StringBuffer(length);
		sb.append(s);
		for(int i=0;i<length-srcLength;i++){
			sb.append(' ');
		}
		return sb.toString();
	}
}
