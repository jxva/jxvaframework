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
package org.jxva.sso.client;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jxva.sso.SSODefine;

import com.jxva.entity.Base64;
import com.jxva.mvc.HttpCookie;


/**
 * 用户信息实用类
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-29 14:20:49 by Jxva
 */
public class UserUtil {
	
	private HttpSession session;
	private HttpCookie cookie;
	
	public UserUtil(){
		super();
	}
		
	/**
	 * 由CoreFilter自动注入HttpServletRequest与HttpServletResponse对象
	 * @see org.CoreFilter.jxva.mvc.ztemt.sso.CoreFilter
	 * @param request
	 * @return
	 */
	protected UserUtil initByCoreFilter(HttpServletRequest request,HttpServletResponse response) {
		this.session=request.getSession(true);
		this.cookie=new HttpCookie(request,response);
		return this;
	}
	
	/**
	 * 判断用户是否登录（与SSO Server交互）
	 * <pre>
	 * -> 判断session中的ssoid是否存在
	 *   ->存在 继续(true)
	 *   ->不存在 
	 *     ->判断cookie中的ssoid是否存在
	 *       ->不存在 重新登录(false) 	
	 *       ->存在 远程校验SSO服务器中的ssoid是否存在
	 *         -> 不存在 清空cookie中的ssoid,重新登录(false)
	 *         -> 存在 将ssoid注入session中
	 *           同时通过SSO服务器获取的SSOINFO提取username
	 *           通过username从数据库中获取User对象
	 *           如果SSO Client数据库中不存在帐户为username的用户
	 *           将新增一个anonymous用户存于session的ssouser变量中
	 * </pre>
	 * @see sso-verify.gif
	 * @return 已经登录：true ; 未登录：false
	 */
	public boolean hasLogin(){
		String ssoId=getSsoId();
		if(session.getAttribute(SSODefine.SSOID)!=null){
			if(ssoId==null){
				session.removeAttribute(SSODefine.SSOID);
				return false;
			}
			return true;
		}
		String result=SSOUtil.verify(ssoId);
		if(result.trim().indexOf(SSODefine.FAILED)>-1){
			cookie.remove(SSODefine.SSOID);
			return false;
		}else{
			String ssoInfo=Base64.decode(result);
			if(ssoInfo.indexOf('?')>-1){
				session.setAttribute(SSODefine.SSOID,ssoId);
				String username=Base64.decode(result).split("\\?")[1];
				UserDao userDao=new UserDao();
				User user=userDao.getUserByUsername(username);
				if(user==null){//如果用户只存在在SSO Server上,则将用户保存至SSO Client端中
					user=new User();
					user.setUsername(username);
					user.setNickname(username);//新建anonymous用户时，默认nickname为username;
					user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
					user.setGender(2);
					user.setEmail(username+"@anonymous.com");
					userDao.save(user);
				}
				session.setAttribute(SSODefine.SSOUSER,user);
				return true;
			}else{
				cookie.remove(SSODefine.SSOID);
				return false;
			}
		}
	}
		
	/**
	 * 获取用户的SSOID（验证票据）
	 * @return
	 */
	public String getSsoId(){
		return cookie.getValue(SSODefine.SSOID);
	}
		
	/**
	 * 获取用户对象
	 * @return
	 */
	public User getUser(){
		return (User)session.getAttribute(SSODefine.SSOUSER);
	}
}
