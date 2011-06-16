package com.jxva.dao.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import com.jxva.dao.DAOException;
import com.jxva.dao.DataAccessException;
import com.jxva.dao.Transaction;

public class JdbcTransaction implements Transaction{

	private Connection conn;
	
	public void setConnection(Connection conn){
		this.conn=conn;
	}
		
	public void begin() throws DAOException {
		try {
			if(isInTransaction())return;
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}		
	}

	
	public void commit() throws DAOException {
		try {
			if(!isInTransaction())return;
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}		
	}

	
	public boolean isInTransaction() throws DAOException {
		try {
			return !conn.getAutoCommit();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	
	public void rollback() throws DAOException {
		try{
			if(!isInTransaction())return;
			conn.rollback();
			conn.setAutoCommit(true);
		}catch(SQLException e){
			throw new DataAccessException(e);
		}	
	}
	
	public void onException() throws DAOException{
		try{
			if(isInTransaction()){
				conn.rollback();
				conn.setAutoCommit(true);
			}
		}catch(SQLException e){
			throw new DAOException(e);
		}
	}
	
	
}
