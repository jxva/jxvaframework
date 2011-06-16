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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jxva.dao.entity.TableCache;
import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.TableEntry;
import com.jxva.dao.jdbc.ConnectionHolder;
import com.jxva.dao.jdbc.GeneratedKeyHolder;
import com.jxva.dao.jdbc.KeyHolder;
import com.jxva.dao.jdbc.PreparedStatementCreator;
import com.jxva.dao.jdbc.ResultSetExtractor;
import com.jxva.dao.parser.JqlParser;
import com.jxva.dao.type.Types;
import com.jxva.dao.util.DAOUtil;
import com.jxva.log.Logger;
import com.jxva.util.ModelUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:55:26 by Jxva
 */
public class DAOImpl implements DAO{
	
	private final Dialect dialect;
	private JdbcTemplate template;
	private Transaction transaction;
	
	private static final Logger log=Logger.getLogger(DAOImpl.class);
	private static final Parser parser=new JqlParser();
	
	public DAOImpl(Dialect dialect){
		this.dialect=dialect;
		log.info("DAOImpl initializing...");	
	}
	
	public void initialize(JdbcTemplate template,Transaction transaction){
		this.template=template;
		this.transaction=transaction;
	}
		
	public void afterAnyInvocation(){
		System.out.println("after invocation");
	}
		
	public void beforeAnyInvocation(){
		System.out.println("before invocation dfdddddddd");
	}

	public Transaction beginTransaction()throws DAOException{
		this.transaction.begin();
		return this.transaction;
	}

	public void close()throws DAOException{
		if(transaction.isInTransaction())transaction.rollback();
		try{
			ConnectionHolder.closeConnection();
		}catch(SQLException e){
			log.error("dao close "+e.getMessage());
			throw new DAOException(e);
		}	
	}
	
	public void onException(){
		transaction.onException();
	}
		
	public JdbcTemplate getJdbcTemplate()throws DAOException{
		return this.template;
	}
	
	public <T> int saveOrUpdate(T entity)throws DAOException{
		return DAOUtil.getPrimaryKeyValue(entity)==null?save(entity):update(entity);
	}
	
	public int update(String jql)throws DAOException{
		return update(jql,(Object[])null);
	}
	
	public int update(String jql, Object arg)throws DAOException{
		return update(jql,new Object[]{arg});
	}
	
	public <T> int delete(T entity)throws DAOException{
		return delete(entity.getClass(),DAOUtil.getPrimaryKeyValue(entity));
	}
	
	public int delete(String jql)throws DAOException{
		return delete(jql,(Object[])null);
	}
	
	public int delete(String jql,Object arg)throws DAOException{
		return delete(jql,new Object[]{arg});
	}
	
	public Object get(String jql)throws DAOException{
		return get(jql,(Object[])null);
	}
	
	public Object get(String jql,Object arg)throws DAOException{
		return get(jql,new Object[]{arg});
	}
	
	public Object get(String jql,Object[] args)throws DAOException{
		List<?> list=findPaginated(jql,args,1,1);
		return list.isEmpty()?null:list.get(0);
	}

	public Object findUnique(String jql)throws DAOException{
		return findUnique(jql,(Object[])null);
	}
	
	public Object findUnique(String jql, Object arg)throws DAOException{
		return findUnique(jql,new Object[]{arg});
	}
	
	public List find(String jql)throws DAOException{
		return find(jql,(Object[])null);
	}
	
	public List find(String jql, Object arg)throws DAOException{
		return find(jql,new Object[]{arg});
	}
	
	public List findByNamedParam(String jql, String paramName, Object arg)throws DAOException{
		return findByNamedParam(jql,new String[]{paramName},new Object[]{arg});
	}
	
	public List findPageBean(String jql,PageBean pageBean)throws DAOException{
		return findPageBean(jql,(Object[])null,pageBean);
	}
	
	public List findPageBean(String jql,Object arg,PageBean pageBean)throws DAOException{
		return findPageBean(jql,new Object[]{arg},pageBean);
	}
	
	public List findPaginated(String jql, int index,int size)throws DAOException{
		return findPaginated(jql,(Object[])null,index,size);
	}
	
	public List findPaginated(String jql, Object arg, int index,int size)throws DAOException{
		return findPaginated(jql,new Object[]{arg},index,size);
	}
	
		
	//------------------------------------------------------
	
