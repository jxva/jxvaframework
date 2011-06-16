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
 *
 */
package com.jxva.entity;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-10 21:10:52 by Jxva
 */
public abstract class Jscape {

	public static String escape(String src) {
		StringBuilder sb = new StringBuilder();
		sb.ensureCapacity(src.length() * 6);
		for (int i = 0; i < src.length(); i++) {
			char j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)){
				sb.append(j);
			}else if (j < 256) {
				sb.append('%');
				if (j < 16)	sb.append('0');
				sb.append(Integer.toString(j, 16));
			} else {
				sb.append("%u");
				sb.append(Integer.toString(j, 16));
			}
		}
		return sb.toString();
	}

	public static String unescape(String src) {
		StringBuilder sb = new StringBuilder();
		sb.ensureCapacity(src.length());
		int lastPos = 0;
		while (lastPos < src.length()) {
			int pos = src.indexOf('%', lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					char ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					sb.append(ch);
					lastPos = pos + 6;
				} else {
					char ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					sb.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					sb.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					sb.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return sb.toString();
	}
}