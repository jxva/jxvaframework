package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (12/17/2001 1:28:50 PM)
 * @author: Administrator
 */
public class ViewDef extends SQLStatement{
	private Table table;
	private SelectStatement query;
/**
 * ViewDef constructor comment.
 */
public ViewDef() {
	super();
}
	public ViewDef(Table t, SelectStatement q){
		table=t;
		query=q;
	}
	public Columns getAllColumns(){
		return query.getAllColumns();
	}
	public Tables getAllTables(){
		return query.getAllTables();
	}
	public Columns getColumns(){
		return query.getColumns();
	}
	public Tables getTables(){
		return query.getTables();
		
	}

		//Get the View Name by Ken
		public Table getViewName(){ return table; }

		//Get the query that define the view by Ken
		public SelectStatement getQuery() { return query; }

	public String toString(){
		String result="CREATE VIEW ";
		result+= table.toString();
		result+= " AS " + query.toString();
		return result;
	}
}