package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/25/00 1:36:59 AM)
 * @author Jianguo Lu
 */
public class VarRef {
	String name;
/**
 * VarRef constructor comment.
 */
public VarRef() {
	super();
}
	public VarRef(String s){ name=s; }
/**
 * Insert the method's description here.
 * Creation date: (1/15/01 4:00:15 PM)
 */
public String toString() {
	String result = "";
	if (name != null)
		result = name;
	return result;
}
}