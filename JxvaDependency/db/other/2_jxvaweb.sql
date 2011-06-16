drop table if exists tbl_blog_comment;
drop table if exists tbl_blog_article;
drop table if exists tbl_blog_category;

drop table if exists tbl_photo_info;
drop table if exists tbl_photo_album;

drop table if exists tbl_music_info;
drop table if exists tbl_music_album;

drop table if exists tbl_guest_book;

create table tbl_blog_category(
	category_id		integer(11) NOT NULL auto_increment,
	name		varchar(32),
	priority	integer default 0,
	amount		integer default 0,
	primary key(category_id)
)engine=innodb default charset=utf8;

insert into tbl_blog_category (name,priority) values ('Personal Diary',1);
insert into tbl_blog_category (name,priority) values ('Focusing Technologes',2);
insert into tbl_blog_category (name,priority) values ('Favorite Informations',3);
insert into tbl_blog_category (name,priority) values ('Design Patterns',4);
insert into tbl_blog_category (name,priority) values ('Refactoring',5);
insert into tbl_blog_category (name,priority) values ('Data Structure',6);
insert into tbl_blog_category (name,priority) values ('Algorithms',7);
insert into tbl_blog_category (name,priority) values ('Open Sources',8);
insert into tbl_blog_category (name,priority) values ('Java Technologes',9);
insert into tbl_blog_category (name,priority) values ('ISO C/C++',10);
insert into tbl_blog_category (name,priority) values ('Linux/Unix',11);
insert into tbl_blog_category (name,priority) values ('Script Languages',12);
insert into tbl_blog_category (name,priority) values ('Operate Systems',13);
insert into tbl_blog_category (name,priority) values ('Database Systems',14);
insert into tbl_blog_category (name,priority) values ('Authentication Design',15);
insert into tbl_blog_category (name,priority) values ('Visual W_idgets',16);
insert into tbl_blog_category (name,priority) values ('Windows Program',17);
insert into tbl_blog_category (name,priority) values ('Web Development',18);

create table tbl_blog_article(
	article_id		integer	not null auto_increment,
	category_id     integer    default 0,
	auto_id			varchar(20),
	subject	        varchar(64),
	content         varchar(8096),
	add_time        datetime default null,
	hits            integer    default 0,
	comments		integer    default 0,
	primary key(article_id),
	FOREIGN KEY (category_id) REFERENCES tbl_blog_category (category_id) ON DELETE NO ACTION
)engine=innodb default charset=utf8;

drop view if exists tbl_article_info;
create view tbl_article_info as select tbl_blog_category.name,tbl_blog_category.amount,tbl_blog_article.* from tbl_blog_category,tbl_blog_article where tbl_blog_category.category_id=tbl_blog_article.category_id;

create table tbl_blog_comment(
	comment_id		integer not null auto_increment,
	article_id      integer default 0,
	information     varchar(512),
	post_time       datetime default null,
	ip              varchar(16),
	os              varchar(128),
	nickname        varchar(32),
	email           varchar(64),
	primary key(comment_id),
	FOREIGN KEY (article_id) REFERENCES tbl_blog_article (article_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

create table tbl_photo_album(
	album_id		integer(11) NOT NULL auto_increment,
	name		varchar(32),
	-- 相册类型  0 公开 1 私有 2 密码
	item		integer default 0,
	amount		integer default 0,
	primary key(album_id)
)engine=innodb default charset=utf8;

create table tbl_photo_info(
	photo_id			integer	not null auto_increment,
	album_id         integer    default 0,
	title	        varchar(64),
	add_time         datetime  	default null,
	hits            integer    	default 0,
	width			integer 	default 0,
	height			integer		default 0,
	size			integer		default	0,
	url				varchar(32),
	primary key(photo_id),
	FOREIGN KEY (album_id) REFERENCES tbl_photo_album (album_id) ON DELETE NO ACTION
)engine=innodb default charset=utf8;

create table tbl_music_album(
	album_id		integer(11) NOT NULL auto_increment,
	name		varchar(32),
	amount		integer default 0,
	primary key(album_id)
)engine=innodb default charset=utf8;

create table tbl_music_info(
	music_id			integer	not null auto_increment,
	album_id         integer    default 0,
	name	        varchar(64),
	url				varchar(256),
	add_time			datetime  	default null,
	hits			integer default 0,
	primary key(music_id),
	FOREIGN KEY (album_id) REFERENCES tbl_music_album (album_id) ON DELETE NO ACTION
)engine=innodb default charset=utf8;

create table tbl_guest_book(
	msg_id			integer	not null auto_increment,
	content			varchar(1024),
	ip				varchar(16),
	os				varchar(512),
	add_time		datetime default null,
	is_reply		integer default 0,
	reply_content	varchar(1024),
	reply_time		datetime default null,
	primary key(msg_id)
)engine=innodb default charset=utf8;