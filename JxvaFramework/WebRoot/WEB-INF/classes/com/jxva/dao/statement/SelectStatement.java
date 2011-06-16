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

import com.jxva.dao.clause.FromClause;
import com.jxva.dao.entity.StatementType;
import com.jxva.dao.entity.Token;
import com.jxva.dao.entry.JqlEntry;

/**
 * 主要获取from中的Table,并将其组合成fromClause
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 14:19:33 by Jxva
 */
public class SelectStatement extends AbstractSelectStatement {
	
	public SelectStatement(final JqlEntry jqlEntry){
		super.jqlEntry=jqlEntry;
		final String finalJql=jqlEntry.getFinalJql();
		
		final int fromPos=finalJql.indexOf(Token.FROM)+6;
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
				}else{
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
		
		//generate sql
		
		if(conditionPos>0){
			fromSection=finalJql.substring(fromPos,conditionPos+fromPos);
		}
		setFromClause(new FromClause(fromSection));
		String selectSection=finalJql.substring(6,fromPos-6);
		String[] array=generateConditionSql(selectSection,conditionPos>0?finalJql.substring(conditionPos+fromPos):null);
		
		StringBuilder sb=new StringBuilder();
		sb.append("select").append(array[0]).append(" from ");
		sb.append(getFromClause().getFromSql());
		if(array[1]!=null){
			sb.append(array[1]);
		}
		
		//generate sql
		sql=padSingleQuoteParams(padSubStatement(sb.toString()));
		//System.out.printf("%-15s %s\n","","select正在测试缓存系统..................................");
	}
		
	public int getStatementType() {
		return StatementType.SELECT;
	}
}
