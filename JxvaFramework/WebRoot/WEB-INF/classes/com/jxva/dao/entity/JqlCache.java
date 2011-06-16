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
package com.jxva.dao.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jxva.dao.Statement;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-26 17:37:43 by Jxva
 */
public abstract class JqlCache {
	
	private static final int CACHE_MAX_SIZE=500;
		
		private static final Map<String,Statement> cache=new LinkedHashMap<String,Statement>(CACHE_MAX_SIZE,0.75f,true) {
			private static final long serialVersionUID = 1L;
			protected boolean removeEldestEntry(Map.Entry<String,Statement> eldest) {
				return size()>CACHE_MAX_SIZE;
			}
		};
		
		private JqlCache(){
			
		}
		
		public static Statement get(String key){
			return cache.get(key);
		}
		
		public static Statement put(String key,Statement statement){
			return cache.put(key,statement);
		}

		public static boolean containsKey(String key){
			return cache.containsKey(key);
		}
		
		public static int size(){
			return cache.size();
		}
		
		public static Statement remove(String key){
			return cache.remove(key);
		}
		
		public static void destroy(){
			cache.clear();
		}
}
