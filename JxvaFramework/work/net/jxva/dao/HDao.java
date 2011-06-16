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
package net.jxva.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.jxva.entity.LockMode;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-18 20:32:11 by Jxva
 */
@SuppressWarnings("unchecked")
public interface HDao {
	public void flush();
	
	public void clear();

	public void refresh(Object entity);
	
	public void refresh(Object entity, LockMode lockMode);
	
	public void evict(Object entity);
	
	public Serializable save(Object entity);
	
	public void update(Object entity);
	
	public void merge(Object entity);
	
	public void lock(Object entity, LockMode lockMode);

	//public void replicate(Object entity, ReplicationMode replicationMode);
	
	public void bulkUpdate(String queryString, Object[] values);
	
	public void bulkUpdate(String queryString, Object value);
	
	public void bulkUpdate(String queryString);

	public void saveOrUpdate(Object entity);


	public void saveOrUpdateAll(Collection entities);
	
	public void delete(Object entity);
	
	public void delete(String qrystr);
	
	public void delete(Class entityClass, Serializable id);
	
	public void delete(Class entityClass, Serializable[] ids);
	
	public void deleteAll(Collection entities);

	public Object get(Class entityClass, Serializable id);
	
	public Object load(Class entityClass, Serializable id);
	
	public List load(Class entityClass, Serializable[] ids);
	
	public List loadAll(Class entityClass);	
	
	public Integer countByExample(Object entity);
	
	public Integer countByExample(Object entity, Boolean enableLike);
	
	//public Integer countByCriteria(DetachedCriteria criteria);
	
	public Object findUnique(String queryString);
	
	public Object findUnique(String queryString, Object value);
	
	public Object findUnique(String queryString, Object[] values);

	public List find(String queryString);
	
	public List find(String queryString, Object value);
	
	public List find(String queryString, Object[] values);

	public List findByNamedParam(String queryString, String paramName, Object value);

	public List findByNamedParam(String queryString, String[] paramNames, Object[] values);
	
	//public List findByCriteria(DetachedCriteria criteria);
	
	//public List findByCriteria(DetachedCriteria criteria,String firstResult,String maxResults);
	
	//public List findByCriteria(DetachedCriteria criteria,int offset,String maxResults,String flag);
	
	public List findAllByExample(Object entity);
	
	public List findAllByExample(Object entity, Boolean enableLike);
	
	public List findPaginated(String queryString, String index, String size);
	
	public List findPaginated(String queryString, Object value, String index, String size);
	
	public List findPaginated(String queryString, Object[] values, String index, String size);
	
	public List findAllPaginated(Class entityClass, String index, String size);
	
	public List findPaginatedByExample(Object entity, String index, String size);
	
	public List findPaginatedByExample(final Object entity, final String index,final String size,String flag);
	
	public List findByNamedQuery(String queryName);

	public List findByNamedQuery(String queryName, Object value);

	public List findByNamedQuery(String queryName, Object[] values);

    public List findBySQLQuery(final String queryString, final String entityAlias, final Class entityClass);

    public List findBySQLQuery(final String queryString, final Object value, final String entityAlias, final Class entityClass);

    public List findBySQLQuery(final String queryString, final Object[] values, final String entityAlias, final Class entityClass);
    
    public List findBySQLQuery(final String queryString, final String entityName, final Object value, final String entityAlias, final Class entityClass);

    public List findBySQLQuery(final String queryString, final String[] entityAliases, final Class[] entityClasses);

    public List findBySQLQuery(final String queryString, final Object value, final String[] entityAliases, final Class[] entityClasses);
    
    public List findBySQLQuery(final String queryString, final Object[] values, final String[] entityAliases, final Class[] entityClasses);
    
    public List findBySQLQuery(final String queryString, final String entityName, final Object value, final String[] entityAliases, final Class[] entityClasses);
    
    public List findBySQLQuery(final String queryString, final String[] entityNames, final Object[] values, final String[] entityAliases, final Class[] entityClasses);

	String getIdName(Class clazz);

	List getAllPojosName();
	
	public List findAllInfo(Object object,String strId);

	//public HibernateTemplate getHibernateTemplate();
}
