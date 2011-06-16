package sql4j.parser;

/**
 * Table in SQL statement.
 * Creation date: (11/2/00 3:06:39 PM)
 * @author Jianguo Lu
 */
public class Table {
	protected String name1;
	private String name2;
	private String alias;		
/**
 * Table constructor comment.
 */
public Table() {
	super();
}
	public Table(String n){ name1=n;}
	public Table(String n1, String n2){name1=n1; name2=n2;}
/**
 * Insert the method's description here.
 * Creation date: (1/31/01 11:34:01 AM)
 * @return boolean
 * @param t com.ibm.commerce.migration.parser.sql.Table
 */
public boolean equals(Table t) {
	return toString().equalsIgnoreCase(t.toString());
}
public String getAlias(){ return alias;}
public void setAlias(String a){ alias=a;}
	public String toString(){
		String result="";
		if (name1!=null && name2!=null){
			result=name1+"."+name2;
		}else if (name1!=null) {
				result=name1;
		}
		return result;
	}
}