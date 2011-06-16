package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 1:20:48 AM)
 * @author:  Jianguo Lu
 */
 
 


import java.util.*;
import sql4j.schema.*;

public class Selection {
	private Vector scalarExps;
	private boolean selectAll = false;
/**
 * Selection constructor comment.
 */
public Selection() {
	super();
}
public Selection(Columns cs) {
	if (cs != null) {
		for (Enumeration e = cs.elements(); e.hasMoreElements();) {
			Column c = (Column) e.nextElement();
			if (scalarExps == null)
				scalarExps = new Vector();
			scalarExps.add(new ScalarExp(c));
		}
	}
}
public Selection(String s){
		selectAll = true;
	}
public Selection(Vector s) {
	scalarExps = s;
}
public Columns getColumns() {
	if (selectAll) return new Columns(new Column("*"));
	if (scalarExps==null) return null;
	Columns columns = null;
	for (Enumeration e = scalarExps.elements(); e.hasMoreElements();) {
		Object o = e.nextElement();
		if (o instanceof ScalarExp) {
			ScalarExp scalar=(ScalarExp)o;
			if (scalar.getColumn() != null) {
				if (columns==null) columns=new Columns();
				columns.add(scalar.getColumn());
			}
		}
	}
	return columns;
}
public String toString() {
	if (selectAll) {
		return "SELECT * ";
	} else
		if (scalarExps == null)
			return "";
	String result = "SELECT ";
	for (Enumeration e = scalarExps.elements(); e.hasMoreElements();) {
		String c = ((ScalarExp) e.nextElement()).toString();
		String currentLine = result.substring((result.lastIndexOf("\n") == -1) ? 0 : result.lastIndexOf("\n"), result.length());
		if (currentLine.length() > 50)
			result += "\n      ";
		if (e.hasMoreElements()) {
			result += c + ", ";
		} else
			result += c;
	}
	return result;
}
}