package demo.dao.mysql.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework at 2008-11-12 18:42:46
 *
 **/

@Table(name="tbl_increment",increment="null",primaryKeys={"pktb"})
public class Increment implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.String pktb;
	private java.lang.Long pk;

	public java.lang.String getPktb(){
		return this.pktb;
	}
	public void setPktb(java.lang.String pktb){
		this.pktb=pktb;
	}

	public java.lang.Long getPk(){
		return this.pk;
	}
	public void setPk(java.lang.Long pk){
		this.pk=pk;
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
