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
package com.jxva.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.jxva.dao.connection.ConnectionProvider;



/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:54:05 by Jxva
 */
public abstract class ConnectionHolder {
	
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private static ConnectionProvider connectionProvider;
	//private static Semaphore semaphore;
	//private volatile static int threadCount=0;
	
	public static void  init(ConnectionProvider connectionProvider){
		ConnectionHolder.connectionProvider=connectionProvider;
		//ConnectionHolder.semaphore=new Semaphore(maxSize);
	}
	
	public static Connection getConnection()throws SQLException{
		Connection conn=threadLocal.get();
		if(conn==null){
			//semaphore.acquire();
			//System.out.println("$$$$$$$$$"+(++threadCount));
			conn=connectionProvider.getConnection();
			conn.setAutoCommit(true);
			threadLocal.set(conn);
		}
		return conn;
	}
		
	public static void closeConnection()throws SQLException{
		Connection conn=threadLocal.get();
		if (conn != null) {
            //if (threadLocal!=null&&threadLocal.get()==conn) {
            	connectionProvider.close(conn);
                threadLocal.set(null);
               // semaphore.release();
                //System.out.println("--------"+(--threadCount));
           // }
        }
	}
	
	public static boolean isClosed()throws SQLException{
		Connection conn=threadLocal.get();
		return conn!=null?conn.isClosed():true;
//		Connection conn=getConnection();
//		if (conn != null) {
//            if (threadLocal!=null&&threadLocal.get()==conn) {
//            	return false;
//            }
//        }
//		return true;
	}
}
