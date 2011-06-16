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
package com.jxva.dao.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 20:42:00 by Jxva
 */
public class PrepareStatementAndRs {
	
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;

	public PrepareStatementAndRs(){
		
	}
	
	public PrepareStatementAndRs(PreparedStatement pstmt,ResultSet rs,String sql){
		this.pstmt=pstmt;
		this.rs=rs;
		this.sql=sql;
	}
	
	public void close(){
		try{if(rs!=null)rs.close();}catch(Exception e){}
		try{if(pstmt!=null)pstmt.close();}catch(Exception e){}
	}
	

	public ResultSet resultSet(){
		return rs;
	}
	
	public PreparedStatement prePareStatemnet(){
		return pstmt;
	}
	
	public String getSql(){
		return sql;
	}
}
