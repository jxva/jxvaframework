package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_test",increment="msgid",primaryKeys={"msgid"})
public class Test implements Serializable{

	private static final long serialVersionUID = 6173075958703404766L;
	private java.lang.Integer msgid;
	private java.lang.String username;
	private java.lang.String email;

	public java.lang.Integer getMsgid(){
		return this.msgid;
	}
	public void setMsgid(java.lang.Integer msgid){
		this.msgid=msgid;
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
