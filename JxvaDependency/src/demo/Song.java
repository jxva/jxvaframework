package demo;

import java.io.Serializable;

import com.jxva.dao.annotation.Table;

/**
 *
 * auto generate by jxva framework at 2008-11-12 10:44:27
 *
 **/

@Table(name="tbl_song",increment="id",primaryKeys={"id"})
public class Song implements Serializable{

	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;
	private java.lang.Integer userId;
	private java.lang.Integer actorId;
	private java.lang.String name;
	private java.lang.String songUrl;
	private java.lang.String picUrl;
	private java.lang.Integer popNum;
	private java.lang.Integer playNum;
	private java.lang.Integer commendNum;
	private java.lang.Integer commentNum;
	private java.lang.Integer likeNum;
	private java.lang.Integer unlikeNum;
	private java.lang.String tags;
	private java.lang.String genre;
	private java.sql.Timestamp addTime;
	private java.lang.String actorName;
	private java.lang.Boolean issystem;
	private java.lang.String colorRing;
	private java.sql.Timestamp updateTime;
	private java.lang.Integer weekPlay;
	private java.lang.Integer monthPlay;
	private java.lang.String spellName;

	public java.lang.Integer getId(){
		return this.id;
	}
	public void setId(java.lang.Integer id){
		this.id=id;
	}

	public java.lang.Integer getUserId(){
		return this.userId;
	}
	public void setUserId(java.lang.Integer userId){
		this.userId=userId;
	}

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

	public java.lang.String getSongUrl(){
		return this.songUrl;
	}
	public void setSongUrl(java.lang.String songUrl){
		this.songUrl=songUrl;
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

	public java.lang.Integer getPlayNum(){
		return this.playNum;
	}
	public void setPlayNum(java.lang.Integer playNum){
		this.playNum=playNum;
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

	public java.lang.String getTags(){
		return this.tags;
	}
	public void setTags(java.lang.String tags){
		this.tags=tags;
	}

	public java.lang.String getGenre(){
		return this.genre;
	}
	public void setGenre(java.lang.String genre){
		this.genre=genre;
	}

	public java.sql.Timestamp getAddTime(){
		return this.addTime;
	}
	public void setAddTime(java.sql.Timestamp addTime){
		this.addTime=addTime;
	}

	public java.lang.String getActorName(){
		return this.actorName;
	}
	public void setActorName(java.lang.String actorName){
		this.actorName=actorName;
	}

	public java.lang.Boolean getIssystem(){
		return this.issystem;
	}
	public void setIssystem(java.lang.Boolean issystem){
		this.issystem=issystem;
	}

	public java.lang.String getColorRing(){
		return this.colorRing;
	}
	public void setColorRing(java.lang.String colorRing){
		this.colorRing=colorRing;
	}

	public java.sql.Timestamp getUpdateTime(){
		return this.updateTime;
	}
	public void setUpdateTime(java.sql.Timestamp updateTime){
		this.updateTime=updateTime;
	}

	public java.lang.Integer getWeekPlay(){
		return this.weekPlay;
	}
	public void setWeekPlay(java.lang.Integer weekPlay){
		this.weekPlay=weekPlay;
	}

	public java.lang.Integer getMonthPlay(){
		return this.monthPlay;
	}
	public void setMonthPlay(java.lang.Integer monthPlay){
		this.monthPlay=monthPlay;
	}

	public java.lang.String getSpellName(){
		return this.spellName;
	}
	public void setSpellName(java.lang.String spellName){
		this.spellName=spellName;
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
