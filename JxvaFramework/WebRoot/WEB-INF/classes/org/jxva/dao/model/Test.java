/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.jxva.dao.model;

import java.io.Serializable;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-02 14:48:59 by Automatic Generate Toolkit
 */
@Table(name="tbl_test",increment="aa",primaryKeys={"aa"})
public class Test implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Long aa;
	private java.io.InputStream bb;
	private java.lang.Character cc;
	private java.lang.Character dd;
	private java.lang.String ee;
	private java.sql.Date ff;
	private java.math.BigDecimal gg;
	private java.lang.Double hh;
	private java.lang.Double ii;
	private java.lang.Float jj;
	private java.lang.Integer kk;
	private java.lang.Integer ll;
	private java.lang.String mm;
	private java.math.BigDecimal nn;
	private java.math.BigDecimal oo;
	private java.lang.Double pp;
	private java.lang.Short qq;
	private java.sql.Time rr;
	private java.sql.Timestamp ss;
	private java.lang.String tt;
	private java.lang.Long uu;

	public java.lang.Long getAa(){
		return this.aa;
	}
	public void setAa(java.lang.Long aa){
		this.aa=aa;
	}

	public java.io.InputStream getBb(){
		return this.bb;
	}
	public void setBb(java.io.InputStream bb){
		this.bb=bb;
	}

	public java.lang.Character getCc(){
		return this.cc;
	}
	public void setCc(java.lang.Character cc){
		this.cc=cc;
	}

	public java.lang.Character getDd(){
		return this.dd;
	}
	public void setDd(java.lang.Character dd){
		this.dd=dd;
	}

	public java.lang.String getEe(){
		return this.ee;
	}
	public void setEe(java.lang.String ee){
		this.ee=ee;
	}

	public java.sql.Date getFf(){
		return this.ff;
	}
	public void setFf(java.sql.Date ff){
		this.ff=ff;
	}

	public java.math.BigDecimal getGg(){
		return this.gg;
	}
	public void setGg(java.math.BigDecimal gg){
		this.gg=gg;
	}

	public java.lang.Double getHh(){
		return this.hh;
	}
	public void setHh(java.lang.Double hh){
		this.hh=hh;
	}

	public java.lang.Double getIi(){
		return this.ii;
	}
	public void setIi(java.lang.Double ii){
		this.ii=ii;
	}

	public java.lang.Float getJj(){
		return this.jj;
	}
	public void setJj(java.lang.Float jj){
		this.jj=jj;
	}

	public java.lang.Integer getKk(){
		return this.kk;
	}
	public void setKk(java.lang.Integer kk){
		this.kk=kk;
	}

	public java.lang.Integer getLl(){
		return this.ll;
	}
	public void setLl(java.lang.Integer ll){
		this.ll=ll;
	}

	public java.lang.String getMm(){
		return this.mm;
	}
	public void setMm(java.lang.String mm){
		this.mm=mm;
	}

	public java.math.BigDecimal getNn(){
		return this.nn;
	}
	public void setNn(java.math.BigDecimal nn){
		this.nn=nn;
	}

	public java.math.BigDecimal getOo(){
		return this.oo;
	}
	public void setOo(java.math.BigDecimal oo){
		this.oo=oo;
	}

	public java.lang.Double getPp(){
		return this.pp;
	}
	public void setPp(java.lang.Double pp){
		this.pp=pp;
	}

	public java.lang.Short getQq(){
		return this.qq;
	}
	public void setQq(java.lang.Short qq){
		this.qq=qq;
	}

	public java.sql.Time getRr(){
		return this.rr;
	}
	public void setRr(java.sql.Time rr){
		this.rr=rr;
	}

	public java.sql.Timestamp getSs(){
		return this.ss;
	}
	public void setSs(java.sql.Timestamp ss){
		this.ss=ss;
	}

	public java.lang.String getTt(){
		return this.tt;
	}
	public void setTt(java.lang.String tt){
		this.tt=tt;
	}

	public java.lang.Long getUu(){
		return this.uu;
	}
	public void setUu(java.lang.Long uu){
		this.uu=uu;
	}

	public boolean equals(Object obj){
		return super.equals(obj);
	}

	public int hashCode(){
		return super.hashCode();
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[ ");
		sb.append("aa=").append(aa).append(',');
		sb.append("bb=").append(bb).append(',');
		sb.append("cc=").append(cc).append(',');
		sb.append("dd=").append(dd).append(',');
		sb.append("ee=").append(ee).append(',');
		sb.append("ff=").append(ff).append(',');
		sb.append("gg=").append(gg).append(',');
		sb.append("hh=").append(hh).append(',');
		sb.append("ii=").append(ii).append(',');
		sb.append("jj=").append(jj).append(',');
		sb.append("kk=").append(kk).append(',');
		sb.append("ll=").append(ll).append(',');
		sb.append("mm=").append(mm).append(',');
		sb.append("nn=").append(nn).append(',');
		sb.append("oo=").append(oo).append(',');
		sb.append("pp=").append(pp).append(',');
		sb.append("qq=").append(qq).append(',');
		sb.append("rr=").append(rr).append(',');
		sb.append("ss=").append(ss).append(',');
		sb.append("tt=").append(tt).append(',');
		sb.append("uu=").append(uu).append(" ]");
		return sb.toString();
	}

}
