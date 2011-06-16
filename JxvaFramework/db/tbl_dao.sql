
drop table if exists tbl_book;
drop table if exists tbl_user;
drop table if exists tbl_publish;

create table tbl_user(
	user_id		integer(11) NOT NULL auto_increment,
	username	varchar(32),	
	primary key(user_id)
)engine=innodb default charset=utf8;

insert into tbl_user values (1,'jxva');
insert into tbl_user values (2,'jack');
insert into tbl_user values (3,'tom');
insert into tbl_user values (4,'lily');
insert into tbl_user values (5,'lucy');
insert into tbl_user values (6,'bluce');

create table tbl_publish(
	publish_id	integer(11) NOT NULL auto_increment,
	name	varchar(64),	
	primary key(publish_id)
)engine=innodb default charset=utf8;

insert into tbl_publish (publish_id,name) values (1,'Oxford');
insert into tbl_publish (publish_id,name) values (2,'Research');
insert into tbl_publish (publish_id,name) values (3,'Foreign');
insert into tbl_publish (publish_id,name) values (4,'Teaching');
insert into tbl_publish (publish_id,name) values (5,'Tsinghua');
insert into tbl_publish (publish_id,name) values (6,'Peking');

create table tbl_book(
	book_id		integer(11) NOT NULL auto_increment,
	user_id		integer(11),
	publish_id  integer(11),
	name		varchar(32),
	primary key(book_id),
	FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (publish_id) REFERENCES tbl_publish (publish_id) ON DELETE NO ACTION ON UPDATE NO ACTION
)engine=innodb default charset=utf8;

insert into tbl_book (user_id,publish_id,name) values (1,2,'java');
insert into tbl_book (user_id,publish_id,name) values (2,3,'csharp');
insert into tbl_book (user_id,publish_id,name) values (4,5,'access');
insert into tbl_book (user_id,publish_id,name) values (5,4,'mysql');
insert into tbl_book (user_id,publish_id,name) values (2,3,'db2');
insert into tbl_book (user_id,publish_id,name) values (3,1,'oracle');
insert into tbl_book (user_id,publish_id,name) values (1,2,'ruby');
insert into tbl_book (user_id,publish_id,name) values (4,4,'php');
insert into tbl_book (user_id,publish_id,name) values (2,2,'perl');
insert into tbl_book (user_id,publish_id,name) values (2,3,'vb');

-- select * from tbl_book b,tbl_user u where b.user_id=u.user_id
-- select * from tbl_book b inner join tbl_user u where b.user_id=u.user_id
-- select * from tbl_book b left join tbl_user u on b.user_id=u.user_id
-- select * from tbl_user u right join tbl_book b on b.user_id=u.user_id
-- select * from tbl_book b right join tbl_user u on b.user_id=u.user_id

-- select * from tbl_book b right join tbl_user u on b.user_id=u.user_id left join tbl_publish p on b.publish_id=p.publish_id where book_id=10
-- select * from tbl_book b right outer join tbl_user u on b.user_id=u.user_id left outer join tbl_publish p on b.publish_id=p.publish_id
-- select * from tbl_song song0_ left outer join tbl_actor actor1_ on song0_.actor_id=actor1_.actor_id left outer join tbl_user user2_ on song0_.user_id=user2_.user_id where song0_.id=890
-- from tbl_song song0_ left outer join tbl_actor actor1_ on song0_.actor_id=actor1_.actor_id order by song0_.week_play desc limit ?







