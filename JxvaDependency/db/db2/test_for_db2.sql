create table tbl_increment(
   pktb        varchar(64)   not null,
   pk          bigint        not null,
   primary key (pktb)
);


create table tbl_test(
   AA          			BIGINT                 not null,
   BB                   BLOB(1024),
   CC                   CHAR,
   DD                   CHARACTER,
   EE                   CLOB(1024),
   FF                   DATE,
   GG                   DECIMAL,
   HH                   DOUBLE,
   II                   DOUBLE PRECISION,
   JJ                   FLOAT,
   KK                   INT,
   LL                   INTEGER,
   MM                   LONG VARCHAR,
   NN                   NUM,
   OO                   NUMERIC,
   PP                   REAL,
   QQ                   SMALLINT,
   RR                   TIME,
   SS                   TIMESTAMP,
   TT                   VARCHAR(1024),
   primary key (AA)
);