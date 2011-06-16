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
package com.jxva.json;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-31 09:47:14 by Jxva
 */
public class Json {
	
	private static final String JSONCALLBACK = "jsoncallback";
	
	private final ConcurrentMap<String, Object> map= new ConcurrentHashMap<String, Object>();
	
    public void clear() {
        map.clear();   
    }   
    
    public Json add(String key, Object value) {   
        map.put(key, value);   
        return this;   
    } 
    
    public Json remove(String key){
    	map.remove(key);
    	return this;
    }
    
    ConcurrentMap<String, Object> getMap() {   
        return this.map;   
    }  
    
    public String toJQuery(HttpServletRequest request){
    	return request.getParameter(JSONCALLBACK)+'('+toString()+')';
    }  
    
    @SuppressWarnings("unchecked")
	public String toString() {   
        StringBuilder sb = new StringBuilder();   
        sb.append('{');   
        Set<String> keys = map.keySet();   
        for (String key: keys) {   
            Object value = map.get(key);   
            if (value == null) {   
                continue;
            }   
            sb.append('"').append(key).append('"').append(':');   
            if (value instanceof Json) {   
                sb.append(value.toString());   
            } else if (JsonUtil.isNoQuote(value)) {   
                sb.append(value);   
            } else if (JsonUtil.isQuote(value)) {   
                sb.append('"').append(value).append('"');   
            } else if (value.getClass().isArray()) {   
                sb.append(JsonUtil.toString(value));   
            } else if (value instanceof Map) {   
                sb.append(JsonUtil.fromObject((Map<String,?>) value).toString());   
            } else if (value instanceof List) {   
                sb.append(JsonUtil.toString((List<?>) value));   
            } else {   
                sb.append(JsonUtil.fromObject(value).toString());   
            }   
            sb.append(',');   
        }   
        sb.deleteCharAt(sb.length()-1);
        sb.append('}');   
        return sb.toString();   
    }   
}
