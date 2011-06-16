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
@Table(name="tbl_user",increment="userId",primaryKeys={"userId"})
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer userId;
	private java.lang.String username;
	private java.lang.String email;

	@OneToMany(field="userId",foreignKey="userId")
	private Set<Order> orderGroup;

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

	public java.lang.String getEmail(){
		return this.email;
	}
	public void setEmail(java.lang.String email){
		this.email=email;
	}

	public Set<Order> getOrderGroup() {
		return orderGroup;
	}
	public void setOrderGroup(Set<Order> orderGroup) {
		this.orderGroup = orderGroup;
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
		sb.append("userId=").append(userId).append(',');
		sb.append("username=").append(username).append(',');
		sb.append("email=").append(email).append(" ]");
		return sb.toString();
	}

}
