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
package com.jxva.plugin;

/**
 * 系统MVC框架初始化接口<br>
 * 所有基于Jxva Framework的初始化工作,必须实现此接口
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:06:32 by Jxva
 */
public interface Pluginable {
	/**
	 * 初始化,子类重写
	 * @throws Exception
	 */
	public void initialize()throws PluginException;
	
	public void dispose();
}
