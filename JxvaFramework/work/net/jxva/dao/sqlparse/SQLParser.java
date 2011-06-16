package net.jxva.dao.sqlparse;

import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jxva.dao.sqlparse.clause.FromClause;
import net.jxva.dao.sqlparse.entry.FromEntry;
import net.jxva.dao.sqlparse.entry.JQLEntry;
import net.jxva.dao.sqlparse.entry.ModelEntry;



/**
 * sql：
select a.x max(b.m_num) from table a,(select sum(num)as m_num from table group by x,y) b where a.x=b.x group by a.x;
hql：
select a.x,max(num) from table as a where num = some (select sum(b.num)from table as b group by b.x,b.y) and a.x=b.x group by a.x;

子查询中引用一个外部查询的别名也是被允许的
如:from Book   b left   join fetch  b.user u where b.userId in (select u.userId from u where u.username like '%t%') and  b.title like '%c%' order by b.bookId desc


 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-24 16:43:05 by Jxva
 */
public class SQLParser{
	
	public String parse(Parser parser,String jql) {
		AST ast=parser.parse(jql);
		if(ast.hasExistCache()){
			return ((JQLEntry)(JQLCache.getCache().getQuietValue(ast.getJql()))).getSql();
		}
		FromClause fc=new FromClause();
		fc.parse(ast.getFrom());
		String fromClause=fc.getClause();
		fromEntrys=fc.getFromEntrys();
		
		String selectClause=ast.getSelect();
		String condition=ast.getCondition();
		boolean hasSelectClause=selectClause!=Token.NULL;
		String cacheJql=null;
		if(!ast.isSubSelect()){
			cacheJql=ast.getJql();
			hasMainSelectClause=hasSelectClause;
		}
		
		SortedMap<String, ModelEntry> cache=ModelCache.getCache();
		
		StringBuilder selectBuf=new StringBuilder(Token.SELECT);
		int index=0;		
		for(int i=0;i<fromEntrys.length;i++){
			String modelName=fromEntrys[i].getModelName();
			String aliasName=fromEntrys[i].getAliasName();
			
			ModelEntry m=cache.get(modelName);
			SortedSet<String> columns=m.getColumns();
			String ts1AndDot=aliasName+'.';
			for(String c:columns){
				selectBuf.append(aliasName).append('.');
				//condition=condition.replaceAll(ts[0],ts[1]);//将对象名替换为别名
				if(hasSelectClause)selectClause=selectClause.replaceAll(modelName+'.',ts1AndDot);
				condition=condition.replaceAll(modelName+'.',ts1AndDot);//将对象名替换为别名
				if(containsUpperChar(c)){
					String columnName=getColumnName(c);
					selectBuf.append(columnName);
					boolean hasAlias=condition.indexOf('.')>-1;//是否带别名
					if(hasSelectClause)selectClause=selectClause.replaceAll(hasAlias?ts1AndDot+c:c,ts1AndDot+columnName);
					condition=condition.replaceAll(hasAlias?ts1AndDot+c:c,ts1AndDot+columnName);			
				}else{
					selectBuf.append(c);
				}
				if(!hasSelectClause){
					if(ast.isSubSelect()){
						selectBuf.append(" as sc");
					}else{
						selectBuf.append(" as c");
					}
					selectBuf.append(index).append(',');
					index++;
				}
			}
			//condition=condition.replaceAll(" "+ts[1]+" "," "+m.getTableName()+" "+ts[1]+" ");//for subSelect
		}
		boolean isOver=false;
		//only for subSelect
		List<String> subSelect=ast.getSubSelect();
		if(subSelect!=null){
			if(!subSelect.isEmpty()){
				SubJQLParser sub=new SubJQLParser(ast.getParams(),fromEntrys);
				for(String s:subSelect){
					String subSql=parse(sub,s.substring(1,s.length()-1));
					String[] marks=condition.split(Token.SUB_CLAUSE_MARK);
					if(marks.length>1||condition.endsWith(Token.SUB_CLAUSE_MARK)){
						condition=marks[0]+subSql+condition.substring(marks[0].length()+2);
					}
				}
				fromEntrys=sub.getFromEntrys();
			}
			isOver=true;
			//System.out.println(subSelect.size());
			//System.out.println("执行DDDDDDDD");
		}
		
		//终结时才执行
		if(isOver){
			condition=condition.replaceAll("@","*");
			List<String> params=ast.getParams();
			for(String s:params){
				Pattern p = Pattern.compile("\\?\\?");
				Matcher matcher = p.matcher(condition);
				while(matcher.find()){
					final String result=matcher.group();
					condition=condition.replace(result,s);
				}
			}
		}
		//System.out.println(parser.isFetch());
		
		if(!hasSelectClause)selectClause=selectBuf.substring(0,selectBuf.length()-1);
		String conditionClause=condition;
		String sql=(selectClause+fromClause+conditionClause).trim();
		
		if(ast.isSubSelect()){
			sql="("+sql+")";
		}
				
		//终结时才执行
		if(isOver){
			JQLEntry jqlEntry=new JQLEntry();
			jqlEntry.setSql(sql);
			jqlEntry.setFromEntrys(fromEntrys);
			jqlEntry.setHasMainSelectClause(hasMainSelectClause);
			JQLCache.getCache().put(cacheJql,jqlEntry);
			//System.out.println("设置缓存");
			//System.out.println("DDDD:"+JQLCache.getCache().getKeys().length);
		}
		return sql;
	}
	
	public String getColumnName(String name){
		StringBuilder sb = new StringBuilder(16);
		sb.append(name);
		int t=0;
		for(int i=0;i<name.length();i++){
			if(Character.isUpperCase(name.charAt(i))){
				sb.insert(i+t,'_');
				t++;
			}
		} 
		return sb.toString().toLowerCase();
	}
	
	public boolean containsUpperChar(String s){
		for(int i=0;i<s.length();i++){
			if(Character.isUpperCase(s.charAt(i))){
				return true;
			}
		} 
		return false;
	}
	
	private boolean hasMainSelectClause;
	private FromEntry[] fromEntrys;
	public FromEntry[] getFromEntrys() {
		return fromEntrys;
	}

	public boolean hasMainSelectClause() {
		return hasMainSelectClause;
	}
}

