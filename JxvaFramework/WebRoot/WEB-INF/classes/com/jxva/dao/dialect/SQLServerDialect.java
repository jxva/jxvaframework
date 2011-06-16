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
 * @version 2008-11-27 10:58:53 by Jxva
 */
public class SQLServerDialect  implements Dialect {

	private static final long serialVersionUID = -1031703340606286662L;
	private static final Logger log=Logger.getLogger(SQLServerDialect.class);
	
	public SQLServerDialect(){
		log.info("SQLServer Dialect initializing...");
	}
	
	/**
	 * for sqlserver 2005
	 * "select * from(select *,row_number() over(order by id) r from student )where r between "+m+" and "+n+";
	 * 
	 *  以下示例将返回行号为 10 到 15 的行（包含这两行），并按 id 进行排序。

		程序代码
		USE db_Wuliu;
		GO
		WITH OrderedOrders AS
		(
		    ｓｅｌｅｃｔ　id,CargoName, CargoWeight,rn=ROW_NUMBER() OVER (ORDER BY id)
		    FROM tb_Cargo
		)
		ｓｅｌｅｃｔ *
		FROM OrderedOrders WHERE rn BETWEEN 10 AND 15;
	 */
	public String getSQLWithRowSetLimit(String sql, int start, int end) {
		StringBuilder pageSelectSQL=new StringBuilder(512);
		pageSelectSQL.append(sql);
		pageSelectSQL.insert( getAfterSelectInsertPoint(sql), " top " + end);
		/*
		pageSelectSQL.append(" as row_");
		pageSelectSQL.insert(0,"select top "+(end-start)+" row_.* from (");

		SELECT TOP 页大小 * 
		FROM TestTable 
		WHERE (ID NOT IN 
		(SELECT TOP 页大小*页数 id 
		FROM 表 
		ORDER BY id)) 
		ORDER BY ID 
   	    */
		//System.out.println(pageSelectSQL.toString());
		//System.out.println(pageSelectSQL.toString());
		return pageSelectSQL.toString();
	}
	
	/**
	 * 
	 * @param sql
	 * @return int
	 */
	private int getAfterSelectInsertPoint(String sql) {
		return sql.startsWith("select distinct") ? 15 : 6;
	}

	public boolean supportsLimitOffset() {
		return false;
	}
	
	public static void main(String[] args)throws Exception{
		SQLServerDialect s=new SQLServerDialect();
		System.out.println(s.getSQLWithRowSetLimit("select * from jxva where id=88",12,100));
	}


	public boolean supportsLimit() {
		return false;
	}

	public boolean supportsGetGeneratedKeys() {
		return true;
	}
}

