package sql4j.schema;

/**
 * Schema class is used to record the database schema.
 * Basically it is a mapping between the table names and the column names.
 * It is constructed from the XSLT file, for both v4 and v5 of WCS.
 * Using this class we can determine the things such as the columns a 
 * table may have, or the tables in which a column may appear.
 * 
 * Creation date: (1/29/01 4:43:47 PM)
 * @author: <Jianguo Lu>
 */


import java.util.*;
import org.w3c.dom.*;

import sql4j.util.*;import sql4j.parser.*;
public class Schema{
	/** ((columnName, Set of TableNames), ....) **/
	private Mapping columnTableMapping;
	/** ((tableName, set of Column Names), ...) **/
	private Mapping tableColumnMapping;


/**
 * Schema constructor comment.
 */
public Schema() {
	super();
}
/**
 * Schema constructor comment.
 * @param ht java.util.Hashtable
 */
public Schema(Mapping tcm) {
	tableColumnMapping = tcm;
	//build the inverse mapping of tableColumnMapping;
	for (Enumeration e = tableColumnMapping.getMapping().keys(); e.hasMoreElements();) {
		Object table = e.nextElement();
		Set columns = (Set) tableColumnMapping.applyMapping((String) table);
		for (Iterator i = columns.iterator(); i.hasNext();) {
			String column = (String) i.next();
			Set tables = new HashSet();
			tables.add(table);
			if (columnTableMapping == null)
				columnTableMapping = new Mapping();
			Set previousTables = (Set) columnTableMapping.put(column, tables);
			if (previousTables != null) {
				previousTables.addAll(tables);
				columnTableMapping.put(column, previousTables);
			}
		}
	}
}
/**
 * Construct the Schema from a file. path: the file path of the schema. 
   version: the version of the schema. the version tag is used to process different schema files.
 * Creation date: (5/8/01 9:00:56 PM)
 * @param path java.lang.String
 * @param version int
 */
public Schema(String path, int version) {
	tableColumnMapping=new Mapping();
	org.w3c.dom.Document doc = DomUtil.readDocumentNoValidate(path);
	NodeList tabledefList = doc.getElementsByTagName("tabledef");
	for (int i = 0; i < tabledefList.getLength(); i++) {
		Node tabledef = tabledefList.item(i);
		
		Node table = ((Element) tabledef).getElementsByTagName("table").item(0);
		String tableName = DomUtil.toString(table.getChildNodes().item(0)); 
		Set columnNames = new HashSet();
		Node columnsNode = ((Element) tabledef).getElementsByTagName("columns").item(0);
		NodeList colnameList = ((Element) columnsNode).getElementsByTagName("colname");
		for (int j = 0; j < colnameList.getLength(); j++) {
			Node colnameNode = colnameList.item(j);
			String colname = DomUtil.toString(colnameNode.getChildNodes().item(0));
			columnNames.add(colname.toUpperCase().trim());
		}
		tableColumnMapping.put(tableName.toUpperCase().trim(), columnNames);
	}
}
public Columns getColumns(Table t) {
	Columns result = null;
	Set columnStrings = (Set) getTableColumnMapping().applyMapping(t.toString());
	if (columnStrings != null) {
		for (Iterator i = columnStrings.iterator(); i.hasNext();) {
			String columnString = (String) i.next();
			Column column = new Column(columnString);
			if (result == null)
				result = new Columns();
			result.add(column);
		}
	}
	return result;
}
	public Mapping getColumnTableMapping(){
		return columnTableMapping;
	}
	public Mapping getTableColumnMapping(){
		return tableColumnMapping;
	}
/**
 * Insert the method's description here.
 * Creation date: (1/30/01 6:47:40 PM)
 * @return com.ibm.commerce.migration.parser.sql.Tables
 * @param c com.ibm.commerce.migration.parser.sql.Column
 */
public Tables getTables(Column c) {
	return getTables(c.getColumnName());
}
	public Tables getTables(ColumnName cn){
		Tables result=null;
		Set tableStrings=(Set)getColumnTableMapping().applyMapping(cn.toString());
		for (Iterator i=tableStrings.iterator();i.hasNext();){
			String tableString =(String)i.next();
			Table table=new Table(tableString);
			if (result==null) result=new Tables();
			result.add(table);
		}
		return result;
	}
	public void merge(Schema s){
		columnTableMapping.putAll(s.getColumnTableMapping());
		tableColumnMapping.putAll(s.getTableColumnMapping());
	}
/**
 * Insert the method's description here.
 * Creation date: (1/3/2002 4:17:34 PM)
 * @param path java.lang.String
 */
public Schema(String path) {
	tableColumnMapping = new Mapping();
	org.w3c.dom.Document doc = DomUtil.readDocumentNoValidate(path);
	NodeList tabledefList = doc.getElementsByTagName("TableName");
	for (int i = 0; i < tabledefList.getLength(); i++) {
		Node table = tabledefList.item(i);
		if (table instanceof Element) {
			String tableName = ((Element) table).getAttribute("Name");
			Set columnNames = new HashSet();
			NodeList columns = ((Element) table).getElementsByTagName("ColumnName");

			for (int j = 0; j < columns.getLength(); j++) {
				Node colnameNode = columns.item(j);
				String colname = 
					(colnameNode instanceof Element)
						? ((Element) colnameNode).getAttribute("Name")
						: null; 
				columnNames.add(colname.toUpperCase().trim());
			}
			tableColumnMapping.put(tableName.toUpperCase().trim(), columnNames);
		}
	}
}	public static void main(String[] args){
		Schema s = new Schema("c:/qr/data/schema/SchemaInfoV5.xml");
		Columns c=s.getColumns(new Table("ACCCMDTYPE"));
		System.out.println(c.toString());
	}}