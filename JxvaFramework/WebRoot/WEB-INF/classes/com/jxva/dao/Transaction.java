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
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-02 11:21:01 by Jxva
 */
public interface Transaction {
	
	public void begin() throws DAOException;

	public void commit() throws DAOException;

	public void rollback() throws DAOException;

	public boolean isInTransaction()throws DAOException;
	
	public void onException() throws DAOException;
	
}
