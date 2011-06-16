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
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

import com.jxva.dao.entity.Environment;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-12 11:26:54 by Jxva
 */
public class Statistics {
	
	private Environment environment;
	private int openedCount;
	private int closedCount;
	private int connectedCount;
	private int releasedCount;
	private ConcurrentMap<Connection,Date> pool;
	
	public Environment getEnvironment() {
		return environment;
	}
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	public int getOpenedCount() {
		return openedCount;
	}
	public void setOpenedCount(int openedCount) {
		this.openedCount = openedCount;
	}
	public int getClosedCount() {
		return closedCount;
	}
	public void setClosedCount(int closedCount) {
		this.closedCount = closedCount;
	}
	public int getConnectedCount() {
		return connectedCount;
	}
	public void setConnectedCount(int connectedCount) {
		this.connectedCount = connectedCount;
	}
	public int getReleasedCount() {
		return releasedCount;
	}
	public void setReleasedCount(int releasedCount) {
		this.releasedCount = releasedCount;
	}
	
	public void setPool(ConcurrentMap<Connection,Date> pool) {
		this.pool = pool;
	}
	public ConcurrentMap<Connection,Date> getPool() {
		return pool;
	}
	
	public String toString(){
		if(environment==null){
			return "Database connection pool hasn't been initialized.";
		}else{
			StringBuilder sb=new StringBuilder();
			sb.append("                overTime: ").append(environment.getOvertime()).append('\n');
			sb.append("               minCount: ").append(environment.getMinSize()).append('\n');
			sb.append("              maxCount: ").append(environment.getMaxSize()).append('\n');
			sb.append("              poolCount: ").append(pool.size()).append('\n');
			sb.append("         openedCount: ").append(openedCount).append('\n');
			sb.append("           closedCount: ").append(closedCount).append('\n');
			sb.append("     connectedCount: ").append(connectedCount).append('\n');
			sb.append("          releasedSize: ").append(releasedCount).append('\n').append('\n');
			Date currentDate=new Date();
		   	for(Connection conn:pool.keySet() ) {
		   	 	long time = currentDate.getTime() - pool.get(conn).getTime();
		   	 	sb.append(conn).append(" active time : ").append(time).append(" ms \n");
			}			
			return sb.toString();
		}
	}

}
