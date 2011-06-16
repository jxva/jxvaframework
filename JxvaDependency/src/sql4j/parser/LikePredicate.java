package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/31/00 12:17:55 PM)
 * @author:  Jianguo Lu
 **/ 
import sql4j.schema.*;public class LikePredicate extends AtomicWhereCondition {
	private ScalarExp scalarExp;
	private String label;
	private String pattern;
/**
 * LikePredicate constructor comment.
 */
public LikePredicate() {
	super();
}
	public LikePredicate(ScalarExp se, String l, String p){
		scalarExp =se;
		column1=se.getColumn();
		label=l;
		pattern=p;
	}
/**
 * Insert the method's description here.
 * Creation date: (1/30/01 7:10:02 AM)
 * @return java.lang.String
 */
public String toString() {
	return scalarExp.toString()+ " " + label +" "+ pattern;
}

}