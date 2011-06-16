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
package org.jxva.dao.transfer.model;

import java.io.Serializable;
import com.jxva.dao.annotation.Table;

/**
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-03 14:27:07 by Automatic Generate Toolkit
 */
@Table(name="tbl_sso_user",increment="userId",primaryKeys={"userId"})
public class SsoUser implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer userId;
	private java.lang.String username;
	private java.lang.String password;
	private java.lang.String nickname;
	private java.lang.String email;
	private java.lang.String mobileType;
	private java.lang.String mobileNo;
	private java.lang.Integer totalCurrency;
	private java.lang.Integer remainCurrency;
	private java.lang.String realname;
	private java.lang.String telephone;
	private java.lang.String province;
	private java.lang.String city;
	private java.lang.String address;
	private java.lang.String postcode;
	private java.lang.Integer restrictLogin;
	private java.sql.Timestamp restrictLoginTime;
	private java.lang.Integer restrictUpdate;
	private java.sql.Timestamp restrictUpdateTime;
	private java.lang.Short gender;

	public java.lang.Integer getUserId(){
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId){
		this.userId=userId;
	}

	public java.lang.String getUsername(){
		return this.username;
	}
	public void setUsername(java.lang.String username){
		this.username=username;
	}

	public java.lang.String getPassword(){
		return this.password;
	}
	public void setPassword(java.lang.String password){
		this.password=password;
	}

	public java.lang.String getNickname(){
		return this.nickname;
	}
	public void setNickname(java.lang.String nickname){
		this.nickname=nickname;
	}

	public java.lang.String getEmail(){
		return this.email;
	}
	public void setEmail(java.lang.String email){
		this.email=email;
	}

	public java.lang.String getMobileType(){
		return this.mobileType;
	}
	public void setMobileType(java.lang.String mobileType){
		this.mobileType=mobileType;
	}

	public java.lang.String getMobileNo(){
		return this.mobileNo;
	}
	public void setMobileNo(java.lang.String mobileNo){
		this.mobileNo=mobileNo;
	}

	public java.lang.Integer getTotalCurrency(){
		return this.totalCurrency;
	}
	public void setTotalCurrency(java.lang.Integer totalCurrency){
		this.totalCurrency=totalCurrency;
	}

	public java.lang.Integer getRemainCurrency(){
		return this.remainCurrency;
	}
	public void setRemainCurrency(java.lang.Integer remainCurrency){
		this.remainCurrency=remainCurrency;
	}

	public java.lang.String getRealname(){
		return this.realname;
	}
	public void setRealname(java.lang.String realname){
		this.realname=realname;
	}

	public java.lang.String getTelephone(){
		return this.telephone;
	}
	public void setTelephone(java.lang.String telephone){
		this.telephone=telephone;
	}

	public java.lang.String getProvince(){
		return this.province;
	}
	public void setProvince(java.lang.String province){
		this.province=province;
	}

	public java.lang.String getCity(){
		return this.city;
	}
	public void setCity(java.lang.String city){
		this.city=city;
	}

	public java.lang.String getAddress(){
		return this.address;
	}
	public void setAddress(java.lang.String address){
		this.address=address;
	}

	public java.lang.String getPostcode(){
		return this.postcode;
	}
	public void setPostcode(java.lang.String postcode){
		this.postcode=postcode;
	}

	public java.lang.Integer getRestrictLogin(){
		return this.restrictLogin;
	}
	public void setRestrictLogin(java.lang.Integer restrictLogin){
		this.restrictLogin=restrictLogin;
	}

	public java.sql.Timestamp getRestrictLoginTime(){
		return this.restrictLoginTime;
	}
	public void setRestrictLoginTime(java.sql.Timestamp restrictLoginTime){
		this.restrictLoginTime=restrictLoginTime;
	}

	public java.lang.Integer getRestrictUpdate(){
		return this.restrictUpdate;
	}
	public void setRestrictUpdate(java.lang.Integer restrictUpdate){
		this.restrictUpdate=restrictUpdate;
	}

	public java.sql.Timestamp getRestrictUpdateTime(){
		return this.restrictUpdateTime;
	}
	public void setRestrictUpdateTime(java.sql.Timestamp restrictUpdateTime){
		this.restrictUpdateTime=restrictUpdateTime;
	}

	public java.lang.Short getGender(){
		return this.gender;
	}
	public void setGender(java.lang.Short gender){
		this.gender=gender;
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
		sb.append("userId=").append(userId).append(',');
		sb.append("username=").append(username).append(',');
		sb.append("password=").append(password).append(',');
		sb.append("nickname=").append(nickname).append(',');
		sb.append("email=").append(email).append(',');
		sb.append("mobileType=").append(mobileType).append(',');
		sb.append("mobileNo=").append(mobileNo).append(',');
		sb.append("totalCurrency=").append(totalCurrency).append(',');
		sb.append("remainCurrency=").append(remainCurrency).append(',');
		sb.append("realname=").append(realname).append(',');
		sb.append("telephone=").append(telephone).append(',');
		sb.append("province=").append(province).append(',');
		sb.append("city=").append(city).append(',');
		sb.append("address=").append(address).append(',');
		sb.append("postcode=").append(postcode).append(',');
		sb.append("restrictLogin=").append(restrictLogin).append(',');
		sb.append("restrictLoginTime=").append(restrictLoginTime).append(',');
		sb.append("restrictUpdate=").append(restrictUpdate).append(',');
		sb.append("restrictUpdateTime=").append(restrictUpdateTime).append(',');
		sb.append("gender=").append(gender).append(" ]");
		return sb.toString();
	}

}