	public <T> int update(T entity)throws DAOException{
		try {	
			Class<? extends Object> cls = entity.getClass();
			TableEntry tableEntry=TableCache.getTableEntry(cls.getSimpleName());
			StringBuilder  sql=new StringBuilder();
			sql.append("update ").append(tableEntry.getTableName()).append(" set ");
			final List<Object> values=new ArrayList<Object>();
			Map<String,ColumnEntry> columns=tableEntry.getColumnEntrys();
			for (String key:columns.keySet()) {
				String columnName =columns.get(key).getColumnName();
				if(tableEntry.getIncrementColumn().equals(columnName)){
					continue;
				}
				Object value=ModelUtil.getPropertyValue(entity,key);
				if(value==null){
					continue;
				}else{
					sql.append(columnName).append("=?,");
					values.add(value);
				}
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(" where ").append(tableEntry.getFirstPrimaryKeyColumn()).append("=?");
			values.add(DAOUtil.getPrimaryKeyValue(entity));
			return template.update(sql.toString(),values.toArray());
		}catch(Exception e){
			throw new DAOException(e);
		}
	}
	
	
	public int update(String jql, Object[] args)throws DAOException{
		//Assert.notNull(jql,"jql must not be null");
		return template.update(parser.parse(jql).getSql(),args);
	}
	
	
	public <T> int save(T entity)throws DAOException{
		StringBuilder  sql=new StringBuilder(128);
		try {
			Class<? extends Object> cls = entity.getClass();
			TableEntry tableEntry=TableCache.getTableEntry(cls.getSimpleName());
			sql.append("insert into ").append(tableEntry.getTableName()).append(" (");
						
			StringBuilder names=new StringBuilder();
			StringBuilder params=new StringBuilder();
			final List<Object> values=new ArrayList<Object>();
			final List<Integer> types=new ArrayList<Integer>();
			final Map<String,ColumnEntry> columns=tableEntry.getColumnEntrys();
			for (String key:columns.keySet()) {
				if(tableEntry.getTable().increment().equals(key)){
					continue;
				}
				Object value=ModelUtil.getPropertyValue(entity,key);
				//Object value=cls.getDeclaredMethod(columnEntry.getGetterName()).invoke(entity);
				if(value==null){
					continue;
				}
				ColumnEntry columnEntry=columns.get(key);
				if(value.equals("")&&columnEntry.getType()!=java.sql.Types.VARCHAR){
					continue;
				}else{
					names.append(columnEntry.getColumnName()).append(',');
					params.append("?,");
					values.add(value);
					types.add(columnEntry.getType());
				}
			}
			sql.append(names.substring(0,names.length()-1)).append(") values (").append(params.substring(0,params.length()-1)).append(')');
			final String strSql=sql.toString();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			template.update(
	                new PreparedStatementCreator(){
	                    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException{
	                        PreparedStatement ps = conn.prepareStatement(strSql, new String[] {"object_id"});
	                        if(template.getShowSql())log.info("SQL -> " + strSql);
	                        ps = conn.prepareStatement(strSql, java.sql.Statement.RETURN_GENERATED_KEYS);
	                        for(int i=0;i<values.size();i++){
	                        	//ps.setObject(i+1,values.get(i));
	                    		switch (types.get(i)){
	                    			case Types.SMALLINT:
	                    				ps.setShort(i+1,(Short)values.get(i));
	                    				break;
	                    			case Types.CHAR:
	                    				ps.setString(i+1,values.get(i).toString().substring(0,1));
	                    				break;
	                    			default:
	                    				ps.setObject(i+1,values.get(i));
	                    		}
	                        	
	                        	
	                        }
	                        return ps;
	                    }
	                },
	                keyHolder);
	        return keyHolder.getKey().intValue();
		}catch(Exception e){
			throw new DAOException(e);
		}
	}
		
	//public int[] saveOrUpdateAll(Collection entities);
		
	public <T> int delete(Class<T> entityClass,Object firstPrimaryKeyValue)throws DAOException{
		//Assert.notNull(entityClass,"entityClass must not be null");
		//Assert.notNull(firstPrimaryKeyValue,"firstPrimaryKeyValue must not be null");
		final TableEntry tableEntry=TableCache.getTableEntry(entityClass.getSimpleName());
		StringBuilder sb=new StringBuilder();
		sb.append("delete from ").append(tableEntry.getTableName());
		sb.append(" where ").append(tableEntry.getFirstPrimaryKeyColumn()).append("=?");
		return template.update(sb.toString(),firstPrimaryKeyValue);
	}
	
	public int delete(String jql,Object[] args)throws DAOException{
		//Assert.notNull(jql,"jql must not be null");
		if(!jql.trim().toLowerCase().startsWith("delete from"))jql=jql+"delete from ";
		return template.update(parser.parse(jql).getSql(),args);
	}
	
	
	public <T> T get(final Class<T> entityClass,Object firstPrimaryKeyValue)throws DAOException{
		//Assert.notNull(entityClass,"entityClass must not be null");
		//Assert.notNull(firstPrimaryKeyValue,"firstPrimaryKeyValue must not be null");
		final TableEntry tableEntry=TableCache.getTableEntry(entityClass.getSimpleName());
		StringBuilder sb=new StringBuilder();
		sb.append("select ").append(tableEntry.getColumnNames());
		sb.append(" from ").append(tableEntry.getTableName());
		sb.append(" where ").append(tableEntry.getFirstPrimaryKeyColumn()).append("=?");
		
		return template.query(sb.toString(),new Object[]{firstPrimaryKeyValue},new ResultSetExtractor<T>(){
			public T extractData(ResultSet rs) throws SQLException,DataAccessException {
				if(rs.next()){
					try{
						T t=entityClass.newInstance();
						int i=0;
						Collection<ColumnEntry> values=tableEntry.getColumnEntrys().values();
						for(ColumnEntry columnEntry:values){
							i++;
							DAOUtil.setSetterValue(t,columnEntry,rs,i);
						}
						return t;
					} catch (Exception e) {
						throw new SQLException(e);
					} 
				}
				return null;
			}
		});
	}
	
			
	public Object findUnique(String jql, Object[] args)throws DAOException{
		//Assert.notNull(jql,"jql must not be null");
		return DAOUtil.findUniqueBySql(template,parser.parse(jql).getSql(),args);
	}
	
	public <T> List<T> find(Class<T> entityClass)throws DAOException{
		TableEntry tableEntry=TableCache.getTableEntry(entityClass.getSimpleName());
		StringBuilder sb=new StringBuilder("select ").append(tableEntry.getColumnNames());
		sb.append(" from ").append(tableEntry.getTableName());
		Map<String,ColumnEntry> columns=tableEntry.getColumnEntrys();
		return DAOUtil.queryForEntityClass(template,sb.toString(),(Object[])null,entityClass,columns,1,columns.size());
	}
	
	public List find(String jql, Object[] args)throws DAOException{
		//Assert.notNull(jql,"jql must not be null");
		final Statement statement=parser.parse(jql);
		return DAOUtil.findBySql(template,statement.getSql(),statement,args);
	}
	

	public List findByNamedParam(String jql, String[] paramNames, Object[] args)throws DAOException{
		return null;
	}
	
	
	public <T> List<T> findPageBean(Class<T> entityClass,PageBean pageBean)throws DAOException{
		TableEntry tableEntry=TableCache.getTableEntry(entityClass.getSimpleName());
		final Map<String,ColumnEntry> columns=tableEntry.getColumnEntrys();
		StringBuilder sb=new StringBuilder("select ").append(tableEntry.getColumnNames()).append(" from ").append(tableEntry.getTableName());
		final String countSql="select count(*) as total from "+tableEntry.getTableName();
		pageBean.setTotalCount((Long)DAOUtil.findUniqueBySql(template, countSql,(Object[])null));
		String nativeSql=dialect.getSQLWithRowSetLimit(sb.toString(),pageBean.getStartIndex(),pageBean.getEndIndex());
		return DAOUtil.queryForEntityClass(template,nativeSql,(Object[])null,entityClass,columns,1,columns.size());
	}
	
	
	
	public List findPageBean(String jql,Object[] args,PageBean pageBean)throws DAOException{
		//Assert.notNull(jql,"jql must not be null");
		final Statement statement=parser.parse(jql);
		String sql=statement.getSql();

		final String countSql="select count(*) as total "+sql.substring(sql.indexOf("from"));
		Long count=(Long)DAOUtil.findUniqueBySql(template, countSql,args);
		
		pageBean.setTotalCount(count==null?0:count);
		String nativeSql=dialect.getSQLWithRowSetLimit(sql,pageBean.getStartIndex(),pageBean.getEndIndex());
		return DAOUtil.findBySql(template,nativeSql,statement,args);
	}
	
	
	public <T> List<T> findPaginated(Class<T> entityClass, int index,int size)throws DAOException{
		//Assert.isTrue(index>0,"index must be greater than zero");
		//Assert.isTrue(size>0,"size must be greater than zero");
		TableEntry tableEntry=TableCache.getTableEntry(entityClass.getSimpleName());
		final Map<String,ColumnEntry> columns=tableEntry.getColumnEntrys();
		StringBuilder sb=new StringBuilder("select ").append(tableEntry.getColumnNames()).append(" from ").append(tableEntry.getTableName());	
		String nativeSql=dialect.getSQLWithRowSetLimit(sb.toString(),index-1,index+size-1);
		return DAOUtil.queryForEntityClass(template,nativeSql,(Object[])null,entityClass,columns,1,columns.size());
	}
			
	public List findPaginated(String jql, Object[] args, int index,int size)throws DAOException{
		//Assert.notNull(jql,"jql must not be null");
		//Assert.isTrue(index>0,"index must be greater than zero");
		//Assert.isTrue(size>0,"size must be greater than zero");
		final Statement statement=parser.parse(jql);
		String nativeSql=dialect.getSQLWithRowSetLimit(statement.getSql(),index-1,index+size-1);
		return DAOUtil.findBySql(template,nativeSql,statement,args);
	}
	
	public Query query(String jql)throws DAOException{
		return new QueryImpl(parser.parse(jql),template);
	}
}