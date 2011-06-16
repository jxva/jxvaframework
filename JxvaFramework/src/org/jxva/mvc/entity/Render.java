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
package org.jxva.mvc.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * mvc控制中心分发者,留后备用
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:20:44 by Jxva
 */
public class Render implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Object> objects;
	private Map<Object,Object> keyObjects;
	private String id;
	

	public Render(){
		id="";
		objects=new LinkedList<Object>();
		keyObjects=new LinkedHashMap<Object,Object>();
	}
	

	public boolean isEmpty(){
		return (objects.size()==0&&keyObjects.size()==0)?true:false;
	}
	

	public void setId(String _id){
		id=_id;
	}
	

	public String getId(){
		return id;
	}
	
	public void clear(){
		objects.clear();
		keyObjects.clear();
	}
	
	public void addObject(Object obj){
		objects.add(obj);
	}
	
	public void addKeyObject(Object key,Object obj){
		if(key==null){
			return;
		}
		keyObjects.put(key,obj);
	}
	
	public List<Object> getObjects(){
		return objects;
	}

	public int getObjectsCount(){
		return objects.size();
	}
	

	public Map<Object,Object> getKeyObjects(){
		return keyObjects;
	}
	
	public int getKeyObjectsCount(){
		return keyObjects.size();
	}
	
	public Object getKeyObject(Object key){
		return keyObjects.get(key);
	}
}
