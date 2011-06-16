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
@Model(tableName="tbl_user",incrementField="user_id",primaryKeys={"user_id"})
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer userId;
	private java.lang.String username;

	@OneToMany(field="user_id",foreignKey="user_id")
	private Set<Book> bookGroup;

	public java.lang.Integer getUserId(){
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId){
		this.userId=userId;
	}

	public java.lang.String getUsername(){
		return this.username;
	}
	public void setUsername(java.lang.String username){
		this.username=username;
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