select song0_.id as id38_0_, actor1_.actor_id as actor1_1_1_, song0_.name as name38_0_, song0_.song_url as song3_38_0_, song0_.pic_url as pic4_38_0_, song0_.pop_num as pop5_38_0_, song0_.play_num as play6_38_0_, song0_.commend_num as commend7_38_0_, song0_.comment_num as comment8_38_0_, song0_.like_num as like9_38_0_, song0_.unlike_num as unlike10_38_0_, song0_.tags as tags38_0_, song0_.genre as genre38_0_, song0_.add_time as add13_38_0_, song0_.actor_name as actor14_38_0_, song0_.isSystem as isSystem38_0_, song0_.color_ring as color16_38_0_, song0_.update_time as update17_38_0_, song0_.week_play as week18_38_0_, song0_.month_play as month19_38_0_, song0_.spell_name as spell20_38_0_, song0_.commend as commend38_0_, song0_.explicit_lyrics as explicit22_38_0_, song0_.upc as upc38_0_, song0_.volume_sequence as volume24_38_0_, song0_.track_sequence as track25_38_0_, song0_.song_resource as song26_38_0_, song0_.isrc as isrc38_0_, song0_.actor_id as actor28_38_0_, song0_.user_id as user29_38_0_, actor1_.name as name1_1_, actor1_.pic_url as pic3_1_1_, actor1_.pop_num as pop4_1_1_, actor1_.like_num as like5_1_1_, actor1_.unlike_num as unlike6_1_1_, actor1_.commend_num as commend7_1_1_, actor1_.comment_num as comment8_1_1_, actor1_.tags as tags1_1_, actor1_.spell_name as spell10_1_1_, actor1_.commend as commend1_1_, actor1_.artist_country as artist12_1_1_, actor1_.artist_id as artist13_1_1_, actor1_.user_id as user14_1_1_ 



from tbl_song song0_ left outer join tbl_actor actor1_ on song0_.actor_id=actor1_.actor_id order by song0_.week_play desc limit ?
Hibernate: 



Hibernate: select song0_.id as id38_0_, actor1_.actor_id as actor1_1_1_, user2_.user_id as user1_47_2_, song0_.name as name38_0_, song0_.song_url as song3_38_0_, song0_.pic_url as pic4_38_0_, song0_.pop_num as pop5_38_0_, song0_.play_num as play6_38_0_, song0_.commend_num as commend7_38_0_, song0_.comment_num as comment8_38_0_, song0_.like_num as like9_38_0_, song0_.unlike_num as unlike10_38_0_, song0_.tags as tags38_0_, song0_.genre as genre38_0_, song0_.add_time as add13_38_0_, song0_.actor_name as actor14_38_0_, song0_.isSystem as isSystem38_0_, song0_.color_ring as color16_38_0_, song0_.update_time as update17_38_0_, song0_.week_play as week18_38_0_, song0_.month_play as month19_38_0_, song0_.spell_name as spell20_38_0_, song0_.commend as commend38_0_, song0_.explicit_lyrics as explicit22_38_0_, song0_.upc as upc38_0_, song0_.volume_sequence as volume24_38_0_, song0_.track_sequence as track25_38_0_, song0_.song_resource as song26_38_0_, song0_.isrc as isrc38_0_, song0_.actor_id as actor28_38_0_, song0_.user_id as user29_38_0_, actor1_.name as name1_1_, actor1_.pic_url as pic3_1_1_, actor1_.pop_num as pop4_1_1_, actor1_.like_num as like5_1_1_, actor1_.unlike_num as unlike6_1_1_, actor1_.commend_num as commend7_1_1_, actor1_.comment_num as comment8_1_1_, actor1_.tags as tags1_1_, actor1_.spell_name as spell10_1_1_, actor1_.commend as commend1_1_, actor1_.artist_country as artist12_1_1_, actor1_.artist_id as artist13_1_1_, actor1_.user_id as user14_1_1_, user2_.username as username47_2_, user2_.email as email47_2_, user2_.nickname as nickname47_2_, user2_.gender as gender47_2_, user2_.realname as realname47_2_, user2_.birthday as birthday47_2_, user2_.mobile as mobile47_2_, user2_.phone as phone47_2_, user2_.address as address47_2_, user2_.post_code as post11_47_2_, user2_.register_time as register12_47_2_, user2_.point as point47_2_, user2_.is_available as is14_47_2_, user2_.avatar_url as avatar15_47_2_, user2_.aboutme as aboutme47_2_, user2_.city as city47_2_, user2_.privacy as privacy47_2_ 


from tbl_song song0_ left outer join tbl_actor actor1_ on song0_.actor_id=actor1_.actor_id left outer join tbl_user user2_ on song0_.user_id=user2_.user_id where song0_.id=890