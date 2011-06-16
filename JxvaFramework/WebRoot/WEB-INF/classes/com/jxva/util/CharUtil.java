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


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:22:37 by Jxva
 */
public abstract class CharUtil {
	
    public static final char[] UPPER_CHAR_TABLE ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
    public static final char[] LOWWER_CHAR_TABLE ={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    public static final char[] CHAR_TABLE ={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
	
	
//	/**
//	 * GB字符串转换为UTF-8格式
//	 * @param src
//	 * @return
//	 */
//	public static String gb2utf8(String str) {
//		byte[] b = str.getBytes();
//		char[] c = new char[b.length];
//		for (int i = 0; i < b.length; i++) {
//			c[i] = (char) (b[i] & 0x00FF);
//		}
//		return new String(c);
//	}
//
//	/**
//	 * Utf8URL编码
//	 * @param str
//	 * @return
//	 */
//	public String Utf8URLencode(String str) {
//		StringBuilder result = new StringBuilder();
//		for (int i = 0; i < str.length(); i++) {
//			char c = str.charAt(i);
//			if (c >= 0 && c <= 255) {
//				result.append(c);
//			} else {
//
//				byte[] b = new byte[0];
//				try {
//					b = Character.toString(c).getBytes(Encoding.UTF_8);
//				} catch (Exception ex) {
//				}
//
//				for (int j = 0; j < b.length; j++) {
//					int k = b[j];
//					if (k < 0)
//						k += 256;
//					result.append("%" + Integer.toHexString(k).toUpperCase());
//				}
//
//			}
//		}
//		return result.toString();
//	}
//
//	/**
//	 * Utf8URL解码
//	 * @param str
//	 * @return
//	 */
//	public String Utf8URLdecode(String str) {
//		StringBuilder result =new StringBuilder();
//		int p = 0;
//		if (str != null && str.length() > 0) {
//			str = str.toLowerCase();
//			p = str.indexOf("%e");
//			if (p == -1)
//				return str;
//			while (p != -1) {
//				result.append(str.substring(0, p));
//				str = str.substring(p, str.length());
//				if (str == "" || str.length() < 9)
//					return result.toString();
//				result.append(CodeToWord(str.substring(0, 9)));
//				str = str.substring(9, str.length());
//				p = str.indexOf("%e");
//			}
//
//		}
//		return result.append(str).toString();
//	}
//
//	/**
//	 * utf8URL编码转字符 
//	 * @param str
//	 * @return
//	 */
//	private String CodeToWord(String str) {
//		String result;
//		if (isValidUTF8Code(str)) {
//			byte[] code = new byte[3];
//			code[0] = (byte) (Integer.parseInt(str.substring(1, 3), 16) - 256);
//			code[1] = (byte) (Integer.parseInt(str.substring(4, 6), 16) - 256);
//			code[2] = (byte) (Integer.parseInt(str.substring(7, 9), 16) - 256);
//			try {
//				result = new String(code,Encoding.UTF_8);
//			} catch (UnsupportedEncodingException ex) {
//				result = null;
//			}
//		} else {
//			result = str;
//		}
//
//		return result;
//	}
//
//	/**
//	 * 编码是否有效
//	 * @param str
//	 * @return
//	 */
//	private boolean isValidUTF8Code(String str) {
//		String sign = "";
//		if (str.startsWith("%e"))
//			for (int i = 0, p = 0; p != -1; i++) {
//				p = str.indexOf("%", p);
//				if (p != -1)
//					p++;
//				sign += p;
//			}
//		return sign.equals("147-1");
//	}
//
//	/**
//	 * 是否Utf8Url编码
//	 * @param str
//	 * @return
//	 */
//	public boolean isUTF8Url(String str) {
//		str = str.toLowerCase();
//		int p = str.indexOf("%");
//		if (p != -1 && str.length() - p > 9) {
//			str = str.substring(p, p + 9);
//		}
//		return isValidUTF8Code(str);
//	}
	
	/**
	 * Get hex string from byte array
	 */
	public static String toHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length << 1);
		for (int i = 0; i < bytes.length; i++) {
			String digit = Integer.toHexString(0xFF & bytes[i]);
			if (digit.length() == 1) {
				digit = '0' + digit;
			}
			sb.append(digit);
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * Get byte array from hex string
	 */
	public static byte[] toByteArray(String hexString) {
		int arrLength = hexString.length() >> 1;
		byte buff[] = new byte[arrLength];
		for (int i = 0; i < arrLength; i++) {
			int index = i << 1;
			String digit = hexString.substring(index, index + 2);
			buff[i] = (byte) Integer.parseInt(digit, 16);
		}
		return buff;
	}

	
	 /**
     * char到byte
     * @param ch 字符
     * @return byte
     */
    public static byte charToByte(char ch) {
		switch (ch) {
		case 48: // '0'
			return 0;
		case 49: // '1'
			return 1;
		case 50: // '2'
			return 2;
		case 51: // '3'
			return 3;
		case 52: // '4'
			return 4;
		case 53: // '5'
			return 5;
		case 54: // '6'
			return 6;
		case 55: // '7'
			return 7;
		case 56: // '8'
			return 8;
		case 57: // '9'
			return 9;
		case 97: // 'a'
			return 10;
		case 98: // 'b'
			return 11;
		case 99: // 'c'
			return 12;
		case 100: // 'd'
			return 13;
		case 101: // 'e'
			return 14;
		case 102: // 'f'
			return 15;
		case 58: // ':'
		case 59: // ';'
		case 60: // '<'
		case 61: // '='
		case 62: // '>'
		case 63: // '?'
		case 64: // '@'
		case 65: // 'A'
		case 66: // 'B'
		case 67: // 'C'
		case 68: // 'D'
		case 69: // 'E'
		case 70: // 'F'
		case 71: // 'G'
		case 72: // 'H'
		case 73: // 'I'
		case 74: // 'J'
		case 75: // 'K'
		case 76: // 'L'
		case 77: // 'M'
		case 78: // 'N'
		case 79: // 'O'
		case 80: // 'P'
		case 81: // 'Q'
		case 82: // 'R'
		case 83: // 'S'
		case 84: // 'T'
		case 85: // 'U'
		case 86: // 'V'
		case 87: // 'W'
		case 88: // 'X'
		case 89: // 'Y'
		case 90: // 'Z'
		case 91: // '['
		case 92: // '\\'
		case 93: // ']'
		case 94: // '^'
		case 95: // '_'
		case 96: // '`'
		default:
			return 0;
		}
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args)throws Exception {

//		CharUtil charTools = new CharUtil();
//
//		String url;
//
//		url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2&btnG=%E6%90%9C%E7%B4%A2&lr=";
//		if (charTools.isUTF8Url(url)) {
//			System.out.println(charTools.Utf8URLdecode(url));
//		} else {
//			System.out.println(URLDecoder.decode(url,Encoding.UTF_8));
//		}
//
//		url = "http://www.baidu.com/baidu?word=%D6%D0%B9%FA%B4%F3%B0%D9%BF%C6%D4%DA%CF%DF%C8%AB%CE%C4%BC%EC%CB%F7&tn=myie2dg";
//		if (charTools.isUTF8Url(url)) {
//			System.out.println(charTools.Utf8URLdecode(url));
//		} else {
//			System.out.println(URLDecoder.decode(url,Encoding.GBK));
//		}

	}


}
