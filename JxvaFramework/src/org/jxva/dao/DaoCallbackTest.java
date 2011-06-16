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
package org.jxva.dao;

import java.sql.SQLException;

import org.jxva.dao.model.Author;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOException;
import com.jxva.dao.DAOFactory;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-21 15:59:24 by Jxva
 */
public class DaoCallbackTest {
	
	private static final DAOFactory factory=DAOFactory.getInstance();
	
	public <T> T execute(DaoCallback<T> callback)throws DAOException{
		DAO dao=null;
		try{
			dao=factory.createDAO();
			return callback.doInDao(dao);
		}catch(SQLException e){
			throw new DAOException(e);
		}finally{
			if(dao!=null)dao.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DaoCallbackTest().save(new Author());
	}
	
	public int save(final Author author){
		return execute(new DaoCallback<Integer>(){
			public Integer doInDao(DAO dao) throws DAOException, SQLException {
				return dao.save(author);
			}
		});
	}
}
