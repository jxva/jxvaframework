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
import com.jxva.dao.annotation.OneToOne;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 22:52:45 by Automatic Generate Toolkit
 */
@Table(name="tbl_order",increment="orderId",primaryKeys={"orderId"})
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer orderId;
	private java.lang.Integer userId;
	private java.sql.Timestamp orderTime;

	@OneToOne(field="userId",foreignKey="userId")
	private User user;

	@OneToMany(field="orderId",foreignKey="orderId")
	private Set<OrderMap> orderMapGroup;

	public java.lang.Integer getOrderId(){
		return this.orderId;
	}
	public void setOrderId(java.lang.Integer orderId){
		this.orderId=orderId;
	}

	public java.lang.Integer getUserId(){
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId){
		this.userId=userId;
	}

	public java.sql.Timestamp getOrderTime(){
		return this.orderTime;
	}
	public void setOrderTime(java.sql.Timestamp orderTime){
		this.orderTime=orderTime;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Set<OrderMap> getOrderMapGroup() {
		return orderMapGroup;
	}
	public void setOrderMapGroup(Set<OrderMap> orderMapGroup) {
		this.orderMapGroup = orderMapGroup;
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
		sb.append("orderId=").append(orderId).append(',');
		sb.append("userId=").append(userId).append(',');
		sb.append("orderTime=").append(orderTime).append(" ]");
		return sb.toString();
	}

}
