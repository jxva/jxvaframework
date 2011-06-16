package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/31/00 1:17:58 AM)
 * @author:  Jianguo Lu
 */
import java.util.*;


import sql4j.schema.*;
public class Column {
	private String tableName;
	private ColumnName columnName; 
	private String schemaName;
	private Column substitute;

public Column(ColumnName column){
	this.columnName = column;
	}
public Column(String columnString) {
	java.util.StringTokenizer st = new StringTokenizer(columnString, ".");
	int count = st.countTokens();
	if (count == 1) {
		columnName = new ColumnName(st.nextToken());
	} else
		if (count == 2) {
			tableName = st.nextToken();
			columnName = new ColumnName(st.nextToken());
		} else
			if (count == 3) {
				schemaName = st.nextToken();
				tableName = st.nextToken();
				columnName = new ColumnName(st.nextToken());
			}
}
public Column(String table, ColumnName column) {
	tableName = table;
	columnName = column;
}
public Column(String s1, String s2) {
	tableName = s1;
	columnName = new ColumnName(s2);
}
public Column(String s1, String s2, String s3) {
	tableName = s1;
	columnName = new ColumnName(s2);
	schemaName = s3;
}
	public void addTableName(String tn){
		if (tableName==null){
			tableName=tn;
		}
	}

	public boolean equals(Column c){
		return this.toString().equalsIgnoreCase(c.toString());
	}
	public ColumnName getColumnName(){
		return columnName;
	}
public String getName() {
	String result;
	if (substitute == null) {
		result = columnName.toString();
	} else
		result = columnName.toString() + "/" + substitute.getName();
	return result;
}
	public Column getSubstitute(){
		return substitute;
	}
	public String getTableName(){
		return tableName;
	}
	public boolean isVariable(){
		return columnName.isVariable();
	}
	public void setSubstitute(Column c){
		substitute = c;
	}
	public String toString(){
		String result="";
		if (tableName!=null && columnName!=null){
			result=tableName.toString()+ "." + columnName.toString();
		}else if(columnName!=null){
			result=columnName.toString();
		}
		return result;
	}

}