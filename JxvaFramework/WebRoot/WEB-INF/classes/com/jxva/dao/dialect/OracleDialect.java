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
import com.jxva.log.Logger;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:58:44 by Jxva
 */
public class OracleDialect implements Dialect {

	private static final long serialVersionUID = -707811360633388630L;
	private static final Logger log=Logger.getLogger(OracleDialect.class);
	
	public OracleDialect(){
		log.info("Oracle Dialect initializing...");
	}
	
	
	public boolean supportsLimitOffset() {
		return true;
	}

	public String getSQLWithRowSetLimit(String sql,int start, int end) {
		sql = sql.trim();
		StringBuilder pagingSelect = new StringBuilder( sql.length()+100 );
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ ) where rownum_ <= "+end+" and rownum_ > "+start);
		return pagingSelect.toString();
	}
	
	
	public static void main(String[] args)throws Exception{
		OracleDialect d=new OracleDialect();
		System.out.println(d.getSQLWithRowSetLimit("select * from tbl where a='b'",0,10));
	}


	public boolean supportsLimit() {
		return true;
	}



	public boolean supportsGetGeneratedKeys() {
		return true;
	}
}