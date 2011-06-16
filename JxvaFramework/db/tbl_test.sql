drop table if exists tbl_increment;
drop table if exists tbl_test;
-- -------------------------------------------------
-- jxva framework 自增字段存储表
-- -------------------------------------------------

create table tbl_increment(
	pktb	varchar(64) not null,
	pk		integer	not null default 0,	
	primary key(pktb)
)engine=innodb default charset=utf8;

create table tbl_test(
   AA					BIGINT NOT NULL auto_increment,
   BB                   BLOB,
   CC                   CHAR,
   DD                   CHARACTER,
   EE                   TEXT, -- db2 clob
   FF                   DATE,
   GG                   DECIMAL,
   HH                   DOUBLE,
   II                   DOUBLE PRECISION,
   JJ                   FLOAT,
   KK                   INT,
   LL                   INTEGER,
   MM                   LONG VARCHAR,
   NN                   NUMERIC,
   OO                   NUMERIC,
   PP                   REAL,
   QQ                   SMALLINT,
   RR                   TIME,
   SS                   TIMESTAMP,
   TT                   VARCHAR(1024),
   UU          			BIGINT,
   primary key (AA)
)engine=innodb default charset=utf8;