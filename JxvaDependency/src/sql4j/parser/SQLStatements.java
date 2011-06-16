package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 3:12:25 AM)
 * @author: Jianguo Lu
 */

import java.util.*;


 
/**
 * A list of SQL statements.
 * Creation date: (10/20/00 3:12:25 AM)
 * @author Jianguo Lu
 */
 
public class SQLStatements {
	Vector sqls;
	SQLStatement selectedSQL;
/**
 * SQLStatements constructor comment.
 */
public SQLStatements() {
	super();
	sqls=new Vector();
}
	SQLStatements(SQLStatement sql){
		if (sql!=null){
			sqls=new Vector();
			sqls.add(sql);
		}
	}
public void add(SQLStatement sql) {
	if (sql != null)
		sqls.add((Object) sql);
}
public Enumeration elements(){ return sqls.elements(); }/**
 * Insert the method's description here.
 * Creation date: (12/13/00 11:35:25 PM)
 * @return com.ibm.commerce.migration.parser.sql.Tables
 */        
public SQLStatement getSelectedSQL(){
		return selectedSQL;
	}/**
 * Insert the method's description here.
 * Creation date: (1/8/01 2:39:04 PM)
 */        
public Tables getTables() {
	Tables result = new Tables();
	for (Enumeration e = sqls.elements(); e.hasMoreElements();) {
		Tables ts=((SQLStatement)e.nextElement()).getTables();
		result.addAll(ts);
	}
	return result;
}

}