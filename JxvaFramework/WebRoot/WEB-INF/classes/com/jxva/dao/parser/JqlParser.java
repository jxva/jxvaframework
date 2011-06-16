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
package com.jxva.dao.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jxva.dao.ParseException;
import com.jxva.dao.Parser;
import com.jxva.dao.Statement;
import com.jxva.dao.entity.JqlCache;
import com.jxva.dao.entity.Mark;
import com.jxva.dao.entry.JqlEntry;
import com.jxva.dao.statement.DeleteStatement;
import com.jxva.dao.statement.FromStatement;
import com.jxva.dao.statement.InsertStatement;
import com.jxva.dao.statement.SelectStatement;
import com.jxva.dao.statement.UpdateStatement;
import com.jxva.log.Logger;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 14:17:55 by Jxva
 */
public class JqlParser implements Parser {

	private static final Pattern singleQuotesPattern=Pattern.compile("'(?:[^']|'')*'");
	private JqlEntry jqlEntry;
	//private byte[] lock = new byte[0]; // 特殊的instance变量
	//private static final ReentrantLock lock = new ReentrantLock();
	
	private static final Logger log=Logger.getLogger(JqlParser.class);
	
	public Statement parse(String jql)throws ParseException{
    	if(JqlCache.containsKey(jql)){
    		//System.out.printf("%-15s %s\n","","<<<<<<<<<<读缓存");
			return JqlCache.get(jql);
		}else{
			synchronized(this){
				if(JqlCache.containsKey(jql)){
					return JqlCache.get(jql);
				}
				jqlEntry=new JqlEntry(jql);
				String neatJql=clear(jql);
				String pureJql=extractSingleQuotes(neatJql);
				jqlEntry.setPureJql(pureJql);
				//String namedJql=extractNamedParams(pureJql);
				//String finalJql=extractParentheses(namedJql);
				String finalJql=extractParentheses(pureJql);
				//System.out.printf("%-15s %s\n","finalJql:",finalJql);
				jqlEntry.setFinalJql(finalJql.trim());
				Statement statement=switchStatement(finalJql);
				JqlCache.put(jql, statement);
				//System.out.printf("%-15s %s\n","","-=>> cache's size:"+JqlCache.size());
				log.info("JQL cache's size:"+JqlCache.size()+" -> '"+jql+"' ");
				return statement;
			}
		}
	}
	
	private Statement switchStatement(String finalJql)throws ParseException{
		String prefix=finalJql.substring(0,6).toLowerCase();
		if(prefix.startsWith("from")){
			return new FromStatement(jqlEntry);
		}else if(prefix.equals("select")){
			return new SelectStatement(jqlEntry);
		}else if(prefix.equals("update")){
			return new UpdateStatement(jqlEntry);
		}else if(prefix.equals("delete")){
			return new DeleteStatement(jqlEntry);
		}else if(prefix.equals("insert")){
			return new InsertStatement(jqlEntry);
		}else{
			throw new ParseException("JQL does not support this grammar,Must start with 'from' or 'select' or 'update' or 'delete' or 'insert',"+jqlEntry.getOriginJql());
		}
	}
	
	private String clear(String jql)throws ParseException{
		//1.清理空白
		return jql.replaceAll("(\\n+|\\r+)"," ").trim();// 将jql语句中原有的回车换行替换成空白
	}
	
	private String extractSingleQuotes(String neatJql)throws ParseException{
		//2.单引号数据提取
		Matcher singleQuoteMatcher = singleQuotesPattern.matcher(neatJql);//匹配单引号内数据,以便独立提取
		List<String> singleQuoteParams=new ArrayList<String>();
		while(singleQuoteMatcher.find()){
			final String result=singleQuoteMatcher.group();
			singleQuoteParams.add(result);
			neatJql=neatJql.replace(result,Mark.SINGLE_QUOTE_MARK);//将所有单引号内数据替换为??
		}
		jqlEntry.setSingleQuoteParams(singleQuoteParams);
		//3.清理连续空格为单个空格,得到一个纯的JQL语句(以便后续的缓存)
		return neatJql.replaceAll(" {2,}"," ");//清理所有连续空格为单个空格
	}
	
//	private String extractNamedParams(String pureJql)throws ParseException{
//		//4.将所有命名参数提取(此步骤可以移到更后的解析过程中)
//		Matcher namedParamsMatcher = Pattern.compile(":[a-zA-Z0-9_]+").matcher(pureJql);//匹配命名参数,以便独立提取
//		List<String> namedParams=new ArrayList<String>();
//		while(namedParamsMatcher.find()){
//			final String result=namedParamsMatcher.group();
//			namedParams.add(result);
//			pureJql=pureJql.replace(result,Mark.NAMED_PARAMS_MARK);//将所有命名参数数据替换为@@
//		}
//		jqlEntry.setNamedParams(namedParams);
//		return pureJql;
//	}
	
	private String extractParentheses(String namedJql)throws ParseException{
		//5.将所有小括号中的子语句提取,得到一个最终的JQL语句
		int begin=0,end=0;
		int start=0;
		List<String> subStatements=new ArrayList<String>();
		for(int i=0;i<namedJql.length();i++){
			char c=namedJql.charAt(i);
			if(c=='('){
				begin++;
				if(begin==1)start=i;
			}else if(c==')'){
				end++;
				if(begin==end){
					begin=end=0;
					String subJql=namedJql.substring(start+1,i).trim();
					if(subJql.toLowerCase().startsWith("select")){
						subStatements.add(subJql);
					}
				}
			}
		}
		if(begin!=0||end!=0){
			throw new ParseException("JQL parentheses matching is illegal:"+jqlEntry.getOriginJql());
		}
		jqlEntry.setSubStatements(subStatements);
		for(String str:subStatements){
			namedJql=namedJql.replace(str,Mark.SUB_STATEMENT_MARK);
		}
		return namedJql;
	}
}
