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
package com.jxva.dao.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jxva.log.Logger;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-12-29 08:35:58 by Jxva
 */
public class BoneCPConnectionProvider  implements ConnectionProvider{
	
	private static final Logger log=Logger.getLogger(BoneCPConnectionProvider.class);

	private BoneCP connectionPool;
	
	public BoneCPConnectionProvider(){
		try {
			Class.forName(env.getDriverClass());
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(env.getUrl()); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername(env.getUsername()); 
			config.setPassword(env.getPassword());
			config.setMinConnectionsPerPartition(env.getMinSize());
			config.setMaxConnectionsPerPartition(env.getMaxSize());
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config);
			log.info("BoneCP Connection Provider initializing...");
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close(Connection conn) throws SQLException {
		conn.close();		
	}

	@Override
	public void destroy() throws SQLException {
		connectionPool.shutdown();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return connectionPool.getConnection();
	}

	@Override
	public Statistics getStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

}
