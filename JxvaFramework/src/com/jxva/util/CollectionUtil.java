/*
 * Copyright 2002-2008 the original author or authors.
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

package com.jxva.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Miscellaneous collection utility methods.
 * Mainly for internal use within the framework.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 1.1.3
 */
public abstract class CollectionUtil {

	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}

	public static boolean isEmpty(Map<?,?> map) {
		return (map == null || map.isEmpty());
	}

	public static List<?> arrayToList(Object source) {
		return Arrays.asList(ObjectUtil.toObjectArray(source));
	}

	public static void mergeArrayIntoCollection(Object array, Collection<Object> collection) {
		Assert.notNull(collection,"Collection must not be null");
		Object[] arr = ObjectUtil.toObjectArray(array);
		for (Object elem : arr) {
			collection.add(elem);
		}
	}

	
	public static void mergePropertiesIntoMap(Properties props, Map<String,String> map) {
		Assert.notNull(map,"Map must not be null");
		if (props != null) {
			for(Object obj:props.keySet()){
				String key=(String)obj;
				map.put(key, props.getProperty(key));
			}
		}
	}


	public static boolean contains(Iterator<?> iterator, Object element) {
		if (iterator != null) {
			while (iterator.hasNext()) {
				Object candidate = iterator.next();
				if (ObjectUtil.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean contains(Enumeration<?> enumeration, Object element) {
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				Object candidate = enumeration.nextElement();
				if (ObjectUtil.nullSafeEquals(candidate, element)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsInstance(Collection<?> collection, Object element) {
		if (collection != null) {
			for (Object candidate : collection) {
				if (candidate == element) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return false;
		}
		for (Object candidate : candidates) {
			if (source.contains(candidate)) {
				return true;
			}
		}
		return false;
	}

	public static Object findFirstMatch(Collection<?> source, Collection<?> candidates) {
		if (isEmpty(source) || isEmpty(candidates)) {
			return null;
		}
		for (Object candidate : candidates) {
			if (source.contains(candidate)) {
				return candidate;
			}
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T findValueOfType(Collection collection, Class<T> type) {
		if (isEmpty(collection)) {
			return null;
		}
		T value = null;
		for (Object element : collection) {
			if (type == null || type.isInstance(element)) {
				if (value != null) {
					// More than one value found... no clear single value.
					return null;
				}
				value = (T) element;
			}
		}
		return value;
	}


	public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
		if (isEmpty(collection) || ObjectUtil.isEmpty(types)) {
			return null;
		}
		for (Class<?> type : types) {
			Object value = findValueOfType(collection, type);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	public static boolean hasUniqueObject(Collection<?> collection) {
		if (isEmpty(collection)) {
			return false;
		}
		boolean hasCandidate = false;
		Object candidate = null;
		for (Iterator<?> it = collection.iterator(); it.hasNext();) {
			Object elem = it.next();
			if (!hasCandidate) {
				hasCandidate = true;
				candidate = elem;
			}
			else if (candidate != elem) {
				return false;
			}
		}
		return true;
	}

}
