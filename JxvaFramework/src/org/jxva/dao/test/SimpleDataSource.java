package org.jxva.dao.test;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

public class SimpleDataSource implements DataSource {
	
	private String url;
	private String username;
	private String password;
	private String driverClassName;

	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url,username,password);
	}

	
	public Connection getConnection(String username, String password)throws SQLException {
		return DriverManager.getConnection(url,username,password);
	}

	
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDriverClassName() {
		return driverClassName;
	}


	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}


	public boolean shouldClose(Connection con) {
		return false;
	}
	


}
