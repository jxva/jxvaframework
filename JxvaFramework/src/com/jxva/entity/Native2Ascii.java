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
package com.jxva.entity;


/**
 * // 十进制转化为十六进制，结果为C8。
Integer.toHexString(200); 

// 十六进制转化为十进制，结果140。
Integer.parseInt("8C",16);
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-02-18 15:31:55 by Jxva
 */
public abstract class Native2Ascii {
	
	public static String encodeHex(String str){
		return encode(str,16);
	}
	
	public static String encodeDecimal(String str) {
		return encode(str,10);
	}

	private static String encode(String str,int radix) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0,n=str.length();i <n; ++i) {
			char c = str.charAt(i);
			int a = (char) c;
			if (a > 255) {
				if(radix==10){
					sb.append("&#").append(a).append(';');
				}else{
					sb.append("\\u").append(Integer.toHexString(a));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static String decodeHex(String str) {
		int len = str.length();
		StringBuilder sb = new StringBuilder(len);
		for (int x = 0; x < len;) {
			char c = str.charAt(x++);
			if (c == '\\') {
				c = str.charAt(x++);
				if (c == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						c = str.charAt(x++);
						switch (c) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + c - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + c - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + c - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					sb.append((char) value);
				} else {
					if (c == 't')
						c = '\t';
					else if (c == 'r')
						c = '\r';
					else if (c == 'n')
						c = '\n';
					else if (c == 'f')
						c = '\f';
					sb.append(c);
				}
			} else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
}