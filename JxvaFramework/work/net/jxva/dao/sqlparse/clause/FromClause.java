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
package net.jxva.dao.sqlparse.clause;

import java.lang.annotation.Annotation;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jxva.dao.sqlparse.ModelCache;
import net.jxva.dao.sqlparse.Relation;
import net.jxva.dao.sqlparse.SQLParseException;
import net.jxva.dao.sqlparse.Token;
import net.jxva.dao.sqlparse.entry.FromEntry;
import net.jxva.dao.sqlparse.entry.ModelEntry;

import com.jxva.dao.OneToMany;
import com.jxva.dao.OneToOne;
import com.jxva.util.RandomUtil;

/**
 * 
 * 

Book left join User right join Publish
Book b left join b.user right join fetch b.publish


public Table{
    String mainTable;
    String mainRelation;
    String mainAliasName;
    
    其它表
    什么关系 是否取 名称  别名
    
    
}

 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-22 11:24:40 by Jxva
 */
public class FromClause implements Clause {
	
	private FromEntry[] fromEntrys;
		
	public void parse(String clause)throws SQLParseException{
		clause=clause.replaceAll(Token.OUTER,Token.WHITESPACE);
		//System.out.println(clause);
		int joinPos=clause.indexOf(Token.JOIN);
		if(joinPos>-1){
			Pattern p=Pattern.compile("(inner\\sjoin\\sfetch)|(left\\sjoin\\sfetch)|(right\\sjoin\\sfetch)|(full\\sjoin\\sfetch)|(join\\sfetch)|(inner\\sjoin)|(left\\sjoin)|(right\\sjoin)|(full\\sjoin)|(cross\\sjoin)|,|join");			
			//System.out.println();
			String[] s = p.split(clause);
			fromEntrys=new FromEntry[s.length];
			for(int i=0;i<s.length;i++){
				s[i]=s[i].trim();
				//System.out.println(s[i]);
				fromEntrys[i]=new FromEntry();
				
				int dotPos=s[i].indexOf('.');
				if(dotPos>-1){
					fromEntrys[i].setPrefixName(s[i].substring(0,dotPos));
					String ss=s[i].substring(dotPos+1);
					s[i]=String.valueOf(ss.charAt(0)).toUpperCase()+ss.substring(1);
				}
				if(s[i].indexOf(Token.WHITESPACE)==-1){//jql不带别名
					s[i]=composeTable(s[i]);
				}
				String[] sArray=s[i].split(Token.WHITESPACE);
				
				
				fromEntrys[i].setModelName(sArray[0]);
				fromEntrys[i].setAliasName(sArray[1]);
				//System.out.println(s[i]);
				//System.out.println(fromEntrys[i]);
			}
			//System.out.println();
			String[] relations=new String[s.length-1];
			Matcher m=p.matcher(clause);
			int i=0;
			while (m.find()) {
				String result=m.group();
				relations[i]=result;
				//System.out.println(result);
				
				if(result.startsWith("join")){
					fromEntrys[i].setRelation(Relation.INNER);
				}else{
					int wsPos=result.indexOf(Token.WHITESPACE);
					String sss=wsPos==-1?"":result.substring(0,wsPos);
					fromEntrys[i].setRelation(Relation.getValue(sss));
				}
				if(result.indexOf("fetch")>-1)fromEntrys[i].setIsFetch(true);
				i++;
				//sql=sql.replaceAll(result,Token.WHITESPACE);
			}
			//System.out.println(clause);
		}else{
			//System.out.println("default ,");
			String[] s=clause.split("[\\s]*,[\\s]*");
			fromEntrys=new FromEntry[s.length];
			for(int i=0;i<s.length;i++){
				s[i]=s[i].trim();
				int dotPos=s[i].indexOf('.');
				if(dotPos>-1){
					String ss=s[i].substring(dotPos+1);
					s[i]=String.valueOf(ss.charAt(0)).toUpperCase()+ss.substring(1);
				}
				if(s[i].indexOf(Token.WHITESPACE)==-1){//jql不带别名
					s[i]=composeTable(s[i]);
				}
				String[] sArray=s[i].split(Token.WHITESPACE);
				fromEntrys[i]=new FromEntry();
				fromEntrys[i].setModelName(sArray[0]);
				fromEntrys[i].setAliasName(sArray[1]);
				fromEntrys[i].setRelation(Relation.DEFAULT);
				//System.out.println(s[i]);
			}
		}
		
	}
	
