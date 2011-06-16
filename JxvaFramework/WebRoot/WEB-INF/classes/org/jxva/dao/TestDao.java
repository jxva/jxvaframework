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

import java.util.List;

import org.jxva.dao.model.User;

import com.jxva.dao.GenericDao;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-24 17:53:30 by Jxva
 */
public class TestDao extends GenericDao<User>{
	
	public User get(){
		return dao.get(User.class,1);
	}
	
	public List<User> find(){
		return dao.find(User.class);
	}
	
	public static void main(String[] args){
		new TestDao().find();
	}
}
