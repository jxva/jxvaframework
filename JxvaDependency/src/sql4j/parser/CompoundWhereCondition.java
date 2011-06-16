package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 1:24:00 AM)
 * @author: Jianguo Lu
 */

import sql4j.schema.*;
import java.util.*;
 
public class CompoundWhereCondition implements WhereCondition{
	private WhereCondition left;
	private WhereCondition right;
	private String operator;
/**
 * CompoundWhereCondition constructor comment.
 */
public CompoundWhereCondition() {
	super();
}
public CompoundWhereCondition(String op, WhereCondition l) {
	operator = op;
	left = l;
}
public CompoundWhereCondition(String op, WhereCondition l, WhereCondition r){
		left=l; right=r; operator =op;
}

/**
* Construct a CompoundWhereCondition Object from a Vector of atomicWhereCondition
* by Ken
*/
public CompoundWhereCondition construct(Vector atomicWhereConditions){
	 if ( atomicWhereConditions.size() > 2 ){
	 this.left = (WhereCondition)atomicWhereConditions.elementAt(0);
	 this.operator = "AND";
	 atomicWhereConditions.remove(0);
	 CompoundWhereCondition temp = new CompoundWhereCondition();
	 this.right = (WhereCondition) temp.construct(atomicWhereConditions);
	 } else {
	 this.left = (WhereCondition) atomicWhereConditions.elementAt(0);
	 this.right = (WhereCondition) atomicWhereConditions.elementAt(1);
	 this.operator = "AND";
	 }
	 return this;
}

	public Columns getColumns(){
		Columns result=null;
		Columns cs1=(left  ==null)?null:left.getColumns();
		Columns cs2=(right ==null)?null:right.getColumns();
		if(cs1==null) {
			result=cs2;
		}else {
			cs1.addAll(cs2);
			result=cs1;
		}
		return result;
	}
	public WhereCondition getLeft(){
		return left;
	}
	public String getOperator(){
		return operator;
	}
	public WhereCondition getRight(){
		return right;
	}
public Tables getTables() {
	Tables t1 = left.getTables();
	Tables t2 = right.getTables();
	if(t1==null) {
		return t2;
	}else t1.addAll(t2);
	return t1;
}
public String toString() {
	String result = "";
	String leftString =(left ==null)? "NULL": left.toString();
	
	if (right == null) {
		result = operator + " " + leftString;
	} else {
		String rightString = right.toString();
		if (leftString.length() > 50)
			leftString += "\n       ";
		if (rightString.length() > 50)
			rightString = "\n       " + rightString;
		result = leftString + " " + operator + " " + rightString;
	}

	return result;
}

	/**
	 * Return a Vector of comparsion where conditions by Ken
	 * Assuming all operator are "AND"
	 */
public Vector toVector(){
	Vector result = new Vector();
	if (this.left instanceof AtomicWhereCondition) { result.add( this.left ); }
	if (this.left instanceof CompoundWhereCondition) { result.addAll( ((CompoundWhereCondition) this.left).toVector() ); }
	if (this.right instanceof AtomicWhereCondition) { result.add( this.right ); }
	if (this.right instanceof CompoundWhereCondition) {result.addAll( ((CompoundWhereCondition) this.right).toVector() ); }
	return result;	
}

}