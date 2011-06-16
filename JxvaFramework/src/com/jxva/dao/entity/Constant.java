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
package com.jxva.dao.entity;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:52:01 by Jxva
 */
public final class Constant {
	
	private Constant(){
		throw new UnsupportedOperationException();
	}
	
	public static final String DB_TYPE="jxva.datasource.db_type";
	public static final String SHOW_SQL="jxva.datasource.show_sql";
	public static final String SYS_TABLE="jxva.datasource.sys_table";
	public static final String PKG_NAME="jxva.datasource.pkg_name";
	public static final String OVERTIME="jxva.datasource.overtime";
	public static final String POOL="jxva.datasource.pool";
	public static final String USERNAME="jxva.datasource.username";
	public static final String PASSWORD="jxva.datasource.password";
	public static final String MAX_SIZE="jxva.datasource.max_size";
	public static final String MIN_SIZE="jxva.datasource.min_size";
	public static final String ASIA="jxva.datasource.asia";
	public static final String JNDI="jxva.datasource.jndi";
	public static final String DRIVER_CLASS="jxva.datasource.driver_class";
	public static final String URL="jxva.datasource.url";
	public static final String DIALECT="com.jxva.dao.dialect.dialect";
}
