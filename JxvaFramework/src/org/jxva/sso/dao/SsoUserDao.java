/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
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
package org.jxva.sso.dao;

import java.util.List;

import org.jxva.sso.entity.SSOUtil;
import org.jxva.sso.model.SsoUser;

import com.jxva.dao.BaseDao;
import com.jxva.entity.MD5;
import com.jxva.util.StringUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-03 16:08:51 by Jxva
 */
public class SsoUserDao extends BaseDao{
		
	public SsoUser getSsoUserByUsername(String username){
		if(StringUtil.isEmpty(username))return null;
		//return dao.findSingle(SsoUser.class,"username='"+username+"'");
		return SSOUtil.getSsoUsersByCache().get(username);
	}
	
	@SuppressWarnings("unchecked")
	public List<SsoUser> listSsoUser(int pageno,int pagesize){
		return (List<SsoUser>)dao.find("from SsoUser s order by s.userId desc",new Object[]{pageno,pagesize});
	}
	
	public int saveOrUpdate(SsoUser ssoUser){
		SSOUtil.getSsoUsersByCache().put(ssoUser.getUsername(),ssoUser);
		if(ssoUser.getUserId()==null){
			ssoUser.setPassword(MD5.encrypt(ssoUser.getPassword()));
			return dao.save(ssoUser);
		}else{
			return dao.update(ssoUser);
		}
	}
	
	public SsoUser autoSsoUser(String randomString){
		String[] rs=randomString.split("\\?");
		SsoUser ssoUser=new SsoUser();
		ssoUser.setUsername(rs[0]);
		ssoUser.setPassword(MD5.encrypt(rs[1]));
		SSOUtil.getSsoUsersByCache().put(ssoUser.getUsername(),ssoUser);
		int incrementValue=dao.save(ssoUser);
		ssoUser.setUserId(incrementValue);
		ssoUser.setPassword(rs[1]);
		return ssoUser;
	}
}
