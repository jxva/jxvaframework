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

import java.io.File;
import java.util.regex.Pattern;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-08 09:23:52 by Jxva
 */
public abstract class HtmlUtil {
	
	private static final Pattern pattern=Pattern.compile("(\\<[^>]*\\>)|(\\&nbsp;)|(\n)");
	private static final Pattern doubleQuotes=Pattern.compile("\"(?:[^\"]|\"\")*\"");
	
	/**
	 * HTML字符过滤,进行过滤的五个字符为'<','>','"','&','''
	 * @param str 需要过滤的字符串
	 * @return 过滤后的字符串
	 */
	public static String escape(String str) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		StringBuilder result = null;
		for (int i = 0; i < str.length(); i++) {
			String filtered = null;
			switch (str.charAt(i)) {
			case '<':
				filtered = "&lt;";
				break;
			case '>':
				filtered = "&gt;";
				break;
			case '&':
				filtered = "&amp;";
				break;
			case '"':
				filtered = "&quot;";
				break;
			case '\'':
				filtered = "&#39;";
				break;
			}

			if (result == null) {
				if (filtered != null) {
					result = new StringBuilder(str.length() + 50);
					if (i > 0) {
						result.append(str.substring(0, i));
					}
					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(str.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}
		return (result == null) ? str : result.toString();
	}
	
	/**
	 *
	 * 完全清除所有HTML代码,空格与换行符
	 * @param str 需要清除的字符串
	 * @return 清除后的字符串
	 */
	public static String filter(String str) {
		if(StringUtil.isEmpty(str))return str;
		return pattern.matcher(doubleQuotes.matcher(str).replaceAll("")).replaceAll("");
		//str = str.replaceAll("(\\<[^>]*\\>)", "");// 过滤掉所有<>之间的标签
		//str = str.replaceAll("(\\&nbsp;)", "");// 过滤掉所有&nbsp;标签;
		//return str.replaceAll("\n", "");
	}
	
}
