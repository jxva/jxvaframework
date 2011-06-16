package com.jxva.demo.model;

import java.io.Serializable;
import com.jxva.dao.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-06-16 01:25:39
 *
 **/

@Table(name="tbl_author",increment="null",primarykeys={"authorid"})
public class Author implements Serializable{

	private java.lang.Integer authorid;
	private java.lang.String authorname;

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
