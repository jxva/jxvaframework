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
 * @version 2008-11-27 10:57:35 by Jxva
 */
public class DB2Dialect implements Dialect{

	private static final long serialVersionUID = -3538766880333588856L;
	private static final Logger log=Logger.getLogger(DB2Dialect.class);
	public DB2Dialect() {
		log.info("DB2 Dialect initializing...");
	}

	public String getSQLWithRowSetLimit(String sql,int start, int end) {
		StringBuilder pageSelectSQL=new StringBuilder(512);
		pageSelectSQL.append("select * from ( select row_.*,");
		pageSelectSQL.append(this.getRowNumber(sql));
		pageSelectSQL.append(" from (");
		pageSelectSQL.append(sql);
		pageSelectSQL.append(" ) as row_ ) as temp_ where rownumber_ between ");
		pageSelectSQL.append((start+1)+" and "+end);
		//System.out.println(pageSelectSQL.toString());
		return pageSelectSQL.toString();
	}
	//select * from(select row_.*,rownumber() over() as rownumber_ from (select * from table) as row_) as temp_ where rownumber_ between 1 and 2
	/**
	 * 
	 * @param sql
	 * @return String
	 */
	private String getRowNumber(String sql) {
		//String tmp=sql.toLowerCase();
		
		StringBuffer rownumber = new StringBuffer(50).append("rownumber() over (");

		//int orderByIndex = tmp.indexOf("order by");
		
		//if ( orderByIndex>0 && !hasDistinct(sql) ) {
			//rownumber.append( StringUtil.replaceAll(sql.substring(orderByIndex),".","_"));
		//	rownumber.append(sql.substring(orderByIndex));
		//}
		rownumber.append(") as rownumber_");
		
		return rownumber.toString();
	}
	
	public boolean supportsLimitOffset() {
		return true;
	}
	
	public boolean supportsLimit() {
		return true;
	}


	public boolean supportsGetGeneratedKeys() {
		return true;
	}
}


