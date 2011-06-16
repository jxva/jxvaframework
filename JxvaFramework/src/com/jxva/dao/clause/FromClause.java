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
package com.jxva.dao.clause;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jxva.dao.Clause;
import com.jxva.dao.ParseException;
import com.jxva.dao.annotation.OneToOne;
import com.jxva.dao.entity.Relation;
import com.jxva.dao.entity.TableCache;
import com.jxva.dao.entity.Token;
import com.jxva.dao.entry.ModelEntry;
import com.jxva.dao.entry.TableEntry;
import com.jxva.util.RandomUtil;
import com.jxva.util.StringUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 17:00:05 by Jxva
 */
public class FromClause implements Clause {
	
	private final static Pattern pattern=Pattern.compile("(inner\\sjoin\\sfetch)|(left\\sjoin\\sfetch)|(right\\sjoin\\sfetch)|(full\\sjoin\\sfetch)|(join\\sfetch)|(inner\\sjoin)|(left\\sjoin)|(right\\sjoin)|(full\\sjoin)|(cross\\sjoin)|,|join",Pattern.CASE_INSENSITIVE);			
	
	private final ModelEntry[] modelEntrys;
	private final String fromSql;
	
	private final Map<String,ModelEntry> aliasModel;
	
	private final Map<String,ModelEntry> modelAlias;
	
	public FromClause(String fromSection){	
		fromSection=fromSection.replaceAll(Token.OUTER," ");
		//System.out.println(clause);
		int joinPos=fromSection.indexOf(Token.JOIN);
		
		aliasModel=new HashMap<String,ModelEntry>();
		modelAlias=new HashMap<String,ModelEntry>();
		if(joinPos>-1){
			//System.out.println();
			String[] s = pattern.split(fromSection);
			modelEntrys=new ModelEntry[s.length];
			for(int i=0;i<s.length;i++){
				s[i]=s[i].trim();
				//System.out.println(s[i]);
				modelEntrys[i]=new ModelEntry();
				int dotPos=s[i].indexOf('.');
				if(dotPos>-1){//带别名  b.Publish c / b.category c / Book.press
//					String prefixName=s[i].substring(0,dotPos);
//					if(aliasModel.containsKey(prefixName)){
//						s[i]=s[i].replace(prefixName+'.',modelAlias.get(aliasModel.get(prefixName))+'.');
//					}
					
					//下面几行主要解决,model名做为前缀的JQL
					modelEntrys[i].setPrefixName(s[i].substring(0,dotPos));
					if(i>0){
						//from Book b left join fetch b.press p left join fetch Press.pressType pt
						if(modelEntrys[i-1].getModelName().equals(modelEntrys[i].getPrefixName())){
							modelEntrys[i].setPrefixName(modelEntrys[i-1].getAliasName());
						
							//,如(from Book left join fetch Book.press left join fetch Book.category)
						}else if(modelEntrys[0].getModelName().equals(modelEntrys[i].getPrefixName())){
							modelEntrys[i].setPrefixName(modelEntrys[0].getAliasName());
						}else if(modelEntrys[0].getAliasName().equals(modelEntrys[i].getPrefixName())){
							modelEntrys[i].setPrefixName(modelEntrys[0].getAliasName());
						}
//						if(aliasModel.containsKey(modelEntrys[i].getPrefixName())){
//							modelEntrys[i].setPrefixName(aliasModel.get(modelEntrys[i].getPrefixName()).getAliasName());
//						}else if(modelAlias.containsKey(modelEntrys[i].getModelName())){
//							modelEntrys[i].setPrefixName(modelAlias.get(modelEntrys[i].getPrefixName()).getAliasName());							
//						}else if(modelAlias.containsKey(modelEntrys[i].getPrefixName())){
//							modelEntrys[i].setPrefixName(modelAlias.get(modelEntrys[i].getPrefixName()).getAliasName());							
//						}else{
//							//下面几行主要解决,model名做为前缀的JQL
//							modelEntrys[i].setPrefixName(s[i].substring(0,dotPos));
//						}
					}
					
					String modelName=s[i].substring(dotPos+1);
					s[i]=String.valueOf(modelName.charAt(0)).toUpperCase()+modelName.substring(1);
				}

				if(s[i].indexOf(' ')==-1){//不带别名 b.category
					s[i]=composeTable(s[i]);
				}
				String[] sArray=s[i].split(" ");
				
				//sArray[0]为modelName,sArray[1]为aliasName
				
				modelEntrys[i].setModelName(sArray[0]);
				modelEntrys[i].setAliasName(sArray[1]);
				modelEntrys[i].setTableEntry(TableCache.getTableEntry(sArray[0]));
				//System.out.println(s[i]);
				//System.out.println(fromEntrys[i]);
				aliasModel.put(sArray[1],modelEntrys[i]);
				modelAlias.put(sArray[0],modelEntrys[i]);
				//System.out.println("XXXX:"+modelEntrys[i]);
				
				
			}
			//System.out.println();

			Matcher m=pattern.matcher(fromSection);
			int i=0;
			while (m.find()) {
				String result=m.group().toLowerCase();		
				if(result.equals("join fetch")){//判断
					try{//OneToOne
						//System.out.println("有此字段"+result);
						modelEntrys[0].getTableEntry().getModel().getDeclaredField(StringUtil.firstCharLower(modelEntrys[i+1].getModelName()));
						modelEntrys[i].setRelation(Relation.INNER);
					}catch(NoSuchFieldException e){//Un OneToOne
						System.out.println("hasn't field");
						modelEntrys[i].setRelation(Relation.FETCH);
					}
				}else if(result.equals("join")){
					modelEntrys[i].setRelation(Relation.INNER);
				}else{
					modelEntrys[i].setRelation(Relation.getValue(result.split(" ")[0]));
				}
				modelEntrys[i+1].setCascadeModel(aliasModel.get(modelEntrys[i+1].getPrefixName()));
				//System.out.println("AAAA:"+modelEntrys[i+1].getModelName()+"->"+modelEntrys[i+1].getCascadeModel());
				
				if(result.indexOf("fetch")>-1)modelEntrys[i].setIsFetch(true);
				i++;
			}
			//System.out.println(clause);
		}else{
			//System.out.println("default ,");
			String[] s=fromSection.split("[\\s]*,[\\s]*");
			modelEntrys=new ModelEntry[s.length];
			for(int i=0;i<s.length;i++){
				s[i]=s[i].trim();
				int dotPos=s[i].indexOf('.');
				if(dotPos>-1){//带别名
					modelEntrys[i].setPrefixName(s[i].substring(0,dotPos));
					String ss=s[i].substring(dotPos+1);
					s[i]=String.valueOf(ss.charAt(0)).toUpperCase()+ss.substring(1);
				}
				if(s[i].indexOf(' ')==-1){//不带别名
					s[i]=composeTable(s[i]);//组合一个modelName+随机数的别名
				}
				String[] sArray=s[i].split(" ");
				modelEntrys[i]=new ModelEntry();
				modelEntrys[i].setModelName(sArray[0]);
				modelEntrys[i].setAliasName(sArray[1]);
				modelEntrys[i].setRelation(Relation.DEFAULT);
				modelEntrys[i].setTableEntry(TableCache.getTableEntry(sArray[0]));
				//System.out.println(s[i]);
				aliasModel.put(sArray[1],modelEntrys[i]);
				modelAlias.put(sArray[0],modelEntrys[i]);
			}
		}
		this.fromSql=generateFromSql();
	}
		
