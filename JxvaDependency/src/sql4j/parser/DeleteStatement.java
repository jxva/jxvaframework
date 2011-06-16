package sql4j.parser;

/**
 * Delete Statement
 * Creation date: (10/20/00 3:43:42 AM)
 * @author Jianguo Lu
 */
public class DeleteStatement extends SQLStatement {
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