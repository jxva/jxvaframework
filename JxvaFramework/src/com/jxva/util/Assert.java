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
package com.jxva.util;

import java.util.Collection;
import java.util.Map;

import com.jxva.exception.ArgumentException;

/**
 * 
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-26 09:23:04 by Jxva
 */
public abstract class Assert {
	
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new ArgumentException(message);
		}
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}
	
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new ArgumentException(message);
		}
	}

	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new ArgumentException(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	public static void hasLength(String text, String message) {
		if (!StringUtil.hasLength(text)) {
			throw new ArgumentException(message);
		}
	}

	public static void hasLength(String text) {
		hasLength(text,"[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	public static void hasText(String text, String message) {
		if (!StringUtil.hasText(text)) {
			throw new ArgumentException(message);
		}
	}

	public static void hasText(String text) {
		hasText(text,"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtil.hasLength(textToSearch) && StringUtil.hasLength(substring) &&
				textToSearch.indexOf(substring) != -1) {
			throw new ArgumentException(message);
		}
	}

	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,"[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}


	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtil.isEmpty(array)) {
			throw new ArgumentException(message);
		}
	}
	
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new ArgumentException(message);
				}
			}
		}
	}

	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtil.isEmpty(collection)) {
			throw new ArgumentException(message);
		}
	}

	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}
	
	public static void notEmpty(Map<?,?> map, String message) {
		if (CollectionUtil.isEmpty(map)) {
			throw new ArgumentException(message);
		}
	}

	public static void notEmpty(Map<?,?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}


	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against");
		if (!type.isInstance(obj)) {
			throw new ArgumentException(message +
					"Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
					"] must be an instance of " + type);
		}
	}

	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Type to check against");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new ArgumentException(message + subType + " is not assignable to " + superType);
		}
	}
}
