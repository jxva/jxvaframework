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
package org.jxva.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jxva.dao.model.Author;

import com.jxva.json.Json;
import com.jxva.json.JsonFormat;



/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-12 12:53:00 by Jxva
 */
public class JsonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(new Json().add("username","jiangzan").add("email","jxva@msn.com").toString());
		
		List<User> list=new ArrayList<User>();
		for(int i=12;i<18;i++){
			User user=new User();
			user.setUsername("jxva"+i);
			user.setAge(i);
			list.add(user);
		}
		System.out.println(new Json().add("list",list).toString());
		//test();
	}
	
	public static void test(){
		Json util = new Json();   
		util.add("name", "fdsa");   
		util.add("blog", "http://blog.jxva.com");   
		util.add("ttt", true);   
		util.add("dd", 1.3d);   
		util.add("ff", 1.3f);   
		util.add("date", new java.util.Date());   
		int []a = new int[]{2,3,4,5};   
		util.add("arr", a);   
		  
		Map<String,Object> map1 = new HashMap<String,Object>();   
		map1.put("name", "fdsa");   
		map1.put("age", 2);   
		Map<String,Object> map2 = new HashMap<String,Object>();   
		map2.put("name", "ereee");   
		map2.put("age", 22);   
		  
		util.add("map1", map1);   
		util.add("map2", map2);   
		List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();   
		list.add(map1);   
		list.add(map2);   
		util.add("listMap", list);   
		//TestBean t = new TestBean("gggg","男");   
		//util.put("beanObject", t);   
		  
		List<Integer> li = new LinkedList<Integer>();   
		li.add(1);   
		li.add(1);   
		li.add(1);   
		util.add("listInteger", li);   
		
		Author author=new Author();
		author.setAuthorId(100);
		author.setName("中国");
		util.add("author",author);
		//System.out.println(JsonUtil.fromObject(map1));   
		System.out.println(util.toString()); 
		System.out.println("==================");
		System.out.println(JsonFormat.formatJSON(util.toString()));
	}

}
