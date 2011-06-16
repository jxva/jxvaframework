package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 1:20:48 AM)
 * @author:  Jianguo Lu
 */
 
import sql4j.schema.*;
 
public class WhereClause {
	private WhereCondition condition;
	public WhereClause(WhereCondition c){
		condition=c;
		
	}
	public Columns getColumns(){
		return condition.getColumns();
	}
	public Tables getTables(){
		 return condition.getTables();
	}
	public WhereCondition getWhereCondition(){
		return condition;
	}
	public String toString(){
		return condition.toString();
	}

}