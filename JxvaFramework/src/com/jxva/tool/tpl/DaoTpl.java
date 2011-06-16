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

import com.jxva.util.ModelUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 14:32:13 by Jxva
 */
public class DaoTpl {
	public static String getTpl(String packageName,String className,String primaryKey,String desc){
				
		StringBuilder sb=new StringBuilder();
		sb.append(LicenseTpl.getTpl());
		sb.append("package ${packageName}.dao;\r\n\r\n");

		sb.append("import com.jxva.dao.BaseDao;\r\n");
		sb.append("import ${packageName}.model.${className};\r\n\r\n");

		sb.append(CommentTpl.getTpl(desc));
		sb.append("public class ${className}Dao extends BaseDao{\r\n\r\n");

		sb.append("\tpublic ${className} get${className}(int ${primaryKeyName}){\r\n");
		sb.append("\t\treturn dao.get(${className}.class,${primaryKeyName});\r\n");
		sb.append("\t}\r\n\r\n");
		   
		sb.append("\tpublic int	save(${className} ${classVarName}){\r\n");
		sb.append("\t\treturn dao.save(${classVarName});\r\n");
		sb.append("\t}\r\n\r\n");
			    
		sb.append("\tpublic int update(${className} ${classVarName}){\r\n");
		sb.append("\t\treturn dao.update(${classVarName});\r\n");
		sb.append("\t}\r\n\r\n");
			    
		sb.append("\tpublic int delete(${className} ${classVarName}){\r\n");
		sb.append("\t\treturn dao.delete(${classVarName});\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\tpublic int delete(int ${primaryKeyName}){\r\n");
		sb.append("\t\treturn dao.delete(${className}.class,${primaryKeyName});\r\n");
		sb.append("\t}\r\n\r\n");
		    
		sb.append("\tpublic int saveOrUpdate(${className} ${classVarName}){\r\n");
		sb.append("\t\treturn dao.saveOrUpdate(${classVarName});\r\n");
		sb.append("\t}\r\n\r\n");			
				
		sb.append("}\r\n");
		
		String classVarName=ModelUtil.getFieldName(className);
		String primaryKeyName=ModelUtil.getFieldName(primaryKey);
		
		String result=sb.toString();
		result=result.replaceAll("\\$\\{packageName\\}",packageName);
		result=result.replaceAll("\\$\\{primaryKeyName\\}",primaryKeyName);
		result=result.replaceAll("\\$\\{className\\}",className);
		result=result.replaceAll("\\$\\{classVarName\\}",classVarName);
		return result;
	}
	
	public static void main(String[] args){
		System.out.println(getTpl("com.jxva","user","user_id","测试"));
	}
}