	private String generateFromSql() throws ParseException{

		StringBuilder sb=new StringBuilder();
		if(modelEntrys.length==1){
			sb.append(getTableAndAlias(modelEntrys[0]));
			return sb.toString();
		}
		ModelEntry firstModelEntry=modelEntrys[0];
		TableEntry firstTableEntry=modelEntrys[0].getTableEntry();				
		sb.append(getTableAndAlias(firstModelEntry));
		try{
			for(int i=1;i<modelEntrys.length;i++){
				ModelEntry curModelEntry=modelEntrys[i-1];
				int relation=curModelEntry.getRelation();
				if(relation==Relation.FETCH)continue;
				
				ModelEntry nextModelEntry=modelEntrys[i];
				sb.append(Relation.getValue(relation));
				sb.append(getTableAndAlias(nextModelEntry));
				
				if(relation>Relation.DEFAULT){//将带有on子语句的SQL为一对一关联
					sb.append(" on ");
					//from Book b left join fetch b.Press p left join p.pressType pt
					String nextModelPrefixName=nextModelEntry.getPrefixName();
					if(nextModelPrefixName==null||nextModelPrefixName.equals(firstModelEntry.getAliasName())){
						OneToOne oneToOne=firstTableEntry.getModel().getDeclaredField(StringUtil.firstCharLower(nextModelEntry.getModelName())).getAnnotation(OneToOne.class);
						String fieldColumnName=firstTableEntry.getColumnEntrys().get(oneToOne.field()).getColumnName();

						String foreignKeyColumnName=nextModelEntry.getTableEntry().getColumnEntrys().get(oneToOne.foreignKey()).getColumnName();
						sb.append(firstModelEntry.getAliasName()).append('.').append(fieldColumnName).append('=');
						sb.append(nextModelEntry.getAliasName()).append('.').append(foreignKeyColumnName);
						
					}else{
						OneToOne oneToOne=aliasModel.get(nextModelPrefixName).getTableEntry().getModel().getDeclaredField(StringUtil.firstCharLower(nextModelEntry.getModelName())).getAnnotation(OneToOne.class);
						String fieldColumnName=curModelEntry.getTableEntry().getColumnEntrys().get(oneToOne.field()).getColumnName();
						String foreignKeyColumnName=curModelEntry.getTableEntry().getColumnEntrys().get(oneToOne.foreignKey()).getColumnName();
						sb.append(curModelEntry.getAliasName()).append('.').append(fieldColumnName).append('=');
						sb.append(nextModelEntry.getAliasName()).append('.').append(foreignKeyColumnName);
						
					}
					
				}
				//System.out.println(fromEntrys[i]);
			}
		}catch(NoSuchFieldException e){
			e.printStackTrace();
			
			throw new ParseException(e);
		}catch(SecurityException e){
			throw new ParseException(e);
		}
		return sb.toString();
	}
	
	public String getFromSql() throws ParseException{
		return this.fromSql;
	}
		
	public ModelEntry[] getModelEntrys() {
		return this.modelEntrys;
	}
	
	private String getTableAndAlias(ModelEntry modelEntry){
		return new StringBuilder().append(modelEntry.getTableEntry().getTableName()).append(' ').append(modelEntry.getAliasName()).toString();
	}
	
	private String composeTable(String s){
		StringBuilder sb=new StringBuilder();
		sb.append(s).append(' ').append(String.valueOf(s.charAt(0)).toLowerCase()).append(s.substring(1));
		sb.append(RandomUtil.getRandomNum(100));
		return sb.toString();
	}
}
