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
package net.jxva.dao;

import java.util.regex.Pattern;

import net.jxva.dao.sqlparse.SQLParseException;



/**
	  A,B
	  A (fetch) B
	  A inner join (fetch) B
	  A left (outer) join (fetch) B
	  A right (outer) join (fetch) B
	  A full (outer) join (fetch) B
	  
 	  Book.userInfo =  'fdsf''fdsds' or Author.id= 43 and email like ' % fdsa % ' and msgid in (12,25,2) 
 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-05 21:31:38 by Jxva
 */
public class SqlParse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test1();
	}
	public static void test1(){
		parse("from User");
		parse("from User where userId=9      order  by userId");
		parse("from User,Book where User.userId=Book.userId  order   by userId desc");
		parse("from User inner join Book where User.userId=Book.userId");
		parse("from User left  outer join Book where User.userId=Book.userId and Book.title like '% fdsa %'     order    by User.userId");
		parse("select User.*,Book.* from User right outer join Book where User.userId=Book.userId");	
		parse("from User full  outer join Book where username=' where is '' order by ' and User.userId=Book.userId order by User.userId desc");

	}
	public static String parse(String jql)throws SQLParseException{
		String ql=jql.trim();//去掉前后空格
		String originJql=jql.replaceAll("'(?:[^']|'')*'","? ");//将''替换为?
		originJql=clearWitchSpace(originJql.trim()); //清理多余空格
		System.out.println("originJql:       "+originJql);
				
		if(originJql.toLowerCase().indexOf(" group by ")>-1){
			throw new UnsupportedOperationException(" Unsupported group by operation,please see findByJql() ");
		}
		
		int srcOrderByPos=originJql.toLowerCase().lastIndexOf(" order by ");
		String srcOrderBy=srcOrderByPos==-1?null:originJql.substring(srcOrderByPos);
		
		int fromPos=originJql.startsWith("from")?0:originJql.indexOf(" from ");
		int wherePos=ql.indexOf(" where ");
		int orderByPos=originJql.indexOf(" order by ");
		String srcSelect=ql.startsWith("select")?ql.substring(0,fromPos):"select * ";
		String srcFrom=ql.substring(fromPos,wherePos==-1?ql.length():wherePos);
		String orderBy=orderByPos==-1?null:originJql.substring(orderByPos+1);
		String srcWhere=null;
		if(wherePos>-1){
			srcWhere=ql.substring(wherePos+1,ql.length());
			if(orderByPos>-1){
				srcWhere=srcWhere.replaceAll(srcOrderBy,"");
			}
		}
		//String srcWhere=wherePos==-1?null:ql.substring(wherePos+1,orderByPos==-1?ql.length():ql.lastIndexOf(" order by ")+1);
		
		//System.out.println(jql);
		System.out.println("srcSelect	:"+srcSelect);
		System.out.println("srcForm  	:"+srcFrom);
		System.out.println("srcWhere 	:"+srcWhere);
		System.out.println("srcOrderBy 	:"+srcOrderBy);
		System.out.println("orderBy 	:"+orderBy);
		
		String from=clearWitchSpace(srcFrom.trim()).substring(5);
		
		String[] tables=null;
		
		if(from.indexOf(',')>-1){//多表
			tables=from.split("\\,");
			System.out.println("多表..............");
		}else if(from.indexOf(" join ")>-1){//关联表
			from=from.replaceAll("outer join","join");
			String[] tbls=from.split(" ");
			tables=new String[]{tbls[0],tbls[3]};
			
			if(from.indexOf("inner join")>-1){//内联接
				System.out.println("内联接..............");
			}else if(from.indexOf("left join")>-1){//左外联接
				System.out.println("左外联接..............");
			}else if(from.indexOf("right join")>-1){//右外联接
				System.out.println("右外联接..............");
			}else{//全外联接
				System.out.println("全外联接..............");
			}
		}else{//单个表
			tables=new String[]{from};
			System.out.println("单个表..............");
			
		}
		System.out.println("tables   	:"+arrayOf(tables));
		System.out.println("from     	:"+from);
		System.out.println("jql=		:from "+from+" "+ (srcWhere==null?"":srcWhere.trim()) +" "+ (orderBy==null?"":orderBy.trim()));
		
		System.out.println("---------------------------------------------------------------------");
		return from;
	}
	
	public static String arrayOf(String...array){
		if(array==null||array.length==0)return null;
		StringBuilder sb=new StringBuilder();
		for(String s:array){
			sb.append(',').append(s);
		}
		return sb.substring(1);
	}

	public static String clearWitchSpace(String jql){
		return Pattern.compile(" {2,}").matcher(jql).replaceAll(" ");
	}
	
	public static void test(){
	  String span="select aaaa.id   name ,hello ,type t,h from datas aaaa,city b  where a.id=b.id and c like 'e%'  and name is null ";
	  span=span.toUpperCase();//测试用sql语句
	  System.out.println(span);
	  String column="(\\w+\\s*(\\w+\\s*){0,1})";//一列的正则表达式 匹配如 product p
	  String columns=column+"(,\\s*"+column+")*"; //多列正则表达式 匹配如 product p,category c,warehouse w
	  String ownerenable="((\\w+\\.){0,1}\\w+\\s*(\\w+\\s*){0,1})";//一列的正则表达式 匹配如 a.product p
	  String ownerenables=ownerenable+"(,\\s*"+ownerenable+")*";//多列正则表达式 匹配如 a.product p,a.category c,b.warehouse w

	  String from="FROM\\s+"+columns;
	  String condition="(\\w+\\.){0,1}\\w+\\s*(=|LIKE|IS)\\s*'?(\\w+\\.){0,1}[\\w%]+'?";//条件的正则表达式 匹配如 a=b 或 a is b..
	  String conditions=condition+"(\\s+(AND|OR)\\s*"+condition+"\\s*)*";//多个条件 匹配如 a=b and c like 'r%' or d is null
	  String where="(WHERE\\s+"+conditions+"){0,1}";
	  String pattern="SELECT\\s+(\\*|"+ownerenables+"\\s+"+from+")\\s+"+where+"\\s*"; //匹配最终sql的正则表达式
	  System.out.println(pattern);//输出正则表达式
	  System.out.println(span.matches(pattern));//是否比配


	 }
}
