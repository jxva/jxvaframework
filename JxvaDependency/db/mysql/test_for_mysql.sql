drop table if exists tbl_increment;
create table tbl_increment(
   pktb        varchar(64)   not null,
   pk          bigint        not null,
   primary key (pktb)
)engine=innodb default charset=utf8;

drop table if exists tbl_test;
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