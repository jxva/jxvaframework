package sql4j.parser;




import sql4j.schema.*;
 
public class ScalarExp {
	 private Column column;
	 private Atom atom;
	 private FunctionRef functionRef;
	 private ScalarExp left;
	 private String operator;
	 private ScalarExp right;

	//part of the scalar we are not interested to process.
	private String string;
/**
 * ScalarExp constructor comment.
 */
public ScalarExp() {
	super();
}
	//
	public ScalarExp(Atom a){
		atom=a;
	}
	public ScalarExp(Column c){ column=c;}
public ScalarExp(FunctionRef fr){
		functionRef=fr;
	}
	public ScalarExp(ScalarExp l, String op, ScalarExp r){
		left=l;right=r; operator=op;
	}	/** get all the columns in case the column is an expression instead of a single column,**/
	public Atom getAtom(){
		return atom;
	}
	public Column getColumn(){ return column;
	}
	public Columns getColumns() {
		Columns cs1 = (left == null) ? null : left.getColumns();
		Columns cs2 = (right == null) ? null : right.getColumns();
		if (cs1 == null && cs2 == null && column == null)
			return null;
		Columns result = new Columns();
		if (cs1 != null)
			result.addAll(cs1);
		if (cs2 != null)
			result.addAll(cs2);
		if (column != null)
			result.add(column);
		return result;
	}
	public String toString(){
		if (this==null) return "null";
		String result="";
		if (atom!=null) { result=atom.toString();
		}else if(column!=null){ result=column.toString();
		}else if(functionRef!=null) {result=functionRef.toString();
		}else if (left!=null && right!=null && operator!=null){
			result=left.toString()+operator+right.toString();
		}
		return result;
	}

	/**
	* return the type of this scalar Expression. Is it a column/function/etc?
	* by Ken
	*/
	public String getType(){
		if (atom!=null) return "atom";
	else if(column!=null) return "column";
		return "unknown";
	}

}