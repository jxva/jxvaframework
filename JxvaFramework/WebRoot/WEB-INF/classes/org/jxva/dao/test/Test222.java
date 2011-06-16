package org.jxva.dao.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jxva.dao.model.Author;

import com.jxva.dao.DataAccessException;
import com.jxva.dao.JdbcTemplate;
import com.jxva.dao.datasource.SimpleDataSource;
import com.jxva.dao.jdbc.PreparedStatementSetter;
import com.jxva.dao.jdbc.RowCallbackHandler;


public class Test222 {
	  static SimpleDataSource datasource=new SimpleDataSource();
	  static{
		    datasource.setDriverClassName("com.mysql.jdbc.Driver");
		    datasource.setUrl("jdbc:mysql://127.0.0.1:3306/jxva?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8");
		    datasource.setUsername("ztemt");
		    datasource.setPassword("ztemt");
	  }
	public static void main(String[] args) throws SQLException {
		
		JdbcTemplate jdbc=new JdbcTemplate(datasource);
		int i=jdbc.update("update tbl_author set name=? where author_id=?",new Object[]{"jxva",1});
		int j=jdbc.update("update tbl_author set name=? where author_id=?","jxva",2);//for Object...args;
		int k=jdbc.update("update tbl_author set name='jxva' where author_id=3");
		//System.out.println(i);
		test1(jdbc);
		test2(jdbc);
		
	}
	
	public static void test1(JdbcTemplate jdbc){
		int j=jdbc.update("update tbl_author set name = ? where author_id = ?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws DataAccessException {
						try{
							ps.setString(1, "jxva");
							ps.setInt(2, 4);
						}catch(SQLException e){
							throw new DataAccessException(e);
						}
					}
			}
		);
		//System.out.println(j);
	}
	
	public static void test2(JdbcTemplate jdbc) throws SQLException{

		final List<Author> authorList = new ArrayList<Author>();
		jdbc.query("select author_id,name from tbl_author where author_id>0",
			new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
					Author author = new Author();
					author.setAuthorId(rs.getInt("author_id"));
					author.setName(rs.getString("name"));
					authorList.add(author);
				}
			}
		);
		
		for(Author a:authorList){
			System.out.println(a.toString());
		}
	}
	
	public static void test(){
		int threadCount=10;
		ExecutorService execute=Executors.newFixedThreadPool(threadCount);
		for(int i=0;i<threadCount;i++){
			execute.submit(new Runnable(){
				public void run(){
					JdbcTemplate jdbc=new JdbcTemplate(datasource);
					int i=jdbc.update("update tbl_author set name=? where author_id=?",new Object[]{"jxva",1});
					int j=jdbc.update("update tbl_author set name=? where author_id=?","jxva",2);//for Object...args;
					int k=jdbc.update("update d tbl_author set name='jxva' where author_id=3");
					//System.out.println(i);
				}
			});
		}
		execute.shutdown();
	}
}
