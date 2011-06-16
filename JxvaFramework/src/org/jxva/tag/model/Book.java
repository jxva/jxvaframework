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

import com.jxva.dao.annotation.OneToOne;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-19 22:52:45 by Automatic Generate Toolkit
 */
@Table(name="tbl_book",increment="bookId",primaryKeys={"bookId"})
public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer bookId;
	private java.lang.String name;
	private java.lang.Integer categoryId;
	private java.lang.Integer pressId;
	private java.sql.Date issuerDate;
	private java.sql.Timestamp createTime;
	private java.lang.Integer quantity;
	private java.math.BigDecimal price;
	private java.lang.Integer hitCount;
	private java.lang.Short isCommend;
	private java.lang.Short isAvailable;
	private java.lang.String description;

	@OneToOne(field="pressId",foreignKey="pressId")
	private Press press;

	@OneToOne(field="categoryId",foreignKey="categoryId")
	private Category category;

	public java.lang.Integer getBookId(){
		return this.bookId;
	}
	public void setBookId(java.lang.Integer bookId){
		this.bookId=bookId;
	}

	public java.lang.String getName(){
		return this.name;
	}
	public void setName(java.lang.String name){
		this.name=name;
	}

	public java.lang.Integer getCategoryId(){
		return this.categoryId;
	}
	public void setCategoryId(java.lang.Integer categoryId){
		this.categoryId=categoryId;
	}

	public java.lang.Integer getPressId(){
		return this.pressId;
	}
	public void setPressId(java.lang.Integer pressId){
		this.pressId=pressId;
	}

	public java.sql.Date getIssuerDate(){
		return this.issuerDate;
	}
	public void setIssuerDate(java.sql.Date issuerDate){
		this.issuerDate=issuerDate;
	}

	public java.sql.Timestamp getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime){
		this.createTime=createTime;
	}

	public java.lang.Integer getQuantity(){
		return this.quantity;
	}
	public void setQuantity(java.lang.Integer quantity){
		this.quantity=quantity;
	}

	public java.math.BigDecimal getPrice(){
		return this.price;
	}
	public void setPrice(java.math.BigDecimal price){
		this.price=price;
	}

	public java.lang.Integer getHitCount(){
		return this.hitCount;
	}
	public void setHitCount(java.lang.Integer hitCount){
		this.hitCount=hitCount;
	}

	public java.lang.Short getIsCommend(){
		return this.isCommend;
	}
	public void setIsCommend(java.lang.Short isCommend){
		this.isCommend=isCommend;
	}

	public java.lang.Short getIsAvailable(){
		return this.isAvailable;
	}
	public void setIsAvailable(java.lang.Short isAvailable){
		this.isAvailable=isAvailable;
	}

	public java.lang.String getDescription(){
		return this.description;
	}
	public void setDescription(java.lang.String description){
		this.description=description;
	}

	public Press getPress() {
		return press;
	}
	public void setPress(Press press) {
		this.press = press;
	}

	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
		sb.append("bookId=").append(bookId).append(',');
		sb.append("name=").append(name).append(',');
		sb.append("categoryId=").append(categoryId).append(',');
		sb.append("pressId=").append(pressId).append(',');
		sb.append("issuerDate=").append(issuerDate).append(',');
		sb.append("createTime=").append(createTime).append(',');
		sb.append("quantity=").append(quantity).append(',');
		sb.append("price=").append(price).append(',');
		sb.append("hitCount=").append(hitCount).append(',');
		sb.append("isCommend=").append(isCommend).append(',');
		sb.append("isAvailable=").append(isAvailable).append(',');
		sb.append("description=").append(description).append(" ]");
		return sb.toString();
	}

}
