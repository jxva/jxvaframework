drop database dao;
create database dao default charset=utf8;
use dao;
set names gbk;
set foreign_key_checks=0;
drop table if exists tbl_increment;
drop table if exists tbl_book_author;
drop table if exists tbl_author;
drop table if exists tbl_book_tag;
drop table if exists tbl_book;
drop table if exists tbl_order;
drop table if exists tbl_user;
drop table if exists tbl_order_map;
drop table if exists tbl_press;
drop table if exists tbl_press_type;
drop table if exists tbl_category;
drop table if exists tbl_tag;


create table tbl_increment(
	table_name	        varchar(64) not null,
	increment_value		integer not null default 0,	
	primary key(table_name)
)engine=innodb default charset=utf8;

create table tbl_author(
	author_id		integer NOT NULL AUTO_INCREMENT ,
	name		    varchar(32) not null,
	primary key(author_id)
)engine=innodb default charset=utf8;

insert into tbl_author values (1,'Jxva');
insert into tbl_author values (2,'Jack');
insert into tbl_author values (3,'Tom');
insert into tbl_author values (4,'Lily');
insert into tbl_author values (5,'Lucy');
insert into tbl_author values (6,'Bluce');
insert into tbl_author values (7,'Jason');
insert into tbl_author values (8,'Lida');
insert into tbl_author values (9,'Cuby');
insert into tbl_author values (10,'Patter');

-- create unique tbl_author_unique on tbl_author(author_name);

create table tbl_category(
	category_id		integer NOT NULL default 0,
	name            varchar(64) not null,
	root_id		    integer  default 0,
	parent_id       integer default 0,
	level_num       integer default 0,
	level_info      varchar(32) not null,
	description     varchar(1024),
	primary key(category_id)
)engine=innodb default charset=utf8;

insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (1,'Software',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (2,'Network',1,1,2,'1,2,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (3,'Asp',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (4,'Php',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (5,'Jsp',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (6,'Database',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (7,'MySql',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (8,'SqlServer',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (9,'DB2',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (10,'Oracle',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (11,'Web',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (12,'CSS',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (13,'Script',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (14,'JavaScript',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (15,'VbScript',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (16,'Html',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (17,'Hardware',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (18,'Store',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (19,'UDisk',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (20,'FlashCard',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (21,'HardDisk',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (22,'SoftDisk',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (23,'CPU',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (24,'Memory',0,0,1,'1,');


create table tbl_tag(
    tag_id      integer NOT NULL AUTO_INCREMENT,
    name        varchar(64) not null,
    primary key(tag_id)
)engine=innodb default charset=utf8;


insert into tbl_tag (tag_id,name) values (1,'One Star');
insert into tbl_tag (tag_id,name) values (2,'Two Star');
insert into tbl_tag (tag_id,name) values (3,'Three Star');
insert into tbl_tag (tag_id,name) values (4,'Four Star');
insert into tbl_tag (tag_id,name) values (5,'Five Star');
insert into tbl_tag (tag_id,name) values (6,'Six Star');
insert into tbl_tag (tag_id,name) values (7,'Seven Star');
insert into tbl_tag (tag_id,name) values (8,'Eight Star');
insert into tbl_tag (tag_id,name) values (9,'Nine Star');
insert into tbl_tag (tag_id,name) values (10,'Ten Star');






create table tbl_press(
    press_id      integer NOT NULL AUTO_INCREMENT,
    press_type_id	integer default 0,
    name            varchar(64) not null,
    primary key(press_id),
    FOREIGN KEY (press_type_id) REFERENCES tbl_press_type (press_type_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

-- create unique tbl_press_unique on tbl_press(name);

create table tbl_press_type(
    press_type_id      integer NOT NULL AUTO_INCREMENT,
    name            varchar(64) not null,
    primary key(press_type_id)
)engine=innodb default charset=utf8;

insert into tbl_press_type (press_type_id,name) values (1,'science');
insert into tbl_press_type (press_type_id,name) values (2,'nature');
insert into tbl_press_type (press_type_id,name) values (3,'medicine');
insert into tbl_press_type (press_type_id,name) values (4,'literature');
insert into tbl_press_type (press_type_id,name) values (5,'agriculture');
insert into tbl_press_type (press_type_id,name) values (6,'economic');
insert into tbl_press_type (press_type_id,name) values (7,'business');
insert into tbl_press_type (press_type_id,name) values (8,'weather');
insert into tbl_press_type (press_type_id,name) values (9,'geography');
insert into tbl_press_type (press_type_id,name) values (10,'biological');

-- create unique tbl_press_type_unique on tbl_press_type(name);

insert into tbl_press (press_id,press_type_id,name) values (1,1,'Oxford');
insert into tbl_press (press_id,press_type_id,name) values (2,2,'Research');
insert into tbl_press (press_id,press_type_id,name) values (3,3,'Foreign');
insert into tbl_press (press_id,press_type_id,name) values (4,4,'Teaching');
insert into tbl_press (press_id,press_type_id,name) values (5,5,'Tsinghua');
insert into tbl_press (press_id,press_type_id,name) values (6,4,'Peking');
insert into tbl_press (press_id,press_type_id,name) values (7,4,'Hunst');
insert into tbl_press (press_id,press_type_id,name) values (8,4,'Bit');
insert into tbl_press (press_id,press_type_id,name) values (9,4,'Buaa');
insert into tbl_press (press_id,press_type_id,name) values (10,4,'Bflu');

create table tbl_book(
	book_id			integer NOT NULL AUTO_INCREMENT ,
	name		    varchar(64) not null,
	category_id     integer default 0,
	press_id		integer default 0,
	issuer_date		date	default null,
	create_time		datetime default null,
	quantity		integer default 0,
	price			decimal(8,2) default 0.00,
	hit_count		integer default 0,
	is_commend		smallint default 0,
	is_available    smallint default 0,
	description		varchar(2048) null,
	primary key(book_id),
	FOREIGN KEY (press_id) REFERENCES tbl_press (press_id) ON DELETE CASCADE,
	FOREIGN KEY (category_id) REFERENCES tbl_category (category_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

insert into tbl_book (book_id,category_id,press_id,name) values (1,3,2,'java');
insert into tbl_book (book_id,category_id,press_id,name) values (2,4,3,'csharp');
insert into tbl_book (book_id,category_id,press_id,name) values (3,8,5,'access');
insert into tbl_book (book_id,category_id,press_id,name) values (4,2,4,'mysql');
insert into tbl_book (book_id,category_id,press_id,name) values (5,12,3,'db2');
insert into tbl_book (book_id,category_id,press_id,name) values (6,16,1,'oracle');
insert into tbl_book (book_id,category_id,press_id,name) values (7,1,2,'ruby');
insert into tbl_book (book_id,category_id,press_id,name) values (8,4,4,'php');
insert into tbl_book (book_id,category_id,press_id,name) values (9,9,2,'perl');
insert into tbl_book (book_id,category_id,press_id,name) values (10,7,3,'vb');

create table tbl_book_author(
    book_author_id    integer NOT NULL AUTO_INCREMENT,
    book_id           integer default 0,
    author_id         integer default 0,
    primary key(book_author_id),
    FOREIGN KEY (book_id) REFERENCES tbl_book (book_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES tbl_author (author_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

insert into tbl_book_author (book_author_id,book_id,author_id) values (1,1,2);
insert into tbl_book_author (book_author_id,book_id,author_id) values (2,4,3);
insert into tbl_book_author (book_author_id,book_id,author_id) values (3,5,5);
insert into tbl_book_author (book_author_id,book_id,author_id) values (4,6,6);
insert into tbl_book_author (book_author_id,book_id,author_id) values (5,2,1);
insert into tbl_book_author (book_author_id,book_id,author_id) values (6,5,3);
insert into tbl_book_author (book_author_id,book_id,author_id) values (7,6,4);
insert into tbl_book_author (book_author_id,book_id,author_id) values (8,4,2);
insert into tbl_book_author (book_author_id,book_id,author_id) values (9,6,3);
insert into tbl_book_author (book_author_id,book_id,author_id) values (10,7,5);

create table tbl_book_tag(
    book_tag_id     integer NOT NULL AUTO_INCREMENT,
    book_id         integer default 0,
    tag_id          integer default 0,
    primary key(book_tag_id),
    FOREIGN KEY (book_id) REFERENCES tbl_book (book_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tbl_tag (tag_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (1,2,3);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (2,1,2);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (3,5,4);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (4,6,5);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (5,8,6);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (6,3,1);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (7,4,2);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (8,7,3);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (9,5,8);
insert into tbl_book_tag (book_tag_id,book_id,tag_id) values (10,4,7);

create table tbl_user(
    user_id     integer NOT NULL AUTO_INCREMENT,
    username    varchar(32),
    email      varchar(64),
    primary key(user_id)
)engine=innodb default charset=utf8;

insert into tbl_user (user_id,username,email) values (1,'aaa','aaa@jxva.com');
insert into tbl_user (user_id,username,email) values (2,'bbb','bbb@jxva.com');
insert into tbl_user (user_id,username,email) values (3,'ccc','ccc@jxva.com');
insert into tbl_user (user_id,username,email) values (4,'ddd','ddd@jxva.com');
insert into tbl_user (user_id,username,email) values (5,'eee','eee@jxva.com');
insert into tbl_user (user_id,username,email) values (6,'fff','fff@jxva.com');
insert into tbl_user (user_id,username,email) values (7,'ggg','ggg@jxva.com');
insert into tbl_user (user_id,username,email) values (8,'hhh','hhh@jxva.com');
insert into tbl_user (user_id,username,email) values (9,'iii','iii@jxva.com');
insert into tbl_user (user_id,username,email) values (10,'jjj','jjj@jxva.com');

create table tbl_order(
    order_id    integer NOT NULL AUTO_INCREMENT,
    user_id    	integer default 0,
    order_time	datetime default null,
    primary key(order_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

insert into tbl_order (order_id,user_id,order_time) values (1,1,'2009-04-01 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (2,3,'2009-04-02 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (3,5,'2009-04-03 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (4,3,'2009-04-04 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (5,4,'2009-04-05 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (6,2,'2009-04-06 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (7,1,'2009-04-07 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (8,1,'2009-04-08 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (9,1,'2009-04-09 12:12:12');
insert into tbl_order (order_id,user_id,order_time) values (10,1,'2009-04-10 12:12:12');

create table tbl_order_map(
    order_map_id    integer NOT NULL AUTO_INCREMENT,
    order_id     integer default 0,
    book_id		integer default 0,
    amount		integer default 0,
    primary key(order_map_id),
    FOREIGN KEY (order_id) REFERENCES tbl_order (order_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES tbl_book (book_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

set foreign_key_checks=1;
