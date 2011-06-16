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

import com.jxva.dao.entity.StatementType;
import com.jxva.dao.entity.TableCache;
import com.jxva.dao.entry.JqlEntry;
import com.jxva.dao.entry.TableEntry;
import com.jxva.dao.util.ParseUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 14:50:02 by Jxva
 */
public class UpdateStatement extends AbstractStatement  {
	

	
	public UpdateStatement(JqlEntry jqlEntry){
		super.jqlEntry=jqlEntry;
		//"update ".length=7
		
		final String finalJql=jqlEntry.getFinalJql();
		String lowerJql=finalJql.substring(7).toLowerCase();
		
		int setPos=lowerJql.indexOf(" set ");
		fromSection=finalJql.substring(7,setPos+7);
		
		String[] modelAlias=fromSection.split(" ");
		
		//generate sql
		StringBuilder sb=new StringBuilder();
		TableEntry entry=TableCache.getTableEntry(modelAlias[0]);
		sb.append("update ").append(entry.getTableName());
		if(modelAlias.length>1){//有别名
			sb.append(' ').append(modelAlias[1]);
		}
		sb.append(finalJql.substring(setPos+7));		
		String nativeSql=ParseUtil.replaceColumns(entry,sb.toString());
		sql=padSingleQuoteParams(padSubStatement(nativeSql));
		//System.out.printf("%-15s %s\n","","update正在测试缓存系统..................................");
	}

	public int getStatementType() {
		return StatementType.UPDATE;
	}
}
