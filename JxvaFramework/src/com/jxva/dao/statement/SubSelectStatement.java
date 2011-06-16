package com.jxva.dao.statement;

import com.jxva.dao.clause.FromClause;
import com.jxva.dao.entity.StatementType;
import com.jxva.dao.entity.Token;
import com.jxva.dao.entry.JqlEntry;

public class SubSelectStatement extends AbstractSelectStatement {

	public SubSelectStatement(JqlEntry jqlEntry,String jql){
		super.jqlEntry=jqlEntry;
		
		final int fromPos=jql.indexOf(Token.FROM)+6;
		String lowerJql=jql.substring(fromPos).toLowerCase();

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
							fromSection=jql.substring(fromPos);
						}
					}
				}
			}
		}
		
		//generate sql
		
		if(conditionPos>0){
			fromSection=jql.substring(fromPos,conditionPos+fromPos);
		}
		setFromClause(new FromClause(fromSection));
		
		String selectSection=jql.substring(6,fromPos-6);
		
		String[] array=generateConditionSql(selectSection,conditionPos>0?jql.substring(conditionPos+fromPos):null);
		StringBuilder sb=new StringBuilder();
		sb.append("select ").append(array[0]).append(" from ");
		sb.append(getFromClause().getFromSql());
		if(array[1]!=null){
			sb.append(array[1]);
		}
		sql=sb.toString();
		//System.out.printf("%-15s %s\n","","subSelect正在测试缓存系统..................................");
	}

	public int getStatementType() {
		return StatementType.SUB_SELECT;
	}

}
