package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 1:19:45 AM)
 * @author: Jianguo Lu
 */


import sql4j.schema.*;

public interface WhereCondition {
	public Columns getColumns();
	public Tables getTables();

}