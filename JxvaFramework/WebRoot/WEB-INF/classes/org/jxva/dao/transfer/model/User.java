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
@Table(name="tbl_user",increment="userId",primaryKeys={"userId"})
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer userId;
	private java.lang.String username;
	private java.lang.String email;
	private java.lang.String nickname;
	private java.lang.Integer gender;
	private java.lang.String realname;
	private java.sql.Date birthday;
	private java.lang.String mobile;
	private java.lang.String phone;
	private java.lang.String address;
	private java.lang.String postCode;
	private java.sql.Timestamp registerTime;
	private java.lang.Integer point;
	private java.lang.Integer isAvailable;
	private java.lang.String avatarUrl;
	private java.lang.String aboutme;
	private java.lang.String city;
	private java.lang.String privacy;

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

	public java.lang.String getEmail(){
		return this.email;
	}
	public void setEmail(java.lang.String email){
		this.email=email;
	}

	public java.lang.String getNickname(){
		return this.nickname;
	}
	public void setNickname(java.lang.String nickname){
		this.nickname=nickname;
	}

	public java.lang.Integer getGender(){
		return this.gender;
	}
	public void setGender(java.lang.Integer gender){
		this.gender=gender;
	}

	public java.lang.String getRealname(){
		return this.realname;
	}
	public void setRealname(java.lang.String realname){
		this.realname=realname;
	}

	public java.sql.Date getBirthday(){
		return this.birthday;
	}
	public void setBirthday(java.sql.Date birthday){
		this.birthday=birthday;
	}

	public java.lang.String getMobile(){
		return this.mobile;
	}
	public void setMobile(java.lang.String mobile){
		this.mobile=mobile;
	}

	public java.lang.String getPhone(){
		return this.phone;
	}
	public void setPhone(java.lang.String phone){
		this.phone=phone;
	}

	public java.lang.String getAddress(){
		return this.address;
	}
	public void setAddress(java.lang.String address){
		this.address=address;
	}

	public java.lang.String getPostCode(){
		return this.postCode;
	}
	public void setPostCode(java.lang.String postCode){
		this.postCode=postCode;
	}

	public java.sql.Timestamp getRegisterTime(){
		return this.registerTime;
	}
	public void setRegisterTime(java.sql.Timestamp registerTime){
		this.registerTime=registerTime;
	}

	public java.lang.Integer getPoint(){
		return this.point;
	}
	public void setPoint(java.lang.Integer point){
		this.point=point;
	}

	public java.lang.Integer getIsAvailable(){
		return this.isAvailable;
	}
	public void setIsAvailable(java.lang.Integer isAvailable){
		this.isAvailable=isAvailable;
	}

	public java.lang.String getAvatarUrl(){
		return this.avatarUrl;
	}
	public void setAvatarUrl(java.lang.String avatarUrl){
		this.avatarUrl=avatarUrl;
	}

	public java.lang.String getAboutme(){
		return this.aboutme;
	}
	public void setAboutme(java.lang.String aboutme){
		this.aboutme=aboutme;
	}

	public java.lang.String getCity(){
		return this.city;
	}
	public void setCity(java.lang.String city){
		this.city=city;
	}

	public java.lang.String getPrivacy(){
		return this.privacy;
	}
	public void setPrivacy(java.lang.String privacy){
		this.privacy=privacy;
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
		sb.append("email=").append(email).append(',');
		sb.append("nickname=").append(nickname).append(',');
		sb.append("gender=").append(gender).append(',');
		sb.append("realname=").append(realname).append(',');
		sb.append("birthday=").append(birthday).append(',');
		sb.append("mobile=").append(mobile).append(',');
		sb.append("phone=").append(phone).append(',');
		sb.append("address=").append(address).append(',');
		sb.append("postCode=").append(postCode).append(',');
		sb.append("registerTime=").append(registerTime).append(',');
		sb.append("point=").append(point).append(',');
		sb.append("isAvailable=").append(isAvailable).append(',');
		sb.append("avatarUrl=").append(avatarUrl).append(',');
		sb.append("aboutme=").append(aboutme).append(',');
		sb.append("city=").append(city).append(',');
		sb.append("privacy=").append(privacy).append(" ]");
		return sb.toString();
	}

}
