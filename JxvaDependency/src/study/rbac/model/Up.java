package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_up",increment="null",primaryKeys={"urlid"})
public class Up implements Serializable{

	private static final long serialVersionUID = -4045596505156991932L;
	private java.lang.Integer urlid;
	private java.lang.String urlname;
	private java.lang.String urldesc;
	private java.lang.Integer privilegeid;
	private java.lang.Integer isclose;
	private java.lang.String privilegedesc;

	public java.lang.Integer getUrlid(){
		return this.urlid;
	}
	public void setUrlid(java.lang.Integer urlid){
		this.urlid=urlid;
	}

	public java.lang.String getUrlname(){
		return this.urlname;
	}
	public void setUrlname(java.lang.String urlname){
		this.urlname=urlname;
	}

	public java.lang.String getUrldesc(){
		return this.urldesc;
	}
	public void setUrldesc(java.lang.String urldesc){
		this.urldesc=urldesc;
	}

	public java.lang.Integer getPrivilegeid(){
		return this.privilegeid;
	}
	public void setPrivilegeid(java.lang.Integer privilegeid){
		this.privilegeid=privilegeid;
	}

	public java.lang.Integer getIsclose(){
		return this.isclose;
	}
	public void setIsclose(java.lang.Integer isclose){
		this.isclose=isclose;
	}

	public java.lang.String getPrivilegedesc(){
		return this.privilegedesc;
	}
	public void setPrivilegedesc(java.lang.String privilegedesc){
		this.privilegedesc=privilegedesc;
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
