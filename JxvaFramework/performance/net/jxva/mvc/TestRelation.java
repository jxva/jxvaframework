/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.jxva.mvc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

/**
 *  The Oracle database stores its table names as Upper-Case,
    if you pass a table name in lowercase characters, it will not work.
    MySQL database does not care if table name is uppercase/lowercase.
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-09 10:19:41 by Jxva
 */
public class TestRelation {
	
	private static final DAOFactory factory=DAOFactory.getInstance(); 
	public static void main(String[] args) throws SQLException {
		DAO dao=factory.createDAO();
		importedKeys(dao.getJdbcTemplate().getConnection());
		exportedKeys(dao.getJdbcTemplate().getConnection());
		dao.close();
	}
	
	/**
	 * 	System.err.println("PKTABLE_CAT   : "+results.getObject(1));
        System.err.println("PKTABLE_SCHEM : "+results.getObject(2));
        System.err.println("PKTABLE_NAME  : "+results.getObject(3));
        System.err.println("PKCOLUMN_NAME : "+results.getObject(4));
        System.err.println("FKTABLE_CAT   : "+results.getObject(5));
        System.err.println("FKTABLE_SCHEM : "+results.getObject(6));
        System.err.println("FKTABLE_NAME  : "+results.getObject(7));
        System.err.println("FKCOLUMN_NAME : "+results.getObject(8));
        System.err.println("KEY_SEQ       : "+results.getObject(9));
        System.err.println("UPDATE_RULE   : "+results.getObject(10));
        System.err.println("DELETE_RULE   : "+results.getObject(11));
        System.err.println("FK_NAME       : "+results.getObject(12));
        System.err.println("PK_NAME       : "+results.getObject(13));
        System.err.println("DEFERRABILITY : "+results.getObject(14));
	 * @param conn
	 */
	public static void importedKeys(Connection conn){
		System.out.println("----------------------imported Keys for OneToOne------------------------------");
		ResultSet rs=null;
		try{
			DatabaseMetaData dbmd=conn.getMetaData();
			rs=dbmd.getImportedKeys(conn.getCatalog(),null,"tbl_book");
			while(rs.next()){
			     System.out.println("  PKTableName="+rs.getString(3));
			     System.out.println(" PKColumnName="+rs.getString(4));
			     System.out.println("   PKSequence="+rs.getInt(9));
			     System.out.println(" FKTABLE_NAME="+rs.getString(7));
			     System.out.println("FPCOLUMN_NAME="+rs.getString(8));
			     System.out.println("================================================================");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
		}
	}
	
	public static void exportedKeys(Connection conn){
		System.out.println("--------------------exported Keys for OneToMany-----------------------------");
		ResultSet rs=null;
		try{
			DatabaseMetaData dbmd=conn.getMetaData();
			rs=dbmd.getExportedKeys(conn.getCatalog(),null,"tbl_book");
			while(rs.next()){
			     System.out.println("  PKTableName="+rs.getString(3));
			     System.out.println(" PKColumnName="+rs.getString(4));
			     System.out.println("   PKSequence="+rs.getInt(9));
			     System.out.println(" FKTABLE_NAME="+rs.getString(7));
			     System.out.println("FPCOLUMN_NAME="+rs.getString(8));
			     System.out.println("================================================================");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
		}
	}
	
	public static void getColumnInfo(Connection conn){
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps=conn.prepareStatement("select * from tbl_book");
			ps.setMaxRows(1);
			rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int colCount=rsmd.getColumnCount();

			for(int i=1;i<=colCount;i++){	
				  System.out.println("        Column: "+i);   
		          System.out.println("          Name: "+rsmd.getColumnName(i));   
		          System.out.println("    Model1 Name: "+rsmd.getTableName(i));   
		          System.out.println("   Schema Name: "+rsmd.getSchemaName(i));   
		          System.out.println("         Class: "+rsmd.getColumnClassName(i));   
		          System.out.println("          Type: "+rsmd.getColumnType(i));   
		          System.out.println("     Type name: "+rsmd.getColumnTypeName(i)); 
		          System.out.println("    isWritable: "+rsmd.isWritable(i));
		          System.out.println("   DisplaySize: "+rsmd.getColumnDisplaySize(i));
		          System.out.println("      isSigned: "+rsmd.isSigned(i));
		          System.out.println("Auto increment: "+rsmd.isAutoIncrement(i));   
		          System.out.print("     Is nullable: ");   
		              
		          switch (rsmd.isNullable(i))   {   
		          case ResultSetMetaData.columnNoNulls:   
		               System.out.println("No Nulls");   
		               break;   
		          case ResultSetMetaData.columnNullable:   
		               System.out.println("Nullable");   
		               break;   
		          case ResultSetMetaData.columnNullableUnknown:   
		               System.out.println("Unknown");   
		               break;   
		          }	
		          System.out.println("==============================================");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
			if(ps!=null)try{ps.close();}catch(SQLException e){}
		}
	}

}
