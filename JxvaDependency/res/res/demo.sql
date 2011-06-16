drop database if exists demo;
create database demo default charset=utf8;
use demo;

drop table if exists tbl_increment;
drop table if exists tbl_author;
drop table if exists tbl_book;

-- jxva framework 自增字段存储表
create table tbl_increment(
	pktb	varchar(64) not null,
	pk		integer	  not null default 0,	
	primary key(pktb)
)engine=innodb default charset=utf8;
-- 以下两个表是为大家学习所用的

-- 建立一个作者表
create table tbl_author(
	authorid		integer	not null default 0,
	authorname	    varchar(32),
	primary key(authorid)
)engine=innodb default charset=utf8;

-- 建立一个存放书信息表
create table tbl_book(
	bookid		integer not null auto_increment,
	bookname	varchar(64),
	amount		integer default 0,
    authorid    integer not null default 0,
	 primary key(bookid),
    -- 外键设为作者Id authorid,级联删除
	FOREIGN KEY (authorid) REFERENCES tbl_author (authorid) ON DELETE CASCADE
)engine=innodb default charset=utf8;
-- 对书名建立唯一约束
create unique index tbl_book_unique on tbl_book(bookname);

-- 建立一个作者与书数据的书信息视图
drop view if exists  tbl_bookinfo;
create view tbl_bookinfo as select tbl_author.authorid,tbl_author.authorname,tbl_book.bookid,tbl_book.bookname,tbl_book.amount  from tbl_author,tbl_book where tbl_author.authorid=tbl_book.authorid;
