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
 */
package demo.cache.reference;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-23 16:46:45 by Jxva
 */
public class TestRef {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String obj="dd";
		Map<String,String> map=new HashMap<String,String>();
		map.put("test","ddd");
		map.put("t","fdsa");
		map.put("fff","ffffff");
		map.put("gg",null);
		map.put(obj,"ok");
		System.out.println(map.size());
		for(String s:map.keySet()){
			System.out.println(map.get(s));
		}
		map.put("test",null);
		obj=null;
		System.out.println(map.size());
		for(String s:map.keySet()){
			System.out.println(map.get(s));
		}
	}

}
