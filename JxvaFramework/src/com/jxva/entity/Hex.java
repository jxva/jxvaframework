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

import com.jxva.util.CharUtil;

/**
 * 
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-01 10:43:11 by Jxva
 */
public abstract class Hex {
	/**
	 * 将字符串解码为byte[]
	 * @param str 字符串
	 * @return 解码的byte数组
	 */
	public static byte[] decode(String str) {
		char chars[] = str.toCharArray();
		byte bytes[] = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0;
			newByte |= CharUtil.charToByte(chars[i]);
			newByte <<= 4;
			newByte |= CharUtil.charToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	/**
	 * 将byte[]编码为字符串
	 * @param bytes byte数组
	 * @return 编码的字符串
	 */
	public static String encode(byte bytes[]) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 16)
				buf.append('0');
			buf.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}
	
//    private String toHex(byte buffer[]) {
//        StringBuffer sb = new StringBuffer(buffer.length * 2);
//        for (int i = 0; i < buffer.length; i++) {
//            sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
//            sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
//        }
//        return sb.toString();
//    }
}
