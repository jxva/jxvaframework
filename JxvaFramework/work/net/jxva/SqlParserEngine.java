package net.jxva;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL 解析
 * 
 * @author anwenhao
 * 
 */
public class SqlParserEngine {

	private static final String TABLE_VALUE = "([^()]+)";

	/**
	 * 1. select * from table 
	 * 2. select * from table where 
	 * 3. select * from	 * tableA , tableB 
	 * 4. select * from tableA where * from (select * from	 * tableB) 
	 * 5. select * from tableA where * from (select * from tableB where * from (select * from tableC))... 
	 * 6. select * from tableA union select * from tableB 
	 * 7. select * from tableA (left/right) join tableB on ...
	 * 
	 * 考虑以上7种情况的SQL parser过程 这里采用正则表达式解析
	 * 
	 * @param sql
	 * @return
	 */
	public List parserSql(String sql) {
		List result = new ArrayList();
		String metaRxp = "(?i)select ([^from]+) (?i)from " + TABLE_VALUE;
		Pattern pattern = null;
		Matcher matcher = null;
		pattern = Pattern.compile(metaRxp);
		matcher = pattern.matcher(sql);
		while (matcher.find()) {
			String result1 = matcher.group();
			result1 = this.parserTable(result1, metaRxp);
			// 处理是否有where的情况
			String regx = TABLE_VALUE + " (?i)where ([^;]+)";
			Pattern pattern1 = Pattern.compile(regx);
			Matcher matcher1 = pattern1.matcher(result1);
			if (matcher1.find()) {
				String result2 = matcher1.group();
				String tableName = this.parserTable(result2, regx);
				result.addAll(getTableResult(tableName));
			} else {
				// 这里有两种情况。一种是标准的另一种是join的
				String regx2 = TABLE_VALUE + " (?i)join " + "([^;]+)";
				Pattern pattern2 = Pattern.compile(regx2);
				Matcher matcher2 = pattern2.matcher(result1);
				if (matcher2.find()) {
					String result3 = matcher2.group();
					String table1 = this.parserTable(result3, regx2);
					String table2 = result3.substring(result3.toLowerCase()
							.indexOf("join")
							+ "join".length(), result3.toLowerCase().indexOf(
							"on"));
					result.addAll(getTableResult(table1));
					result.addAll(getTableResult(table2));
				} else {
					result.addAll(getTableResult(result1));
				}
			}
		}
		return result;
	}

	/**
	 * 将解析出来的table的表名和别名分别存储
	 * 
	 * @param table
	 * @return
	 */
	private List getTableResult(String table) {
		List result = new ArrayList();
		String[] tempTable = table.split(",");
		for (int i = 0; i < tempTable.length; i++) {
			table = tempTable[i].trim();
			String tableResult[] = new String[2];
			String regx = "([a-zA-Z0-9_]+)([\\s]+)([a-zA-Z0-9_]+)";
			Pattern pattern1 = Pattern.compile(regx);
			Matcher matcher1 = pattern1.matcher(table);
			if (matcher1.find()) {
				String[] temp = table.split("([\\s]+)");
				if (temp.length >= 2) {
					tableResult[0] = temp[0];
					tableResult[1] = temp[1];
				}
			} else {
				tableResult[0] = table;
			}
			result.add(tableResult);
		}
		return result;
	}

	/**
	 * 通过传入符合规则的sql语句去得到当前sql的table
	 * 
	 * @param sql
	 * @param metaRxp
	 * @return
	 */
	private String parserTable(String sql, String metaRxp) {
		if (null == metaRxp || metaRxp.length() < 2) {
			return "";
		}
		int i = metaRxp.indexOf(TABLE_VALUE);
		if (i != -1) {
			String str1 = metaRxp.substring(0, i);
			String str2 = metaRxp.substring(i + TABLE_VALUE.length());
			String regex = str1 + TABLE_VALUE + str2;
			Pattern pattern = null;
			Matcher matcher = null;
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(sql);
			while (matcher.find()) {
				String functionMethod = matcher.group();
				if (functionMethod != null) {
					functionMethod = functionMethod.replaceAll(str1, "");
					functionMethod = functionMethod.replaceAll(str2, "");
					return functionMethod;
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String sql = "select * from tableA aa , tableD dd where * from (select * from tableB  where * from (select * from tableC))";
		SqlParserEngine engine = new SqlParserEngine();
		List tempList = engine.parserSql(sql);
		for (int i = 0; i < tempList.size(); i++) {
			String[] result = (String[]) tempList.get(i);
			System.out.println("表名 ：" + result[0]);
			System.out.println("别名 ：" + result[1]);
			System.out.println("==========================================");
		}
	}

}
