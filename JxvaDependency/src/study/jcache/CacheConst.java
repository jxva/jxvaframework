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

public final class CacheConst {
	private CacheConst(){
		
	}
	public static final String ARITHMETIC_LEAST_USED_RECENTLY = "__ARITHMETIC_LRU";
	public static final String ARITHMETIC_LEAST_USED = "__ARITHMETIC_LU";
	
	public static final String EQUAL="__EQUAL";//==
	public static final String SMALLER="__SMALLER";//<
	public static final String BIGGER="__BIGGER";//>
	
	public static final String ASC="__ASC";//升序
	public static final String DESC="__DESC";//>降序
}
