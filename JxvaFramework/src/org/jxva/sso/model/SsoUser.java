package org.jxva.sso.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework at 2008-12-15 11:35:06
 *
 **/

@Table(name="tbl_sso_user",increment="user_id",primaryKeys={"user_id"})
public class SsoUser implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer userId;
	private java.lang.String username;
	private java.lang.String password;
	
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

	public java.lang.String getPassword(){
		return this.password;
	}
	public void setPassword(java.lang.String password){
		this.password=password;
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
