/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
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
package demo.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.jxva.entity.Encoding;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-12 09:46:22 by Jxva
 */
public class UrlCode {

	/**
	 *对应的JS函数为:
	 *encodeURI(str); 				decodeURI(str);
	 *encodeURIComponent(str);		decodeURIComponent(str);
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s=URLEncoder.encode("中国的",Encoding.UTF_8);
		System.out.println(s);
		System.out.println(URLDecoder.decode("%E4%B8%AD%E5%9B%BD%E4%BA%BA",Encoding.UTF_8));
	}

}
