package sql4j.parser;

/**
 * Alter statement, a kind of SQL statement.
 * Creation date: (10/20/00 1:20:48 AM)
 * @author Jianguo Lu
 */
 public class AlterTableStatement extends SQLStatement {
	  public Columns getAllColumns(){
		 Columns result=null;
		 return result;
	 }
	 public Tables getAllTables(){
		 return tables;
	 }
	public Columns getColumns() {
		return columns;
	}
	public Tables getTables() {
		return tables;
	}
}