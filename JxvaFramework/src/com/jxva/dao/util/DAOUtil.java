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
package com.jxva.dao.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jxva.dao.DAOException;
import com.jxva.dao.DataAccessException;
import com.jxva.dao.JdbcTemplate;
import com.jxva.dao.Statement;
import com.jxva.dao.entity.StatementType;
import com.jxva.dao.entity.TableCache;
import com.jxva.dao.entry.ColumnEntry;
import com.jxva.dao.entry.ModelEntry;
import com.jxva.dao.entry.TableEntry;
import com.jxva.dao.jdbc.ResultSetExtractor;
import com.jxva.dao.type.Blob;
import com.jxva.dao.type.ByteList;
import com.jxva.dao.type.Clob;
import com.jxva.dao.type.SqlType;
import com.jxva.dao.type.Types;
import com.jxva.util.ModelUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-24 09:27:18 by Jxva
 */
public abstract class DAOUtil {

	public static List<?> findBySql(JdbcTemplate template,String sql,final Statement statement,Object[] args)throws DAOException{
		if(statement.getStatementType()==StatementType.SELECT){
			return template.queryForList(sql);
		}else{			
			return template.query(sql,args,new ResultSetExtractor<List<Object>>(){
				public List<Object> extractData(ResultSet rs) throws SQLException,DataAccessException {
					try{
						ModelEntry[] modelEntrys=statement.getFromClause().getModelEntrys();
						TableEntry tableEntry=modelEntrys[0].getTableEntry();
						Map<String,ColumnEntry> columns=tableEntry.getColumnEntrys();
						Collection<ColumnEntry> values=columns.values();
						Class<?> clazz=tableEntry.getModel();
						
						List<Object> list=new ArrayList<Object>();
						while(rs.next()){
							Object t=clazz.newInstance();
							int i=0;
							for(ColumnEntry columnEntry:values){
								i++;
								setSetterValue(t,columnEntry,rs,i);
							}
							//System.out.println(modelEntrys[0].getModelName()+":"+modelEntrys[0].getCascadeModel());
							for(int j=0;j<modelEntrys.length-1;j++){
								ModelEntry modelEntry=modelEntrys[j+1];
								//System.out.println(modelEntry.getModelName()+":"+modelEntry.getCascadeModel());
								TableEntry _tableEntry=modelEntry.getTableEntry();
								Map<String,ColumnEntry> _columns=_tableEntry.getColumnEntrys();
								if(!modelEntrys[j].isFetch()){
									i+=_columns.size();
									continue;
								}else if(modelEntrys[j].getRelation()>0){//OnoToOne Fetch
									if(modelEntrys[0].getAliasName().equals(modelEntry.getPrefixName())){
										Object value=_tableEntry.getModel().newInstance();
										Collection<ColumnEntry> _values=_columns.values();
										for(ColumnEntry columnEntry:_values){
											i++;
											setSetterValue(value,columnEntry,rs,i);
										}
										clazz.getDeclaredMethod(ModelUtil.getSetterName(modelEntry.getModelName()),_tableEntry.getModel()).invoke(t,value);
									}else{//两层Fetch,如(from Book b left join fetch b.press p left join fetch p.pressType pt)
										//System.out.println("DDDDDDDDDDDDDDDDDDD");
										//TODO
										i+=_columns.size();
										continue;
									}
									
								}else{//Fetch OnoToMany or ManyToOne or ManyToMany
									//TODO
									i+=_columns.size();
									continue;
								}
							}
							list.add(t);
						}
						return list;
					} catch (Exception e) {
						throw new SQLException(e);
					}
				}
			});
		}
	}
	
	public static ColumnEntry getPrimaryKeyColumnEntry(Class<?> cls) throws DAOException{
		try{
			TableEntry tableEntry=TableCache.getTableEntry(cls.getSimpleName());	
			return tableEntry.getColumnEntrys().get(tableEntry.getTable().primaryKeys()[0]);
		}catch(Exception e){
			throw new DAOException(e);
		}
	}
	
	public static <T> Object getPrimaryKeyValue(T entity) throws DAOException{
		return ModelUtil.getPropertyValue(entity,getPrimaryKeyColumnEntry(entity.getClass()).getColumnName());
		//return entity.getClass().getDeclaredMethod(getPrimaryKeyColumnEntry(entity.getClass()).getGetterName()).invoke(entity);
	}
	
	public static <T> List<T> queryForEntityClass(JdbcTemplate template,String sql,Object[] args,final Class<T> entityClass,final Map<String,ColumnEntry> columns,final int begin,final int end){
		return template.query(sql,args,new ResultSetExtractor<List<T>>(){
			public List<T> extractData(ResultSet rs) throws SQLException,DataAccessException {
				List<T> list=new ArrayList<T>(end-begin);
				try{
					Collection<ColumnEntry> columnEntrys=columns.values();
					while(rs.next()){
						T t=entityClass.newInstance();
						int i=0;
						for(ColumnEntry columnEntry:columnEntrys){
							i++;
							setSetterValue(t,columnEntry,rs,i);
						}
						list.add(t);
					}
					return list;
				} catch (Exception e) {
					throw new SQLException(e);
				}
			}
		});
	}
	
	public static <T> void setSetterValue(T t,ColumnEntry column,ResultSet rs,int index)throws Exception{
		String setterName=column.getSetterName();
		int sqlType=column.getType();
		Class<?> cls=SqlType.get(sqlType);
		//System.out.println("setterName:"+setterName+",setterType:"+sqlType);
		try{
			switch (sqlType){
				case Types.SMALLINT:
					t.getClass().getDeclaredMethod(setterName,cls).invoke(t,rs.getShort(index));
					break;
				case Types.NCHAR:
				case Types.CHAR:
					String s=rs.getString(index);
					if(s!=null)t.getClass().getDeclaredMethod(setterName,cls).invoke(t,s.charAt(0));
					break;
				case Types.BLOB:
					java.sql.Blob blob=rs.getBlob(index);
					if(blob!=null){
						t.getClass().getDeclaredMethod(setterName,java.sql.Blob.class).invoke(t,new Blob(blob.getBinaryStream()));
					}
					break;
				case Types.CLOB:
					java.sql.Clob clob=rs.getClob(index);
					if(clob!=null){
						t.getClass().getDeclaredMethod(setterName,java.sql.Clob.class).invoke(t,new Clob(clob.getCharacterStream()));
					}
					break;
				case Types.LONGVARBINARY:
				case Types.VARBINARY:
				case Types.BINARY:
					InputStream in=rs.getBinaryStream(index);
					if(in!=null){
						ByteList bl=new ByteList();
						bl.read(in);
						t.getClass().getDeclaredMethod(setterName,java.io.InputStream.class).invoke(t,new ByteArrayInputStream(bl.detach()));
					}
					break;
				default:
					t.getClass().getDeclaredMethod(setterName,cls).invoke(t,rs.getObject(index));
			}
		}catch(IllegalArgumentException e){
			throw new DAOException("field '"+setterName+"' argument type mismatch,sql type:"+SqlType.get(sqlType),e);
		}
	}
	
	public static Object findUniqueBySql(JdbcTemplate template,String sql, Object[] args)throws DAOException{
		return template.query(sql,args,new ResultSetExtractor<Object>(){
			public Object extractData(ResultSet rs) throws SQLException,DataAccessException {
				if(rs.next()){
					rs.last();
					Integer row=rs.getRow();
					return row>1?Long.valueOf(row):rs.getObject(1);
				}
				return null;
			}
		});
	}
}
