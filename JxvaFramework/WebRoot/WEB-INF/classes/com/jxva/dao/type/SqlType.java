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
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 15:42:13 by Jxva
 */
public abstract class SqlType {
	private final static Map<Integer,Class<?>> SQLTYPE=new HashMap<Integer,Class<?>>();
	static{
		SQLTYPE.put(Types.ARRAY,java.sql.Array.class);//2003
		SQLTYPE.put(Types.BIGINT,java.lang.Long.class);//-5
		SQLTYPE.put(Types.BINARY,java.io.InputStream.class);//-2
		SQLTYPE.put(Types.BIT,java.lang.Boolean.class);//-7
		SQLTYPE.put(Types.BLOB,java.sql.Blob.class);//2004
		SQLTYPE.put(Types.BOOLEAN,java.lang.Boolean.class);//16
		SQLTYPE.put(Types.CHAR,java.lang.Character.class);//1
		SQLTYPE.put(Types.CLOB,java.sql.Clob.class);//2005
		//SQLTYPE.put(Types.DATALINK,DataLink.class);//70
		SQLTYPE.put(Types.DATE,java.sql.Date.class);//91
		SQLTYPE.put(Types.DECIMAL,java.math.BigDecimal.class);//3
		//SQLTYPE.put(Types.DISTINCT,"");//2001
		SQLTYPE.put(Types.DOUBLE,java.lang.Double.class);//8
		SQLTYPE.put(Types.FLOAT,java.lang.Float.class);//6 
		SQLTYPE.put(Types.INTEGER,java.lang.Integer.class);//4
		//SQLTYPE.put(Types.JAVA_OBJECT,"");//2000
		SQLTYPE.put(Types.LONGNVARCHAR,java.lang.String.class);//-16
		SQLTYPE.put(Types.LONGVARBINARY,java.io.InputStream.class);//-4
		SQLTYPE.put(Types.LONGVARCHAR,java.lang.String.class);//-1
		SQLTYPE.put(Types.NCHAR,java.lang.Character.class);//15
		SQLTYPE.put(Types.NCLOB,java.sql.NClob.class);//2011
		//SQLTYPE.put(Types.NULL,"");//0
		SQLTYPE.put(Types.NUMERIC,java.math.BigDecimal.class);//2
		SQLTYPE.put(Types.NVARCHAR,java.lang.String.class);//-9
		//SQLTYPE.put(Types.OTHER,"");//1111
		SQLTYPE.put(Types.REAL,java.lang.Float.class);//7
		//SQLTYPE.put(Types.REF,"");//2006
		//SQLTYPE.put(Types.ROWID,"");//-8
		SQLTYPE.put(Types.SMALLINT,java.lang.Short.class);//5
		//SQLTYPE.put(Types.SQLXML,"");//2009
		//SQLTYPE.put(Types.STRUCT,"");//2002
		SQLTYPE.put(Types.TIME,java.sql.Time.class);//92
		SQLTYPE.put(Types.TIMESTAMP,java.sql.Timestamp.class);//93
		SQLTYPE.put(Types.TINYINT,java.lang.Short.class);//-6
		SQLTYPE.put(Types.VARBINARY,java.io.InputStream.class);//-3 byte[]
		SQLTYPE.put(Types.VARCHAR,java.lang.String.class);//12
		
		SQLTYPE.put(Types.DATETIME,java.util.Date.class);//3000
		
	}
	
	public static boolean containsKey(int key){
		return SQLTYPE.containsKey(key);
	}
	
	public static Class<?> get(int key){
		Class<?> cls=SQLTYPE.get(key);
		return cls==null?NullSqlType.class:cls;
	}
	
	private static class NullSqlType{
		public String getName(){
			return "Unknown";
		}
	}
}
