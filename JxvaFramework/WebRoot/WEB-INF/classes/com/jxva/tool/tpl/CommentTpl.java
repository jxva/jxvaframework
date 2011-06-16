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
package com.jxva.tool.tpl;

import com.jxva.util.DateUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 14:16:55 by Jxva
 */
public class CommentTpl {
	public static String getTpl(String desc){
		StringBuilder sb=new StringBuilder();
		sb.append("/**\r\n");
		sb.append(" * ").append(desc).append("\r\n");
		sb.append(" * @author  The Jxva Framework Foundation\r\n");
		sb.append(" * @since   1.0\r\n");
		sb.append(" * @version ");
		sb.append(DateUtil.getDateTime());
		sb.append(" by Automatic Generate Toolkit\r\n");
		sb.append(" */\r\n");
		return sb.toString();
	}
	
	public static void main(String[] args){
		System.out.println(getTpl(""));
	}
}
