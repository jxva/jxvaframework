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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jxva.dao.model.Author;

import com.jxva.dao.JdbcTemplate;
import com.jxva.dao.datasource.SimpleDataSource;
import com.jxva.dao.jdbc.PreparedStatementSetter;
import com.jxva.dao.jdbc.RowCallbackHandler;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-08 13:55:00 by Jxva
 */
public class TestJdbcTemplate1 {

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
		//test1(jdbc);
		//test2(jdbc);
		test3(jdbc);
	}
	
	public static void test3(JdbcTemplate jdbc)throws SQLException{
		//jdbc.setMaxRows(10);
		//jdbc.setFetchSize(5);
		List<Map<String,Object>> list=jdbc.queryForList("select * from tbl_author");

		for(Map<String,Object> map:list){
			System.out.println("Name="+map.get("Name"));
			for(String key:map.keySet()){
				System.out.println(key+"="+map.get(key));
			}
		}
	}
	
	public static void test1(JdbcTemplate jdbc) throws SQLException{
		int j=jdbc.update("update tbl_author set name = ? where author_id = ?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setString(1, "jxva");
						ps.setInt(2, 4);
						
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
	
	public static void test(final JdbcTemplate jdbc){
		int threadCount=10;
		ExecutorService execute=Executors.newFixedThreadPool(threadCount);
		for(int i=0;i<threadCount;i++){
			execute.submit(new Runnable(){
				public void run(){
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
