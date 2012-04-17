
drop database jv_db;
create database jv_db default charset=utf8;

use jv_db;

grant all privileges on *.* to 'ztemt'@'%' identified by 'ztemt';
grant all privileges on *.* to 'ztemt'@'127.0.0.1';
grant all privileges on *.* to 'ztemt'@'localhost';
grant all privileges on *.* to 'root'@'%';
grant all privileges on *.* to 'root'@'127.0.0.1';
grant all privileges on *.* to 'root'@'localhost';

flush privileges;

drop table if exists tbl_test;

create table tbl_test(
	test_id		integer(11) NOT NULL auto_increment,
  	username 	varchar(16),
  	email 		varchar(32),
	primary key(test_id)	
)engine=innodb default charset=utf8;