# MySQL-Front Dump 2.2
#
# Host: localhost   Database: javasite
#--------------------------------------------------------
# Server version 4.0.13-nt


#
# Table structure for table 'test'
#

CREATE TABLE `test` (
  `id` bigint(22) unsigned NOT NULL auto_increment,
  `name` varchar(255) default '0',
  `createtime` timestamp(14) NOT NULL,
  `testdate` date default NULL,
  `size` int(10) unsigned default NULL,
  `count` int(10) unsigned default NULL,
  PRIMARY KEY  (`id`),
  KEY `id` (`id`)
) TYPE=MyISAM;



#
# Dumping data for table 'test'
#
INSERT INTO test VALUES("1","≤‚ ‘","20050323160415","2005-03-16","100","0");
INSERT INTO test VALUES("2","Java","20050323160415","2005-03-16","2000","0");
