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
package com.jxva.mvc.invoke;

/**
 * 用调用者提供模板方法模式的调用接口<br>
 * <b>Usage:</b>
 * <pre>
 * Invoke invoke=null;
 * try{
 *    invoke=子类实现;
 *    invoke.beforeInvoke();
 *    //do something
 * }catch(Exception e){
 *    invoke.exceptionInvoke(e);
 *    //do something
 * }finally{
 *    invoke.afterInvoke();
 *    //do something
 * }
 * </pre>
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:08:46 by Jxva
 */
public interface Invoke {
		
	public void tryInvoke();
	
	public void catchInvoke(Exception e);
	
	public void finallyInvoke();	

}
