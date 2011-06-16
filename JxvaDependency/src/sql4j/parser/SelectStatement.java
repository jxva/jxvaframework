package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 1:20:48 AM)
 * @author:  Jianguo Lu
 */



import sql4j.schema.*;
import java.util.*;
public class SelectStatement extends SQLStatement {
	private WhereClause where;
	private Selection selection;
	private boolean translatable = true;
	/** get all the tables in the SQL.
	**/
/**
 * Select statement constructor
 * Creation date: (12/7/00 7:49:04 PM)
 */
public SelectStatement() {}/**
 * Insert the method's description here.
 * Creation date: (12/7/00 7:49:04 PM)
 */           
	/**this method is obsolete.
	**/
	public SelectStatement(Columns cs, Tables ts, WhereClause wc){
		columns=cs;
		tables=ts;
		where=wc;
	}
/** Contruct the SelectStatement.
**/
public SelectStatement(Selection s, Tables t, WhereClause w) {
	selection = s;
	columns = selection.getColumns();
	where = w;
	tables = t;
}
/** Get the columns in Select clause as well as in the Where clause.
**/
public Columns getAllColumns() {
	Columns columnsInWhere = (where == null) ? null : where.getColumns();
	if (columnsInWhere == null) {
		return columns;
	} else
		columnsInWhere.addAll(columns);
	return columnsInWhere;
}
	public Tables getAllTables(){
		Tables t=this.getTables();
		if (where !=null) t.addAll(where.getTables());
		return t;
	}	/** get the columns in the where clause.
	**/
/** Get the Columns from the SELECT clause.
**/
public Columns getColumns(){
		return columns;
	}
	public Columns getColumnsInWhereClause(){
		return (where == null) ? null : where.getColumns();
	}	/** get the tables in where clause.**/
	public Selection getSelection() {
		return selection;
	}
/** get the tables from the FROM clause.
**/
public Tables getTables(){
		return tables;
	}
	public Tables getTablesInWhereClause(){
		return where.getTables();
	}
public boolean getTranslatable(){
		return translatable;
	}
public WhereClause getWhereClause(){
		return where;
	}
public void setTranslatable(boolean b){
		translatable=b;
	}
public String toString() {
	String result = "";
	
	if(selection!=null && tables != null && where !=null){
		result += selection.toString() + "\n" + 
		         "FROM " + tables.toString() + "\n" + 
		         "WHERE " + where.toString() + "\n";
		         
	}else if (selection!=null && tables != null) {
		result += selection.toString() + 
		              "\n" + "FROM   " + tables.toString() + "\n";
	} else if (selection!=null) {
		result+=selection.toString();
	}
	return result;
}


	public SelectStatement(Selection s, Tables t, WhereClause w, AllDistinctPredicate distinct, Columns groupBys){
		selection = s;
	    columns = selection.getColumns();
	where = w;
	tables = t;
	}/**
 * Insert the method's description here.
 * Creation date: (12/17/2001 12:59:32 PM)
 */  
public void addOrderBy() {}/**
 * Insert the method's description here.
 * Creation date: (12/17/2001 12:59:32 PM)
 */  
public void addOrderBy(Vector v) {
}}