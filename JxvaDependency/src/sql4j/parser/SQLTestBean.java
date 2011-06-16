package sql4j.parser;

/**
 * Insert the type's description here.
 * Creation date: (11/1/00 4:16:41 PM)
 * @author: Jianguo Lu
 */

import java.io.*;


import org.w3c.dom.*;
import java.util.*;
import javax.swing.*;

import sql4j.util.*;
import org.apache.log4j.Category;import org.apache.log4j.PropertyConfigurator;import org.apache.log4j.BasicConfigurator;/**
* Insert the type's description here.
* Creation date: (11/1/00 4:16:41 PM)
* @author Jianguo Lu
*/
public class SQLTestBean {
	private SQLStatement sqlStatement;
	private String name;
/**
 * SQL constructor comment.
 */
public SQLTestBean() {
	super();
}

	/** Construct a SQL using a sqlString.
	**/
	public SQLTestBean(String sqlString) {
		init(sqlString);
	}
/**
 * Insert the method's description here.
 * Creation date: (5/3/01 1:45:35 AM)
 * @param sql java.lang.String
 * @param errRecover boolean
 * @param gui boolean
 */
public SQLTestBean(String sqlString, boolean errRecover, boolean gui) {
	try {
		StringBufferInputStream is = new StringBufferInputStream(sqlString);
		SQLParser p = null;

			if (errRecover) {
				p = new SQLParserWithErrProcess(is);
			} else
				p = new SQLParser(is);
		sqlStatement = (SQLStatement) p.parse().value;
		//System.out.println(sqlStatement.toString());
	} catch (Exception e) {
		diagnosis=e.toString();
		e.printStackTrace();
	}
}
		public String getName(){ return name;
		}
	public SelectStatement getSelectStatement(){
		if (sqlStatement instanceof SelectStatement){
			return (SelectStatement)sqlStatement;
		}else return null;
	}
	public SQLStatement getSQLStatement() {
		return sqlStatement;
	}
/**
 * Insert the method's description here.
 * Creation date: (4/30/01 12:04:09 AM)
 */
public void init(String sqlString) {
	Global.msg="";
	try {
		StringBufferInputStream is = new StringBufferInputStream(sqlString);
		SQLParserWithErrProcess p = new SQLParserWithErrProcess(is);
		diagnosis += Global.msg + "\n";
		sqlStatement = (SQLStatement) p.parse().value;
		sqlStatement.addVerbatim(sqlString);
	} catch (Exception e) {
		diagnosis += e.toString();
		e.printStackTrace();
	}
}
/** Test program for the SQL parser and Mapping classes.
**/
public static void main(String[] args) {
	new SQLTestBean();
	//File f = new File(args[0]);
	
	BasicConfigurator.configure();
	cat.setPriority(org.apache.log4j.Priority.ERROR);
	cat.info("Entering application.");
	  
	SQL sql = new SQL("select a.userid uid,b.bookid bid from user a,book b where a.title like '%d%' and b.s=' order by ' order by a.userid");
	if (sql.getSQLStatement() instanceof SelectStatement){
		System.out.println(sql.getSelectStatement());
	} else if( sql.getSQLStatement() instanceof ViewDef){
		System.out.println(sql.getViewDef());
	}
	     cat.info("Exiting application.");    	
}
		public void setName(String n){
			name=n;
		}
	static Category cat = Category.getInstance(SQL.class.getName());	String diagnosis="";	private String SQLString;	private boolean success=false;	public String getDiagnosis(){ return diagnosis;
	}	public String getSQLString(){
		return SQLString;
	}	public boolean getSuccess(){
		sqlStatement = null;
		if (SQLString!=null){
			init(SQLString);
		}
		if (sqlStatement!=null) success=true;
		return success;
	}	/**
 * Insert the method's description here.
 * Creation date: (12/17/2001 2:27:37 PM)
 */
ViewDef getViewDef() {
	return (sqlStatement instanceof ViewDef)? (ViewDef)sqlStatement:null;
	
}	public void setDiagnosis(String s){ diagnosis=s;
	}	public void setSQLStatement(SQLStatement s){
		sqlStatement=s;
	}	public void setSQLString(String s){
		SQLString =s;
	}	public void setSuccess(boolean s){
		success=s;
	}}