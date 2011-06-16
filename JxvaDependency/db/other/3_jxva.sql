drop table if exists tbl_increment;
drop table if exists tbl_user;
drop table if exists tbl_group;
drop table if exists tbl_role;
drop table if exists tbl_privilege;
drop table if exists tbl_menu;
drop table if exists tbl_url;
drop table if exists tbl_operate;


-- -------------------------------------------------
-- jxva framework 自增字段存储表
-- -------------------------------------------------

create table tbl_increment(
	pktb	varchar(64) not null,
	pk		integer	not null default 0,	
	primary key(pktb)
)engine=innodb default charset=utf8;

-- -------------------------------------------------
-- url资源表
-- -------------------------------------------------

create table tbl_url(
	url_id		integer	not null,
  	href 		varchar(255),
  	description varchar(255),
	primary key(url_id)	
)engine=innodb default charset=utf8;

create unique index tbl_url_unique on tbl_url(href);

insert into tbl_url values (1,'platform/index.jsp','平台首页');
insert into tbl_url values (2,'platform/login.jv','会员使用页面1');
insert into tbl_url values (3,'platform/workspace.jv','登录权限');
insert into tbl_url values (4,'platform/top.jv','登录权限');
insert into tbl_url values (5,'platform/left.jv','管理员');
insert into tbl_url values (6,'platform/middle.jv','部门经理');
insert into tbl_url values (7,'platform/main.jv','超级管理员');
insert into tbl_url values (8,'platform/system.jv','用户列表管理');

-- -------------------------------------------------
-- 菜单资源表(树型结构) 用户可自行设计此表扩展字段结构
-- -------------------------------------------------

create table tbl_menu(                
    menu_id        	integer not null default 0,
    parent_id      	integer default 0,
    root_id        	integer default 0,
    level_num      	integer default 0,
    level_info     	varchar(128),
    title	  		varchar(32)	not null,
	main_href       varchar(256),
	left_href		varchar(256),
	-- 0:开启,1:关闭,2:默认
	left_stat		integer default 0,
 	icon			varchar(32),
    primary key(menu_id)
)engine=innodb default charset=utf8;
 
insert into tbl_menu values (1,0,1,1,'1,','集成平台','main.html',null,2,null);
insert into tbl_menu values (2,0,2,1,'2,','网上商城','main.html',null,2,null);
insert into tbl_menu values (3,0,3,1,'3,','互动娱乐','main.html',null,2,null);
insert into tbl_menu values (4,0,4,1,'4,','手机平台','main.html',null,2,null);
insert into tbl_menu values (5,1,1,2,'1,5,','资源管理','menu!admin.jv',null,2,null);
insert into tbl_menu values (6,2,2,2,'2,6,','订单管理','main.html',null,2,null);
insert into tbl_menu values (7,1,1,2,'2,7,','角色管理','main.html',null,2,null);
insert into tbl_menu values (8,5,1,3,'1,5,8,','菜单资源管理','menu!admin.jv',null,0,'189');
insert into tbl_menu values (9,5,1,3,'1,5,9,','用户角色管理','role!admin.jv',null,0,'190');
insert into tbl_menu values (10,6,2,3,'2,6,10,','商品信息管理','menu!admin.jv',null,0,'191');

create index tbl_menu_index on tbl_menu(level_info);

-- -------------------------------------------------
-- 权限表
-- -------------------------------------------------

create table tbl_privilege(
	privilege_id		integer	not null,
	resource_id			integer not null default 0,
	resource_type 		varchar(32),
	is_close 		integer	not null default 0,
	description  	varchar(256),
	primary key(privilege_id)
)engine=innodb default charset=utf8;

insert into tbl_privilege values (1,1,'url',0,null);
insert into tbl_privilege values (2,2,'url',0,null);
insert into tbl_privilege values (3,3,'url',0,null);
insert into tbl_privilege values (4,4,'url',0,null);
insert into tbl_privilege values (5,5,'url',0,null);
insert into tbl_privilege values (6,6,'url',0,null);
insert into tbl_privilege values (7,7,'url',0,null);
insert into tbl_privilege values (8,9,'url',0,null);
insert into tbl_privilege values (9,10,'url',0,null);
insert into tbl_privilege values (10,11,'url',0,null);
insert into tbl_privilege values (11,12,'url',0,null);
insert into tbl_privilege values (12,13,'url',0,null);
insert into tbl_privilege values (13,14,'url',0,null);

