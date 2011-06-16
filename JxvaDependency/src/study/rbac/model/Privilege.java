package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_privilege",increment="null",primaryKeys={"privilegeid"})
public class Privilege implements Serializable{


	private static final long serialVersionUID = -1953140656012417915L;
	private java.lang.Integer privilegeid;
	private java.lang.Integer resid;
	private java.lang.String restype;
	private java.lang.Integer isclose;
	private java.lang.String privilegedesc;

	public java.lang.Integer getPrivilegeid(){
		return this.privilegeid;
	}
	public void setPrivilegeid(java.lang.Integer privilegeid){
		this.privilegeid=privilegeid;
	}

	public java.lang.Integer getResid(){
		return this.resid;
	}
	public void setResid(java.lang.Integer resid){
		this.resid=resid;
	}

	public java.lang.String getRestype(){
		return this.restype;
	}
	public void setRestype(java.lang.String restype){
		this.restype=restype;
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
