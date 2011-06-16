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
package net.jxva.dao.model;

import java.io.Serializable;
import java.util.Set;

import com.jxva.dao.OneToMany;
import com.jxva.dao.Model;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-25 18:01:57 by Automatic Generate Toolkit
 */
@Model(tableName="tbl_publish",incrementField="publish_id",primaryKeys={"publish_id"})
public class Publish implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer publishId;
	private java.lang.String name;

	@OneToMany(field="publish_id",foreignKey="publish_id")
	private Set<Book> bookGroup;

	public java.lang.Integer getPublishId(){
		return this.publishId;
	}
	public void setPublishId(java.lang.Integer publishId){
		this.publishId=publishId;
	}

	public java.lang.String getName(){
		return this.name;
	}
	public void setName(java.lang.String name){
		this.name=name;
	}

	public Set<Book> getBookGroup() {
		return bookGroup;
	}
	public void setBookGroup(Set<Book> bookGroup) {
		this.bookGroup = bookGroup;
	}

	public boolean equals(Object obj){
		return super.equals(obj);
	}

	public int hashCode(){
		return super.hashCode();
	}

	public String toString(){
		return super.toString();
	}

}
