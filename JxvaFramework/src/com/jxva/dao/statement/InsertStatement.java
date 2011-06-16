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
package com.jxva.dao.statement;

import java.util.Map;

import com.jxva.dao.entity.StatementType;
import com.jxva.dao.entity.TableCache;
import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.JqlEntry;
import com.jxva.dao.entry.TableEntry;

/**
 * insert into table (...) values (...)
 * insert into table values ()
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 14:50:15 by Jxva
 */
public class InsertStatement extends AbstractStatement {
		
	public InsertStatement(JqlEntry jqlEntry){
		super.jqlEntry=jqlEntry;
		//"insert into ".length=12
		String finalJql=jqlEntry.getFinalJql().substring(12);

		int conditionPos=finalJql.indexOf(" ");
		fromSection=finalJql.substring(0,conditionPos);
		
		//generate sql
		StringBuilder sb=new StringBuilder();
		String modelName=getModelName();
		TableEntry entry=TableCache.getTableEntry(modelName);
		String finalJql1=jqlEntry.getFinalJql();
		sb.append("insert into ").append(entry.getTableName());
		String conditionSection=finalJql1.substring(conditionPos+12);	
		sb.append(conditionSection);
		
		String nativeSql=sb.toString();
		Map<String, ColumnEntry> columnEntrys=entry.getColumnEntrys();
		for(String key:columnEntrys.keySet()){
			nativeSql=nativeSql.replaceAll(key,columnEntrys.get(key).getColumnName());
		}
		sql=padSingleQuoteParams(padSubStatement(nativeSql));
		//System.out.printf("%-15s %s\n","","insert正在测试缓存系统..................................");
	}


	public int getStatementType() {
		return StatementType.INSERT;
	}
}
