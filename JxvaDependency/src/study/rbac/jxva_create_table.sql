
drop table if exists JXVA_SYSTEM;

drop table if exists JXVA_USER;

drop table if exists JXVA_ROLE;

drop table if exists JXVA_GROUP;

drop table if exists JXVA_RULE;

drop table if exists JXVA_PERMISSION;

-- -------------------------------------------------
-- Jxva Framework系统表
-- -------------------------------------------------
create table JXVA_SYSTEM
(
	PKTB		VARCHAR(64) 		not null,
	PK		INT			not null,	
	PRIMARY KEY(PKTB)
	
)engine=myisam default charset utf8;

-- -------------------------------------------------
-- Jxva Framework 系统用户表
-- -------------------------------------------------

create table JXVA_USER (                

    UserId              INTEGER  not null default 0,
	
	UserName		    VARCHAR(32)		not null,

	Password        VARCHAR(64),
	
	Description     VARCHAR(128),
	
	Roles		VARCHAR(1024),

	Groups		VARCHAR(1024),

	PRIMARY KEY(UserId)

)engine=myisam default charset utf8;

insert into JXVA_USER values (1,'webmaster','c32e1653d94a466133fff041533e9765','系统管理员',null,null);

-- -------------------------------------------------
-- Jxva Framework 系统角色表(树型结构)
-- -------------------------------------------------

create table JXVA_ROLE (                

    RoleId              INTEGER  not null default 0,
	
	ParentId          INTEGER default 0,
	
	RootId             INTEGER default 0,
	
	LevelNum         INTEGER default 0,
	
	LevelInfo         VARCHAR(256),
	
	RoleName		    VARCHAR(32)		not null,
	
	Description     VARCHAR(128),
	
	Users		VARCHAR(1024),

	Groups		VARCHAR(1024),
	
	PRIMARY KEY(LevelInfo)

)engine=myisam default charset utf8;

CREATE  INDEX Jxva_Role_LevelInfo ON JXVA_ROLE(levelinfo);

insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (1,0,1,1,',1','软件');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (2,0,2,1,',2','硬件');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (3,2,2,2,',2,3','CPU');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (4,1,1,2,',1,4','网络编程');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (5,3,2,3,',2,3,5','Intel');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (6,2,2,2,',2,6','存储器');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (7,3,2,3,',2,3,7','AMD');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (8,1,1,2,',1,8','数据库');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (9,4,1,3,',1,4,9','ASP');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (10,8,1,3,',1,8,10','Mysql');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (11,6,2,3,',2,6,11','U盘');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (12,8,1,3,',1,8,12','DB2');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (13,4,1,3,',1,4,13','PHP');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (14,6,2,3,',2,6,14','移动盘');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (15,4,1,3,',1,4,15','Java');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (16,15,1,4,',1,4,15,16','Servlet');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (17,15,1,4,',1,4,15,17','JSP');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (18,8,1,3,',1,8,18','access');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (19,15,1,4,',1,4,15,19','EJB');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (20,4,1,3,',1,4,20','NET');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (21,1,1,2,',1,21','可视化编程');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (22,21,1,3,',1,21,22','Delphi');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (23,6,2,3,',2,6,23','Flash盘');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (26,20,1,4,',1,4,20,26','C#');
insert into jxva_role (roleid,parentid,rootid,levelnum,levelinfo,rolename) values (30,20,1,4,',1,4,20,30','VB.NET');

-- -------------------------------------------------
-- Jxva Framework 系统用户组表(树型结构)
-- -------------------------------------------------

create table JXVA_GROUP(                

    GroupId              INTEGER  not null default 0,
	
	ParentId          INTEGER default 0,
	
	RootId             INTEGER default 0,
	
	LevelNum         INTEGER default 0,
	
	LevelInfo         VARCHAR(256),
	
	GroupName		    VARCHAR(32)		not null,
	
	Description     VARCHAR(128),
	
	Users		VARCHAR(1024),

	Roles		VARCHAR(1024),
	
	PRIMARY KEY(LevelInfo)

)engine=myisam default charset utf8;

CREATE  INDEX Jxva_Group_LevelInfo ON JXVA_GROUP(levelinfo);