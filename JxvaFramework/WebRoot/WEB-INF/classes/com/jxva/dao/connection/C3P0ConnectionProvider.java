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
package com.jxva.dao.connection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.jxva.log.Logger;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:59:47 by Jxva
 */
public class C3P0ConnectionProvider implements ConnectionProvider{

	private static final Logger log=Logger.getLogger(C3P0ConnectionProvider.class);
	private DataSource ds;

	public C3P0ConnectionProvider(){
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(env.getDriverClass());
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl(env.getUrl());
		cpds.setUser(env.getUsername());
		cpds.setPassword(env.getPassword());
		cpds.setMaxStatements(180);
		cpds.setMaxPoolSize(env.getMaxSize());
		ds=cpds;
		log.info("C3P0 Connection Provider initializing...");
	}
	public Connection getConnection() throws SQLException{
		return  ds.getConnection();
	}
	
	
	public void destroy() {
		try {
			DataSources.destroy( ds );
		} catch (SQLException e) {
			
		}
	}

	public void close(Connection conn) throws SQLException {
		conn.close();
	}
	
	public Statistics getStatistics(){
		return null;
	}
}
