
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @author  The Jxva Framework Foundation
 *
 */
public class OXMProxy implements InvocationHandler{
	//private static Method beforeAnyInvocation;
	//private static Method afterAnyInvocation;
	//private static Method onException;
	private OXM oxm;
	
	static{
		try{
			//beforeAnyInvocation=DAO.class.getDeclaredMethod("afterAnyInvocation",new Class[]{});
			//afterAnyInvocation=DAO.class.getDeclaredMethod("afterAnyInvocation",new Class[]{});
			//onException=DAO.class.getDeclaredMethod("onException",new Class[]{});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Object bind(OXM _oxm){
		oxm=_oxm;
		return Proxy.newProxyInstance(OXMImpl.class.getClassLoader(),
				OXMImpl.class.getInterfaces(),this);
	}
	
	public Object rebind(){
		return Proxy.newProxyInstance(OXMImpl.class.getClassLoader(),
				OXMImpl.class.getInterfaces(),
				this);
	}


	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object returnValue=null;
		try{
			returnValue=method.invoke(oxm,args);
		}catch(Exception e){
			//onException.invoke(dao,new Object[]{});
			e.printStackTrace();
			throw e;
		}
		//afterAnyInvocation.invoke(dao,new Object[]{});
		return returnValue;
	}
}
