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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jxva.util.FieldUtil;
import com.jxva.util.ModelUtil;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-31 09:52:45 by Jxva
 */
public abstract class JsonUtil {

    public static boolean isNoQuote(Object value) {   
        return (value instanceof Number || value instanceof Boolean);   
    }   
    public static boolean isQuote(Object value) {   
        return (value instanceof String || value instanceof Character || value instanceof Date);   
    }
    
    public static String toString(Object array) {   
        if (!array.getClass().isArray())return "[]";   
        StringBuilder sb = new StringBuilder();   
        sb.append('[');     
        for (int i = 0,len=Array.getLength(array); i < len; i++) {   
        	Object v = Array.get(array, i);   
           if (isQuote(v)) {   
                sb.append('"').append(v).append('"').append(',');   
            } else if (isNoQuote(v)) {   
                sb.append(i).append(',');   
            } else {   
                sb.append(fromObject(v)).append(',');   
            }   
        }   
        sb.deleteCharAt(sb.length()-1); 
        sb.append(']');   
        return sb.toString();   
    }   

    @SuppressWarnings("unchecked")
	public static String toString(List<?> list) {   
        if (list==null||list.isEmpty())return null;   
        StringBuilder sb = new StringBuilder();   
        sb.append('[');   
        for (Object value:list) {   
            if (value instanceof Map) {   
                sb.append(fromObject((Map<String,?>) value).toString()).append(',');   
            } else if (isNoQuote(value)) {   
                sb.append(value).append(',');   
            } else if (isQuote(value)) {   
                sb.append('"').append(value).append('"').append(',');   
            } else {   
                sb.append(fromObject(value).toString()).append(',');   
            }   
        }   
        sb.deleteCharAt(sb.length()-1);   
        sb.append(']');   
        return sb.toString();   
    }   
 
    public static <T> Json fromObject(T bean) {   
        Json json = new Json();   
        if (bean == null)return json;   
        Class<?> beanClass = bean.getClass();   
        final List<Field> fields = FieldUtil.getFields(beanClass);  
        for (int i = 0,n=fields.size();i<n; i++) {   
        	String fieldName = fields.get(i).getName();   
            try { 
            	Object value=ModelUtil.getPropertyValue(bean,fieldName);
            	//Object value=beanClass.getDeclaredMethod(ModelUtil.getGetterName(fieldName)).invoke(bean);   
            	json.add(fieldName, value);   
            } catch (Exception e) {
            	e.printStackTrace();
                continue;   
            }
        }   
        return json;   
    }   

    public static Json fromObject(Map<String,?> map) {   
        Json json = new Json();   
        if (map == null)return json;   
        json.getMap().putAll(map);   
        return json;   
    }   
}
