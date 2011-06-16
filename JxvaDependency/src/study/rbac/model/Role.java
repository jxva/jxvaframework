package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_role",increment="null",primaryKeys={"roleid"})
public class Role implements Serializable{

	private static final long serialVersionUID = 8721981291759807237L;
	private java.lang.Integer roleid;
	private java.lang.Integer parentid;
	private java.lang.Integer rootid;
	private java.lang.Integer levelnum;
	private java.lang.String levelinfo;
	private java.lang.String rolename;
	private java.lang.String roledesc;
	private java.lang.String roleprivileges;

	public java.lang.Integer getRoleid(){
		return this.roleid;
	}
	public void setRoleid(java.lang.Integer roleid){
		this.roleid=roleid;
	}

	public java.lang.Integer getParentid(){
		return this.parentid;
	}
	public void setParentid(java.lang.Integer parentid){
		this.parentid=parentid;
	}

	public java.lang.Integer getRootid(){
		return this.rootid;
	}
	public void setRootid(java.lang.Integer rootid){
		this.rootid=rootid;
	}

	public java.lang.Integer getLevelnum(){
		return this.levelnum;
	}
	public void setLevelnum(java.lang.Integer levelnum){
		this.levelnum=levelnum;
	}

	public java.lang.String getLevelinfo(){
		return this.levelinfo;
	}
	public void setLevelinfo(java.lang.String levelinfo){
		this.levelinfo=levelinfo;
	}

	public java.lang.String getRolename(){
		return this.rolename;
	}
	public void setRolename(java.lang.String rolename){
		this.rolename=rolename;
	}

	public java.lang.String getRoledesc(){
		return this.roledesc;
	}
	public void setRoledesc(java.lang.String roledesc){
		this.roledesc=roledesc;
	}

	public java.lang.String getRoleprivileges(){
		return this.roleprivileges;
	}
	public void setRoleprivileges(java.lang.String roleprivileges){
		this.roleprivileges=roleprivileges;
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
