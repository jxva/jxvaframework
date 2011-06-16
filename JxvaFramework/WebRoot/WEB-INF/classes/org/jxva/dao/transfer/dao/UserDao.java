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
package org.jxva.dao.transfer.dao;

import com.jxva.dao.BaseDao;
import org.jxva.dao.transfer.model.User;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-03 14:27:07 by Automatic Generate Toolkit
 */
public class UserDao extends BaseDao{

	public User getUser(int userId){
		return dao.get(User.class,userId);
	}

	public int	save(User user){
		return dao.save(user);
	}

	public int update(User user){
		return dao.update(user);
	}

	public int delete(User user){
		return dao.delete(user);
	}

	public int saveOrUpdate(User user){
		return dao.saveOrUpdate(user);
	}

}
