package net.jxva.dao.sqlparse;

import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jxva.dao.sqlparse.entry.ModelEntry;

public class SqlParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SqlParser11 parser=new SqlParser11();
		//parser.parse("from User full oUter join fetch Book where username=' whe\"re is '' order by ' and User.userId=Book.userId gRoup By User.username having count(*)>6 order by User.userId,User.username desc");
		parser.parse("from Book   b inner join fetch  b.user   u where b.userId=u.userId and  b.title like '%c%' order by b.bookId desc");
		System.out.println(parser.getJqlClause());
		//System.out.println(parser.getWhereCause());
		System.out.println(toSql(parser));
	}

	public static String toSql(SqlParser11 parser){
		SortedMap<String,ModelEntry> cache=ModelCache.getCache();
		String[] tablesCause=parser.getTablesClause();
		String jql=parser.getJqlClause();
		for(String table:tablesCause){
			ModelEntry m=cache.get(table);
			String tableName=m.getModel().tableName();
			jql=jql.replaceAll(table,tableName);
			SortedSet<String> upperColumns=m.getUpperCharColumns();
			for(String upperColumn:upperColumns){
				String column=tableName+'.'+upperColumn;
				jql=jql.replaceAll(column,getColumnName(column));
			}
		}
		
		//where 处理
		List<String> params=parser.getParams();
		for(String s:params){
			Pattern p = Pattern.compile("\\?\\?");
			Matcher m = p.matcher(jql);
			while(m.find()){
				final String result=m.group();
				jql=jql.replace(result,s);
			}
		}
		
		//select处理
		StringBuilder sb=new StringBuilder();
		if(parser.getSelectClause()==null){
			for(String table:tablesCause){
				ModelEntry m=cache.get(table);
				for(String c:m.getColumns()){
					sb.append(',').append(m.getModel().tableName()).append('.');
					if(!c.equals(c.toLowerCase())){
						sb.append(getColumnName(c));
					}else{
						sb.append(c);
					}
				}
			}
		}
		
		return "select "+sb.substring(1).toString()+" "+jql;
	}
	
	public static String getColumnName(String name){
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
}
