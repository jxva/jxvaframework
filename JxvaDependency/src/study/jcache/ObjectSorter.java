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
package study.jcache;

import java.lang.reflect.Method;

/**
 * 该类包含一个对象数组,数组中某个位置的值(必需是数值)将用来和其它对象比较大小,
 * 该用来比较的位置,用sortMetaIndex表示,排序的方法由ObjectArraySorter类提供
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version Nov 25, 2008 2:10:36 PM by Jxva
 *
 */
public class ObjectSorter extends Sort{

	private Method getter;
	
	/**
	 * 
	 * @param objectType
	 * @param getterName
	 * @throws Exception
	 */
	public ObjectSorter(Class<?> objectType,String getterName)throws Exception{
		getter=objectType.getDeclaredMethod(getterName,new Class[]{});
	}


	public String compare(Object pre,Object after)throws Exception{
		if(pre==null||after==null){
			throw new Exception("Exception: One or both of objects to be compared is null");
		}
		
		String preSortMeta=getter.invoke(pre,new Object[]{}).toString();
		String afterSortMeta=getter.invoke(after,new Object[]{}).toString();
		
		if(preSortMeta==null||afterSortMeta==null){
			throw new Exception("Exception: One or both of SortMeta to be compared is null");
		}
		
		if(preSortMeta.equals(afterSortMeta)){
			return CacheConst.EQUAL;
		}
		
		//小数
		if(preSortMeta.indexOf(".")!=-1||afterSortMeta.indexOf(".")!=-1){
			float preFloat=Float.parseFloat(preSortMeta);
			float afterFloat=Float.parseFloat(afterSortMeta);
			if(preFloat==afterFloat){
				return CacheConst.EQUAL;
			}else if(preFloat<afterFloat){
				return CacheConst.SMALLER;
			}else{
				return CacheConst.BIGGER;
			}
		}else{//整数
			long preLong=Long.parseLong(preSortMeta);
			long afterLong=Long.parseLong(afterSortMeta);
			if(preLong==afterLong){
				return CacheConst.EQUAL;
			}else if(preLong<afterLong){
				return CacheConst.SMALLER;
			}else{
				return CacheConst.BIGGER;
			}
		}
	}
}
