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

import java.io.InputStream;
import java.util.Calendar;

import com.jxva.util.DateUtil;
import com.jxva.util.FileUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 14:19:07 by Jxva
 */
public class LicenseTpl {
	
	public static String getTpl(){
		InputStream in=LicenseTpl.class.getResourceAsStream("license.tpl");
		String content=FileUtil.read(in);
		return content.replaceAll("\\$\\{year\\}",String.valueOf(DateUtil.CALENDAR.get(Calendar.YEAR)));
	}
	
	public static void main(String[] args){
		System.out.println(getTpl());
	}
	
	public void d(){
		StringBuilder sb=new StringBuilder();
		sb.append("/*\r\n");
		sb.append(" * Copyright @ 2006-");
		sb.append(DateUtil.CALENDAR.get(Calendar.YEAR));
		sb.append(" by The Jxva Framework Foundation\r\n");
		sb.append(" * \r\n");
		sb.append(" * Licensed under the Apache License, Version 2.0 (the \"License\");\r\n");
		sb.append(" * you may not use this file except in compliance with the License.\r\n");
		sb.append(" * You may obtain a copy of the License at\r\n");
		sb.append(" * \r\n");
		sb.append(" *      http://www.apache.org/licenses/LICENSE-2.0\r\n");
		sb.append(" * \r\n");
		sb.append(" * Unless required by applicable law or agreed to in writing, software\r\n");
		sb.append(" * distributed under the License is distributed on an \"AS IS\" BASIS,\r\n");
		sb.append(" * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n");
		sb.append(" * See the License for the specific language governing permissions and\r\n");
		sb.append(" * limitations under the License.\r\n");
		sb.append(" */\r\n");
		//return sb.toString();
	}
}
