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
package study.cache;

import java.util.List;

/**
 * 缓存接口
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:11:09 by Jxva
 */
public interface Cache {
	
	public void clear();

	public boolean containsKey(Object key);
	
	public void destroy();
	
	public Object get(Object key);
	
	public Element getElement(Object key);
	
	public List<Object> getKeys();
	
	public String getName();

	public int getSize();

	public void put(Object key, Object value);

	public void remove(Object key);	
}
