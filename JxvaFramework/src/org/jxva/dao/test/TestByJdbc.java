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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.jxva.dao.Jdbc;
import com.jxva.dao.Parser;
import com.jxva.dao.Statement;
import com.jxva.dao.entity.StatementType;
import com.jxva.dao.jdbc.Column;
import com.jxva.dao.parser.JqlParser;


public class TestByJdbc extends BaseDataSource{
	  
	private static final Parser parser=new JqlParser();
		
	public static void main(String[] args) throws SQLException {
		Connection conn=datasource.getConnection();
		//test1(conn);
		List<Column> list=new Jdbc(conn).getAllColumns("tbl_book");
		for(Column c:list){
			System.out.println(c.getName());
		}
		conn.close();
	}
	
	public static void test23(Connection conn) throws SQLException{
		PreparedStatement ps=conn.prepareStatement("select * from tbl_book where book_id exists (?)",ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
		ps.setObject(1,"1,2,3,4,5,6");
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			System.out.println(rs.getObject(1));
		}
		
		rs.close();
		
//		PreparedStatement ps=conn.prepareStatement("delete from tbl_book where book_id in (?)");
//		ps.setString(1,"1,2,3,4,5,6");
		ps.close();
	}
	
	public static void test1(Connection conn) throws SQLException{
		//String jql="from Book   b left   join fetch  b.Publish p,b.category c where b.publishId in (select a.publishId from Publish a where a.publishId in (1,2,3,4) or a.name like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 or b.name like '%c%' and p.publishId in (select sb.publishId from Book sb where sb.bookId in (2,3,5)) or b.bookId=2 order by b.bookId desc";
		List<String> jqls=TestData.jqls;
		for(String jql:jqls){
			Statement statement=parser.parse(jql);
			System.out.println(statement.getSql());
			test(conn,statement);
		}
	}
	
	public static void test(Connection conn,Statement statement) throws SQLException{
		//conn.prepareCall(statement.getSql());
		PreparedStatement ps=conn.prepareStatement(statement.getSql());
		if(statement.getStatementType()>StatementType.SELECT){
			ps.executeUpdate();
		}else{
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData  rsmd=rs.getMetaData();
			int columnCount=rsmd.getColumnCount();
			for(int i=1;i<=columnCount;i++){
				System.out.printf("%-24s",rsmd.getColumnName(i));
			}
			rs.close();
		}
		
		System.out.println();
//		while(rs.next()){
//			for(int i=1;i<=columnCount;i++){
//				System.out.printf("%-24s",rs.getString(i));
//			}
//			System.out.println();
//		}
		ps.close();
		
	}

}
