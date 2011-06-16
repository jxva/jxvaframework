drop table if exists tbl_node;

-- -------------------------------------------------
-- Jxva Framework系统表
-- -------------------------------------------------

create table tbl_node(
	node_id		    INTEGER         not null,
	parent_id		INTEGER			not null,	
	root_id          INTEGER         not null,
	level_num        INTEGER         not null,
	level_info       VARCHAR(256),
	node_name        VARCHAR(32),
	PRIMARY KEY(node_id,level_info)
)engine=myisam default charset utf8;

CREATE  INDEX level_info ON node(level_info);

insert into tbl_node values (1,0,1,1,',1','软件');
insert into tbl_node values (2,0,2,1,',2','硬件');
insert into tbl_node values (3,2,2,2,',2,3','CPU');
insert into tbl_node values (4,1,1,2,',1,4','网络编程');
insert into tbl_node values (5,3,2,3,',2,3,5','Intel');
insert into tbl_node values (6,2,2,2,',2,6','存储器');
insert into tbl_node values (7,3,2,3,',2,3,7','AMD');
insert into tbl_node values (8,1,1,2,',1,8','数据库');
insert into tbl_node values (9,4,1,3,',1,4,9','ASP');
insert into tbl_node values (10,8,1,3,',1,8,10','Mysql');
insert into tbl_node values (11,6,2,3,',2,6,11','U盘');
insert into tbl_node values (12,8,1,3,',1,8,12','DB2');
insert into tbl_node values (13,4,1,3,',1,4,13','PHP');
insert into tbl_node values (14,6,2,3,',2,6,14','移动盘');
insert into tbl_node values (15,4,1,3,',1,4,15','Java');
insert into tbl_node values (16,15,1,4,',1,4,15,16','Servlet');
insert into tbl_node values (17,15,1,4,',1,4,15,17','JSP');
insert into tbl_node values (18,8,1,3,',1,8,18','access');
insert into tbl_node values (19,15,1,4,',1,4,15,19','EJB');
insert into tbl_node values (20,4,1,3,',1,4,20','NET');
insert into tbl_node values (21,1,1,2,',1,21','可视化编程');
insert into tbl_node values (22,21,1,3,',1,21,22','Delphi');
insert into tbl_node values (23,6,2,3,',2,6,23','Flash盘');
insert into tbl_node values (26,20,1,4,',1,4,20,26','C#');
insert into tbl_node values (30,20,1,4,',1,4,20,30','VB.NET');

