package net.jxva.dao.sqlparse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ParserStudy {

	private List<String> subSelect;
	private List<String> params;
	private String select;
	
	private boolean isFetch;
	private int relation;
	private String[] tables;
	private String condition=Token.EMPTY; //设置默认值,防止NULL Exception
	private boolean isSubSelect;
	
	public void setSubSelect(boolean isSubSelect) {
		this.isSubSelect = isSubSelect;
	}

	public ParserStudy(){
		this(false);
	}
	
	public ParserStudy(boolean isSubSelect){
		this.isSubSelect=isSubSelect;
	}
			
	public void parse(String jql){
		//if(!isSubSelect){
			jql=jql.trim();// 去除前后空白
	        jql=jql.replaceAll("(\\n+|\\r+)", " ");// 将Sql语句中原有的回车换行替换成空白
			Matcher matcher = Pattern.compile("'(?:[^']|'')*'").matcher(jql);//单引号内数据独立
			params=new ArrayList<String>();
			while(matcher.find()){
				final String result=matcher.group();
				params.add(result);
				jql=jql.replace(result,"??");//将所有单引号内数据替换为??
			}
			jql=jql.replaceAll(" {2,}",Token.WHITESPACE);
		//}
		int wherePos=jql.indexOf(Token.WHERE);
		int orderByPos=jql.lastIndexOf(Token.ORDERBY);
		int fromPos=jql.startsWith("from ")?5:jql.indexOf(" from ")+6;
		
		select=jql.startsWith("select")?jql.substring(0,fromPos-5):Token.NULL;
		
		String from=null;
		if(wherePos>-1){
			from=jql.substring(fromPos,wherePos);
			condition=jql.substring(wherePos);
			if(!isSubSelect){
				//为了在where条件中支持子查询
				Matcher subMatcher = Pattern.compile("\\([^()]*\\)").matcher(condition);//子查询数据独立
				subSelect=new ArrayList<String>();
				while(subMatcher.find()){
					final String result=subMatcher.group();
					if(result.indexOf("select ")==-1)continue;
					subSelect.add(result);
					condition=condition.replace(result,"!!");//将所有子查询替换为!!
				}
			}
		}else{
			if(orderByPos>-1){
				from=jql.substring(5,orderByPos);
				condition=jql.substring(orderByPos+1);
			}else{
				from=jql.substring(5);
			}
		}
		from=from.replaceAll(Token.OUTER,Token.WHITESPACE);
		int joinPos=from.indexOf(Token.JOIN);
		
		if(!isSubSelect&&joinPos>-1){
			tables=new String[2];
			from=from.replaceAll(Token.JOIN,Token.WHITESPACE);
			if(from.indexOf(Token.FETCH)>-1){
				from=from.replaceAll(Token.FETCH,Token.WHITESPACE);
				isFetch=true;
			}
			if(from.indexOf(Token.INNER)>-1){
				relation=Relation.INNER;
				tables=from.split(Token.INNER);
			}else if(from.indexOf(Token.LEFT)>-1){
				relation=Relation.LEFT;
				tables=from.split(Token.LEFT);
			}else if(from.indexOf(Token.RIGHT)>-1){
				relation=Relation.RIGHT;
				tables=from.split(Token.RIGHT);
			}else if(from.indexOf(Token.FULL)>-1){
				relation=Relation.FULL;
				tables=from.split(Token.FULL);
			}else{
				throw new UnsupportedOperationException();
			}
			String s=tables[1].substring(tables[1].indexOf('.')+1);
			tables[1]=new StringBuilder().append(String.valueOf(s.charAt(0)).toUpperCase()).append(s.substring(1)).toString();
		}else{
			relation=Relation.DEFAULT;
			tables=from.split("\\,");
		}
		for(int i=0;i<tables.length;i++){
			tables[i]=tables[i].trim();
		}
	}
	
	public List<String> getParams() {
		return params;
	}

	public String getSelect() {
		return select;
	}
	
	public boolean isFetch() {
		return isFetch;
	}

	public int getRelation() {
		return relation;
	}

	public String[] getTables() {
		return tables;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public List<String> getSubSelect() {
		return subSelect;
	}

	public boolean isSubSelect() {
		return isSubSelect;
	}
}
