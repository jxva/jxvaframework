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
import com.jxva.dao.annotation.OneToOne;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 22:52:45 by Automatic Generate Toolkit
 */
@Table(name="tbl_order_map",increment="orderMapId",primaryKeys={"orderMapId"})
public class OrderMap implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer orderMapId;
	private java.lang.Integer orderId;
	private java.lang.Integer bookId;
	private java.lang.Integer amount;

	@OneToOne(field="orderId",foreignKey="orderId")
	private Order order;

	@OneToOne(field="bookId",foreignKey="bookId")
	private Book book;

	public java.lang.Integer getOrderMapId(){
		return this.orderMapId;
	}
	public void setOrderMapId(java.lang.Integer orderMapId){
		this.orderMapId=orderMapId;
	}

	public java.lang.Integer getOrderId(){
		return this.orderId;
	}
	public void setOrderId(java.lang.Integer orderId){
		this.orderId=orderId;
	}

	public java.lang.Integer getBookId(){
		return this.bookId;
	}
	public void setBookId(java.lang.Integer bookId){
		this.bookId=bookId;
	}

	public java.lang.Integer getAmount(){
		return this.amount;
	}
	public void setAmount(java.lang.Integer amount){
		this.amount=amount;
	}

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}

	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
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
		sb.append("orderMapId=").append(orderMapId).append(',');
		sb.append("orderId=").append(orderId).append(',');
		sb.append("bookId=").append(bookId).append(',');
		sb.append("amount=").append(amount).append(" ]");
		return sb.toString();
	}

}
