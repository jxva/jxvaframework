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
package com.jxva.dao.type;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
		附MS SQL Server 2000系统数据类型：
		
		1 bigint         从 -2^63 到 2^63-1 之间的 integer (整数)数据
		2 binary         定长的binary数据，最长为8,000字节
		3 bit         integer数据，值为1或0
		4 char         定长的非unicode character数据，长度为8,000个字符
		5 cursor         含有对游标的引用的变量或存储过程OUTPUT参数所采用的数据类型
		6 datetime     date和time数据，从1753年1月1日到9999年12月31日
		7 decimal         定点精度和小数的numeric数据，从-10^38-1到10^38-1之间
		8 float         浮点精度数字数据，从-1.79E+308到1.79E+308之间
		9 image         长度可变的binary数据，最长为2^31-1字节
		10 int         从-2^31到2^31-1之间的integer(整数)数据
		11 money         monetary数据值，从-2^63到2^63-1，准确度为货币单位的千分之一
		12 nchar         定长的unicode数据，长度为4,000个字符
		13 ntext         长度可变的unicode数据，最长为2^30-1个字符
		14 numeric         decimal的同义词
		15 nvarchar     长度可变的unicode数据，最长为4,000字符
		16 real         浮点精度数字数据，从-3.40E+38到3.40E+38之间
		17 rowversion     数据库范围内的唯一号
		18 smalldatetime     date和time数据，从1900年1月1日到2079年6月6日
		19 smallint     从-2^15到2^15-1之间的integer数据
		20 smallmoney     monetary数据值，-214,748.3648到+214,748.3647之间
		21 sql_variant     可存储多种SQL Server支持的数据类型的值的数据类型， 但不存储text, ntext, timestamp和sql_variant类型的值
		22 sysname         系统提供的用户定义的数据类型，为nvarchar(128)的同义词
		23 table         一种特殊的数据类型，可用于为以后进行处理而存储结果集
		24 text         长度可变的非unicode数据，最长为2^31-1个字符
		25 timestamp     数据库范围内的唯一号
		26 tinyint         从0到255之间的integer数据
		27 uniqueidentifier全局唯一标识符(GUID)
		28 varbinary     长度可变的binary数据，最长为2^31-1字节
		29 varchar         长度可变的非unicode数据，最长为8,000个字符
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 12:45:36 by Jxva
 */
public abstract class JavaType {
	
	private static final Map<Class<?>,Integer> JAVATYPE=new HashMap<Class<?>,Integer>();
	static{
		JAVATYPE.put(java.sql.Array.class,Types.ARRAY);//ARRAY
		JAVATYPE.put(java.math.BigDecimal.class,Types.DECIMAL);//DECIMAL(x,y)
		JAVATYPE.put(Blob.class,Types.BLOB);//BLOB
		JAVATYPE.put(java.lang.Boolean.class,Types.BOOLEAN);//BIT,CHAR(1)('Y'或'N'),TINYINT(1)
		JAVATYPE.put(java.lang.Byte.class,Types.BIT);//TINYINT
		JAVATYPE.put(java.util.Calendar.class,Types.TIMESTAMP);//TIMESTAMP,DATE
		JAVATYPE.put(java.lang.Character.class,Types.CHAR);//CHAR(1)
		JAVATYPE.put(java.lang.Class.class,Types.VARCHAR);//VARCHAR
		JAVATYPE.put(Clob.class,Types.CLOB);//CLOB
		JAVATYPE.put(java.sql.Date.class,Types.DATE); //DATE
		JAVATYPE.put(java.lang.Double.class,Types.DOUBLE);//DOUBLE
		JAVATYPE.put(java.lang.Float.class,Types.FLOAT);//FLOAT
		JAVATYPE.put(java.lang.Integer.class,Types.INTEGER);//INGEGER
		JAVATYPE.put(java.io.InputStream.class,Types.LONGVARBINARY);//CLOB,BLOB
		JAVATYPE.put(java.util.Locale.class,Types.VARCHAR);//VARCHAR
		JAVATYPE.put(java.lang.Long.class,Types.BIGINT);//BIGINT
		JAVATYPE.put(java.lang.Number.class,Types.DECIMAL);//NUMERIC
		JAVATYPE.put(java.io.Serializable.class,Types.BINARY);//VARBINARY、BLOB
		JAVATYPE.put(java.lang.Short.class,Types.SMALLINT);//SMALLINT
		JAVATYPE.put(java.lang.String.class,Types.VARCHAR);//VARCHAR,(text)->CLOB
		JAVATYPE.put(java.sql.Time.class,Types.TIME); //TIME
		JAVATYPE.put(java.sql.Timestamp.class,Types.TIMESTAMP);//TIMESTAMP,DATETIME
		JAVATYPE.put(java.util.TimeZone.class,Types.VARCHAR);//VARCHAR
		JAVATYPE.put(java.util.Currency.class,Types.VARCHAR);//VARCHAR
		//JAVATYPE.put(byte[].class,Types.VARBINARY);//VARBINARY、BLOB
		
		JAVATYPE.put(java.util.Date.class,Types.DATETIME);//DATETIME,TIMESTAMP,DATE
	}
		
	public static boolean containsKey(Class<?> key){
		return JAVATYPE.containsKey(key);
	}
	
	public static int get(Class<?> key){
		//if(key.getName().equals("[B"))return Types.VARBINARY;
		Integer i=JAVATYPE.get(key);
		return (i==null)?Types.NULL:i;
	}
}
