package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 1:20:48 AM)
 * @author:  Jianguo Lu
 */
/** This corresponds to the single condition in the where clause
of the SQL statement. The condition could be 
   value COMPARISON value, or
   value in SelectStatement, or other predicates.
**/



import sql4j.schema.*;
public abstract class AtomicWhereCondition implements WhereCondition {
	protected String operator;
	protected String value;
	protected Column column1;
	protected Column column2;
	protected SelectStatement stmt;
	public Column getColumn1() {
		return column1;
	}
	public Column getColumn2() {
		return column2;
	}
/** A simple implementation of getting all the column names in the clause.
 Filter out the columns that are variaibles, expressions, literals. 
 A more complete implementation should move this method into each specific
 atomic condition.
 **/
public Columns getColumns() {
	Columns cs = null;
	if (column1 != null && !column1.isVariable()) {
		cs = new Columns(column1);
		if (column2 != null && !column2.isVariable()) {
			cs.add(column2);
		}
	}
	return cs;
}
	public String getOperator() {
		return operator;
	}
	public SelectStatement getSubquey() {
		return stmt;
	}


/** Get the tables from the two operands of the condition.
**/
public Tables getTables(){
		Tables result=null;
		Table t1=(column1==null || column1.getTableName()==null)?(null):new Table(column1.getTableName());		
		Table t2=(column2==null || column2.getTableName()==null)?(null):new Table(column2.getTableName());
		if (t1!=null) { 
			result=new Tables(t1);
			result.add(t2);
		} else if(t2!=null){
			result=new Tables(t2);
		}
		return result;
	}
	public abstract String toString();

}