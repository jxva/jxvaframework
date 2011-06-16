package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/31/00 5:44:45 PM)
 * @author: Jianguo Lu
 */

import sql4j.schema.*;
 
public class Atom {
	private String literal;
	private ParameterRef parameterRef;
/**
 * Atom constructor comment.
 */
public Atom() {
	super();
}
	public Atom(ParameterRef pr){ parameterRef=pr;}
	public Atom(String l){ literal=l;}
	public String toString(){
		String result="";
		if (literal!=null) {
			result = literal;
		}else if (parameterRef!=null){
			result=parameterRef.toString();
		}
		return result;
	}

}