package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * 	auto generate by jxva framework
 * 	on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_group",increment="null",primaryKeys={"groupid"})
public class Group implements Serializable{

	private static final long serialVersionUID = 6737909915902089986L;
	private java.lang.Integer groupid;
	private java.lang.Integer parentid;
	private java.lang.Integer rootid;
	private java.lang.Integer levelnum;
	private java.lang.String levelinfo;
	private java.lang.String groupname;
	private java.lang.String groupdesc;
	private java.lang.String grouproles;

	public java.lang.Integer getGroupid(){
		return this.groupid;
	}
	public void setGroupid(java.lang.Integer groupid){
		this.groupid=groupid;
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

	public java.lang.String getGroupname(){
		return this.groupname;
	}
	public void setGroupname(java.lang.String groupname){
		this.groupname=groupname;
	}

	public java.lang.String getGroupdesc(){
		return this.groupdesc;
	}
	public void setGroupdesc(java.lang.String groupdesc){
		this.groupdesc=groupdesc;
	}

	public java.lang.String getGrouproles(){
		return this.grouproles;
	}
	public void setGrouproles(java.lang.String grouproles){
		this.grouproles=grouproles;
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
