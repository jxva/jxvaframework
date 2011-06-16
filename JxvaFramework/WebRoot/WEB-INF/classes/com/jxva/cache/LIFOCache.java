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

import java.util.LinkedHashMap;


/**
 * Last In First Out
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-23 09:58:38 by Jxva
 */
public class LIFOCache extends AbstractCache {

	private static final long serialVersionUID = 1L;
	
	public LIFOCache(String name){
		this(name,DEFAULT_MAX_SIZE);
	}
	
	public LIFOCache(String name,int maxSize){
		this.name=name;
		this.maxSize=maxSize;
		this.map=new LinkedHashMap<Object,Element>();
	}
	
	protected final void doPut(Element element) throws CacheException {
        if (isFull()) {
            removeLastElement();
        }
    }

    Element getLastElement() {
        if (map.size() == 0) {
            return null;
        }
        Object[] keys = map.keySet().toArray();
        return map.get(keys[keys.length-1]);
    }
    
    private void removeLastElement() throws CacheException {
        remove(getLastElement().getKey());
    }
}
