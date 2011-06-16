package net.jxva.dao.sqlparse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SqlParser11 {

	private List<String> params;

	private String jqlClause;
	private String selectClause;
	private String fromClause;
	private String whereClause;
	private String orderByClause;
	private String groupByClause;
	private String[] tablesClause;
	
	
	public static void main(String[] args) {
		
		new SqlParser11().parse("from User");
		new SqlParser11().parse("from User join fetch Book order  by userId");
		new SqlParser11().parse("from User,Book where User.userId=Book.userId  order   by userId desc");
		new SqlParser11().parse("from User inner join Book where User.userId=Book.userId");
		new SqlParser11().parse("from User left  outer join Book where User.userId=Book.userId and Book.title like '% fdsa %'     order    by User.userId");
		new SqlParser11().parse("select User.*,Book.* from User right outer join Book where User.userId=Book.userId");	
		new SqlParser11().parse("from User full oUter join fetch Book where username=' whe\"re is '' order by ' and User.userId=Book.userId gRoup By User.username having count(*)>6 order by User.userId,User.username desc");
		new SqlParser11().parse("from User full     Outer join Book where username=' where is '' order by ' and User.userId=Book.userId order by User.userId desc");
		new SqlParser11().parse(" select User.*,Book.* From User full outer join fetch Book where username=' whe\"re is '' order by ' and User.userId=Book.userId gRoup By User.username having count(*)>6 order by User.userId,User.username desc");
		
	}

	public String parse(String jql)throws SQLParseException{
		jql=jql.trim();//1.去掉前后空格
		if(jql.indexOf('\'')>-1){//2.将'之间的参数'替换为??,并将参数保存到params中
			Pattern p = Pattern.compile("'(?:[^']|'')*'");
			Matcher m = p.matcher(jql);
			params=new ArrayList<String>();
			while(m.find()){
				final String result=m.group();
				params.add(result);
				jql=jql.replace(result,"??");
			}
		}
		jql=jql.replaceAll(" {2,}", " ");
		//jql=clearWitchSpace(jql.trim()); //3.清理连续空格,连续空格仅保留一个
		//System.out.println("jql:  "+jql);
				
		//if(jql.toLowerCase().indexOf(" group by ")>-1){
		//	throw new UnsupportedOperationException(" Unsupported group by operation,please see findByJql() ");
		//}
		
		String lowerJql=jql.toLowerCase();
		
		int fromPos=lowerJql.startsWith("from")?0:lowerJql.indexOf(" from ")+1;
		int wherePos=lowerJql.indexOf(" where ",fromPos);
		int groupByPos=lowerJql.indexOf(" group by ",wherePos==-1?fromPos:wherePos);
		int orderByPos=lowerJql.indexOf(" order by ",groupByPos==-1?wherePos:groupByPos);
		
		
		String srcSelect=lowerJql.startsWith("select")?jql.substring(0,fromPos):null;
		String srcFrom=jql.substring(fromPos,wherePos==-1?jql.length():wherePos);
		String srcGroupBy=groupByPos==-1?null:jql.substring(groupByPos+1,orderByPos==-1?jql.length():orderByPos);
		String srcOrderBy=orderByPos==-1?null:jql.substring(orderByPos+1);
		
		
		String srcWhere=null;
		if(wherePos>-1){
			if(groupByPos>-1){
				srcWhere=jql.substring(wherePos+1,groupByPos);
			}else{
				if(orderByPos>-1){
					srcWhere=jql.substring(wherePos+1,orderByPos);
				}else{
					srcWhere=jql.substring(wherePos+1,jql.length());
				}
			}
		}

		//System.out.println("select	:"+srcSelect);
		//System.out.println("from  	:"+srcFrom);
		//System.out.println("where 	:"+srcWhere);
		//System.out.println("groupBy :"+srcGroupBy);
		//System.out.println("orderBy :"+srcOrderBy);

		
		
		
		String[] tables=null;
		String from=srcFrom.substring(5);
		String lowerFrom=from.toLowerCase();
		if(from.indexOf(',')>-1){//多表
			tables=from.split("\\,");
			//System.out.println("多表..............");
		}else if(lowerFrom.indexOf(" join ")>-1){//关联表
			from=Pattern.compile(" outer ",Pattern.CASE_INSENSITIVE).matcher(from).replaceAll(" ");//替换掉outer不区分大小写
			String[] tbls=from.split(" ");
			
			String tbl1=tbls[0];
			String tbl2=tbls[tbls.length-1];
			if(tbl1.length()>tbl2.length()){
				tables=new String[]{tbl1,tbl2};
			}else{
				tables=new String[]{tbl2,tbl1};
			}
			
			if(lowerFrom.indexOf("inner join")>-1){//内联接
				//System.out.println("内联接..............");
			}else if(lowerFrom.indexOf("left join")>-1){//左外联接
				//System.out.println("左外联接..............");
			}else if(lowerFrom.indexOf("right join")>-1){//右外联接
				//System.out.println("右外联接..............");
			}else{//全外联接
				//System.out.println("全外联接..............");
			}
		}else{//单个表
			tables=new String[]{from};
			//System.out.println("单个表..............");
		}
		//System.out.println("tables   	:"+arrayOf(tables));
		//System.out.println("from     	:"+from);
		//System.out.println("jql=		:from "+from+" "+ (srcWhere==null?"":srcWhere) +" "+ (srcGroupBy==null?"":srcGroupBy)+" "+ (srcOrderBy==null?"":srcOrderBy));
		
		//System.out.println("---------------------------------------------------------------------");
		
		selectClause=srcSelect;
		whereClause=srcWhere;
		jqlClause=jql;
		tablesClause=tables;
		return from;
	}
	
	public String arrayOf(String...array){
		if(array==null||array.length==0)return null;
		StringBuilder sb=new StringBuilder();
		for(String s:array){
			sb.append(',').append(s);
		}
		return sb.substring(1);
	}
	
	public List<String> getParams() {
		return params;
	}

	public String getSelectClause() {
		return selectClause;
	}

	public String getFromClause() {
		return fromClause;
	}

	public String getWhereClause() {
		return whereClause;
	}
	
	public String getJqlClause(){
		return jqlClause;
	}

	public String[] getTablesClause(){
		return tablesClause;
	}
	public String getOrderByClause() {
		return orderByClause;
	}

	public String getGroupByClause() {
		return groupByClause;
	}

	
}
