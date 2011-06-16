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
package com.jxva.cache;

import java.io.Serializable;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-22 23:27:36 by Jxva
 */
public final class Element implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	private long createTime;
	private long lastAccessTime;
	private long lastUpdateTime;
	private int hitCount;
	//private int missCount;
	
	private final Object key;
	private final Object value;
	
	public Element(Object key,Object value){
		long now=System.currentTimeMillis();
		this.key=key;
		this.value=value;
		this.createTime=now;
		this.lastUpdateTime=now;
		this.hitCount=0;
	}
	
	public final Object getKey(){
		return this.key;
	}
	
	public final Object getValue(){
		return this.value;
	}
	
	public final int getHitCount(){
		return this.hitCount;
	}
	
	//public final int getMissCount(){
	//	return this.missCount;
	//}
	
	public final long getCreateTime(){
		return this.createTime;
	}
	
	public final long getLastAccessTime(){
		return this.lastAccessTime;
	}
	
	public final long getLastUpdateTime(){
		return this.lastUpdateTime;
	}
	
	public final long getIdle(){
		return this.createTime-this.lastAccessTime;
	}
	
    public final void resetAccessStatistics() {
        lastAccessTime = 0;
        hitCount = 0;
    }
	
	public final void updateHitStatistics() {
        lastAccessTime = System.currentTimeMillis();
        hitCount++;
    }
	
	//public final void updateMissStatistics() {
    //    lastAccessTime = System.currentTimeMillis();
    //    missCount++;
    //}
	
    public final void updateUpdateStatistics() {
        lastUpdateTime = System.currentTimeMillis();
    }
    
    public final boolean equals(Object object) {
        if (object == null || !(object instanceof Element)) {
            return false;
        }
        Element element = (Element) object;
        if (key == null || element.getKey() == null) {
            return false;
        }
        return key.equals(element.getKey());
    }
    
    public final int hashCode() {
        return key.hashCode();
    }
    
    public final String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ key = ").append(key)
                .append(", value=").append(value)
                .append(", hitCount=").append(hitCount)
                .append(", createTime = ").append(createTime)
                .append(", lastAccessTime = ").append(lastAccessTime)
                .append(", lastUpdateTime = ").append(lastUpdateTime)
                .append(" ]");

        return sb.toString();
    }
}
