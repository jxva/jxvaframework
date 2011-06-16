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

import java.sql.Timestamp;
import java.util.List;


public abstract class Sort {
		
	/**
	 * 
	 * @param in
	 * @param sortType  ASC or DESC
	 * @return
	 */
	public List<Object> bubble(List<Object> in,String sortType)throws Exception{
		int cnt = in.size();
		for (int j = 0; j < cnt - 1; ++j) {
			for (int i = 1; i < cnt - j; ++i) {
				Object pre = in.get(i - 1);
				Object after = in.get(i);

				if(sortType.equals(CacheConst.DESC)){
					if (compare(pre,after).equals(CacheConst.SMALLER)) {
						in.set(i - 1, after);
						in.set(i, pre);
					}
				}else{
					if (compare(pre,after).equals(CacheConst.BIGGER)) {
						in.set(i - 1, after);
						in.set(i, pre);
					}
				}
			}
		}
		return in;
	}
	
	/**
	 * 
	 * @param in
	 * @param sortType  ASC or DESC
	 * @return
	 */
	public Object[] bubble(Object[] in,String sortType)throws Exception{
		int cnt = in.length;
		for (int j = 0; j < cnt - 1; ++j) {
			for (int i = 1; i < cnt - j; ++i) {
				Object pre = in[i - 1];
				Object after = in[i];

				if(sortType.equals(CacheConst.DESC)){
					if (compare(pre,after).equals(CacheConst.SMALLER)) {
						in[i-1]=after;
						in[i]=pre;
					}
				}else{
					if (compare(pre,after).equals(CacheConst.BIGGER)) {
						in[i-1]=after;
						in[i]=pre;
					}
				}
			}
		}
		return in;
	}	
	
	/**
	 * 比较两个对象的大小
	 * @param pre
	 * @param after
	 * @return Sort.SMALLER: 前者<后者；
	 *         Sort.EQUAL: 相等；
	 *         Sort.BIGGER: 前者>后者
	 */
	public abstract String compare(Object pre,Object after)throws Exception;
	
	/**
	 * 
	 * @param tPre
	 * @param tAfter
	 * @return
	 */
	public static String compareTime(Timestamp pre,Timestamp after)throws Exception{
		if(pre==null||after==null){
			throw new Exception("Roar Exception: One or both of objects to be compared is null");
		}
		if(pre.before(after)){
			return CacheConst.SMALLER;
		}else if(pre.after(after)){
			return CacheConst.BIGGER;
		}else{
			return CacheConst.EQUAL;
		}
	}
}
