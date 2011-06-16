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
package org.jxva.dao.model;

import java.io.Serializable;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 22:52:45 by Automatic Generate Toolkit
 */
@Table(name="tbl_increment",increment="null",primaryKeys={"tableName"})
public class Increment implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.String tableName;
	private java.lang.Integer incrementValue;

	public java.lang.String getTableName(){
		return this.tableName;
	}
	public void setTableName(java.lang.String tableName){
		this.tableName=tableName;
	}

	public java.lang.Integer getIncrementValue(){
		return this.incrementValue;
	}
	public void setIncrementValue(java.lang.Integer incrementValue){
		this.incrementValue=incrementValue;
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
		sb.append("tableName=").append(tableName).append(',');
		sb.append("incrementValue=").append(incrementValue).append(" ]");
		return sb.toString();
	}

}
