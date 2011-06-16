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
 *
 */
package com.jxva.dao;

import java.sql.SQLException;


/**
 * Wraps an <tt>SQLException</tt>. Indicates that an exception
 * occurred during a JDBC call.
 *
 * @see 	java.sql.SQLException
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:55:59 by Jxva
 */
public class DataAccessException extends DAOException{


	private static final long serialVersionUID = 575533917627923339L;
	private SQLException sqle;
	private String sql;
	
	public DataAccessException(String string){
		super(string);
	}
	
	public DataAccessException(SQLException root){
		super(root);
		sqle=root;
	}
	
	public DataAccessException(SQLException root,String sql){
		super(root);
		sqle=root;
		this.sql=sql;
	}
	
	public DataAccessException(String string, SQLException root) {
		super(string, root);
		sqle=root;
	}

	public DataAccessException(String string, SQLException root, String sql) {
		this(string, root);
		this.sql = sql;
	}

	/**
	 * Get the SQLState of the underlying <tt>SQLException</tt>.
	 * @see java.sql.SQLException
	 * @return String
	 */
	public String getSQLState() {
		return sqle.getSQLState();
	}

	/**
	 * Get the <tt>errorCode</tt> of the underlying <tt>SQLException</tt>.
	 * @see java.sql.SQLException
	 * @return int the error code
	 */
	public int getErrorCode() {
		return sqle.getErrorCode();
	}

	/**
	 * Get the underlying <tt>SQLException</tt>.
	 * @return SQLException
	 */
	public SQLException getSQLException() {
		return sqle;
	}
	
	/**
	 * Get the actual SQL statement that caused the exception
	 * (may be null)
	 */
	public String getSQL() {
		return sql;
	}

}
