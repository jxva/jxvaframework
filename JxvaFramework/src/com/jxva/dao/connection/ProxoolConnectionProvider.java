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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Semaphore;

import org.logicalcobwebs.proxool.ProxoolFacade;

import com.jxva.log.Logger;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 11:00:44 by Jxva
 */
public class ProxoolConnectionProvider implements ConnectionProvider{

	private static final Logger log=Logger.getLogger(ProxoolConnectionProvider.class);
	
	private String proxoolAlias;
	private Semaphore semaphore;
	
	public ProxoolConnectionProvider(){
		try {
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
			Properties info = new Properties();
			info.setProperty("proxool.maximum-new-connections","100");
			info.setProperty("proxool.maximum-connection-count",env.getMaxSize().toString());
			info.setProperty("proxool.minimum-connection-count",env.getMinSize().toString());
			info.setProperty("proxool.maximum-active-time","300000");//5 minute
			info.setProperty("proxool.house-keeping-sleep-time","90000");
			info.setProperty("proxool.prototype-count","1");
			info.setProperty("proxool.house-keeping-test-sql", "select CURRENT_DATE");
			info.setProperty("user",env.getUsername());
			info.setProperty("password",env.getPassword());
			proxoolAlias="proxool." + env.getAsia();
			String url = proxoolAlias + ":" + env.getDriverClass() + ":" + env.getUrl();
			ProxoolFacade.registerConnectionPool(url, info);
			semaphore=new Semaphore(env.getMaxSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Proxool Connection Provider initializing...");
	}
	
	public Connection getConnection() throws SQLException{
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			//ignore
		}
		synchronized(log){
			return DriverManager.getConnection(proxoolAlias);
		}
	}

	public void destroy() {
		try {
	        ProxoolFacade.shutdown(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close(Connection conn) throws SQLException {
		conn.close();
		semaphore.release();
	}
	
	public Statistics getStatistics(){
		
		return null;
	}
}
