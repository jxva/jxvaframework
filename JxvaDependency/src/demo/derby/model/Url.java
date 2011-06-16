package demo.derby.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework at 2008-11-12 13:20:49
 *
 **/

@Table(name="tbl_url",increment="null",primaryKeys={"url_id"})
public class Url implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer urlId;
	private java.lang.String href;
	private java.lang.String description;

	public java.lang.Integer getUrlId(){
		return this.urlId;
	}
	public void setUrlId(java.lang.Integer urlId){
		this.urlId=urlId;
	}

	public java.lang.String getHref(){
		return this.href;
	}
	public void setHref(java.lang.String href){
		this.href=href;
	}

	public java.lang.String getDescription(){
		return this.description;
	}
	public void setDescription(java.lang.String description){
		this.description=description;
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
