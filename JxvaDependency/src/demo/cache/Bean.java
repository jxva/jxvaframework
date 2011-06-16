package demo.cache;

import java.io.Serializable;

public class Bean implements Serializable {

	private static final long serialVersionUID = 3384056550927859887L;
	private java.lang.Integer msgid;
	private java.lang.String realname;
	private java.lang.String factoryno;

	public java.lang.Integer getMsgid() {
		return this.msgid;
	}

	public void setMsgid(java.lang.Integer msgid) {
		this.msgid = msgid;
	}

	public java.lang.String getRealname() {
		return this.realname;
	}

	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}

	public java.lang.String getFactoryno() {
		return this.factoryno;
	}

	public void setFactoryno(java.lang.String factoryno) {
		this.factoryno = factoryno;
	}

}