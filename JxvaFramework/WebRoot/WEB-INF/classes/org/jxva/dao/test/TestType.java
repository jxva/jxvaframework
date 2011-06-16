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
package org.jxva.dao.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.jxva.dao.type.SqlType;

/**
 * 
		----------------------------SQL Server------------------------------------------------

		Name Type    JavaType                TypeName                      ClassName                               
		a1   -5      java.lang.Long          bigint                        java.lang.Long                          
		a2   -5      java.lang.Long          bigint                        java.lang.Long                          
		a3   -2      java.io.InputStream     binary                        [B                                      
		a4   -7      java.lang.Boolean       bit                           java.lang.Boolean                       
		a5   1       java.lang.Character     char                          java.lang.String                        
		a6   93      java.sql.Timestamp      datetime                      java.sql.Timestamp                      
		a7   3       java.math.BigDecimal    decimal                       java.math.BigDecimal                    
		a8   8       java.lang.Double        float                         java.lang.Double                        
		a9   -4      java.io.InputStream     image                         [B                                      
		a10  4       java.lang.Integer       int                           java.lang.Integer                       
		a11  3       java.math.BigDecimal    money                         java.math.BigDecimal                    
		a12  -15     java.lang.Character     nchar                         java.lang.String                        
		a13  -16     java.lang.String        ntext                         java.lang.String                        
		a14  2       java.math.BigDecimal    numeric                       java.math.BigDecimal                    
		a15  -9      java.lang.String        nvarchar                      java.lang.String                        
		a16  -16     java.lang.String        nvarchar                      java.lang.String                        
		a17  7       java.lang.Float         real                          java.lang.Float                         
		a18  93      java.sql.Timestamp      smalldatetime                 java.sql.Timestamp                      
		a19  5       java.lang.Short         smallint                      java.lang.Short                         
		a20  3       java.math.BigDecimal    smallmoney                    java.math.BigDecimal                    
		a22  -1      java.lang.String        text                          java.lang.String                        
		a23  -2      java.io.InputStream     timestamp                     [B                                      
		a24  -6      java.lang.Short         tinyint                       java.lang.Short                         
		a25  1       java.lang.Character     uniqueidentifier              java.lang.String                        
		a26  -3      java.io.InputStream     varbinary                     [B                                      
		a27  -4      java.io.InputStream     varbinary                     [B                                      
		a28  12      java.lang.String        varchar                       java.lang.String                        
		a29  -1      java.lang.String        varchar                       java.lang.String                        
		a30  -16     java.lang.String        xml                           java.lang.String      

		--------------------------Derby-----------------------------------------------

		Name Type    JavaType                TypeName                      ClassName                               
		A1   4       java.lang.Integer       INTEGER                       java.lang.Integer                       
		A2   -5      java.lang.Long          BIGINT                        java.lang.Long                          
		A3   2004    java.sql.Blob           BLOB                          java.sql.Blob                           
		A4   1       java.lang.Character     CHAR                          java.lang.String                        
		A5   -2      java.io.InputStream     CHAR FOR BIT DATA             byte[]                                  
		A6   2005    java.sql.Clob           CLOB                          java.sql.Clob                           
		A7   91      java.sql.Date           DATE                          java.sql.Date                           
		A8   3       java.math.BigDecimal    DECIMAL                       java.math.BigDecimal                    
		A9   8       java.lang.Double        DOUBLE                        java.lang.Double                        
		A10  8       java.lang.Double        DOUBLE                        java.lang.Double                        
		A11  8       java.lang.Double        DOUBLE                        java.lang.Double                        
		A12  4       java.lang.Integer       INTEGER                       java.lang.Integer                       
		A13  -1      java.lang.String        LONG VARCHAR                  java.lang.String                        
		A14  -4      java.io.InputStream     LONG VARCHAR FOR BIT DATA     byte[]                                  
		A15  3       java.math.BigDecimal    DECIMAL                       java.math.BigDecimal                    
		A16  7       java.lang.Float         REAL                          java.lang.Float                         
		A17  5       java.lang.Short         SMALLINT                      java.lang.Integer                       
		A18  92      java.sql.Time           TIME                          java.sql.Time                           
		A19  93      java.sql.Timestamp      TIMESTAMP                     java.sql.Timestamp                      
		A20  12      java.lang.String        VARCHAR                       java.lang.String                        
		A21  -3      java.io.InputStream     VARCHAR FOR BIT DATA          byte[] 

		------------------------Mysql---------------------------------------------
		
		Name NULL  LENGTH      SQLTYPE                       TypeName            ClassName                               
		a1   1     4           java.lang.Short               TINYINT             java.lang.Integer                       
		a2   1     6           java.lang.Short               SMALLINT            java.lang.Integer                       
		a3   1     9           java.lang.Integer             MEDIUMINT           java.lang.Integer                       
		a4   1     11          java.lang.Integer             INT                 java.lang.Integer                       
		a5   1     20          java.lang.Long                BIGINT              java.lang.Long                          
		a6   1     12          java.lang.Float               FLOAT               java.lang.Float                         
		a7   1     22          java.lang.Double              DOUBLE              java.lang.Double                        
		a8   1     11          java.math.BigDecimal          DECIMAL             java.math.BigDecimal                    
		a9   1     10          java.sql.Date                 DATE                java.sql.Date                           
		a10  1     19          java.sql.Timestamp            DATETIME            java.sql.Timestamp                      
		a11  0     19          java.sql.Timestamp            TIMESTAMP           java.sql.Timestamp                      
		a12  1     8           java.sql.Time                 TIME                java.sql.Time                           
		a13  1     4           java.sql.Date                 YEAR                java.sql.Date                           
		a14  1     1           java.lang.Character           CHAR                java.lang.String                        
		a15  1     255         java.lang.String              VARCHAR             java.lang.String                        
		a16  1     255         java.io.InputStream           TINYBLOB            [B                                      
		a17  1     65535       java.io.InputStream           BLOB                [B                                      
		a18  1     16777215    java.io.InputStream           MEDIUMBLOB          [B                                      
		a19  1     2147483647  java.io.InputStream           LONGBLOB            [B                                      
		a20  1     85          java.lang.String              VARCHAR             java.lang.String                        
		a21  1     21845       java.lang.String              VARCHAR             java.lang.String                        
		a22  1     5592405     java.lang.String              VARCHAR             java.lang.String                        
		a23  1     715827882   java.lang.String              VARCHAR             java.lang.String                        
		a24  1     6           java.lang.Character           CHAR                java.lang.String                        
		a25  1     11          java.lang.Character           CHAR                java.lang.String                        
		a26  1     2147483647  java.io.InputStream           GEOMETRY            [B                                      
		a27  1     1           java.io.InputStream           BINARY              [B                                      
		a28  1     10          java.io.InputStream           VARBINARY           [B                                      
		a29  1     1           java.lang.Boolean             BIT                 java.lang.Boolean                       
		a30  1     1           java.lang.Boolean             TINYINT             java.lang.Boolean        

     
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 14:14:06 by Jxva
 */
