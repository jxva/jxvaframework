package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/31/00 11:34:16 AM)
 * @author:  Jianguo Lu
 */

import sql4j.schema.*;
 
public class ComparisonPredicate extends AtomicWhereCondition {
	ScalarExp scalar1;
	ScalarExp scalar2;
public ComparisonPredicate(ScalarExp s1, String op, ScalarExp s2) {
	scalar1 = s1;
	column1 = s1.getColumn();
	scalar2 = s2;
	column2 = s2.getColumn();
	operator = op;

}
public ComparisonPredicate(ScalarExp s, String op, SelectStatement st) {
	scalar1 = s;
	column1 = s.getColumn();
	operator = op;
	stmt = st;
}

// Get the left side of the comparsion predicate by Ken
public ScalarExp getLeft(){ return scalar1; }

// Get the right side of the comparsion predicate by Ken
public ScalarExp getRight() { return scalar2; }

//Get the type(scalar/statement) of scalar2 (right scalar)
public String getRightType(){
	if (scalar2 != null) return "scalar";
	else if ( stmt != null ) return "statement";
	return "unknown";
} 

public String toString() {
	String result = "";
	if (column1 != null && column2 != null) {
		result = column1.toString() + " " + operator + " " + column2.toString();
	} else
		if (scalar1 != null && scalar2 != null) {
			result = scalar1.toString() + " " + operator + " " + scalar2.toString();
		} else
			if (scalar2 == null && stmt != null) {
				result = scalar1.toString() + " " + operator + " (" + stmt.toString() + ")";
			}
	return result;
}

}