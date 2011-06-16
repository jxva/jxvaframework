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
import com.jxva.dao.OneToOne;
import com.jxva.dao.Model;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-25 18:01:57 by Automatic Generate Toolkit
 */
@Model(tableName="tbl_book",incrementField="book_id",primaryKeys={"book_id"})
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer bookId;
	private java.lang.Integer userId;
	private java.lang.Integer publishId;
	private java.lang.String name;

	@OneToOne(field="user_id",foreignKey="user_id")
	private User user;

	@OneToOne(field="publish_id",foreignKey="publish_id")
	private Publish publish;

	public java.lang.Integer getBookId(){
		return this.bookId;
	}
	public void setBookId(java.lang.Integer bookId){
		this.bookId=bookId;
	}

	public java.lang.Integer getUserId(){
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId){
		this.userId=userId;
	}

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

	public User getuser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Publish getpublish() {
		return publish;
	}
	public void setPublish(Publish publish) {
		this.publish = publish;
	}

	public boolean equals(Object obj){
		return super.equals(obj);
	}

	public int hashCode(){
		return super.hashCode();
	}

	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("[ ");
		sb.append("bookId=").append(bookId).append(',');
		sb.append("userId=").append(userId).append(',');
		sb.append("publishId=").append(publishId).append(',');
		sb.append("name=").append(name).append(" ]");
		return sb.toString();
	}

}
