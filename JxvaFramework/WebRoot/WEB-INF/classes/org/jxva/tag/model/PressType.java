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
package org.jxva.tag.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 22:52:45 by Automatic Generate Toolkit
 */
@Table(name="tbl_press_type",increment="pressTypeId",primaryKeys={"pressTypeId"})
public class PressType implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer pressTypeId;
	private java.lang.String name;

	public PressType(Integer pressTypeId,String name){
		this.pressTypeId=pressTypeId;
		this.name=name;
	}

	public java.lang.Integer getPressTypeId(){
		return this.pressTypeId;
	}
	public void setPressTypeId(java.lang.Integer pressTypeId){
		this.pressTypeId=pressTypeId;
	}

	public java.lang.String getName(){
		return this.name;
	}
	public void setName(java.lang.String name){
		this.name=name;
	}

	public boolean equals(Object obj){
		return super.equals(obj);
	}

	public int hashCode(){
		return super.hashCode();
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[ ");
		sb.append("pressTypeId=").append(pressTypeId).append(',');
		sb.append("name=").append(name).append(" ]");
		return sb.toString();
	}

}
