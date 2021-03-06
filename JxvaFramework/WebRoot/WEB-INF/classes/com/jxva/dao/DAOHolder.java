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
 * @version 2008-11-27 09:06:52 by Jxva
 */
public abstract class DAOHolder {
	
	private static final DAOFactory factory=DAOFactory.getInstance();
	private static final ThreadLocal<DAO> threadLocal = new ThreadLocal<DAO>();
		
	public static DAO getDAO(){
		DAO dao=threadLocal.get();
		if(dao==null){
			dao=factory.createDAO();
			threadLocal.set(dao);
		}
		return dao;
	}
		
	public static void removeDAO(){
		DAO dao=threadLocal.get();
        if (dao!=null){
          dao.close();
          threadLocal.set(null);
        }
	}
}
