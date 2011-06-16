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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.jxva.log.Logger;

/**
 * Database Connection Pool 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-19 00:26:39 by Jxva
 */
public class JDBCConnectionProvider implements ConnectionProvider {
	
	private static final Logger log=Logger.getLogger(JDBCConnectionProvider.class);
	private static final ConcurrentMap<Connection,Date> pool = new ConcurrentHashMap<Connection,Date>();
	private final Semaphore semaphore;
	private final Timer timer;
	//for statistics
	private int openedCount;
	private int closedCount;
	
	private int connectedCount;
	private int releasedCount;
	
	public JDBCConnectionProvider(){
		try {
			Class.forName(env.getDriverClass());			
			initMinSizeConnectionPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
		semaphore=new Semaphore(env.getMaxSize(),false);
		timer=new Timer(true);
		timer.schedule(new Task(),env.getOvertime(),env.getOvertime());
		log.info("JDBC Connection Provider initializing...");
	}
	
	/**
	 * initialize connection pool with min size
	 * @throws SQLException
	 */
	private void initMinSizeConnectionPool() throws SQLException{
		for(int i=0;i<env.getMinSize();i++){
			openedCount++;
			Connection conn=DriverManager.getConnection(env.getUrl(),env.getUsername(),env.getPassword());
			pool.put(conn,new Date());
		}
	}
	
	public void close(Connection conn) throws SQLException {
		try{
			synchronized (pool) {
				releasedCount++;
				if (pool.size() < env.getMaxSize()) {
					pool.put(conn,new Date());
					return;
				}
			}
			conn.close();
		}finally{
			semaphore.release();
		}
	}

	public void destroy() throws SQLException {
		for(Connection conn:pool.keySet() ) {
			conn.close();
			closedCount++;
		}
		pool.clear();
		timer.cancel();
	}

	public Connection getConnection() throws SQLException{
		try {
			semaphore.acquire();
			/*
			if(!semaphore.tryAcquire(5,TimeUnit.SECONDS)){
				if(semaphore.availablePermits()<2){
					for(Connection conn:pool.keySet() ) {
						conn.close();
						closedCount++;
					}
				}
			}
			*/
		} catch (InterruptedException e1) {
			//ignore
		}
		
		synchronized (pool) {
			connectedCount++;
			if (!pool.isEmpty() ) {
				Connection conn=pool.keySet().iterator().next();// fetch last connection for ever
				//Connection conn=(Connection)pool.keySet().toArray()[RandomUtil.getRandomNum(pool.size())];// random fetch one connection
				pool.remove(conn);
				try{
					conn.setAutoCommit(true);
				}catch(SQLException e){
					pool.clear();
					conn=getConnection();
				}
				return conn;
			}
		}
		log.info("reInitialize one database connection to pool...........................");
		synchronized(this){
			openedCount++;
		}
		return DriverManager.getConnection(env.getUrl(),env.getUsername(),env.getPassword());
	}

	class Task extends TimerTask {
	    public void run(){
	   	 	Date currentDate=new Date();
	   	 	try{
		   	 	for(Connection conn:pool.keySet() ) {
		   	 		long time = currentDate.getTime() - pool.get(conn).getTime();
					if (time > env.getOvertime()) {
						pool.remove(conn);
						conn.close();
						closedCount++;
						log.info("automatic remove one connection from database connection pool,overtime is "+time+" ms.");
					}
				}
	   	 	}catch(SQLException e){
				//ignore
			}
	    }
	}
	
	public Statistics getStatistics(){
		Statistics stat=new Statistics();
		stat.setEnvironment(env);
		stat.setPool(pool);
		stat.setOpenedCount(openedCount);
		stat.setClosedCount(closedCount);
		stat.setConnectedCount(connectedCount);
		stat.setReleasedCount(releasedCount);
		return stat;
	}
}