	public String getClause() throws SQLParseException{
		
		StringBuilder sb=new StringBuilder(Token.FROM);
		if(fromEntrys.length==1){
			sb.append(getTableAndAlias(fromEntrys[0]));
			return sb.toString();
		}
		try{
			for(int i=0;i<fromEntrys.length-1;i++){
				FromEntry firstFromEntry=fromEntrys[0];
				FromEntry curFromEntry=fromEntrys[i];
				FromEntry nextFromEntry=fromEntrys[i+1];
				
				SortedMap<String, ModelEntry> cache=ModelCache.getCache();
	
				ModelEntry firstModelEntry=cache.get(fromEntrys[0].getModelName());				
	
				if(i==0){
					sb.append(getTableAndAlias(firstFromEntry));
				}
				
				int relation=curFromEntry.getRelation();
				sb.append(Relation.getValue(relation));
				
				sb.append(getTableAndAlias(nextFromEntry));
				
				if(relation>Relation.DEFAULT){
					sb.append(Token.ON);
					
					Annotation annotation=firstModelEntry.getClassName().getDeclaredField(firstCharToLower(nextFromEntry.getModelName())).getAnnotations()[0];
					
					if(annotation instanceof OneToOne){//注解不支持多态,晕
						OneToOne oneToOne=(OneToOne)annotation;
						sb.append(firstFromEntry.getAliasName()).append('.').append(oneToOne.field()).append('=');
						sb.append(nextFromEntry.getAliasName()).append('.').append(oneToOne.foreignKey());
					}else{
						OneToMany oneToMany=(OneToMany)annotation;
						sb.append(firstFromEntry.getAliasName()).append('.').append(oneToMany.field()).append('=');
						sb.append(nextFromEntry.getAliasName()).append('.').append(oneToMany.foreignKey());
					}
				}
				//System.out.println(fromEntrys[i]);
			}
		}catch(NoSuchFieldException e){
			throw new SQLParseException(e);
		}catch(SecurityException e){
			throw new SQLParseException(e);
		}
		return sb.toString();
	}
	
	public static void main(String[] args){
		//String from="Book b join b.author a left join b.user u right join fetch b.publish p join fetch b.sale s";
		//String from="Book b right join b.user u left join fetch b.publish p";
		//String from="Book left join  User right join fetch Publish";
		//String from="Book b,User u,Publish p";
		//String from="Book,User  ,  Publish  ";
		//String from="Book";
		//String from="Book b";
		String from="Book b left join b.user,b.publish";
		FromClause fromClause=new FromClause();
		fromClause.parse(from);
		System.out.println(fromClause.getClause());
	}

	public void setFromEntrys(FromEntry[] fromEntrys) {
		this.fromEntrys = fromEntrys;
	}

	public FromEntry[] getFromEntrys() {
		return fromEntrys;
	}
	
	private String getTableAndAlias(FromEntry fromEntry){
		return new StringBuilder().append(ModelCache.getCache().get(fromEntry.getModelName()).getModel().tableName()).append(' ').append(fromEntry.getAliasName()).toString();
	}
	
	private String firstCharToLower(String s){
		return String.valueOf(s.charAt(0)).toLowerCase()+s.substring(1);
	}
	
	private String composeTable(String s){
		StringBuilder sb=new StringBuilder();
		sb.append(s).append(Token.WHITESPACE).append(String.valueOf(s.charAt(0)).toLowerCase()).append(s.substring(1));
		sb.append(RandomUtil.getRandomNum(100));
		return sb.toString();
	}
}
