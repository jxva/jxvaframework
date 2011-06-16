package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_url",increment="null",primaryKeys={"urlid"})
public class Url implements Serializable{

	private static final long serialVersionUID = 5565002268228918583L;
	private java.lang.Integer urlid;
	private java.lang.String urlname;
	private java.lang.String urldesc;

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
