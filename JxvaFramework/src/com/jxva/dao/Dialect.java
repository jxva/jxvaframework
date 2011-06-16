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
package com.jxva.dao;


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:58:03 by Jxva
 */
public interface Dialect {
	
	/**
	 * 
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 * @throws DataAccessException
	 */
	public String getSQLWithRowSetLimit(String sql,int start,int end)throws DataAccessException;
	
	/**
	 * Does this dialect support some form of limiting query results
	 * via a SQL clause?
	 *
	 * @return True if this dialect supports some form of LIMIT.
	 */
	public boolean supportsLimit();

	/**
	 * Does this dialect's LIMIT support (if any) additionally
	 * support specifying an offset?
	 *
	 * @return True if the dialect supports an offset within the limit support.
	 */
	public boolean supportsLimitOffset();
	
	public boolean supportsGetGeneratedKeys();
}
