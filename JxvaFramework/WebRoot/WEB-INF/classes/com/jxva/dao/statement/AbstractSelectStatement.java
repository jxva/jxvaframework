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

import com.jxva.dao.clause.FromClause;
import com.jxva.dao.entity.Relation;
import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.ModelEntry;
import static com.jxva.util.StringUtil.replaceAll;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-18 16:42:17 by Jxva
 */
public abstract class AbstractSelectStatement extends AbstractStatement{

	protected String[] generateConditionSql(String selectSection,String condition){
		boolean hasAlias=selectSection.indexOf('.')>-1;
		FromClause fromClause=getFromClause();
		ModelEntry[] modelEntry=fromClause.getModelEntrys();
	
		for(int i=0;i<modelEntry.length;i++){
			if(i>0){
				if(modelEntry[i-1].getRelation()==Relation.FETCH)continue;
			}
						
			String aliasNameAndDot=modelEntry[i].getAliasName()+'.';
			String modelNameAndDot=modelEntry[i].getModelName()+'.';
			
			selectSection=selectSection.replaceAll(modelNameAndDot,aliasNameAndDot);
			
			if(condition!=null){
				condition=condition.replaceAll(modelNameAndDot,aliasNameAndDot);//将对象名替换为别名
			}
			
			Map<String,ColumnEntry> columns=modelEntry[i].getTableEntry().getColumnEntrys();
			if(hasAlias){
				for(String c:columns.keySet()){
					String columnName=aliasNameAndDot+columns.get(c).getColumnName();
					
					String aliasName=aliasNameAndDot+c;
					
					selectSection=replaceAll(selectSection,aliasName,columnName);
					condition=replaceAll(condition,aliasName,columnName);
				}
			}else{
				for(String c:columns.keySet()){
					String columnName=' '+aliasNameAndDot+columns.get(c).getColumnName();
					
					String aliasName=' '+c;
					
					selectSection=replaceAll(selectSection,aliasName,columnName);
					condition=replaceAll(condition,aliasName,columnName);		
				}
			}
		}
		return new String[]{selectSection,condition};
	}
}
