drop table if exists tbl_sso_user;

create table tbl_sso_user(
	user_id		integer(11) NOT NULL auto_increment,
	username	varchar(32),
	password	varchar(32),
	primary key(user_id)
)engine=innodb default charset=utf8;

insert into tbl_sso_user (username,password) values ('admin','aae8098e5541f9cb3739fccc69071558');