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
package com.jxva.cache;

import java.io.Serializable;
import java.util.Map;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-23 10:25:22 by Jxva
 */
public abstract class AbstractCache implements Cache,Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	protected static final int DEFAULT_MAX_SIZE=100;
	
	protected String name;
	protected Map<Object,Element> map;
	protected int maxSize=DEFAULT_MAX_SIZE;
	
	public final synchronized void clear() {
		map.clear();
	}

	public final synchronized Object getValue(Object key) {
		Element element=get(key);
		return element==null?null:element.getValue();
	}
	
	public final synchronized Object getQuietValue(Object key) {
		Element element=getQuiet(key);
		return element==null?null:element.getValue();
	}

	public final synchronized void put(Object key, Object value) {
		put(new Element(key,value));
	}

	public final synchronized Element remove(Object key) {
		return map.remove(key);
	}

	public final int size() {
		return map.size();
	}
	
	public final boolean containsKey(Object key){
		return map.containsKey(key);
	}

	public final synchronized void dispose() {
		map.clear();
		map=null;		
	}

	public final synchronized Element get(Object key) {
		Element element=getElement(key);
		if(element!=null){
			element.updateHitStatistics();
		}
		return element;
	}

	public final synchronized Element getQuiet(Object key) {
		return getElement(key);
	}
	
	private final Element getElement(Object key){
		return map.get(key);
	}

	public final synchronized void put(Element element) {
		if (element != null) {
			map.put(element.getKey(),element);
			doPut(element);
		}
	}

	public final synchronized Object[] getKeys() {
		return map.keySet().toArray();
	}

	public final String getName() {
		return name;
	}
	
	public final boolean isEmpty() {
		return map.isEmpty();
	}
	
    protected final boolean isFull() {
        return map.size() > maxSize;
    }
	
    protected void doPut(Element element) throws CacheException {
        //empty
    }
}
