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

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.jxva.log.Logger;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 11:00:39 by Jxva
 */
public class JndiConnectionProvider implements ConnectionProvider{

	private static final Logger log = Logger.getLogger(JndiConnectionProvider.class);

	private Context initCtx;
	private DataSource ds;
	
	public JndiConnectionProvider(){
		try {
			initCtx = new InitialContext();
			ds = (DataSource)initCtx.lookup(env.getJndi());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		log.info("Jndi Connection Provider initializing...");
	}

	public Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
			
	public void destroy() {
		try{
			initCtx.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void close(Connection conn) throws SQLException {
		conn.close();		
	}
	
	public Statistics getStatistics(){
		return null;
	}
}
