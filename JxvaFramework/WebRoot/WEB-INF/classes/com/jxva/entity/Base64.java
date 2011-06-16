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

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-31 09:52:41 by Jxva
 */
public abstract class Base64 {

	public static String decode(String str) throws EntityException {
		return new String(decodeBytes(str));
	}
	
	public static String encode(String str) {
		return encodeBytes(str.getBytes());
	}

	public static String encodeBytes(byte[] bytes){
		if (bytes == null)return null;
		return new BASE64Encoder().encode(bytes);
	}

	public static byte[] decodeBytes(String str)throws EntityException {
		if (str == null)return null;
		try{
			BASE64Decoder decoder = new BASE64Decoder();
			return decoder.decodeBuffer(str);
		}catch(IOException e){
			throw new EntityException(e);
		}
	}
}
