package demo.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.dao.type.SqlType;
/**
	create table tbl_mysql(
		a1 	tinyint,
		a2 	smallint,
		a3 	mediumint,
		a4 	integer,
		a5 	bigint,
		a6 	float,
		a7 	double,
		a8 	decimal,
		a9 	date,
		a10 datetime,
		a11 timestamp,
		a12 time,
		a13 year,
		a14 char,
		a15 varchar(255),
		a16	tinyblob,
		a17 blob,
		a18 mediumblob,
		a19 longblob,
		a20 tinytext,
		a21 text,
		a22 mediumtext,
		a23 longtext,
		a24 enum('inbox','outbox','other'),
		a25 set('male','female'),
		a26 geometry,
		a27 binary,
		a28 varbinary(10),
		a29 bit,
		a30 boolean
	)engine=innodb default charset=utf8;
	
	Name Type    JavaType                TypeName                      ClassName                               
	a1   -6      java.lang.Short         TINYINT                       java.lang.Integer                       
	a2   5       java.lang.Short         SMALLINT                      java.lang.Integer                       
	a3   4       java.lang.Integer       MEDIUMINT                     java.lang.Integer                       
	a4   4       java.lang.Integer       INT                           java.lang.Integer                       
	a5   -5      java.lang.Long          BIGINT                        java.lang.Long                          
	a6   7       java.lang.Float         FLOAT                         java.lang.Float                         
	a7   8       java.lang.Double        DOUBLE                        java.lang.Double                        
	a8   3       java.math.BigDecimal    DECIMAL                       java.math.BigDecimal                    
	a9   91      java.sql.Date           DATE                          java.sql.Date                           
	a10  93      java.sql.Timestamp      DATETIME                      java.sql.Timestamp                      
	a11  93      java.sql.Timestamp      TIMESTAMP                     java.sql.Timestamp                      
	a12  92      java.sql.Time           TIME                          java.sql.Time                           
	a13  91      java.sql.Date           YEAR                          java.sql.Date                           
	a14  1       java.lang.Character     CHAR                          java.lang.String                        
	a15  12      java.lang.String        VARCHAR                       java.lang.String                        
	a16  -3      java.io.InputStream     TINYBLOB                      [B                                      
	a17  -4      java.io.InputStream     BLOB                          [B                                      
	a18  -4      java.io.InputStream     MEDIUMBLOB                    [B                                      
	a19  -4      java.io.InputStream     LONGBLOB                      [B                                      
	a20  -1      java.lang.String        VARCHAR                       java.lang.String                        
	a21  -1      java.lang.String        VARCHAR                       java.lang.String                        
	a22  -1      java.lang.String        VARCHAR                       java.lang.String                        
	a23  -1      java.lang.String        VARCHAR                       java.lang.String                        
	a24  1       java.lang.Character     CHAR                          java.lang.String                        
	a25  1       java.lang.Character     CHAR                          java.lang.String                        
	a26  -2      java.io.InputStream     GEOMETRY                      [B                                      
	a27  -2      java.io.InputStream     BINARY                        [B                                      
	a28  -3      java.io.InputStream     VARBINARY                     [B                                      
	a29  -7      java.lang.Boolean       BIT                           java.lang.Boolean                       
	a30  -7      java.lang.Boolean       TINYINT                       java.lang.Boolean       
 */
public class MySQLDataType {

	private static final DAOFactory factory=DAOFactory.getInstance("demo.dao.mysql.jxva");
	private static DAO dao;
	public static void main(String[] args) throws Exception {
		dao=factory.createDAO();
		Connection conn=dao.getJdbcTemplate().getConnection();
		Statement stmt=conn.createStatement();
		stmt.setMaxRows(1);
		ResultSet rs=stmt.executeQuery("select * from tbl_mysql");
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
