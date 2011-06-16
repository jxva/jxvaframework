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


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 *
 * <p>A customized implementation of <code>java.util.HashMap</code> designed
 * to operate in a multithreaded environment where the large majority of
 * method calls are read-only, instead of structural changes.
 * Read calls are non-synchronized and write calls perform the
 * following steps:</p>
 * <ul>
 * <li>Clone the existing collection
 * <li>Perform the modification on the clone
 * <li>Replace the existing collection with the (modified) clone
 * </ul>
 * <p><strong>NOTE</strong>: If you are creating and accessing a
 * <code>HashMap</code> only within a single thread, you should use
 * <code>java.util.HashMap</code> directly (with no synchronization), for
 * maximum performance.</p>
 *
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2009-01-13 17:12:44 by Jxva
 */
public final class FastHashMap<K,V> implements Map<K,V>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private HashMap<K,V> map;

	public FastHashMap() {
		super();
		this.map = new HashMap<K,V>();
	}

	public FastHashMap(int capacity) {
		super();
		this.map = new HashMap<K,V>(capacity);
	}

	public FastHashMap(int capacity, float factor) {
		super();
		this.map = new HashMap<K,V>(capacity, factor);
	}

	public FastHashMap(Map<K,V> map) {
		super();
		this.map = new HashMap<K,V>(map);
	}

	public void clear() {
		synchronized (this) {
			HashMap<K,V> temp = (HashMap<K,V>) map.clone();
			temp.clear();
			map = temp;
		}
	}

	public Object clone() {
		return new FastHashMap<K,V>(map);
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set<Entry<K,V>> entrySet() {
		return map.entrySet();
	}

	public boolean equals(Object o) {
		// Simple tests that require no synchronization
		if (o == this)
			return true;
		else if ( !(o instanceof Map) )
			return false;
		Map<K,V> mo = (Map<K,V>) o;

		// Compare the two maps for equality

		if ( mo.size() != map.size() )
			return false;
		Iterator<Entry<K,V>> i = map.entrySet().iterator();
		while ( i.hasNext() ) {
			Map.Entry<K,V> e =  i.next();
			Object key = e.getKey();
			Object value = e.getValue();
			if (value == null) {
				if ( !( mo.get(key) == null && mo.containsKey(key) ) )
					return false;
			}
			else {
				if ( !value.equals( mo.get(key) ) )
					return false;
			}
		}
		return true;
	}

	public V get(Object key) {
		return map.get(key);
	}

	public int hashCode() {
		int h = 0;
		Iterator<Entry<K,V>> i = map.entrySet().iterator();
		while ( i.hasNext() )
			h += i.next().hashCode();
		return h;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V put(K key, V value) {
		synchronized (this) {
			HashMap<K,V> temp = (HashMap<K,V>) map.clone();
			V result = temp.put(key, value);
			map = temp;
			return result;
		}
	}

	public void putAll(Map<? extends K, ? extends V> in) {
		synchronized (this) {
			HashMap<K,V> temp = (HashMap<K,V>) map.clone();
			temp.putAll(in);
			map = temp;
		}
	}

	public V remove(Object key) {
		synchronized (this) {
			HashMap<K,V> temp = (HashMap<K,V>) map.clone();
			V result =temp.remove(key);
			map = temp;
			return result;
		}
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		return map.values();
	}

	public String toString() { return map.toString(); }
}






