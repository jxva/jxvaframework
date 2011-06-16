package com.jxva.demo.model;

import java.io.Serializable;
import com.jxva.dao.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-06-16 01:25:39
 *
 **/

@Table(name="tbl_increment",increment="null",primarykeys={"pktb"})
public class Increment implements Serializable{

	private java.lang.String pktb;
	private java.lang.Integer pk;

	public java.lang.String getPktb(){
		return this.pktb;
	}
	public void setPktb(java.lang.String pktb){
		this.pktb=pktb;
	}

	public java.lang.Integer getPk(){
		return this.pk;
	}
	public void setPk(java.lang.Integer pk){
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
