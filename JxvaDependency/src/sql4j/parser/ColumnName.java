package sql4j.parser;

/**
 * Column Name of a SQL.
 * Creation date: (10/25/00 11:34:58 AM)
 * @author Jianguo Lu
 */
public class ColumnName {
	private VarRef varRef;
	private String name;
	private boolean isVariable;
/**
 * ColumnName constructor comment.
 */
public ColumnName() {
	super();
}
	//not used for the moment
	public ColumnName(VarRef v){
		varRef=v;
	}
	public ColumnName(String n){
		name=n;
		if (n.startsWith("$") || n.startsWith("'") || n.equals("?")){
			isVariable=true;
		}else isVariable=false;
	}
//not used for the moment
public ColumnName(String t, String n) {
	name = n;
	if (n.startsWith("$") || n.startsWith("'") || n.equals("?")) {
		isVariable = true;
	} else
		isVariable = false;
}
	/** Test whether the column name is a variable for the imbedded SQL. 
	  names like  "?", ($a) are variables. Note the "variable" is the constant in the Datalog notation.
	**/
	public boolean isVariable(){
		return isVariable;
	}
	public String toString(){
		String result="";
		if (name!=null){
			result=name;
		}else if(varRef!=null){
			result= varRef.toString();
		}else {
		}
		return result;
	}
}