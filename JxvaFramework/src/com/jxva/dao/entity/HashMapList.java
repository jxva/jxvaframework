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
package com.jxva.dao.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:56:52 by Jxva
 */
public class HashMapList implements List {
	
	private transient Map<Integer,Object> map;
	private int size;
	private int amount;
	
	public HashMapList(){
		map=new HashMap<Integer,Object>();
	}
	
	public void add(Object obj) {
		add(getEmptyPosition()+1,obj);		
	}
	
	public void add(int index, Object obj) {
		if(index>size){
			size=index;
		}
		if(map.containsKey(index-1)){
			add(index+1,obj);
		}else{
			map.put(index-1,obj);
			amount++;
		}
	}

	public void clear() {
		map.clear();		
	}
	
	private int getEmptyPosition(){
		for(int i=0;i<size();i++){
			if(!map.containsKey(i))return i;
		}
		return size();
	}

	
	public Object get(int index) {
		return map.get(index);
	}

	
	public boolean isEmpty(){
		return map.isEmpty();
	}

	
	public void remove(int index) {
		map.remove(index);
	}

	
	public int size() {
		return Math.max(size,amount);
	}

}
