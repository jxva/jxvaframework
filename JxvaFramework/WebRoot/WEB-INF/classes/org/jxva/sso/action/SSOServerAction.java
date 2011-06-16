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
package org.jxva.sso.action;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import org.jxva.sso.SSOData;
import org.jxva.sso.SSODefine;
import org.jxva.sso.SSOException;
import org.jxva.sso.SSOServer;
import org.jxva.sso.dao.SsoUserDao;
import org.jxva.sso.entity.SSOMap;
import org.jxva.sso.entity.SSOTpl;
import org.jxva.sso.entity.SSOUtil;
import org.jxva.sso.entity.SSOValid;
import org.jxva.sso.model.SsoUser;

import com.jxva.entity.MD5;
import com.jxva.log.Logger;
import com.jxva.mvc.Action;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-02 15:04:46 by Jxva
 */
public class SSOServerAction extends Action implements SSOServer {
	
	private static final Logger log=Logger.getLogger(SSOServerAction.class);
		
	/* (non-Javadoc)
	 * @see com.jxva.sso.SSO#check()
	 */
	public String check()  throws SSOException {
		String[] params=form.getParams(SSODefine.USERNAME,SSODefine.PASSWORD);
		SsoUser ssoUser=new SsoUserDao().getSsoUserByUsername(params[0]);
		return new SSOValid().check(ssoUser,params[0],params[1]);
	}

	/* (non-Javadoc)
	 * @see com.jxva.sso.SSO#control()
	 */
	public String control() throws SSOException  {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jxva.mvc.Action#execute()
	 */
	public String execute() {
		
		return SSODefine.SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.jxva.sso.SSO#login()
	 */
	public String login() throws SSOException  {
		String[] params=form.getParams(SSODefine.USERNAME,SSODefine.PASSWORD,SSODefine.BACK_URL);
		SsoUser ssoUser=new SsoUserDao().getSsoUserByUsername(params[0]);
		String result=new SSOValid().check(ssoUser,params[0],params[1]);
		if(!result.equals(SSODefine.SUCCESS))return result;
		
		String ssoId=UUID.randomUUID().toString();
		SSOUtil.saveToSsoMap(ssoUser,ssoId);
		if(request.getMethod().equalsIgnoreCase(SSODefine.POST)){//以POST方式登录成功之后的处理
			return SSOTpl.getLoginSsoTpl(params[2],"method=login&ssoid="+ssoId+"&ssoinfo="+SSOUtil.getSsoInfo(ssoUser));
		}else{//以GET方式登录成功之后,返回ssoId
			return ssoId;
		}
	}

	/* (non-Javadoc)
	 * @see com.jxva.sso.SSO#logout()
	 */
	public String logout()  throws SSOException {
		String ssoId=getSsoId();
		if(request.getMethod().equalsIgnoreCase(SSODefine.GET)){
			ConcurrentMap<String, SSOData> ssoMap=SSOMap.getSsoMap();
			ssoMap.remove(ssoId);
			return SSODefine.SUCCESS;
		}else{
			return SSOTpl.getLogoutSsoTpl(form.getParam(SSODefine.BACK_URL),"method=logout");
		}
	}

	/* (non-Javadoc)
	 * @see com.jxva.sso.SSO#verify()
	 */
	public String verify() throws SSOException  {
		ConcurrentMap<String, SSOData> ssoMap=SSOMap.getSsoMap();
		String ssoId=getSsoId();
		if(ssoMap.get(ssoId)==null){
			return SSODefine.FAILED;
		}else{//更新用户活动时间
			SSOData ssoData=ssoMap.get(ssoId);
			ssoData.setVisitCount(ssoData.getVisitCount()+1);
			ssoData.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			ssoMap.put(ssoId,ssoData);
			return ssoData.getSsoInfo();
		}
	}

	
	/* (non-Javadoc)
	 * @see com.jxva.sso.SSO#callBack()
	 */
	public String callBack() throws SSOException {
		log.info(form.getParam("client")+" load failed.");
		return SSODefine.SUCCESS;
	}
	
	private String getSsoId(){
		String ssoId=request.getParameter(SSODefine.SSOID);
		return ssoId==null?"":ssoId;
	}

	/* (non-Javadoc)
	 * @see com.jxva.sso.SSOServer#register()
	 */
	public String register() throws SSOException {
		String[] params=form.getParams(SSODefine.USERNAME,SSODefine.PASSWORD);
		SsoUserDao sud=new SsoUserDao();
		SsoUser ssoUser=sud.getSsoUserByUsername(params[0]);
		if(ssoUser!=null)return SSODefine.USERNAME_HAS_EXIST;
		ssoUser=new SsoUser();
		ssoUser.setUsername(params[0]);
		ssoUser.setPassword(MD5.encrypt(params[1]));
		int incrementValue=sud.saveOrUpdate(ssoUser);
		return SSODefine.SUCCESS+"?"+incrementValue;
	}

	/* (non-Javadoc)
	 * @see com.ztemc.sso.SSOServer#modifySsoUser()
	 */
	public String updatePassword() throws SSOException {
		String[] params=form.getParams(SSODefine.USERNAME,SSODefine.PASSWORD);
		SsoUserDao sud=new SsoUserDao();
		SsoUser ssoUser=sud.getSsoUserByUsername(params[0]);
		ssoUser.setUsername(params[0]);
		ssoUser.setPassword(MD5.encrypt(params[1]));
		sud.saveOrUpdate(ssoUser);
		return SSODefine.SUCCESS;
	}
}