/*
 * Copyright 2002-2009 the original author or authors.
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

package com.jxva.dao.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.jxva.util.SystemUtil;


public abstract class CollectionFactory {



	private static final Set<Class<?>> approximableCollectionTypes = new HashSet<Class<?>>(10);

	private static final Set<Class<?>> approximableMapTypes = new HashSet<Class<?>>(6);

	static {
		approximableCollectionTypes.add(Collection.class);
		approximableCollectionTypes.add(List.class);
		approximableCollectionTypes.add(Set.class);
		approximableCollectionTypes.add(SortedSet.class);
		approximableMapTypes.add(Map.class);
		approximableMapTypes.add(SortedMap.class);
		if (SystemUtil.isJavaVersionAtLeast(160)) {
			approximableCollectionTypes.add(NavigableSet.class);
			approximableMapTypes.add(NavigableMap.class);
		}
		approximableCollectionTypes.add(ArrayList.class);
		approximableCollectionTypes.add(LinkedList.class);
		approximableCollectionTypes.add(HashSet.class);
		approximableCollectionTypes.add(LinkedHashSet.class);
		approximableCollectionTypes.add(TreeSet.class);
		approximableMapTypes.add(HashMap.class);
		approximableMapTypes.add(LinkedHashMap.class);
		approximableMapTypes.add(TreeMap.class);
	}



	/**
	 * Create a linked case-insensitive Map if possible: if Commons Collections
	 * 3.x is available, a CaseInsensitiveMap with ListOrderedMap decorator will
	 * be created. Else, a JDK {@link java.util.LinkedHashMap} will be used.
	 * @param initialCapacity the initial capacity of the Map
	 * @return the new Map instance
	 * @see com.jxva.entity.apache.commons.collections.map.CaseInsensitiveMap
	 * @see org.apache.commons.collections.map.ListOrderedMap
	 */
	public static Map<String,Object> createLinkedCaseInsensitiveMapIfPossible(int initialCapacity) {
			return CommonsCollectionFactory.createListOrderedCaseInsensitiveMap(initialCapacity);
	}


	/**
	 * Determine whether the given collection type is an approximable type,
	 * i.e. a type that {@link #createApproximateCollection} can approximate.
	 * @param collectionType the collection type to check
	 * @return <code>true</code> if the type is approximable,
	 * <code>false</code> if it is not
	 */
	public static boolean isApproximableCollectionType(Class<?> collectionType) {
		return (collectionType != null && approximableCollectionTypes.contains(collectionType));
	}

	/**
	 * Create the most approximate collection for the given collection.
	 * <p>Creates an ArrayList, TreeSet or linked Set for a List, SortedSet
	 * or Set, respectively.
	 * @param collection the original collection object
	 * @param initialCapacity the initial capacity
	 * @return the new collection instance
	 * @see java.util.ArrayList
	 * @see java.util.TreeSet
	 * @see java.util.LinkedHashSet
	 */
	@SuppressWarnings("unchecked")
	public static Collection createApproximateCollection(Object collection, int initialCapacity) {
		if (collection instanceof LinkedList) {
			return new LinkedList();
		}
		else if (collection instanceof List) {
			return new ArrayList(initialCapacity);
		}
		else if (collection instanceof SortedSet) {
			return new TreeSet(((SortedSet) collection).comparator());
		}
		else {
			return new LinkedHashSet(initialCapacity);
		}
	}

	/**
	 * Determine whether the given map type is an approximable type,
	 * i.e. a type that {@link #createApproximateMap} can approximate.
	 * @param mapType the map type to check
	 * @return <code>true</code> if the type is approximable,
	 * <code>false</code> if it is not
	 */
	public static boolean isApproximableMapType(Class<?> mapType) {
		return (mapType != null && approximableMapTypes.contains(mapType));
	}

	/**
	 * Create the most approximate map for the given map.
	 * <p>Creates a TreeMap or linked Map for a SortedMap or Map, respectively.
	 * @param map the original map object
	 * @param initialCapacity the initial capacity
	 * @return the new collection instance
	 * @see java.util.TreeMap
	 * @see java.util.LinkedHashMap
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> createApproximateMap(Object map, int initialCapacity) {
		if (map instanceof SortedMap) {
			return new TreeMap<K,V>(((SortedMap<K,V>) map).comparator());
		}
		else {
			return new LinkedHashMap<K,V>(initialCapacity);
		}
	}


	private static abstract class CommonsCollectionFactory {
		private static Map<String,Object> createListOrderedCaseInsensitiveMap(int initialCapacity) {
			// Commons Collections does not support initial capacity of 0.
			return new CaseInsensitiveLinkedHashMap<String,Object>(initialCapacity == 0 ? 1 : initialCapacity);
		}
	}

}
