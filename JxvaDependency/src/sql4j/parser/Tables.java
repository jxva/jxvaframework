package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 1:20:48 AM)
 * @author:  Jianguo Lu
 */
 import java.util.*;


import sql4j.util.*;import sql4j.schema.*;

public class Tables {
	private Vector tables;

/**
 * Insert the method's description here.
 * Creation date: (12/13/00 11:41:00 PM)
 */
public Tables() {
	tables = new Vector();
}/**
 * Insert the method's description here.
 * Creation date: (12/13/00 11:42:55 PM)
 * @param tables com.ibm.commerce.migration.parser.sql.Tables
 */        
	public Tables(Table t){
		tables=new Vector();
		tables.add(t);
	}
public Tables(String s) {
	tables = new Vector();
	tables.add(s);
}
public Tables(Vector t){
		tables=t;
		
	}
/** Add a table to the table list. No duplicate tables.
**/
public Tables add(Object o) {
	if(o==null) return this;
	if (!this.toSet().contains(o.toString())) {
		tables.add(o);
	}
	return this;
}
/** Add two tables, without duplicate tables.
**/
public void addAll(Tables ts) {
	if (ts != null) {
		for (Enumeration e=ts.toVector().elements();e.hasMoreElements();){
			Table t=(Table)e.nextElement();
			this.add(t);
		}
	}
}
public boolean contains(Object o) {
	return tables.contains(o);
}
	/** transform the tables to String Set, using the table names.
	**/
	public StringSet toSet(){
		if (tables==null) return null;
		StringSet result=new StringSet();
		for (Enumeration e=tables.elements();e.hasMoreElements();){
			String tableName=((Table)e.nextElement()).toString();
			result.add(tableName);
		}
		return result;
	}
	public String toString(){
		return Misc.toString(tables);
	}
	public Vector toVector(){ return tables;}

}