-- ---------------------------------------------
-- 创建url资源与权限信息表视图
-- tbl_url+tbl_privilege=tbl_url_privilege
-- ---------------------------------------------
drop view if exists  tbl_url_privilege;
create view tbl_url_privilege as select tbl_url.url_id,tbl_url.href,tbl_url.description as url_description,tbl_privilege.privilege_id,tbl_privilege.is_close,tbl_privilege.description as privilege_description  from tbl_url,tbl_privilege where tbl_url.url_id=tbl_privilege.resource_id and tbl_privilege.resource_type='url';

-- -------------------------------------------------
-- jxva framework 系统用户表
-- -------------------------------------------------

create table tbl_user (                
    user_id         integer not null default 0,
	username		varchar(32)	not null,
	password        varchar(32),
	description     varchar(255),
	roles			varchar(1024),
	groups			varchar(1024),
	primary key(user_id)
)engine=innodb default charset=utf8;

create unique index tbl_user_unique on tbl_user(username);

insert into tbl_user values (1,'webmaster','1','系统管理员','1',null);
insert into tbl_user values (2,'tom','tom','超级管理员',null,null);
insert into tbl_user values (3,'jack','jack','b厂管理员',null,null);
insert into tbl_user values (4,'lucy','lucy','a厂营业部经理',null,null);
insert into tbl_user values (5,'lily','lily','a厂营业部文员',null,null);

-- -------------------------------------------------
-- jxva framework 系统角色表(树型结构)
-- -------------------------------------------------

create table tbl_role (                
  	role_id    		integer not null default 0,
	parent_id        integer default 0,
	root_id          integer default 0,
	level_num        integer default 0,
	level_info       varchar(128) not null,
	name			varchar(64)	not null,
	description     varchar(255),
	privileges 		varchar(2048),
	primary key(role_id)
)engine=innodb default charset=utf8;

create  index tbl_role_index on tbl_role(role_id,level_info);

insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description,privileges) values (1,0,1,1,'1','systemadmin','systemadmin','1,2,3,4,5,6,7,8,9,10,11,12,13,14');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (2,1,1,2,'1,2','superadmin','superadmin');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (3,2,1,3,'1,2,3','afactoryadmin','afactoryadmin');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (4,3,1,4,'1,2,3,4','adepartmentadmin','adepartmentadmin');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (5,4,1,5,'1,2,3,4,5','asaleadmin','asaleadmin');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (6,5,1,6,'1,2,3,4,5,6','ateam','ateam');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (7,6,1,7,'1,2,3,4,5,6,7','aclerk','aclerk');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (8,4,1,5,'1,2,3,4,8','acomputer','acomputer');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (9,4,1,5,'1,2,3,4,9','aaudit','aaudit');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (10,4,1,5,'1,2,3,4,10','aproduct','aproduct');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (11,4,1,5,'1,2,3,4,11','afinance','afinance');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (12,2,1,3,'1,2,12','bfactoryadmin','bfactoryadmin');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (13,12,1,4,'1,2,12,13','bdepartmentadmin','bdepartmentadmin');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (14,13,1,5,'1,2,12,13,14','bsaleadmin','bsaleadmin');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (15,13,1,5,'1,2,12,13,15','bcomputer','bcomputer');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (16,13,1,5,'1,2,12,13,16','baudit','baudit');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (17,13,1,5,'1,2,12,13,17','bproduct','bproduct');
insert into tbl_role (role_id,parent_id,root_id,level_num,level_info,name,description) values (18,13,1,5,'1,2,12,13,18','bfinance','bfinance');

-- -------------------------------------------------
-- jxva framework 系统用户组表(树型结构)
-- -------------------------------------------------

create table tbl_group(                
    group_id			integer not null default 0,
	parent_id        integer default 0,
	root_id          integer default 0,
	level_num        integer default 0,
	level_info       varchar(128),
	name		varchar(64)	not null,
	description     	varchar(255),
	roles		varchar(1024),
	primary key(group_id)
)engine=innodb default charset=utf8;

create  index tbl_group_index on tbl_group(group_id,level_info);

insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (1,0,1,1,'1','factorya','factorya');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (2,0,2,1,'2','factoryb','factoryb');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (3,0,3,1,'3','factoryc','factoryc');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (4,0,4,1,'4','factoryd','factoryd');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (5,1,1,2,'1,5','sale','sale');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (6,2,2,2,'2,6','finance','finance');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (7,2,2,2,'2,7','human','human');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (8,3,3,2,'3,8','computer','computer');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (9,3,3,2,'3,9','audit','audit');
insert into tbl_group (group_id,parent_id,root_id,level_num,level_info,name,description) values (10,4,4,2,'4,10','product','product');

-- -------------------------------------------
-- ###########################################
-- -------------------------------------------


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
insert into tbl_blog_category (name,priority) values ('Visual Widgets',16);
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