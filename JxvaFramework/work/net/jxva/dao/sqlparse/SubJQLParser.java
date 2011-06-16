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
package net.jxva.dao.sqlparse;

import java.util.List;

import net.jxva.dao.sqlparse.entry.FromEntry;

/**
 * 	
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-25 08:18:48 by Jxva
 */
public class SubJQLParser implements Parser {

	private List<String> params;
	private FromEntry[] fromEntrys;
	
	public SubJQLParser(List<String> params,FromEntry[] fromEntrys){
		this.params=params;
		this.fromEntrys=fromEntrys;
	}
			
	public AST parse(String jql){
		AST ast=new AST();
		
		int fromPos=jql.startsWith("from ")?5:jql.indexOf(" from ")+5;//获取from子句起始位置
		ast.setSelect(jql.startsWith("select")?jql.substring(0,fromPos-5):Token.NULL);//获取select子句
		String from,condition;		
		int wherePos=jql.indexOf(Token.WHERE);
		if(wherePos>-1){//存在where语句 condition赋值为where语句
			from=jql.substring(fromPos,wherePos);
			condition=jql.substring(wherePos);
		}else{//不存在where语句,判断是否存在group by 与 order by
			int groupByPos=jql.lastIndexOf(Token.GROUPBY);
			if(groupByPos>-1){//存在group by  condition赋值为group by语句
				from=jql.substring(fromPos,groupByPos);
				condition=jql.substring(groupByPos);
			}else{//不存在group by 判断是否存在order by
				int orderByPos=jql.lastIndexOf(Token.ORDERBY);
				if(orderByPos>-1){// condition赋值为order by语句
					from=jql.substring(fromPos,orderByPos);
					condition=jql.substring(orderByPos);
				}else{//不存在where,group by,order by 则condition赋值为空
					from=jql.substring(fromPos);
					condition=Token.EMPTY;
				}
			}
		}	
		
		ast.setIsSubSelect(true);
		ast.setParams(params);
		ast.setCondition(condition);
		ast.setFrom(from);
		return ast;
	}
	
	public FromEntry[] getFromEntrys() {
		return fromEntrys;
	}
	

}
