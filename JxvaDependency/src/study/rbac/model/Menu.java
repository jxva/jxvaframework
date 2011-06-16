package study.rbac.model;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework
 * on 2008-05-31 21:57:10
 *
 **/

@Table(name="tbl_menu",increment="null",primaryKeys={"menuid"})
public class Menu implements Serializable{

	private static final long serialVersionUID = 1431469390493889591L;
	private java.lang.Integer menuid;
	private java.lang.Integer parentid;
	private java.lang.Integer rootid;
	private java.lang.Integer levelnum;
	private java.lang.String levelinfo;
	private java.lang.String menuname;
	private java.lang.String menudesc;
	private java.lang.String menuurl;
	private java.lang.String menutarget;
	private java.lang.Integer openstat;
	private java.lang.String lefturl;
	private java.lang.String lefttarget;

	public java.lang.Integer getMenuid(){
		return this.menuid;
	}
	public void setMenuid(java.lang.Integer menuid){
		this.menuid=menuid;
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

	public java.lang.String getMenuname(){
		return this.menuname;
	}
	public void setMenuname(java.lang.String menuname){
		this.menuname=menuname;
	}

	public java.lang.String getMenudesc(){
		return this.menudesc;
	}
	public void setMenudesc(java.lang.String menudesc){
		this.menudesc=menudesc;
	}

	public java.lang.String getMenuurl(){
		return this.menuurl;
	}
	public void setMenuurl(java.lang.String menuurl){
		this.menuurl=menuurl;
	}

	public java.lang.String getMenutarget(){
		return this.menutarget;
	}
	public void setMenutarget(java.lang.String menutarget){
		this.menutarget=menutarget;
	}

	public java.lang.Integer getOpenstat(){
		return this.openstat;
	}
	public void setOpenstat(java.lang.Integer openstat){
		this.openstat=openstat;
	}

	public java.lang.String getLefturl(){
		return this.lefturl;
	}
	public void setLefturl(java.lang.String lefturl){
		this.lefturl=lefturl;
	}

	public java.lang.String getLefttarget(){
		return this.lefttarget;
	}
	public void setLefttarget(java.lang.String lefttarget){
		this.lefttarget=lefttarget;
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
