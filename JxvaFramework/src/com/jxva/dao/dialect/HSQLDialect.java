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
package com.jxva.dao.dialect;

import com.jxva.dao.Dialect;
import com.jxva.dao.DialectException;
import com.jxva.log.Logger;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:58:17 by Jxva
 */
public class HSQLDialect implements Dialect{
	
	private static final long serialVersionUID = 4213805280416974787L;
	private static final Logger log=Logger.getLogger(HSQLDialect.class);


	public HSQLDialect(){
		log.info("HSQL Dialect initializing...");
	}
	
	/**
	 *  1. select top 1 * from test;
	 *	2. select limit 0 2 * from test;
	 */
	public String getSQLWithRowSetLimit(String sql, int start, int end)throws DialectException {
		StringBuilder limit=new StringBuilder(" limit ");
		if(start<0){
			start=0;
		}
		if(end<0){
			end=0;
		}
		limit.append(start).append(" ").append(end).append(" ");
		StringBuilder pageSelectSQL=new StringBuilder(512);
		pageSelectSQL.append(sql);
		pageSelectSQL.insert( getAfterSelectInsertPoint(sql), limit.toString());
		return pageSelectSQL.toString();
	}

	private int getAfterSelectInsertPoint(String sql) {
		return sql.startsWith("select distinct") ? 15 : 6;
	}
	
	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsLimitOffset() {
		return false;
	}
	
	public static void main(String[] args)throws Exception{
		HSQLDialect s=new HSQLDialect();
		System.out.println(s.getSQLWithRowSetLimit("select * from hr_info where name='4' order by msgid",10,15));
	}

	/**
	 * HSQL 1.8 unsupport
	 * HSQL 1.9 support 
	 */
	public boolean supportsGetGeneratedKeys() {
		return false;
	}
}
