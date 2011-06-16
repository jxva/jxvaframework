package demo.dao.sqlserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.dao.type.SqlType;

/**
		drop table tbl_sqlserver;
		create table tbl_sqlserver(
			a1	bigint identity primary key not null,
			a2	bigint,
			a3	binary(50000),
			a4	bit,
			a5	char(10),
			a6	datetime,
			a7	decimal(18,0),
			a8	float,
			a9	image,
			a10	int,
			a11	money,
			a12	nchar(10),
			a13	ntext,
			a14	numeric(18,0),
			a15	nvarchar(50),
			a16	nvarchar(MAX),
			a17	real,
			a18	smalldatetime,
			a19	smallint,
			a20	smallmoney,
			-- a21	sql_variant,
			a22	text,
			a23	timestamp,
			a24	tinyint,
			a25	uniqueidentifier, -- for uuid
			a26	varbinary(50),
			a27	varbinary(MAX),
			a28	varchar(50),
			a29	varchar(MAX),
			a30	xml
		);
	
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
 
 		-- error
 		a23  -2      java.io.InputStream     timestamp                     [B            
 */

public class SQLServerDataType {

	private static final DAOFactory factory=DAOFactory.getInstance("test.dao.sqlserver.jxva");
	private static DAO dao;
	public static void main(String[] args) throws Exception {
		dao=factory.createDAO();
		Connection conn=dao.getJdbcTemplate().getConnection();
		Statement stmt=conn.createStatement();
		stmt.setMaxRows(1);
		ResultSet rs=stmt.executeQuery("select * from tbl_sqlserver");
		ResultSetMetaData rsmd=rs.getMetaData();
		int colCount=rsmd.getColumnCount();
		System.out.printf("%-5s%-8s%-24s%-30s%-40s\n","Name","Type","JavaType","TypeName","ClassName");
		for(int i=1;i<=colCount;i++){	
			System.out.printf("%-5s",rsmd.getColumnName(i));
			//System.out.print(rsmd.isNullable(i)+"\t");
			//System.out.print(" "+rsmd.getColumnDisplaySize(i));
			System.out.printf("%-8s",rsmd.getColumnType(i));
			System.out.printf("%-24s",SqlType.get(rsmd.getColumnType(i)).getName());
			System.out.printf("%-30s",rsmd.getColumnTypeName(i));
			System.out.printf("%-40s\n",rsmd.getColumnClassName(i));
		}
		dao.close();
	}
}
