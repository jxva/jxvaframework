package com.jxva.demo.model;

import java.io.Serializable;
import com.jxva.dao.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-06-16 01:25:39
 *
 **/

@Table(name="tbl_bookinfo",increment="bookid",primarykeys={"authorid"})
public class Bookinfo implements Serializable{

	private java.lang.Integer authorid;
	private java.lang.String authorname;
	private java.lang.Integer bookid;
	private java.lang.String bookname;
	private java.lang.Integer amount;

	public java.lang.Integer getAuthorid(){
		return this.authorid;
	}
	public void setAuthorid(java.lang.Integer authorid){
		this.authorid=authorid;
	}

	public java.lang.String getAuthorname(){
		return this.authorname;
	}
	public void setAuthorname(java.lang.String authorname){
		this.authorname=authorname;
	}

	public java.lang.Integer getBookid(){
		return this.bookid;
	}
	public void setBookid(java.lang.Integer bookid){
		this.bookid=bookid;
	}

	public java.lang.String getBookname(){
		return this.bookname;
	}
	public void setBookname(java.lang.String bookname){
		this.bookname=bookname;
	}

	public java.lang.Integer getAmount(){
		return this.amount;
	}
	public void setAmount(java.lang.Integer amount){
		this.amount=amount;
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
