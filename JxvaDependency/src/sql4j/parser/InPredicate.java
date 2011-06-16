package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/31/00 1:17:58 AM)
 * @author:  Jianguo Lu
 */ 
 

import java.util.*;

  import sql4j.schema.*;
public class InPredicate extends AtomicWhereCondition {
	private SelectStatement select;
	private String label;
	private ScalarExp scalar;
	private Vector atomList;
/**
 * InPredicate constructor comment.
 */
public InPredicate() {
	super();
}
	public InPredicate(ScalarExp exp, String l, SelectStatement s){
			select=s;
			scalar=exp;
			column1=exp.getColumn();
			label=l;
	}
		public InPredicate(ScalarExp exp, String l, Vector atoms){
			atomList=atoms;
			scalar=exp;
			column1=exp.getColumn();
			label=l;
	}
/**
 * Insert the method's description here.
 * Creation date: (1/30/01 7:12:09 AM)
 * @return java.lang.String
 */
public String toString() {
	String result=scalar.toString()+" "+ label;
	if (select!=null){
		result+="( "+select.toString() + " )";
	}	
	return result;
}

}