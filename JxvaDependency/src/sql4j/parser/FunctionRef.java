package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (11/1/00 9:47:11 PM)
 * @author Jianguo Lu
 */
public class FunctionRef {
	private  ScalarExp scalar;
	
/**
 * FunctionRef constructor comment.
 */
public FunctionRef() {
	super();
}
public FunctionRef(	ScalarExp se) {
	scalar=se;
}
public String toString(){
		return scalar.toString();
	}
	private String op;/** FunctionRef constructor comment.

 */
 public FunctionRef(String o){
	 op=o;
 }    public FunctionRef(String op, ScalarExp se){
 }   }