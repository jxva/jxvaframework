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
package com.jxva.tag;

import java.util.regex.Pattern;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-29 14:51:46 by Jxva
 */
public abstract class TagUtil {
	
	public static final Pattern PATTERN_CONTEXT = Pattern.compile("(#(parameter|pageContext|request|session|application|attr)((.[a-zA-Z0-9_]+)|(\\[\\'[a-zA-Z0-9_]+\\'\\])))");
	
	/**
	 * <b>增加属性</b> <br>
	 * 例如:<br>
	 * renderAttribute(sb, "value", getValue());
	 * 
	 * @param results
	 *            缓冲的字符串
	 * @param attribute
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public static void addAttribute(StringBuilder sb, String attribute,String value) {
		if (value != null) {
			sb.append(' ');
			sb.append(attribute);
			sb.append("=\"");
			sb.append(value);
			sb.append('"');
		}
	}
	
	public static String formatValue(Object obj) {
		if (obj instanceof java.lang.String) {
			return (String) obj;
		} else if (obj instanceof Number) {
			return obj + "";
		} else if (obj instanceof java.util.Date) {
			return obj + "";
		} else if (obj instanceof java.lang.Character) {
			return obj + "";
		} else {
			return "";
		}
	}
}
