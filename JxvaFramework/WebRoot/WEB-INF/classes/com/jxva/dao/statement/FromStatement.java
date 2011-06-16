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
import com.jxva.dao.entity.StatementType;
import com.jxva.dao.entity.Token;
import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.JqlEntry;
import com.jxva.dao.entry.ModelEntry;
import static com.jxva.util.StringUtil.replaceAll;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 16:00:12 by Jxva
 */
public class FromStatement extends AbstractStatement {
	
		
	public FromStatement(JqlEntry jqlEntry){
		this.jqlEntry=jqlEntry;
		//"from ".length=5
		
		final int fromPos=5;
		String finalJql=jqlEntry.getFinalJql();
		String lowerJql=finalJql.substring(fromPos).toLowerCase();
		
		int conditionPos=0;
		int wherePos=lowerJql.indexOf(Token.WHERE);
		if(wherePos>-1){//存在where语句 condition赋值为where语句
			conditionPos=wherePos;
		}else{//不存在where语句,判断是否存在group by 与 order by
			int groupByPos=lowerJql.lastIndexOf(Token.GROUPBY);
			if(groupByPos>-1){//存在group by  condition赋值为group by语句
				conditionPos=groupByPos;
			}else{//不存在group by 判断是否存在order by
				int orderByPos=lowerJql.lastIndexOf(Token.ORDERBY);
				if(orderByPos>-1){// condition赋值为order by语句
					conditionPos=orderByPos;
				}else{//不存在where,group by,order by 判断是否存在union
					int limitPos=lowerJql.lastIndexOf(" limit ");
					if(limitPos>-1){
						conditionPos=limitPos;
					}else{
						//不存在where,group by,order by 判断是否存在union
						int unionPos=lowerJql.lastIndexOf(Token.UNION);
						if(unionPos>-1){
							conditionPos=unionPos;
						}else{//不存在where,group by,order by,union
							fromSection=finalJql.substring(fromPos);
						}
					}
				}
			}
		}		
		if(conditionPos>0){
			fromSection=finalJql.substring(fromPos,conditionPos+fromPos);
		}
		setFromClause(new FromClause(fromSection));
		//generate sql
		String nativeSql=generateSql(conditionPos,finalJql);
		sql=padSingleQuoteParams(padSubStatement(nativeSql));
		//System.out.printf("%-15s %s\n","","from正在测试缓存系统..................................");
	}

	private String generateSql(int conditionPos,String finalJql){
		String condition=null;
		boolean hasAlias=false;
		if(conditionPos>0){
			condition=finalJql.substring(conditionPos+5);
			hasAlias=condition.indexOf('.')>-1;//是否带别名
		}
		FromClause fromClause=getFromClause();
		ModelEntry[] modelEntry=fromClause.getModelEntrys();
		StringBuilder sb=new StringBuilder("select ");
		int index=0;		
		for(int i=0;i<modelEntry.length;i++){
			if(i>0){
				if(modelEntry[i-1].getRelation()==Relation.FETCH)continue;
			}
			String modelName=modelEntry[i].getModelName();
			String aliasName=modelEntry[i].getAliasName();
			
			Map<String,ColumnEntry> columns=modelEntry[i].getTableEntry().getColumnEntrys();
			
			
			String aliasNameAndDot=aliasName+'.';
			String modelNameAndDot=modelName+'.';
			if(conditionPos>0){
				condition=replaceAll(condition,modelNameAndDot,aliasNameAndDot);//将对象名替换为别名
			}
			for(String c:columns.keySet()){
				sb.append(aliasName).append('.');			
				String columnName=columns.get(c).getColumnName();
				sb.append(columnName).append(" as c").append(index++).append(',');
				if(conditionPos>0){
					if(hasAlias){
						condition=replaceAll(condition,aliasNameAndDot+c,aliasNameAndDot+columnName);
					}else{
						condition=replaceAll(condition,' '+c,' '+aliasNameAndDot+columnName);
					}
				}
			}
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" from ").append(fromClause.getFromSql()).append(condition==null?"":condition);
		return sb.toString();
	}
	
	public int getStatementType() {
		return StatementType.FROM;
	}
}
