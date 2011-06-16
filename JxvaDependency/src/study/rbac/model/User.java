package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_user",increment="null",primaryKeys={"userid"})
public class User implements Serializable{

	private static final long serialVersionUID = -7180663721968328023L;
	private java.lang.Integer userid;
	private java.lang.String username;
	private java.lang.String password;
	private java.lang.String userdesc;
	private java.lang.String userroles;
	private java.lang.String usergroups;

	public java.lang.Integer getUserid(){
		return this.userid;
	}
	public void setUserid(java.lang.Integer userid){
		this.userid=userid;
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

	public java.lang.String getUserdesc(){
		return this.userdesc;
	}
	public void setUserdesc(java.lang.String userdesc){
		this.userdesc=userdesc;
	}

	public java.lang.String getUserroles(){
		return this.userroles;
	}
	public void setUserroles(java.lang.String userroles){
		this.userroles=userroles;
	}

	public java.lang.String getUsergroups(){
		return this.usergroups;
	}
	public void setUsergroups(java.lang.String usergroups){
		this.usergroups=usergroups;
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
