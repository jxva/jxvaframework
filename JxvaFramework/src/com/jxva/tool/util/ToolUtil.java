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
package com.jxva.tool.util;


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:53:50 by Jxva
 */
public class ToolUtil {
	
	/**
	 * @param savePath
	 * @param packageName
	 * @return
	 */
	public static String getRealSavePath(String savePath, String packageName) {
		StringBuilder realPath = new StringBuilder(savePath);

		if (savePath.endsWith("/") || savePath.endsWith("\\")) {
			savePath = savePath.substring(0, savePath.length() - 1);
		}
		StringBuilder sb = new StringBuilder(savePath);
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '\\') {
				sb.setCharAt(i, '/');
			}
		}
		realPath = sb;
		if (realPath.charAt(realPath.length() - 1) != '/') {
			realPath.append("/");
		}
		String[] packs =packageName.split("\\.");
		for (int i = 0; i < packs.length; i++) {
			realPath.append(packs[i]);
			realPath.append("/");
		}
		if(realPath.toString().endsWith("//")){
			return realPath.substring(0,realPath.length()-1).toString();
		}
		return realPath.toString();
	}
	
	public static void main(String[] args){
		System.out.println(getRealSavePath("C:\\Documents and Settings\\Administrator\\Desktop",""));
		System.out.println(getRealSavePath("C:\\Documents and Settings\\Administrator\\Desktop","com.jxva.com"));
	}
}
