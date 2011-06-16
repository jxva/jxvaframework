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
import org.apache.log4j.Category;import org.apache.log4j.PropertyConfigurator;import org.apache.log4j.BasicConfigurator;
public class SQL {
	private SQLStatement sqlStatement;
	private String name;
/**
 * SQL constructor comment.
 */
public SQL() {
	super();
}
public SQL(File f) {
	init(Misc.file2string(f));
}
	/** Construct a SQL using a sqlString.
	**/
	public SQL(String sqlString) {
		init(sqlString);
	}
/**
 * Insert the method's description here.
 * Creation date: (5/3/01 1:45:35 AM)
 * @param sql java.lang.String
 * @param errRecover boolean
 * @param gui boolean
 */
public SQL(String sqlString, boolean errRecover, boolean gui) {
	try {
		StringBufferInputStream is = new StringBufferInputStream(sqlString);
		SQLParser p = null;

			if (errRecover) {
				p = new SQLParserWithErrProcess(is);
			} else
				p = new SQLParser(is);
		sqlStatement = (SQLStatement) p.parse().value;
		System.out.println(sqlStatement.toString());
	} catch (Exception e) {
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
	try {
		StringBufferInputStream is = new StringBufferInputStream(sqlString);
		SQLParserWithErrProcess p = new SQLParserWithErrProcess(is);
		sqlStatement = (SQLStatement) p.parse().value;
		System.out.println(sqlStatement.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}
}
/** Test program for the SQL parser and Mapping classes.
**/
public static void main(String[] args) {
	//File f = new File("C:/test.sql");
	
	BasicConfigurator.configure();
	cat.setPriority(org.apache.log4j.Priority.ERROR);
	cat.info("Entering application.");
	  
	SQL sql = new SQL("select id,name from user where title like '%d%'");
	if (sql.getSQLStatement() instanceof SelectStatement){
		System.out.println(sql.getSelectStatement());
	} else if( sql.getSQLStatement() instanceof ViewDef){
		System.out.println(sql.getViewDef());
		
	}
	     cat.info("Exiting application.");    
	//Mappings m=new Mappings(sql);
	//m.setVisible(true);		
}
		public void setName(String n){
			name=n;
		}
	static Category cat = Category.getInstance(SQL.class.getName());	/**
 * Insert the method's description here.
 * Creation date: (12/17/2001 2:27:37 PM)
 */
ViewDef getViewDef() {
	return (sqlStatement instanceof ViewDef)? (ViewDef)sqlStatement:null;
	
}/**
 * Insert the method's description here.
 * Creation date: (12/17/2001 2:27:37 PM)
 */  
void newMethod() {
}}