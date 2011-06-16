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
package org.jxva.dao.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.jxva.dao.Jdbc;
import com.jxva.dao.entry.TableEntry;
import com.jxva.dao.jdbc.Column;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-02 09:26:17 by Jxva
 */
public class TestJdbc extends BaseDataSource{
	
	public static void main(String[] args) throws SQLException {
		Connection conn=datasource.getConnection();
		Jdbc jdbc=new Jdbc(conn);
		
		List<Column> list=jdbc.getAllColumns("tbl_book");
		for(Column c:list){
			System.out.println(c.getName());
		}
		
		List<TableEntry> list2=jdbc.getTables(null,null,"%",new String[]{"TABLE","VIEW"});
		for(TableEntry t:list2){
			System.out.println(t.getTableName());
		}
		
		conn.close();
	}
}
