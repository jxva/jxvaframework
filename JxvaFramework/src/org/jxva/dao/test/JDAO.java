package org.jxva.dao.test;

import java.util.List;

import com.jxva.dao.DAOException;
import com.jxva.dao.JdbcTemplate;
import com.jxva.dao.PageBean;
import com.jxva.dao.Transaction;


public interface JDAO {
	
	public void afterAnyInvocation()throws DAOException;
	
	public void beforeAnyInvocation()throws DAOException;
	
	public Transaction beginTransaction()throws DAOException;
	
	public void close()throws DAOException;

	public void onException();
		
	public JdbcTemplate getJdbcTemplate()throws DAOException;
	
	//------------------------------------------------------
	
	public int update(Object entity,Object firstPrimaryKeyValue)throws DAOException;
	
	public int update(String jql)throws DAOException;
	
	public int update(String jql, Object arg)throws DAOException;
	
	public int update(String jql, Object[] args)throws DAOException;
	
	
	public int saveOrUpdate(Object entity)throws DAOException;

	//public int[] saveOrUpdateAll(Collection entities);
	
	public <T> int delete(Class<T> entityClass,Object firstPrimaryKeyValue)throws DAOException;
	
	public int delete(String jql)throws DAOException;
	
	public int delete(String jql,Object arg)throws DAOException;
	
	public int delete(String jql,Object[] args)throws DAOException;
	
	
	public <T> T get(Class<?> entityClass,Object firstPrimaryKeyValue)throws DAOException;
	
	public Object get(String jql)throws DAOException;
	
	public Object get(String jql,Object arg)throws DAOException;
	
	public Object get(String jql,Object[] args)throws DAOException;
	
	
	
	public Object findUnique(String jql)throws DAOException;
	
	public Object findUnique(String jql, Object arg)throws DAOException;
	
	public Object findUnique(String jql, Object[] args)throws DAOException;
	
	
	public List<?> find(String jql)throws DAOException;
	
	public List<?> find(String jql, Object arg)throws DAOException;
	
	public List<?> find(String jql, Object[] args)throws DAOException;
	
	
	public List<?> findByNamedParam(String jql, String paramName, Object arg)throws DAOException;

	public List<?> findByNamedParam(String jql, String[] paramNames, Object[] args)throws DAOException;
	
	
	public <T> List<T> findByPageBean(Class<T> entityClass,PageBean pageBean)throws DAOException;
	
	public <T> List<T> findByPageBean(String jql,PageBean pageBean)throws DAOException;
	
	public <T> List<T> findByPageBean(String jql,Object arg,PageBean pageBean)throws DAOException;
	
	public <T> List<T> findByPageBean(String jql,Object[] args,PageBean pageBean)throws DAOException;
	
	
	public <T> List<T> findPaginated(Class<T> entityClass, String index, String size)throws DAOException;
		
	public List<?> findPaginated(String jql, String index, String size)throws DAOException;
	
	public List<?> findPaginated(String jql, Object arg, String index, String size)throws DAOException;
	
	public List<?> findPaginated(String jql, Object[] args, String index, String size)throws DAOException;

}
