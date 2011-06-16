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
@Table(name="tbl_book_author",increment="bookAuthorId",primaryKeys={"bookAuthorId"})
public class BookAuthor implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer bookAuthorId;
	private java.lang.Integer bookId;
	private java.lang.Integer authorId;

	@OneToOne(field="bookId",foreignKey="bookId")
	private Book book;

	@OneToOne(field="authorId",foreignKey="authorId")
	private Author author;

	public java.lang.Integer getBookAuthorId(){
		return this.bookAuthorId;
	}
	public void setBookAuthorId(java.lang.Integer bookAuthorId){
		this.bookAuthorId=bookAuthorId;
	}

	public java.lang.Integer getBookId(){
		return this.bookId;
	}
	public void setBookId(java.lang.Integer bookId){
		this.bookId=bookId;
	}

	public java.lang.Integer getAuthorId(){
		return this.authorId;
	}
	public void setAuthorId(java.lang.Integer authorId){
		this.authorId=authorId;
	}

	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
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
		sb.append("bookAuthorId=").append(bookAuthorId).append(',');
		sb.append("bookId=").append(bookId).append(',');
		sb.append("authorId=").append(authorId).append(" ]");
		return sb.toString();
	}

}
