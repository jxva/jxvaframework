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

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:56:34 by Jxva
 */
public class ArrayList implements List{
	
	private Object[] objs;
	private int size;
	
	public ArrayList(){
		objs=new Object[10];
	}
	
	public void add(Object obj){
		add(getEmptyPosition()+1,obj);
	}
	
	public void add(int index,Object obj){
		if(index>objs.length){
			Object[] newObjs=new Object[objs.length+5];
			for(int i=0;i<objs.length;i++){
				newObjs[i]=objs[i];
			}
			objs=newObjs;
			newObjs=null;
		}
		if(size<index)size=index;
		objs[index-1]=obj;
	}
		
	public Object get(int index){
		return objs[index];
	}
	
	public int size(){
		return size;
	}
	
	private int getEmptyPosition(){
		for(int i=0;i<objs.length;i++){
			if(objs[i]==null)return i;
		}
		return objs.length;
	}

	public void clear() {
		for(int i=0;i<size;i++){
			objs[i]=null;
		}
		size=0;
	}

	public boolean isEmpty() {
		return size==0;		
	}

	public void remove(int index) {
		objs[index]=null;		
	}
}
