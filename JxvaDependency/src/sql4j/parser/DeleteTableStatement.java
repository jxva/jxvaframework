package sql4j.parser;

/**
 * Delete table statement.
 * Creation date: (10/20/00 1:20:48 AM)
 * @author  Jianguo Lu
 */
 
 public class DeleteTableStatement extends SQLStatement {
	 
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