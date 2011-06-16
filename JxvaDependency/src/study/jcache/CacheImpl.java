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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CacheImpl implements Cache{
	
	private final String arithmetic;
	private Map<Object,Object> elements=new HashMap<Object,Object>();
	private final int maxSize;
	
	//static{
	//	if(elements==null){
	//		elements=new HashMap<Object,Object>();
	//	}
	//}
	
	public CacheImpl(){
		this(CacheConst.ARITHMETIC_LEAST_USED,1000);
	}
	
	public CacheImpl(int maxSize){
		this(CacheConst.ARITHMETIC_LEAST_USED,maxSize);
	}

	public CacheImpl(String arithmetic,int maxSize){
		this.arithmetic=arithmetic;
		this.maxSize=maxSize;
	}
	
	public void put(Object key, Object value){
		//synchronized(elements){
			if(key==null){
				return;
			}
			if(this.arithmetic.equals(CacheConst.ARITHMETIC_LEAST_USED)){
				this.LU();
			}else{
				this.LUR();
			}
			Element element=new Element(key,value);
			elements.put(key,element);
		//}
	}

	public Object get(Object key){
		//synchronized(elements){
			if(key==null){
				return null;
			}
			if(elements.containsKey(key)){
				Element element=(Element)elements.get(key);
				element.setHits(element.getHits()+1);
				element.setUpdateTime(System.currentTimeMillis());
				elements.put(key,element);
				return element.getValue();
			}else{
				return null;
			}
		//}
	}
	
	public boolean containsKey(Object key){
		return elements.containsKey(key);
	}
	
	public Element getElement(Object key){
		//synchronized(elements){
			if(key==null){
				return null;
			}
			if(elements.containsKey(key)){
				Element element=(Element)elements.get(key);
				element.setHits(element.getHits()+1);
				elements.put(key,element);
				return element;
			}else{
				return null;
			}
		//}
	}

	public void remove(Object key){
		if(key==null){
			return;
		}
		if(elements.containsKey(key)){
			elements.remove(key);	
		}
	}

	public List<Object> getElements(){
		List<Object> list=new LinkedList<Object>();
		list.addAll(elements.values());
		return list;
	}
	
	public void clear(){
		elements.clear();
		elements=null;
	}
		
	private void LUR(){
		arthmetic(CacheConst.DESC,"getHits");
	}
	

	private void LU(){
		arthmetic(CacheConst.ASC,"getIdle");
	}
	
	private void arthmetic(String order,String by){
		try{
			if(elements.size()<this.maxSize){
				return;
			}else{
				List<Object> list=new LinkedList<Object>();
				list.addAll(elements.values());
				ObjectSorter sorter=new ObjectSorter(Element.class,by);
				sorter.bubble(list,order);
				Element luElement=(Element)list.get(0);
				elements.remove(luElement.getKey());
				list.clear();
				list=null;
				luElement=null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
