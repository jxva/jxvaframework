package sql4j.parser;


import java.io.*;
import java.util.*;



import sql4j.util.*;import sql4j.schema.*;
 
public abstract class SQLStatement {
	//tables in FROM clause
	protected Tables tables;
	//columns in SELECT clause
	protected Columns columns;

	/** Test whether the SQL is selected in the GUI. 
	**/

	private boolean selected = false;
	private String verbatim;

/**Add the table names prefix to the column names in the SQL statement.
**/
public void addTableNamesToColumns(Schema schema) throws SQLException {
	Columns cs = getAllColumns();
	if (cs == null)
		return;
	for (Enumeration e = cs.toVector().elements(); e.hasMoreElements();) {
		Column column = (Column) e.nextElement();
		if (!column.isVariable()) { //do nothing for variables.	
			if (column.getTableName() == null) { // Table name is not in the column class, retrieve that from the schema.
				Tables tables1 = schema.getTables(column);
				StringSet tableSet1 = (tables1 == null) ? null : new StringSet(tables1.toVector());
				StringSet tableSet2 = (tables == null) ? null : new StringSet(tables.toVector());
				StringSet intersection = (tables1 == null) ? null : tableSet1.intersection(tableSet2);
				if (intersection == null || intersection.size() == 0) {
					System.out.println("can't find the table name for the column " + column.toString());
				} else
					if (intersection.size() > 1) {
						throw new SQLException("more than one table associated with a column.");
					} else
						if (intersection.size() == 1) {
							String tableName = (String) intersection.elements().nextElement();
							column.addTableName(tableName);
						}
			}
		}
	}
}
	public void addVerbatim(String s){
		verbatim=s;
	}
	/** Test whether this SQL has the table, whether it is in FROM clause or in WHERE clause.
	**/
	public boolean containsTable(Table table){
		boolean result=false;
		StringSet tablesInSQL=this.getAllTables().toSet();
		result=tablesInSQL.contains(table.toString());
		return result; 
	}
/** Get all the columns in the SQL.
**/
public abstract Columns getAllColumns();
	public abstract Tables getAllTables();
/** get the columns in the FROM clause.
**/
public abstract Columns getColumns();
/** get the tables in the FROM clause
**/

public abstract Tables getTables();
	/** get the exact sql string in the original context.
	**/
	public String getVerbatim(){
		return verbatim;
	}
/** Set the SQL is selected in the GUI.
**/
public boolean isSelected() {
	return selected;
}
/** Set the SQL is selected in the GUI.
**/
public void setSelected(boolean b){
	  selected=b;
  }                
}