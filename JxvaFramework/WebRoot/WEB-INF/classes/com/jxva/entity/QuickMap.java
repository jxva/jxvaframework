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
package com.jxva.entity;

import java.util.HashMap;

/**
 * Shortcut Map by extending HashMap. 
 * 
 * You can use it as below:
 * map.put("country", "China", "city", "wuhan"); 
 * or new QuickMap("country","China", "city", "wuhan") 
 * or map.add("country", "China").add("city","wuhan");
 * 
 * ablow is the same as old style: 
 * map.put("country", "Chian") map.put("city","wuhan");
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-10-10 16:47:38 by Jxva
 **/
public class QuickMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public QuickMap() {
	}

	public QuickMap(Object... args) {
		put(args);
	}

	/**
	 * shortcut method for put key-value in map.
	 * 
	 * @param args
	 *            key-vlaue objects, the firt is key , the second is value, and
	 *            so on.
	 */
	public void put(Object... args) {
		for (int i = 1; i < args.length; i += 2) {
			put(String.valueOf(args[i - 1]), args[i]);
		}
	}

	/**
	 * shortcut method
	 * 
	 * @param key
	 *            key in map
	 * @param value
	 *            value in map
	 * @return a QuickMap instance , you can add next key-value.
	 * @see
	 */
	public QuickMap add(String key, Object value) {
		this.put(key, value);
		return this;
	}
}