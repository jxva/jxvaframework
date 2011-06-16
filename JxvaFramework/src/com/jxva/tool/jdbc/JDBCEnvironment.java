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
package com.jxva.tool.jdbc;

import com.jxva.dao.entity.Environment;

/**
 *
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-02 12:51:20 by Jxva
 */
public class JDBCEnvironment extends Environment{
		
	public String getDbType() {
		return dbType;
	}
	
	public String getDriverClass() {
		return driverClass;
	}
	
	
	public String getPassword() {
		return password;
	}
	

	public String getUrl() {
		return url;
	}
	public String getUsername() {
		return username;
	}
	
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public String getPool() {
		return pool;
	}

	public Integer getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getMinSize() {
		return minSize;
	}

	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}

	public Long getOvertime() {
		return overtime;
	}

	public void setOvertime(Long overtime) {
		this.overtime = overtime;
	}

	public Boolean getShowSql() {
		return showSql;
	}

	public void setShowSql(Boolean showSql) {
		this.showSql = showSql;
	}
}
