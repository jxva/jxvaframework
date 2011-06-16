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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-25 08:12:18 by Jxva
 */
public class JQLParser implements Parser {
				
	public AST parse(String jql){
	    jql=jql.replaceAll("(\\n+|\\r+)", " ");// 将jql语句中原有的回车换行替换成空白
		jql=jql.trim();// 去除前后空白
		Matcher matcher = Pattern.compile("'(?:[^']|'')*'").matcher(jql);//匹配单引号内数据,以便独立提取
		List<String> params=new ArrayList<String>();
		while(matcher.find()){
			final String result=matcher.group();
			params.add(result);
			jql=jql.replace(result,"??");//将所有单引号内数据替换为??
		}
		
		jql=jql.replaceAll(" {2,}",Token.WHITESPACE);//清理所有连续空格为单个空格
		
		AST ast=new AST();
		ast.setJql(jql);
		//在些进行缓存判断,如果缓存中存在jql,将不再进行以下任何操作
		if(JQLCache.getCache().containsKey(jql)){
			ast.setHasExistCache(true);
			return ast;
		}

		ast.setParams(params);//暂存单引号内数据
		//---------------------------------------------
		int fromPos=jql.startsWith("from ")?5:jql.indexOf(" from ")+5;//获取from子句起始位置
		ast.setSelect(jql.startsWith("select")?jql.substring(0,fromPos-5):Token.NULL);//获取select子句
		String from,condition;
		List<String> subSelect=null;
		
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
				}else{//不存在where,group by,order by 判断是否存在union
					int unionPos=jql.lastIndexOf(" union ");
					if(unionPos>-1){
						from=jql.substring(fromPos,unionPos);
						condition=jql.substring(unionPos);
					}else{//不存在where,group by,order by,union则condition赋值为空
						from=jql.substring(fromPos);
						condition=Token.EMPTY;
					}
				}
			}
		}
		if(condition.length()>0){
			//将condition中的*替换为@
			condition=condition.replaceAll("\\*","@");
			//为了在where条件中支持子查询
			Matcher subMatcher = Pattern.compile("\\(([^()]|\\([^()]*\\))*\\)").matcher(condition);//子查询数据独立,匹配最内两层
			subSelect=new ArrayList<String>();
			while(subMatcher.find()){
				String result=subMatcher.group();
				if(result.indexOf("select ")==-1)continue;//如果子查询中不包括select语句,将返回继续
				subSelect.add(result);
				//result=result.replaceAll("\\(","\\\\(");
				//result=result.replaceAll("\\)","\\\\)");
				condition=condition.replace(result,Token.SUB_CLAUSE_MARK);//将所有子查询替换为!!
			}
		}
		//System.out.println(condition);
		ast.setFrom(from);
		ast.setCondition(condition);
		ast.setSubSelect(subSelect);
		return ast;
	}
}
