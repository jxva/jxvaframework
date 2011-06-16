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

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import com.jxva.dao.PageBean;


/**
 *  JQL 
 * 
 * 	1. from User where userId=?;
 *  2. from User,Song where User.userId=Song.userId;
 *  3. from User fetch Song on User.userId=Song.userId;
 *  4. from User inner join (fetch) Song on User.userId=Song.userId;
 *  5. from User left  (outer) join (fetch) Song on User.userId=Song.userId;
 *  6. from User right (outer) join (fetch) Song on User.userId=Song.userId;	
 *  7. from User full  (outer) join (fetch) Song on User.userId=Song.userId;
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-05 11:15:31 by Jxva
 */
@SuppressWarnings("unchecked")
public interface IDAO {

	public void afterAnyInvocation();
	public void beforeAnyInvocation();
	public void beginTransaction();
	public void close();
	public void commit();
	public boolean isClosed();
	public boolean isInTransaction();
	public void onException();
	public void rollback();
	public Connection getConnection();
	
	
	public Long getAutoId(Class cls);
	public Long getAutoId(Class cls,String generatorId);
	
	//=====================以下方法主要应用于对象,支持多表==========================
	public int save(Object obj);
	public int[] save(Collection col);
	
	public int update(Object obj);
	public int update(Object obj,String jwhere,Object...params);
	
	public int saveOrUpdate(Object obj);
	
	public int remove(Object obj);
	public int remove(Class cls,String jwhere,Object...params);
	public int remove(String jql,Object...params);
		
	public int getRecordCnt(Class cls);
	public int getRecordCnt(Class cls,String jwhere,Object...params);
	public int getRecordCnt(String jql,Object...params);//支持多表
	
	public Object findUnique(Object obj);
	public Object findUnique(Class cls,String jwhere,Object...params);
	public Object findUnique(String jql,Object...params);//支持多表
	
	public List find(Class cls);
	public List find(Class cls,String jwhere,Object...params);
	public List find(String jql,Object...params);//支持多表
	
	public List findPaginated(Class cls,PageBean pageBean);
	public List findPaginated(Class cls,PageBean pageBean,String jwhere,Object...params);
	public List findPaginated(String jql,PageBean pageBean,Object...params);//支持多表
	
	public List findSize(Class cls,int size);
	public List findSize(Class cls,int size,String jwhere,Object...params);
	public List findSize(String jql,int size,Object...params);//支持多表
	
	//============================支持Group By语句==================================
	public List findBySQL(String sql,Object...params);
	public List findBySQL(String sql,PageBean pageBean,Object...params);
}
