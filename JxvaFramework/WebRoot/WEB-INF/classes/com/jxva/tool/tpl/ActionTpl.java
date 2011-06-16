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


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 16:33:10 by Jxva
 */
public class ActionTpl {
	
	public static String getTpl(String packageName,String className,String desc){
		
		StringBuilder sb=new StringBuilder();
		sb.append(LicenseTpl.getTpl());
		sb.append("package "+packageName+".action;\r\n\r\n");

		sb.append("import com.jxva.mvc.Action;\r\n\r\n");

		sb.append(CommentTpl.getTpl(desc));
		sb.append("public class "+className+"Action extends Action{\r\n\r\n");

		sb.append("\tpublic String execute() {\r\n");
		sb.append("\t\treturn SUCCESS;\r\n");
		sb.append("\t}\r\n\r\n");
		   				
		sb.append("}\r\n");
		return sb.toString();
	}
	
	public static void main(String[] args){
		System.out.println(getTpl("com.jxva","Test","fdas"));
	}
}
