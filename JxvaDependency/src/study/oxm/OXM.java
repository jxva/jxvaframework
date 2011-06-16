/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
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
package study.oxm;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface OXM {
	/**
	 * 添加对象,如果需要递增主键字段,见getAutoid方法
	 */
	public void insert(Object obj);
	public void update(Object obj,String where);
	public void delete(Class<? extends Object> cls,String where);
	public List<Object> find(Class<? extends Object> cls,String where);
	public List<Object> findOrderBy(Class<? extends Object> cls,String where,String orderby);
	/**
	 * 获取自动递增的主键值
	 * @param obj
	 * @return
	 */	
	public Long getAutoId(Class<? extends Object> cls);
	/**
	 * 获取系统日期
	 * @return
	 */	
	public Date getDate();
	/**
	 * 获取系统时间
	 * @return
	 */	
	public Timestamp getTime();
	
	/**
	 *  通过VO对象的主键来查询单个对象
	 * @param obj
	 * @return
	 */	
	public Object findByPrimaryKeys(Object obj);
	/**
	 * 根据主键删除对象
	 * @param obj
	 */	
	public void deleteByPrimaryKeys(Object obj);
	
	/**
	 * 根据主键更新对象仅更新
	 * @param obj
	 */
	public void updateByPrimaryKeys(Object obj);	
}
