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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jxva.dao.connection.ConnectionProvider;
import com.jxva.dao.connection.ConnectionProviderFactory;
import com.jxva.dao.dialect.DialectFactory;
import com.jxva.dao.entity.Environment;
import com.jxva.dao.jdbc.ConnectionHolder;
import com.jxva.dao.transaction.JdbcTransaction;
import com.jxva.log.Logger;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:47:24 by Jxva
 */
public class DAOFactory{
	
	private static final ConcurrentMap<String,DAOFactory> daos=new ConcurrentHashMap<String,DAOFactory>();
	private static final Logger log=Logger.getLogger(DAOFactory.class);
	
	private ConnectionProvider connectionProvider;
	private DAO dao;
	private DAOImpl daoImpl;
	private JdbcTemplate jdbcTemplate;
	private JdbcTransaction jdbcTransaction;
	
	private DAOFactory(){
		
	}
		
	public static DAOFactory getInstance(){
		return getInstance("jxva-dao");
	}
	
	public static DAOFactory getInstance(final String conf){
		DAOFactory daoFactory=daos.get(conf);
		if(daoFactory==null){
			synchronized(DAOFactory.class){
				daoFactory=daos.get(conf);
				if(daoFactory==null){
					log.info("DAOFactory initializing...");	
					DAOFactory factory=new DAOFactory();
					Environment env=new Environment(conf);
					factory.connectionProvider=ConnectionProviderFactory.newConnectionProvider();
					ConnectionHolder.init(factory.connectionProvider);
					factory.jdbcTemplate=new JdbcTemplate();
					factory.jdbcTemplate.setShowSql(env.getShowSql());
					factory.jdbcTransaction=new JdbcTransaction();
					factory.daoImpl=new DAOImpl(DialectFactory.createDialect(env));	
					daoFactory=factory;
					daos.put(conf,factory);
				}
			}
		}
		return daoFactory;
	}
		
	public DAO createDAO()throws DAOException{		
		try {
			if(ConnectionHolder.isClosed()){//prevent recreate dao
				Connection connection=ConnectionHolder.getConnection();
				jdbcTemplate.setConnection(connection);
				jdbcTransaction.setConnection(connection);
				daoImpl.initialize(jdbcTemplate,jdbcTransaction);
				dao=(DAO)new DAOProxy().bind(daoImpl);
			}
			return dao;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public ConnectionProvider getConnectionProvider(){
		return connectionProvider;
	}
		
	public void destroy(){
		try {
			connectionProvider.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
