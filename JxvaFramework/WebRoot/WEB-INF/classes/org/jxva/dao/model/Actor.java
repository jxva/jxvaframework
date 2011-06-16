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
 * @version 2009-03-20 21:10:58 by Automatic Generate Toolkit
 */
@Table(name="tbl_actor",increment="actorId",primaryKeys={"actorId"})
public class Actor implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer actorId;
	private java.lang.String name;
	private java.lang.String picUrl;
	private java.lang.Integer popNum;
	private java.lang.Integer likeNum;
	private java.lang.Integer unlikeNum;
	private java.lang.Integer commendNum;
	private java.lang.Integer commentNum;
	private java.lang.String tags;
	private java.lang.Integer userId;
	private java.lang.String spellName;
	private java.lang.Integer commend;
	private java.lang.String artistCountry;
	private java.lang.Integer artistId;


	public java.lang.Integer getActorId(){
		return this.actorId;
	}
	public void setActorId(java.lang.Integer actorId){
		this.actorId=actorId;
	}

	public java.lang.String getName(){
		return this.name;
	}
	public void setName(java.lang.String name){
		this.name=name;
	}

	public java.lang.String getPicUrl(){
		return this.picUrl;
	}
	public void setPicUrl(java.lang.String picUrl){
		this.picUrl=picUrl;
	}

	public java.lang.Integer getPopNum(){
		return this.popNum;
	}
	public void setPopNum(java.lang.Integer popNum){
		this.popNum=popNum;
	}

	public java.lang.Integer getLikeNum(){
		return this.likeNum;
	}
	public void setLikeNum(java.lang.Integer likeNum){
		this.likeNum=likeNum;
	}

	public java.lang.Integer getUnlikeNum(){
		return this.unlikeNum;
	}
	public void setUnlikeNum(java.lang.Integer unlikeNum){
		this.unlikeNum=unlikeNum;
	}

	public java.lang.Integer getCommendNum(){
		return this.commendNum;
	}
	public void setCommendNum(java.lang.Integer commendNum){
		this.commendNum=commendNum;
	}

	public java.lang.Integer getCommentNum(){
		return this.commentNum;
	}
	public void setCommentNum(java.lang.Integer commentNum){
		this.commentNum=commentNum;
	}

	public java.lang.String getTags(){
		return this.tags;
	}
	public void setTags(java.lang.String tags){
		this.tags=tags;
	}

	public java.lang.Integer getUserId(){
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId){
		this.userId=userId;
	}

	public java.lang.String getSpellName(){
		return this.spellName;
	}
	public void setSpellName(java.lang.String spellName){
		this.spellName=spellName;
	}

	public java.lang.Integer getCommend(){
		return this.commend;
	}
	public void setCommend(java.lang.Integer commend){
		this.commend=commend;
	}

	public java.lang.String getArtistCountry(){
		return this.artistCountry;
	}
	public void setArtistCountry(java.lang.String artistCountry){
		this.artistCountry=artistCountry;
	}

	public java.lang.Integer getArtistId(){
		return this.artistId;
	}
	public void setArtistId(java.lang.Integer artistId){
		this.artistId=artistId;
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
		sb.append("actorId=").append(actorId).append(',');
		sb.append("name=").append(name).append(',');
		sb.append("picUrl=").append(picUrl).append(',');
		sb.append("popNum=").append(popNum).append(',');
		sb.append("likeNum=").append(likeNum).append(',');
		sb.append("unlikeNum=").append(unlikeNum).append(',');
		sb.append("commendNum=").append(commendNum).append(',');
		sb.append("commentNum=").append(commentNum).append(',');
		sb.append("tags=").append(tags).append(',');
		sb.append("userId=").append(userId).append(',');
		sb.append("spellName=").append(spellName).append(',');
		sb.append("commend=").append(commend).append(',');
		sb.append("artistCountry=").append(artistCountry).append(',');
		sb.append("artistId=").append(artistId).append(" ]");
		return sb.toString();
	}

}
