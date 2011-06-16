package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 4:06:35 AM)
 * @author: Jianguo Lu
 * A list of columns.

 **/
import java.util.*;



import sql4j.schema.*;import sql4j.util.Misc;

public class Columns {
	private Vector columns=new Vector();

	

/**
 * Insert the method's description here.
 * Creation date: (1/12/01 8:15:14 PM)
 */
public Columns() {}/**
 * Insert the method's description here.
 * Creation date: (1/12/01 8:07:57 PM)
 */        
public Columns(Column c) {
	columns = new Vector();
	columns.add(c);
}
public Columns(Vector c) {
	columns = c;
}/**
 * Insert the method's description here.
 * Creation date: (1/12/01 8:07:57 PM)
 */        
	public Columns add(Column c){
		columns.add(c);
		return this;
	}
/**
 * Insert the method's description here.
 * Creation date: (1/30/01 11:28:35 AM)
 * @param cs com.ibm.commerce.migration.parser.sql.Columns
 */
public void addAll(Columns cs) {
	if (cs!=null) columns.addAll(cs.toVector());
}

public boolean contains(Object o) {
	return columns.contains(o);
}
	public Enumeration elements(){
		return columns.elements();
	}
/**
 * Insert the method's description here.
 * Creation date: (1/30/01 12:22:46 PM)
 * @return com.ibm.commerce.migration.parser.sql.Column
 */
public Column first() {
	if (columns==null) {
		return null;
	}else {
		return (Column)columns.elementAt(0);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (5/9/01 1:58:14 AM)
 */
public String getColumnNames() {
	Vector stringVector = new Vector();
	for (Enumeration e = columns.elements(); e.hasMoreElements();) {
		Column c= (Column) e.nextElement();
		if(c!=null){
			stringVector.add(c.getName());
		}
	}
	String result = Misc.toString(stringVector);
	return result;
}
/**
 * The complete qualified name of the columns.
 * For column names only, use getColumnNames().
 * Creation date: (10/23/00 12:32:10 AM)
 */
public String toString() {
	/**Selection selection = new Selection(columns);
	return selection.toString();
	**/
	Vector stringVector = new Vector();
	for (Enumeration e = columns.elements(); e.hasMoreElements();) {
		Column c= (Column) e.nextElement();
		if(c!=null){
			stringVector.add(c.toString());
		}
	}
	String result = Misc.toString(stringVector);
	return result;
}
public Vector toVector() {
	return columns;
}

}