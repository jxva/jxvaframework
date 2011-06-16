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
package com.jxva.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.jxva.dao.jdbc.ConnectionHolder;

/**
 * Implement AOP for Exception
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:48:49 by Jxva
 */
public class DAOProxy implements InvocationHandler{
	//private static Method beforeAnyInvocation;
	//private static Method afterAnyInvocation;
	private static Method onException;
	private DAO dao;
	
	static{
		try{
			//beforeAnyInvocation=DAO.class.getDeclaredMethod("afterAnyInvocation",new Class[]{});
			//afterAnyInvocation=DAO.class.getDeclaredMethod("afterAnyInvocation",new Class[]{});
			onException=DAO.class.getDeclaredMethod("onException");
		}catch(NoSuchMethodException e){
			throw new DAOException("No onException Method!");
		}
	}
	
	public Object bind(DAO dao){
		this.dao=dao;
		return Proxy.newProxyInstance(DAOImpl.class.getClassLoader(),DAOImpl.class.getInterfaces(),this);
	}
	
	public Object rebind(){
		return Proxy.newProxyInstance(DAOImpl.class.getClassLoader(),DAOImpl.class.getInterfaces(),this);
	}


	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(ConnectionHolder.isClosed()){
			if(method.getName().equals("close")){
				return null;
			}else{
				throw new DAOException(" dao is null or dao has closed,at 'dao."+method.toString()+"' ");
			}
		}
		Object result=null;
		try{
			result=method.invoke(dao,args);
		}catch(InvocationTargetException e){
			onException.invoke(dao);
			throw e.getTargetException();
		}
		//afterAnyInvocation.invoke(dao,new Object[]{});
		return result;
	}
}
