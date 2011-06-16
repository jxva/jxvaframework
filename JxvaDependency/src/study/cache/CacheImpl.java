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

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:13:34 by Jxva
 */
public class CacheImpl  implements Cache{
	
	private CacheEntity cacheEntity;

	public CacheImpl(CacheEntity cacheEntity) {
		try {
			this.cacheEntity = cacheEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object get(Object key) {
		try {
			if (key == null) {
				return null;
			} else {
				Element element=getElement(key);
				if (element == null) {
					return null;
				} else {
					return element.getObjectValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Element getElement(Object key) {
		try {
			if (key == null) {
				return null;
			} else {
				Element element = cacheEntity.get((Serializable) key);
				if (element == null) {
					return null;
				} else {
					return element;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void put(Object key, Object value) {
		try {
			Element element = new Element(key,value);
			cacheEntity.put(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Object key) {
		try {
			cacheEntity.remove((Serializable) key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clear() {
		try {
			cacheEntity.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			CacheFactory.getInstance().removeCache(cacheEntity.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return cacheEntity.getName();
	}
	
	public int getSize(){
		return cacheEntity.getSize();
	}
	
	public boolean containsKey(Object key) {
		return cacheEntity.containsKey(key);
	}

	public List<Object> getKeys() {
		return cacheEntity.getKeys();
	}
}
