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
import java.util.Set;

import com.jxva.dao.annotation.OneToMany;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 22:52:45 by Automatic Generate Toolkit
 */
@Table(name="tbl_category",increment="null",primaryKeys={"categoryId"})
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer categoryId;
	private java.lang.String name;
	private java.lang.Integer rootId;
	private java.lang.Integer parentId;
	private java.lang.Integer levelNum;
	private java.lang.String levelInfo;
	private java.lang.String description;

	@OneToMany(field="categoryId",foreignKey="categoryId")
	private Set<Book> bookGroup;

	public java.lang.Integer getCategoryId(){
		return this.categoryId;
	}
	public void setCategoryId(java.lang.Integer categoryId){
		this.categoryId=categoryId;
	}

	public java.lang.String getName(){
		return this.name;
	}
	public void setName(java.lang.String name){
		this.name=name;
	}

	public java.lang.Integer getRootId(){
		return this.rootId;
	}
	public void setRootId(java.lang.Integer rootId){
		this.rootId=rootId;
	}

	public java.lang.Integer getParentId(){
		return this.parentId;
	}
	public void setParentId(java.lang.Integer parentId){
		this.parentId=parentId;
	}

	public java.lang.Integer getLevelNum(){
		return this.levelNum;
	}
	public void setLevelNum(java.lang.Integer levelNum){
		this.levelNum=levelNum;
	}

	public java.lang.String getLevelInfo(){
		return this.levelInfo;
	}
	public void setLevelInfo(java.lang.String levelInfo){
		this.levelInfo=levelInfo;
	}

	public java.lang.String getDescription(){
		return this.description;
	}
	public void setDescription(java.lang.String description){
		this.description=description;
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
		StringBuffer sb=new StringBuffer();
		sb.append("[ ");
		sb.append("categoryId=").append(categoryId).append(',');
		sb.append("name=").append(name).append(',');
		sb.append("rootId=").append(rootId).append(',');
		sb.append("parentId=").append(parentId).append(',');
		sb.append("levelNum=").append(levelNum).append(',');
		sb.append("levelInfo=").append(levelInfo).append(',');
		sb.append("description=").append(description).append(" ]");
		return sb.toString();
	}

}
