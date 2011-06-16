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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.jxva.log.Logger;


/**
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:52:55 by Jxva
 */
public abstract class ModelUtil {
	
	private static final Logger log=Logger.getLogger(ModelUtil.class);
	private static final Map<Class<?>,Map<String,Method>> cacheGetMethod=new HashMap<Class<?>,Map<String,Method>>();
	
	public static String getGetterName(String s){
		return "get"+getClassName(s);
	}
	
	public static String getSetterName(String s){
		return "set"+getClassName(s);
	}
	
	/**
	 * 将字符串转换为类的字段规范<br>
	 * 
	 * 首字母小写,将所有下划线之后的第一个字母大写,并将下划线去掉<br>
	 * 
	 * user_info -> userInfo
	 * tbl_user_info -> tblUserInfo
	 * 
	 * @param str
	 * @return
	 */
	public static String getFieldName(String str){
		String s=getClassName(str);
		return Character.toLowerCase(s.charAt(0))+s.substring(1);
	}
	
	/**
	 * 将字符串转换为数据表字段名的规范<br>
	 * 将所有大写字母前加入下划线,并将整个字符串转换为小写<br>
	 * 
	 * UserInfo -> _user_info
	 * userInfo -> user_info
	 * 
	 * @param str
	 * @return
	 */
	public static String getColumnName(String str){
		StringBuilder sb = new StringBuilder(str);
		int t=0;
		for(int i=0;i<str.length();i++){
			char c=str.charAt(i);
			if(Character.isUpperCase(c)){
				sb.insert(i+t,'_');
				t++;
			}
		} 
		return sb.toString().toLowerCase();
	}
	
	/**
	 * 将字符串转换为类名的规范<br>
	 * 首字母大写,将所有下划线之后的第一个字母大写,并将下划线去掉<br>
	 * 
	 * user_info -> UserInfo
	 * tbl_user_info -> TblUserInfo 
	 * 
	 * @param str
	 * @return
	 */
	public static String getClassName(String str){
		String[] ss=str.split("_");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<ss.length;i++){
			sb.append(Character.toUpperCase(ss[i].charAt(0))).append(ss[i].substring(1));
		}
		return sb.toString();
	}
	
	
	public static Object getPropertyValue(Object obj, String prop){
		try {
			return get(obj,prop).invoke(obj);
			//return obj.getClass().getDeclaredMethod(getGetterName(prop)).invoke(obj).toString();
		}catch(NullPointerException e){
			log.warn(obj.getClass()+" ->  "+prop+"'s value is null");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Method get(Object obj,String prop) throws SecurityException, NoSuchMethodException{
		Class<?> cls=obj.getClass();
		Map<String,Method> map=cacheGetMethod.get(cls);
		if(map==null){
			map=new HashMap<String,Method>();
			Method method=cls.getDeclaredMethod(getGetterName(prop));
			map.put(prop,method);
			cacheGetMethod.put(cls,map);
			return method;
		}else{
			Method method=map.get(prop);
			if(method==null){
				method=cls.getDeclaredMethod(getGetterName(prop));
				map.put(prop,method);
				cacheGetMethod.put(cls,map);
			}
			return method;
		}
	}
}
