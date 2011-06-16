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

import java.io.Serializable;

public final class Element implements Serializable{
	
	private static final long serialVersionUID = -4380711883324644986L;
	private long createTime;
	private long updateTime;
	private int hits;
	private Object key;
	private Object value;
	
	protected Element(Object key,Object value){
		long now=System.currentTimeMillis();
		this.key=key;
		this.value=value;
		this.createTime=now;
		this.updateTime=now;
		this.hits=0;
	}
	
	protected void setCreateTime(long createTime){
		this.createTime=createTime;
	}
	
	protected void setUpdateTime(long updateTime){
		this.updateTime=updateTime;
	}
	
	protected void setHits(int hits){
		this.hits=hits;
	}
	
	protected Object getKey(){
		return this.key;
	}
	
	protected Object getValue(){
		return this.value;
	}
	
	public int getHits(){
		return this.hits;
	}
	
	public long getIdle(){
		return this.createTime-this.updateTime;
	}
}
