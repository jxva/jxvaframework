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
import java.util.Map;

/**
 * A key value pair, useful for moving serialized cache contents around.
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:14:59 by Jxva
 */
public final class KeyValuePair implements Serializable, Map.Entry<Object,Object> {

	private static final long serialVersionUID = 1L;
	private final Serializable key;
    private Serializable value;

    public KeyValuePair(Serializable key, Serializable value) {
        this.key = key;
        this.value = value;
    }


    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Object setValue(Object value) throws IllegalArgumentException {
        if (!(value instanceof Serializable)) {
            throw new IllegalArgumentException("Value is not serializable");
        }
        Object oldValue = this.value;
        this.value = (Serializable) value;
        return oldValue;
    }
}
