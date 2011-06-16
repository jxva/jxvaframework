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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-23 10:42:42 by Jxva
 */
public class TestMultiThreadDao {

	private static final DAOFactory factory=DAOFactory.getInstance();
	static DAO dao=null;
	
	public static void main(String[] args) {	
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for (int i = 0; i <10; i++) {
			pool.execute(new Runnable() {
				public void run() {
					dao=factory.createDAO();
					//DAOHolder.getDAO();
					dao.getJdbcTemplate();
					
					dao.close();
					dao.close();
					dao.close();
					dao.close();
					dao.close();
					//dao.getJdbcTemplate();
					//DAOHolder.removeDAO();
				}
			});
		}
		pool.shutdown();
	}
}
