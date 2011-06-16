package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/31/00 1:04:22 AM)
 * @author:  Jianguo Lu
 */


import sql4j.schema.*;
public class TestForNull extends AtomicWhereCondition{
	String label;

/**
 * TestForNull constructor comment.
 */
public TestForNull() {
	super();
}
	public TestForNull(String s, Column c){
		label=s;
		column1=c;
	}
/**
 * Insert the method's description here.
 * Creation date: (1/30/01 6:58:06 AM)
 * @return java.lang.String
 */
public String toString() {
	return column1.toString()+" "+ label +" null";
}

}