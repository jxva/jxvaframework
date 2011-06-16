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

import java.util.List;

import com.jxva.dao.Jdbc;
import com.jxva.dao.entry.KeyEntry;
import com.jxva.dao.jdbc.Column;
import com.jxva.dao.type.SqlType;
import com.jxva.util.ModelUtil;
/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 15:13:29 by Jxva
 */
public class ModelTpl {
	
	public static String getTpl(String packageName,String tblName,String className,String ignorePrefix,Jdbc jdbc){
		
		List<Column> cols=jdbc.getAllColumns(tblName);
		

		List<KeyEntry>	importedKeys=jdbc.getImportedKeys(tblName);
		List<KeyEntry>	exportedKeys=jdbc.getExportedKeys(tblName);

		String primaryKeys=getPrimaryKeys(jdbc,tblName,cols);	
		
		Column incrementColumn=jdbc.getAutoIncrementColumn(tblName);

		StringBuilder sb=new StringBuilder();
		sb.append(LicenseTpl.getTpl());
		sb.append("package "+packageName+".model;\r\n\r\n");

		sb.append("import java.io.Serializable;\r\n");
		if(!exportedKeys.isEmpty()){
			sb.append("import java.util.Set;\r\n\r\n");
			sb.append("import com.jxva.dao.annotation.OneToMany;\r\n");
		}
		if(!importedKeys.isEmpty()){
			sb.append("import com.jxva.dao.annotation.OneToOne;\r\n");
		}
		sb.append("import com.jxva.dao.annotation.Table;\r\n\r\n");

		sb.append(CommentTpl.getTpl(""));
		sb.append("@Table(name=\""+tblName+"\",increment=\""+(incrementColumn==null?null:ModelUtil.getFieldName(incrementColumn.getName()))+"\",primaryKeys={"+primaryKeys+"})\r\n");
		sb.append("public class "+className+" implements Serializable{\r\n\r\n");

		sb.append("	private static final long serialVersionUID = 1L;\r\n");
		
		for (int j = 0; j < cols.size(); j++) {
			Column col = (Column) cols.get(j);
			String type = SqlType.get(col.getType()).getName();
			if (type == null) {
				System.out.println(col.getName() + "<>" + col.getType() + "<>");
			}
			sb.append("\tprivate " + type + " " + ModelUtil.getFieldName(col.getName().toLowerCase())+ ";\r\n");
		}
		sb.append("\r\n");
		
		//for OneToOne
		for(KeyEntry key:importedKeys){
			sb.append("\t@OneToOne(field=\""+ModelUtil.getFieldName(key.getForeignKeyColumnName())+"\",foreignKey=\""+ModelUtil.getFieldName(key.getPrimaryKeyColumnName())+"\")\r\n");
			sb.append("\tprivate "+ModelUtil.getClassName(key.getPrimaryKeyTableName().replaceAll(ignorePrefix,""))+" "+ModelUtil.getFieldName(key.getPrimaryKeyTableName().replaceAll(ignorePrefix,""))+";\r\n\r\n");
		}
		
		//for OneToMany
		for(KeyEntry key:exportedKeys){
			sb.append("\t@OneToMany(field=\""+ModelUtil.getFieldName(key.getPrimaryKeyColumnName())+"\",foreignKey=\""+ModelUtil.getFieldName(key.getForeignKeyColumnName())+"\")\r\n");
			sb.append("\tprivate Set<"+ModelUtil.getClassName(key.getForeignKeyTableName().replaceAll(ignorePrefix,""))+"> "+ModelUtil.getFieldName(key.getForeignKeyTableName().replaceAll(ignorePrefix,""))+"Group;\r\n\r\n");
		}

		for (int j = 0; j < cols.size(); j++) {
			Column col = (Column) cols.get(j);
			String type = SqlType.get(col.getType()).getName();
			if (type == null) {
				System.out.println(col.getName() + "<>" + col.getType() + "<>");
			}
			
			String fieldName=ModelUtil.getFieldName(col.getName().toLowerCase());
			sb.append("\tpublic " + type + " " + ModelUtil.getGetterName(col.getName().toLowerCase()) + "(){\r\n");
			sb.append("\t\treturn this." + fieldName + ";");
			sb.append("\r\n\t}\r\n");
			
			sb.append("\tpublic void " + ModelUtil.getSetterName(col.getName().toLowerCase()) + "(");
			sb.append(type + " " + fieldName);
			sb.append("){\r\n");
			sb.append("\t\tthis." + fieldName + "="+ fieldName + ";\r\n");
			sb.append("\t}\r\n\r\n");
		}
		
		//for OneToOne
		for(KeyEntry key:importedKeys){
			String clsName=ModelUtil.getClassName(key.getPrimaryKeyTableName().replaceAll(ignorePrefix,""));
			String fieldName=ModelUtil.getFieldName(key.getPrimaryKeyTableName().replaceAll(ignorePrefix,""));
			
			sb.append("\tpublic "+clsName+" get"+clsName+"() {\r\n");
			sb.append("\t\treturn "+fieldName+";\r\n");
			sb.append("\t}\r\n");
			
			sb.append("\tpublic void set"+clsName+"("+clsName+" "+fieldName+") {\r\n");
			sb.append("\t\tthis."+fieldName+" = "+fieldName+";\r\n");
			sb.append("\t}\r\n\r\n");
		}
		
		//for OneToMany
		for(KeyEntry key:exportedKeys){
			String clsName=ModelUtil.getClassName(key.getForeignKeyTableName().replaceAll(ignorePrefix,""));
			String fieldName=ModelUtil.getFieldName(key.getForeignKeyTableName().replaceAll(ignorePrefix,""));
			
			sb.append("\tpublic Set<"+clsName+"> get"+clsName+"Group() {\r\n");
			sb.append("\t\treturn "+fieldName+"Group;\r\n");
			sb.append("\t}\r\n");
			
			sb.append("\tpublic void set"+clsName+"Group(Set<"+clsName+"> "+fieldName+"Group) {\r\n");
			sb.append("\t\tthis."+fieldName+"Group = "+fieldName+"Group;\r\n");
			sb.append("\t}\r\n\r\n");
		}

		sb.append("\tpublic boolean equals(Object obj){\r\n");
		sb.append("\t\treturn super.equals(obj);\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\tpublic int hashCode(){\r\n");
		sb.append("\t\treturn super.hashCode();\r\n");
		sb.append("\t}\r\n\r\n");
		
		sb.append("\tpublic String toString(){\r\n");
		sb.append("\t\tStringBuffer sb=new StringBuffer();\r\n");
		sb.append("\t\tsb.append(\"[ \");\r\n");
		for (int j = 0; j < cols.size(); j++) {
			Column col = (Column) cols.get(j);			
			String fieldName=ModelUtil.getFieldName(col.getName().toLowerCase());
			if(j<cols.size()-1){
				sb.append("\t\tsb.append(\""+fieldName+"=\").append("+fieldName+").append(',');\r\n");
			}else{
				sb.append("\t\tsb.append(\""+fieldName+"=\").append("+fieldName+").append(\" ]\");\r\n");
			}
		}
		sb.append("\t\treturn sb.toString();\r\n");
		//sb.append("\t\treturn super.toString();\r\n");
		sb.append("\t}\r\n\r\n");
		sb.append("}\r\n");
		return sb.toString();
	}
	
	
	private static String getPrimaryKeys(Jdbc jdbc,String tblName,List<Column> cols){
		List<Column> keys=jdbc.getPrimaryKeyColumns(tblName);
		StringBuilder keyStr=new StringBuilder();
		if(keys.isEmpty()){
			return keyStr.append('\"').append(ModelUtil.getFieldName(cols.get(0).getName())).append('\"').toString();
		}else{
			for(Column column:keys){
				keyStr.append('\"').append(ModelUtil.getFieldName(column.getName())).append("\",");
			}
			return keyStr.substring(0,keyStr.length()-1);
		}
	}
}
