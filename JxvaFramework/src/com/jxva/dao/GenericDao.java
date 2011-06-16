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
package com.jxva.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-30 15:25:07 by Jxva
 */
public abstract class GenericDao<T> extends BaseDao{
	
	private static final Map<Class<?>,Class<?>> cache=new HashMap<Class<?>,Class<?>>();
		
	protected final Class<T> entityClass;  

	@SuppressWarnings("unchecked")
	public GenericDao(){
		super();
		Class<?> cls=getClass();
		Class<?> result=cache.get(cls);
		if(result!=null){
			entityClass=(Class<T>)result;
		}else{
			entityClass=(Class<T>)getEntityClass(cls);
			cache.put(cls,entityClass);
		}
	}
	
	private Class<?> getEntityClass(Class<?> cls){
		Type type=cls.getGenericSuperclass();
		if(type.toString().lastIndexOf('>')>-1){
			return (Class<?>)((ParameterizedType)type).getActualTypeArguments()[0];
		}else{
			throw new DAOException("com.jxva.dao.GenericDao's subclass must be generic class");
		}
	}
	
	public Class<T> getEntityClass(){
		return this.entityClass;
	}
	
	public T get(Object pkey) {
		return dao.get(entityClass,pkey);
	}
	
	public int delete(Object pkey){
		return dao.delete(entityClass,pkey);
	}
	
	public List<T> findAll(){
		return dao.find(entityClass);
	}
	
	public List<T> findPageBean(PageBean pageBean){
		return dao.findPageBean(entityClass, pageBean);
	}
	
	public  List<T> findPaginated(int index,int size){
		return dao.findPaginated(entityClass, index, size);
	}
}
