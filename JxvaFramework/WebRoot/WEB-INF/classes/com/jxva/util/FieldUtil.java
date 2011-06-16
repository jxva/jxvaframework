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
 *
 */
package com.jxva.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jxva.dao.annotation.Column;


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:52:13 by Jxva
 */
public abstract class FieldUtil {
	
	private static final Map<String, List<Field>> tblFields =new HashMap<String, List<Field>>();
	private static final Map<String, List<Field>> tblFieldsWithParent =new HashMap<String, List<Field>>();
	
	
	/**
	 * 从缓存中获取对象的所有字段,包括所有父类
	 * @param cls
	 * @return
	 */
	public static List<Field> getFieldsWithParent(Class<?> cls){
		List<Field> list=tblFieldsWithParent.get(cls.getName());
		if(list==null){
			list=getFieldsWithParentForCache(cls);
			tblFieldsWithParent.put(cls.getName(),list);
		}
		return list;
	}
	
	/**
	 * 从缓存中获取对象的所有字段,不包括父类
	 * @param cls
	 * @return
	 */
	public static List<Field> getFields(Class<?> cls){
		List<Field> list=tblFields.get(cls.getName());
		if(list==null){
			list=getFieldsForCache(cls);
			tblFields.put(cls.getName(),list);
		}
		return list;
	}
	
    /**
     * 获取class的所有字段,包括所有父类
     * @param cls
     * @return
     */
	private static List<Field> getFieldsWithParentForCache(Class<?> cls){
		final List<Field> list=new ArrayList<Field>();
		do{
			addFieldToList(list,cls);
			cls=cls.getSuperclass();
		}while(cls!=Object.class);
		return list;
	}
	
    /**
     * 获取class的所有字段,不包括父类
     * @param cls
     * @return
     */
	private static List<Field> getFieldsForCache(Class<?> cls){
		final List<Field> list=new ArrayList<Field>();
		addFieldToList(list,cls);
		return list;
	}
	
	private static void addFieldToList(List<Field> list,Class<?> cls){
		final Field[] fields=cls.getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals("serialVersionUID"))continue;
			Annotation[] annotations=field.getDeclaredAnnotations();
			if(annotations.length==0){
				list.add(field);
			}else{
				Annotation annotation=annotations[0];
				if(annotation instanceof Column){
					list.add(field);
				}else{
					continue;
				}
			}	
		}
	}
}
