package demo.hsqldb.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework at 2008-11-12 15:16:06
 *
 **/

@Table(name="text",increment="id",primaryKeys={"id"})
public class Text implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.String name;

	public java.lang.Integer getId(){
		return this.id;
	}
	public void setId(java.lang.Integer id){
		this.id=id;
	}

	public java.lang.String getName(){
		return this.name;
	}
	public void setName(java.lang.String name){
		this.name=name;
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
