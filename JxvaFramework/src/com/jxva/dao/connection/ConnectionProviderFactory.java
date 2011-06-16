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


import com.jxva.dao.entity.Environment;
import com.jxva.log.Logger;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 11:00:01 by Jxva
 */
public class ConnectionProviderFactory {
	
	private static final Logger log=Logger.getLogger(ConnectionProviderFactory.class);

	private ConnectionProviderFactory(){
		throw new UnsupportedOperationException();
	}
	
	public static ConnectionProvider newConnectionProvider(){
		log.info("Connection Provider Factory initializing...");
		Provider provider=Provider.parse(Environment.getEnvironment().getPool());
		if(provider==Provider.JDBC){
			return new JDBCConnectionProvider();
		}else if(provider==Provider.PROXOOL){
			return new ProxoolConnectionProvider();
		}else if(provider==Provider.C3P0){
			return new C3P0ConnectionProvider();
		}else if(provider==Provider.DBCP){
			return new DBCPConnectionProvider();
		}else if(provider==Provider.JNDI){
			return new JndiConnectionProvider();
		}else if(provider==Provider.BONECP){
			return new BoneCPConnectionProvider();
		}else{
			throw new IllegalArgumentException("must select a connection pool.");
		}
	}
}
