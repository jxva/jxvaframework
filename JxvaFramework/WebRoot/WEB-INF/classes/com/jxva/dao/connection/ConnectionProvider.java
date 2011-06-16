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

import com.jxva.dao.entity.Environment;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:59:54 by Jxva
 */
public interface ConnectionProvider {
	
	public static final Environment env=Environment.getEnvironment();
	
	/**
	 * Grab a connection
	 * @return a JDBC connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;
	
	public void close(Connection conn) throws SQLException;
		
	/**
	 * Release all resources held by this provider. JavaDoc requires a second sentence.
	 * @throws Exception
	 */
	public void destroy() throws SQLException;
	
	public Statistics getStatistics();
}







