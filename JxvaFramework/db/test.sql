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

insert into tbl_author values (1,'jxva');
insert into tbl_author values (2,'jack');
insert into tbl_author values (3,'tom');
insert into tbl_author values (4,'lily');
insert into tbl_author values (5,'lucy');
insert into tbl_author values (6,'bluce');


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

insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (1,'软件',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (2,'网络开发',1,1,2,'1,2,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (3,'Asp',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (4,'Php',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (5,'Jsp',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (6,'数据库',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (7,'MySql',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (8,'SqlServer',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (9,'DB2',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (10,'Oracle',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (11,'网页设计',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (12,'CSS',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (13,'Script',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (14,'JavaScript',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (15,'VbScript',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (16,'Html',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (17,'硬件',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (18,'存储器',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (19,'U盘',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (20,'Flash卡',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (21,'硬盘',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (22,'软盘',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (23,'CPU',0,0,1,'1,');
insert into tbl_category (category_id,name,root_id,parent_id,level_num,level_info) values (24,'内存',0,0,1,'1,');


create table tbl_tag(
    tag_id      integer NOT NULL AUTO_INCREMENT,
    name        varchar(64) not null,
    primary key(tag_id)
)engine=innodb default charset=utf8;

insert into tbl_tag (tag_id,name) values (1,'五星');

create table tbl_press(
    press_id      integer NOT NULL AUTO_INCREMENT,
    press_type_id	integer default 0,
    name            varchar(64) not null,
    primary key(press_id)
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

-- create unique tbl_press_type_unique on tbl_press_type(name);

insert into tbl_press (press_id,press_type_id,name) values (1,4,'Oxford');
insert into tbl_press (press_id,press_type_id,name) values (2,7,'Research');
insert into tbl_press (press_id,press_type_id,name) values (3,2,'Foreign');
insert into tbl_press (press_id,press_type_id,name) values (4,8,'Teaching');
insert into tbl_press (press_id,press_type_id,name) values (5,2,'Tsinghua');
insert into tbl_press (press_id,press_type_id,name) values (6,3,'Peking');

create table tbl_book(
	book_id			integer NOT NULL AUTO_INCREMENT ,
	name		    varchar(64) not null,
	category_id     integer default 0,
	press_id		integer default 0,
	issuer_date		date	default null,
	create_time		datetime default null,
	quantity			integer default 0,
	price			decimal(8,2) default 0.00,
	hit_count			integer default 0,
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


create table tbl_book_tag(
    book_tag_id     integer NOT NULL AUTO_INCREMENT,
    book_id         integer default 0,
    tag_id          integer default 0,
    primary key(book_tag_id),
    FOREIGN KEY (book_id) REFERENCES tbl_book (book_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tbl_tag (tag_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

create table tbl_user(
    user_id     integer NOT NULL AUTO_INCREMENT,
    username    varchar(32),
    email      varchar(64),
    primary key(user_id)
)engine=innodb default charset=utf8;

create table tbl_order(
    order_id    integer NOT NULL AUTO_INCREMENT,
    user_id    	integer default 0,
    order_time	datetime default null,
    primary key(order_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user (user_id) ON DELETE CASCADE
)engine=innodb default charset=utf8;

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
