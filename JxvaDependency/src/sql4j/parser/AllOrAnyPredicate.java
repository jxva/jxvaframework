package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/31/00 12:18:40 PM)
 * @author:  Jianguo Lu
 */ 
import sql4j.schema.*;
 
public class AllOrAnyPredicate extends AtomicWhereCondition {
	ScalarExp left;		String operator;		String quanlifier;		String quantifier;		SelectStatement right;	
/**
 * AllOrAnyPredicate constructor comment.
 */
public AllOrAnyPredicate() {
	super();
}
public AllOrAnyPredicate(ScalarExp se, String op, String q, SelectStatement sub){
		left=se;
		operator =op;
		quantifier = q;
		right = sub;
	}
public String toString() {
	String result = "";
	result+=left.toString()+operator + quantifier+ "("+ right.toString()+")"; 
	return result;
}

/** By Ken */
public ScalarExp getLeft(){ return left; }

/** By Ken */
public SelectStatement getRight(){ return right; }

/** By Ken */
public String getOperator(){ return operator; }

/** By Ken */
public String getQuantifier(){ return quantifier; }

}