public class TestType {
	static SimpleDataSource datasource=new SimpleDataSource();
	  static{
		    datasource.setDriverClassName("com.mysql.jdbc.Driver");
		    datasource.setUrl("jdbc:mysql://127.0.0.1:3306/jxva?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8");
		    datasource.setUsername("ztemt");
		    datasource.setPassword("ztemt");
	  }
	  
	  
	  		
	public static void main(String[] args) throws SQLException {
		Connection conn=datasource.getConnection();
		Statement stmt=conn.createStatement();
		stmt.setMaxRows(1);
		ResultSet rs=stmt.executeQuery("select * from tbl_mysql");
		ResultSetMetaData rsmd=rs.getMetaData();
		int colCount=rsmd.getColumnCount();
		System.out.printf("%-5s%-6s%-12s%-8s%-30s%-20s%-40%s\n","Name","NULL","LENGTH","SQLTYPE","TypeName","ClassName");
		for(int i=1;i<=colCount;i++){	
			System.out.printf("%-5s",rsmd.getColumnName(i));
			System.out.printf("%-6s",rsmd.isNullable(i));
			System.out.printf("%-12s",rsmd.getColumnDisplaySize(i));
			System.out.printf("%-8s",rsmd.getColumnType(i));
			System.out.printf("%-30s",SqlType.get(rsmd.getColumnType(i)).getName());
			System.out.printf("%-20s",rsmd.getColumnTypeName(i));
			System.out.printf("%-40s\n",rsmd.getColumnClassName(i));
		}
	}

	public static int getSqlType(int columnType,int size){
		switch(columnType){
			case java.sql.Types.CHAR:
				return size==1?java.sql.Types.CHAR:java.sql.Types.VARCHAR;
			
			default:
				return columnType;	
		}
	}
}
