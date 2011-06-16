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
import java.util.Map;


/**
 * Least Recently Used (Not Recently Used)
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2009-01-23 11:13:34 by Jxva
 */
public class LRUCache extends AbstractCache {

	private static final long serialVersionUID = 1L;
	
	public LRUCache(String name){
		this(name,DEFAULT_MAX_SIZE);
	}
	
	public LRUCache(String name,int maxSize) {
		this.name=name;
		this.maxSize = maxSize;
		
		this.map = new LinkedHashMap<Object,Element>(maxSize,0.75f,true) {
			private static final long serialVersionUID = 1L;
			protected boolean removeEldestEntry(Map.Entry<Object,Element> eldest) {
				return isFull();
			}
		};
	}
}