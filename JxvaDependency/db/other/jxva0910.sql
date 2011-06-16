-- MySQL dump 10.11
--
-- Host: localhost    Database: jxva
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary table structure for view `tbl_article_info`
--

DROP TABLE IF EXISTS `tbl_article_info`;
/*!50001 DROP VIEW IF EXISTS `tbl_article_info`*/;
/*!50001 CREATE TABLE `tbl_article_info` (
  `name` varchar(32),
  `articleid` int(11),
  `catid` int(11),
  `autoid` varchar(20),
  `subject` varchar(64),
  `content` varchar(20480),
  `addtime` datetime,
  `hits` int(11),
  `comments` int(11)
) */;

--
-- Table structure for table `tbl_blog_article`
--

DROP TABLE IF EXISTS `tbl_blog_article`;
CREATE TABLE `tbl_blog_article` (
  `articleid` int(11) NOT NULL auto_increment,
  `catid` int(11) default '0',
  `autoid` varchar(20) default NULL,
  `subject` varchar(64) default NULL,
  `content` varchar(20480) default NULL,
  `addtime` datetime default NULL,
  `hits` int(11) default '0',
  `comments` int(11) default '0',
  PRIMARY KEY  (`articleid`),
  KEY `catid` (`catid`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_blog_article`
--

LOCK TABLES `tbl_blog_article` WRITE;
/*!40000 ALTER TABLE `tbl_blog_article` DISABLE KEYS */;
INSERT INTO `tbl_blog_article` VALUES (1,2,'200809071439064502','What Is the RESTful?','\n<div>&nbsp;&nbsp;&nbsp; REST是一种网络软件架构设计风格。REST认为网络就是一个资源（Resource）的集合。一个用户（或者一个网络应用程序）可以通过\nHTTP协议与某个网络应用程序交互——获取（GET）、更改（UPDATE）、建立（CREATE）或者删除（DELETE）其内部的\nResource。</div>\n<div>&nbsp;</div>\n<div>&nbsp;&nbsp;&nbsp; 什么是Resource呢？发票、订单……任何你在某个应用程序中需要了解和操作的东西。Resource存在于应用程序中，应用程序可以选择以不同的表现形式（Representation）输出Resource——HTML、XML、图形或其他格式。</div>\n<div>&nbsp;</div>\n<div>&nbsp;&nbsp;&nbsp; 这样设计实现起来很简单，不需要发明任何新东西。在整体网络应用的性能上还可以通过对Resource使用通用的HTTP缓存技术进行优化，以较少的网络设施支持更多的用户。<br><br></div>\n\n\n<div>&nbsp;&nbsp;&nbsp; 相比过去的RPC（远程过程调用）、XML Web\nService等等系统间集成方式，REST的抽象方式提供了更好的功能解耦方式，对于应对开发、维护中的需求变化更加敏捷。以资源为中心的设计\n（Resource-centric\nDesign）还可以减少API设计的复杂性，让开发人员以类似的方式调用外部API可以减少学习的复杂度。正是由于这些原因，很多Web\n2.0网站都提供了Restful API作为基于XML Web Service的API的补充或代替。</div>\n\n\n\n','2008-09-07 14:39:06',0,0),(2,10,'200809071441157204','Implement HashMap Using JavaScript','\nfunction HashMap(){<br>&nbsp;&nbsp;&nbsp;&nbsp; this.length = 0;<br>&nbsp;&nbsp;&nbsp;&nbsp; this.entry = new Object();<br>}<br>HashMap.prototype={<br>&nbsp;&nbsp; /**<br>&nbsp;&nbsp;&nbsp; * @param {Object|String|Number|Date} key<br>&nbsp;&nbsp;&nbsp; * @param {Object} value<br>&nbsp;&nbsp;&nbsp; * @param {Number} expire as second unit<br>&nbsp;&nbsp;&nbsp; */<br>&nbsp;&nbsp; put:function (key, value,expire){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(!this.hasKey(key)){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; this.length ++ ;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; expire=typeof expire==\"number\"&amp;&amp;expire&gt;0?expire:-1; //-1 is never expire<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; this.entry[this._getHashCode(key)] ={\"value\":value,\"expire\":expire,\"date\":new Date()};<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; /**<br>&nbsp;&nbsp;&nbsp; * @return null or cache data<br>&nbsp;&nbsp;&nbsp; * @param {Object|String|Number|Date} key<br>&nbsp;&nbsp;&nbsp; * @param {Boolean} anyway if true,will get the data avoid the expire,else will get the data when it is not due to expire;<br>&nbsp;&nbsp;&nbsp; */<br>&nbsp;&nbsp; get:function(key,anyway) {<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var o=this.entry[this._getHashCode(key)];<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(anyway&amp;&amp;this.has(key)){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return o[\"value\"];<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(this.isValid(key)){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return o[\"value\"];<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return null;<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; remove:function(key){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(this.hasKey(key)&amp;&amp;(delete this.entry[this._getHashCode(key)])){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; this.length --;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; hasKey:function(key){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; key=this._getHashCode(key);<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return (key in this.entry);<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; hasValue:function(value) {&nbsp;&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for(var prop in this.entry){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(this.entry[prop] == value){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return true;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return false;<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; values:function() {<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var values = new Array();<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for(var prop in this.entry) {<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; values.push(this.entry[prop]);<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return values;<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; /** all key **/<br>&nbsp;&nbsp; keys:function(){&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var keys = new Array();<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for(var prop in this.entry) {<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; keys.push(prop);<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return keys;<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; getSize:function(){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return this.length;<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; clear:function(){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; this.length = 0;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; this.entry = new Object();<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; contains:function(key){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return this.hasKey(key);<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; containsValue:function(value){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return this.hasValue(value);<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; /**<br>&nbsp;&nbsp;&nbsp; * check data whether is due to expire<br>&nbsp;&nbsp;&nbsp; * @return {Boolean} return true if not expire,else return false; <br>&nbsp;&nbsp;&nbsp; * @param {Object} key<br>&nbsp;&nbsp;&nbsp; */<br>&nbsp;&nbsp; isValid:function(key){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(this.hasKey(key)){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var o=this.entry[this._getHashCode(key)];<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var now=new Date();<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(o[\"expire\"]&lt;0||now.calDateDistance(o[\"date\"],\"ss\")&lt;o[\"expire\"])<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return true;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return false;<br>&nbsp;&nbsp; },<br>&nbsp;&nbsp; _getHashCode:function(obj){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var ret=\"\";<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(typeof obj==\"undefined\"||obj==null)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return ret;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(typeof obj.__hash_code==\"undefined\"||obj==null){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(typeof obj==\"object\"){ //object and array<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for(var i in obj){<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if(typeof obj[i]!=\'function\')ret+=i+obj[i];<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }&nbsp;&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; else //string or number or date except function<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ret=obj;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; obj.__hash_code=ret;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; else<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ret=obj.__hash_code;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return ret;<br>&nbsp;&nbsp; }<br>}<br>&nbsp;\n','2008-09-07 14:41:15',0,0),(3,16,'200809071452326188','IE and Firefox Some Compatible Questions','<span style=\"font-weight: bold;\">innerText</span><br style=\"font-weight: bold;\"><br>function getInnerText(obj){<br>&nbsp;&nbsp;&nbsp; return document.all?obj.innerText:obj.textContent;<br>}<br><br style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">backColor</span><br><br>this.editorFrame.execCommand(document.all?\"backColor\":\"hiliteColor\",A);<br><br><span style=\"font-weight: bold;\">event</span><br><br>var ev=window.event||arguments.callee.caller.arguments[0];<br>\n\n','2008-09-07 14:52:32',0,0),(4,17,'200809071502106804','Exit Stack in the Recursion Using C','#include &lt;stdio.h&gt;<br>main(){<br>&nbsp;&nbsp;&nbsp; char c;<br>&nbsp;&nbsp;&nbsp; if((c=getchar())!=\'\\n\')<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; main();<br>&nbsp;&nbsp;&nbsp; putchar(c);<br>} &nbsp;<br><br>','2008-09-07 15:02:10',0,0),(5,11,'200809071506188883','Some Common Skills on the Windows Platform','<span style=\"font-weight: bold;\">解决Win2K3搜索文件内容的功能限制</span><br style=\"font-weight: bold;\"><br>HKEY_LOCAL_MACHINE\\SYSTEM\\ControlSet\\Control\\ContentIndex&nbsp;&nbsp; \n<br>右侧FilterFilesWithUnknownExtensions子键（REG_DEWORD类型）的键值改为1<br><br><br>','2008-09-07 15:06:18',0,0),(6,16,'200809071701163196','DOM(document object module)','\n\n<div id=\"blogContainer\" style=\"font-size: 12px;\"><font style=\"line-height: 1.3em; font-weight: bold;\">&nbsp; 节点：元素节点，文本节点，属性节点</font><br><font style=\"line-height: 1.3em; font-weight: bold;\">&nbsp;&nbsp;一. \n处理DOM中的节点</font><wbr style=\"font-weight: bold;\"><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;1. \n直接引用节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n(1)使用document.getElementById(...)引用指定id的节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n(2)使用document.getElementByTagName(...)引用指定标记名称的节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;2. 间接引用节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (1)引用字节点(childNodes)</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n每个节点都有一个childNodes集合属性，类型为数组对象，表示该节点的所有字节点的集合</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n如：document.childNodes[0];</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;element.firstChild;//第一个字节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;element.lastChild;//最后一个字节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (2) 引用父节点(parentNode)</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;element.parentNode;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (3) 引用兄弟节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;element.nextSibling;//引用下一个兄弟节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;element.previousSibling;//引用上一个兄弟节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果没有相应的兄弟节点，返回null.也可以利用parentNode和childNodes属性实现兄弟节点的相互引用。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;3. 获取节点的信息</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (1) 使用nodeName 属性获取节点的名称</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Node.nodeName;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 对不同的节点类型，返回值不同</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 元素节点：标记的名称。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 属性节点：属性的名称。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 文本节点：文本的内容。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (2) 使用nodeType属性获取节点的类型</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Node.nodeType;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n对不同的节点类型，以数字的形式返回他们的类型</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 元素节点：1。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 属性节点：2。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 文本节点：3。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (3) 使用nodeValue获取节点的值</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Node.nodeValue;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 对不同的节点类型，返回值不同</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 元素节点：null。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 属性节点：undefined。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 文本节点：文本的内容。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (4) \n使用hasChildNodes()方法判断给定节点是否有字节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Node.hasChildNodes();</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n(5)使用tagName属性返回元素节点的标记名称</font><wbr><br><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;4. 处理属性节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (1) 获取和设定属性节点的值</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 元素节点.属性名称</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (2) \n使用setAttribute()方法添加一个属性</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n原型：elementNode.setAttribute(attributeName,attributeValue);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; elementNode \n要添加属性的节点。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \nattributeName 要添加属性的名称。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \nattributeValue要添加属性的值。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n(3) 使用getAttribute()方法获取一个属性值</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \nelementNode.getAttribute(attributeName);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; elementNode \n要获取属性的节点。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \nelementName 要获取值的属性的名称。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;5. \n处理文本节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n获取节点的文本，通常使用innerHTML属性，如：</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;span id \n=\"span1\"&gt;hello&lt;/span&gt;</font><wbr><br><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \ndocument.getElementById(\"span1\").innerHTML;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \ndocuemtn.getElementById(\"span1\").childNodes[0].nodeValue;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;6. 因浏览器而异的空白节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文本仅仅由空格，换行，制表符等特殊的文本字符组成，IE浏览器和Firefox浏览器就会产生分歧。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;script language=\"JavaScript \ntype=\"text/javascript\"&gt;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;!--</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;function \ncleanWhitespace(element){</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //遍历element的字节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for(var \ni=0;i&lt;element.childNodes.length;i++){</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var node = \nelement.childNodes[i];</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n//判断是否是空白文本节点，如果是，则删除该节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \nif(node.nodeType==3&amp;&amp;!/\\s/.test(node.nodeValue))</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \nnode.parentNode.removeChild(node);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//--&gt;</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/script&gt;</font><wbr><br><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;7.使用innerHTML改变节点的内容</font><wbr><br><br><font style=\"line-height: 1.3em; font-weight: bold;\">二 改变文档的层次结构</font><wbr style=\"font-weight: bold;\"><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp; 1. 创建元素节点(createElement)</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \ndocument.createElement(elementName);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; elementName所要创建节点的标记名称</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp; 2. 创建文本节点(createTextNode)</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \ndocument.createTextNode(text);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; text要创建的文本节点中的文本值</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp; 3. 添加节点(appendChild)</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \nparentElement.appendChild(childElement);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n其中parentElement是父节点的引用，childElement是要添加的字节点的引用，返回对新节点的引用。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 添加到所有子节点的后面。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp; 4. \n插入字节点（insertBefore)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parentNode.insertBefore(newNode,referenceNode);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parentNode \n是父节点，newNode是待插入的新节点，referenceNode是父节点中已存在的节点，新节点将插入此节点之前，返回新节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;的引用。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp; 5. 取代字节点（replaceChild）</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parentNode.replaceChild(newNode,oldNode);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parentNode是父节点，newNode是待插入的节点，oldNode是被取代的节点，返回被取代了的节点。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;参数oldNode必须已经是parentNode的字节点，否则将引起异常。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp; 6. 复制节点（cloneNode）</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;node.cloneNode(includChildren);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;node \n是待复制的节点，includechildren是布尔值，表示是否复制字节点，返回值是复制得到的新节点。</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp; 7. 删除字节点</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parentNode.removeChild(childNode);</font><wbr><br><font style=\"line-height: 1.3em;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;parentNode是父接待你，childNode是待删除的字节点，返回被删除的字节点的引用。&nbsp;&nbsp;&nbsp;&nbsp; \n</font><wbr></div><img id=\"paperPicArea1\" style=\"display: none; position: relative;\" src=\"http://imgcache.qq.com/ac/b.gif\">\n\n\n','2008-09-07 17:01:16',0,0),(7,16,'200809071707083648','Operate XML Document Using JavaScript ','var xmlDoc;<br>if (window.ActiveXObject){ <br>&nbsp;&nbsp;xmlDoc=new \nActiveXObject(\"Microsoft.XMLDOM\");<br>}else{<br>&nbsp;&nbsp;if(document.implementation&amp;&amp;document.implementation.createDocument){<br>&nbsp;&nbsp; \nxmlDoc=document.implementation.createDocument(\"\",\"doc\",null);<br>&nbsp;&nbsp; \nxmlDoc.preserveWhiteSpace=true;<br>&nbsp;&nbsp;}<br>}<br>xmlDoc.async = \nfalse;<br>&nbsp;&nbsp;<br>xmlDoc.load(\"menu.xml\");<br>//var \ndomroot=xmlDoc.documentElement;<br>var xmlnode = \nxmlDoc.getElementsByTagName(\"rs\");<br><br>for(var \ni=0;i&lt;xmlnode.length;i++){<br>&nbsp;&nbsp;sb.append (\"&lt;li&gt;&lt;a \nhref=\'\"+xmlnode[i].getElementsByTagName(\"item\")[0].getAttribute(\"href\")+\"\'&gt;&lt;img \nsrc=\'\"+xmlnode[i].getElementsByTagName(\"item\")[0].getAttribute(\"src\")+\"\' \nborder=0&gt;&lt;/a&gt;\");<br>&nbsp;&nbsp;<br>&nbsp;&nbsp;var \nXmlRs=xmlnode[i].getElementsByTagName(\"citem\");<br>&nbsp;&nbsp;if(XmlRs.length&gt;0){<br>&nbsp;&nbsp; \nsb.append(\"&lt;ul&gt;\");<br>&nbsp;&nbsp; for(var j=0;j&lt;XmlRs.length;j++)<br>&nbsp;&nbsp; \n{<br>&nbsp;&nbsp;&nbsp;&nbsp;sb.append (\"&lt;li&gt;&lt;a \nhref=\'\"+XmlRs[j].getAttribute(\"href\")+\"\'&gt;\"+XmlRs[j].firstChild.nodeValue+\"&lt;/a&gt;&lt;/li&gt;\");<br>&nbsp;&nbsp; \n}<br>&nbsp;&nbsp; sb.append (\"&lt;div \nid=\'last\'&gt;&lt;/div&gt;&lt;/ul&gt;\");<br>&nbsp;&nbsp;}<br>&nbsp;&nbsp;sb.append(\"&lt;/li&gt;\");<br>}','2008-09-07 17:07:08',0,0),(8,16,'200809071708309404','Get Object\'s Position Using JavaScript','\nfunction&nbsp;&nbsp; getAbsPoint(obj)&nbsp;&nbsp; <br>{&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;//var \nobj=document.getElementById(id);<br>&nbsp;&nbsp;&nbsp;&nbsp;//获取事件的位置<br>&nbsp;&nbsp;&nbsp;&nbsp;//var \ne=window.event||arguments.callee.caller.arguments[0];<br>&nbsp;&nbsp;&nbsp;&nbsp;//var \nelm=e.srcElement||e.target;<br>&nbsp;&nbsp;&nbsp;&nbsp;//var x=e.clientX;<br>&nbsp;&nbsp;&nbsp;&nbsp;//var \ny=e.clientY;<br>&nbsp;&nbsp;&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;//获取对象的宽与高<br>var obj_w=obj.offsetWidth;<br>var \nobj_h=obj.offsetHeight;<br><br>//获得屏幕的分辨率<br>var scr_w=screen.width;&nbsp;&nbsp; \n<br>&nbsp;&nbsp;&nbsp;&nbsp;var \nscr_h=screen.height;<br>&nbsp;&nbsp;&nbsp;&nbsp;alert(scr_w+\"=\"+scr_h);<br><br>//获取body内容的宽与高<br>var \nbody_w=document.body.clientWidth;<br>var \nbody_h=document.body.clientHeight;<br>//alert(body_w+\"=\"+body_h);<br><br>//获取body内容的宽与高（包含Border、Scroll等元素）<br>//var \nbody_w1=document.body.offsetWidth;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;//var \nbody_h1=document.body.offsetHeight;<br>&nbsp;&nbsp;&nbsp;&nbsp;//alert(body_w1+\"=\"+body_h1);<br>&nbsp;&nbsp;&nbsp;&nbsp; \n<br>&nbsp;&nbsp;&nbsp;&nbsp;//获取对象的位置<br>&nbsp;&nbsp;&nbsp;&nbsp;var left_offset = obj.offsetLeft;<br>var top_offset = \nobj.offsetTop;<br>while((obj = obj.offsetParent) != null) {<br>&nbsp;&nbsp;left_offset += \nobj.offsetLeft;<br>&nbsp;&nbsp;top_offset += obj.offsetTop;<br>}<br>&nbsp;&nbsp;<br>var x = \nleft_offset;<br>var y = top_offset;<br><br>&nbsp;&nbsp;&nbsp;&nbsp; <br>&nbsp;&nbsp;&nbsp;&nbsp;var \nm=document.createElement(\'div\');<br>&nbsp;&nbsp;&nbsp;&nbsp;m.style.cssText=\"position:absolute;left:\"+x+\"px;top:\"+(y+obj_h)+\"px;width:100px;height:100px;background:red;\";<br>&nbsp;&nbsp;&nbsp;&nbsp;m.innerHTML=\"fdsfasfdsafdsa\";<br>&nbsp;&nbsp;&nbsp;&nbsp;document.body.appendChild(m);<br>}&nbsp; <br>\n','2008-09-07 17:08:30',0,0),(9,1,'200809101306409684','Reading is a Enjoyment','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 或许我对我自身生命周期有预知，以致于我非常吝啬我的时间，任何我觉得需要浪费我时间的事情我都会极不情愿地参与。 <br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 我总是在我困累的时候确实需要休息了，才会上床入睡，我不会轻意的将我拥有能够正常学习、工作精力的时间浪费在睡觉之上，这也导致我经常困累。 <br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 晚上，一点半，我醒了，感觉自身的精力可以不再需要以睡觉的方式来补充，索性拿起刚买的新书看了看，往往夜越深给人的感觉就越安静，正好是我精力集中发挥的时候，一直以来我都喜欢这种在深夜起床工作或读书的感觉，很大程度上与我的工作及性格有关。 <br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 读的是一本《Effective C++ Third Edition》，很早就想拜读这本书了（其实很早之前就有了这书电子版，我拥有大量的电子版书籍，对于电子版我经常只是下载下来超高速的浏览一下大概内容，以决定我是否需要或值得以购买书籍的方式来阅读）由于工作性质的原因一直未买，我喜欢在拿到任何我喜欢的书籍时，都全部通读一遍，虽然这种通读有可能是一目十行或更多，但我也不会放过任一页，读了一下序言及前面两个与最后三个改善程序与设计的做法，内容很简单但很实用，所以阅读起来很顺利，相信我对 C++的深入理解将在几天的时间内大饱一餐，以快速充实自我精神食粮，即时这种快速阅读会让我快速忘缺，总比在高速信息化及知识高速大爆炸的时代慢慢的阅读而获取知识来得快，因此我经常需要对以往读过的书籍重复地通读多遍，因为快速阅读的结果使我对深入知识的理解总是不记得那么确切，最现实的例子就是设计模式书籍的阅读。但是这种快速的感觉我喜欢。 <br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 在我认为时间花在读对自身有用之书上不能算是浪费，当然还有更多需要花费时间而不能算做浪费时间的事情。但是如果你有具备不以睡觉的方式来补充精力的时候，我建议你读下书，因为读书其实就是一种享受。<br><br>','2008-09-10 13:06:40',0,0),(10,16,'200809101316019783','Speed up the Firefox\'s Start Speed','在火狐地址栏中输入： about:config进入配置<br>1.限制页面快进/快退功能中保存的页面总数(默认值是-1表示无限页)：在过滤器栏输入：browser.sessionhistory.max_total_viewers，双击该项，修改值为5或更小。<br>可以工具您的内存来设置该参数，参考值如下：<br>32MB 0 64MB 1 128MB 2 256MB 3 512MB 5 1GB 8<br>2.让火狐在最小化时自动释放内存：右击鼠标－新建－布尔（boolean)项，输入：config.trim_on_minimize,并设置为true。<br>做了这两个设置后火狐速度就快多了，在开了一排页面的情况下，也就23M的内存占用,最小化后只占5M内存。这里我设置值为是0。<br>3.禁用ipv6解析：找到network.http.proxy.pipelining，点击右键，“切换”其值成 true，找到network.dns.disableIPv6 ，右键，切换，值变为true。<br>5.让火狐在后台打开新页面，您继续浏览当前页：找到browser.tabs.loadDivertedInBackground的值切换true。<br>6.让火狐一次发送多个个请求：找到network.http.pipelining，点击右键，“切换”其值成 true，找到network.http.pipelining.maxrequests并把它的值改的高一些，例如改成30(请求个数)。<br>8.让火狐快速对网站回复信息反应：点右键，选择 “新建”—“整数”在弹出的对话框中输入 nglayout.initialpaint.delay并将其值改为“0”。<br>9.关闭自动更新检测选项\n\n','2008-09-10 13:16:01',0,0),(11,6,'200809101744169782','Server Side Include in GlassFish','<p>\n                            Server Side Include (SSI)\nallows including dynamic contents in html.\nSSI and CGI were very popular before the the appearance of JSP and PHP.\nThe SSI code in GlassFish\nworkspace is based on Tomcat.\nIn GlassFish v3, SSI will be a supported feature.\nLet us look at a very simple example.\n</p><p>\n</p><h3>Create a SSI file</h3>\nIn our example, we create a <code>index.shtml</code> which\n<ul><li>includes the content of <code>header.html</code>,\n  </li><li>prints a Hello message with server side timestamp, and\n  </li><li>executes a command say, uname (or any command in your operating system).\n</li></ul>\nThe page is as follows:\n<p><code>\n &nbsp;&nbsp;&nbsp; &lt;!--#include virtual=\"header.html\"--&gt;\n <br>&nbsp;&nbsp;&nbsp; &lt;br&gt;Hello, it is &lt;!--#echo var=\"DATE_LOCAL\"--&gt;.\n <br>&nbsp;&nbsp;&nbsp; &lt;br&gt;Result: &lt;!--#exec cmd=\"uname\"--&gt;\n</code>\n</p><p>Note that the extension <code>shtml</code> is configurable\nin <code>web.xml</code> (see <code>servlet-mapping</code> below).\n\n</p><h3>Enable SSI processing</h3>\n<p>The SSI processing can be enabled in a war file by adding\nSSIServlet et al in <code>web.xml</code> as follows:\n</p><p><code>\n&nbsp; &lt;web-app&gt;\n<br>&nbsp;&nbsp;&nbsp; &lt;servlet&gt;\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;servlet-name&gt;ssi&lt;/servlet-name&gt;\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;servlet-class&gt;org.apache.catalina.ssi.SSIServlet&lt;/servlet-class&gt;\n<br>&nbsp;&nbsp;&nbsp; &lt;/servlet&gt;\n<br>\n<br>&nbsp;&nbsp;&nbsp; &lt;servlet-mapping&gt;\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;servlet-name&gt;ssi&lt;/servlet-name&gt;\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;url-pattern&gt;*.shtml&lt;/url-pattern&gt;\n<br>&nbsp;&nbsp;&nbsp; &lt;/servlet-mapping&gt;\n<br>\n<br>&nbsp;&nbsp;&nbsp; &lt;mime-mapping&gt;\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;extension&gt;shtml&lt;/extension&gt;\n<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;mime-type&gt;text/html&lt;/mime-type&gt;\n<br>&nbsp;&nbsp;&nbsp; &lt;/mime-mapping&gt;\n<br>&nbsp; &lt;/web-app&gt;\n</code>\n</p><p>\nOne can find more details about the configuration of SSIServlet in\nthe section below.\nAlternatively, one can enable SSI by uncommenting the corresponding sections\nin <code>default-web.xml</code>.\n</p><p>\nNote that the <code>mime-mapping</code> is to notify the browser\nthat the result of <code>shtml</code> file is of <code>content-type: text/html</code>.\nIf you don\'t specify this, then GlassFish will try to get the\n<code>mime-type</code> from <code>default-web.xml</code> or\nthe default in the system.\n\n</p><h3>Configuration of SSIServlet</h3>\n<p>One can configure SSIServlet by specifying the <code>init-param</code> as follows:\n</p>\n  <table border=\"1\"><tbody><tr><th>init-param</th><th>Type</th><th>Description</th><th>Default\n  </th></tr><tr><td>buffered</td><td>boolean (or String converted to boolean)</td><td>whether the output should be buffered</td><td>false\n  </td></tr><tr><td>debug</td><td>int</td><td>represents debug level</td><td>0 (no debug)\n  </td></tr><tr><td>expires</td><td>Long</td><td>expiration time in seconds</td><td>do not set \"Expires\" header in Http Response\n  </td></tr><tr><td>inputEncoding</td><td>String</td><td>encoding for SSI input if there is no URL content encoding specified</td><td>server platform encoding\n  </td></tr><tr><td>isVirtualWebappRelative</td><td>boolean (or String converted to boolean)</td><td>whether the \"virtual\" path of \"#include\" directive is relative to content-root</td><td>false (means relative to the given SSI file)\n  </td></tr><tr><td>outputEncoding</td><td>String</td><td>encoding for SSI output</td><td>UTF-8\n</td></tr></tbody></table>\n\n','2008-09-10 17:44:16',0,0);
/*!40000 ALTER TABLE `tbl_blog_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_blog_category`
--

DROP TABLE IF EXISTS `tbl_blog_category`;
CREATE TABLE `tbl_blog_category` (
  `catid` int(11) NOT NULL auto_increment,
  `name` varchar(32) default NULL,
  `priority` int(11) default '0',
  `amount` int(11) default '0',
  PRIMARY KEY  (`catid`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_blog_category`
--

LOCK TABLES `tbl_blog_category` WRITE;
/*!40000 ALTER TABLE `tbl_blog_category` DISABLE KEYS */;
INSERT INTO `tbl_blog_category` VALUES (1,'Personal Diary',160,1),(2,'Focusing Technologes',150,1),(3,'Favorite Informations',140,0),(4,'Design Patterns',130,0),(5,'Refactoring',120,0),(6,'Open Sources',110,1),(7,'Java Technologes',100,0),(8,'ISO C/C++',90,0),(9,'Linux/Unix',80,0),(10,'Script Languages',70,1),(11,'Operate Systems',60,1),(12,'Database Systems',50,0),(13,'Authentication Design',40,0),(14,'Visual Widgets',30,0),(15,'Windows Program',20,0),(16,'Web Development',10,5),(17,'Data Structure',115,1),(18,'Algorithms',114,0);
/*!40000 ALTER TABLE `tbl_blog_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_blog_comment`
--

DROP TABLE IF EXISTS `tbl_blog_comment`;
CREATE TABLE `tbl_blog_comment` (
  `commentid` int(11) NOT NULL auto_increment,
  `articleid` int(11) default '0',
  `information` blob,
  `posttime` datetime default NULL,
  `ip` varchar(16) default NULL,
  `os` varchar(128) default NULL,
  `nickname` varchar(32) default NULL,
  `email` varchar(64) default NULL,
  PRIMARY KEY  (`commentid`),
  KEY `articleid` (`articleid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_blog_comment`
--

LOCK TABLES `tbl_blog_comment` WRITE;
/*!40000 ALTER TABLE `tbl_blog_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_blog_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_guestbook`
--

DROP TABLE IF EXISTS `tbl_guestbook`;
CREATE TABLE `tbl_guestbook` (
  `msgid` int(11) NOT NULL auto_increment,
  `content` varchar(1024) default NULL,
  `ip` varchar(16) default NULL,
  `os` varchar(512) default NULL,
  `addtime` datetime default NULL,
  `isreply` int(11) default '0',
  `replycontent` varchar(1024) default NULL,
  `replytime` datetime default NULL,
  PRIMARY KEY  (`msgid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_guestbook`
--

LOCK TABLES `tbl_guestbook` WRITE;
/*!40000 ALTER TABLE `tbl_guestbook` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_guestbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_increment`
--

DROP TABLE IF EXISTS `tbl_increment`;
CREATE TABLE `tbl_increment` (
  `pktb` varchar(64) NOT NULL,
  `pk` int(11) NOT NULL default '0',
  PRIMARY KEY  (`pktb`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_increment`
--

LOCK TABLES `tbl_increment` WRITE;
/*!40000 ALTER TABLE `tbl_increment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_increment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_music_album`
--

DROP TABLE IF EXISTS `tbl_music_album`;
CREATE TABLE `tbl_music_album` (
  `albumid` int(11) NOT NULL auto_increment,
  `name` varchar(32) default NULL,
  `amount` int(11) default '0',
  PRIMARY KEY  (`albumid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_music_album`
--

LOCK TABLES `tbl_music_album` WRITE;
/*!40000 ALTER TABLE `tbl_music_album` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_music_album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_music_info`
--

DROP TABLE IF EXISTS `tbl_music_info`;
CREATE TABLE `tbl_music_info` (
  `musicid` int(11) NOT NULL auto_increment,
  `albumid` int(11) default '0',
  `name` varchar(64) default NULL,
  `url` varchar(256) default NULL,
  `addtime` datetime default NULL,
  `hits` int(11) default '0',
  PRIMARY KEY  (`musicid`),
  KEY `albumid` (`albumid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_music_info`
--

LOCK TABLES `tbl_music_info` WRITE;
/*!40000 ALTER TABLE `tbl_music_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_music_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_photo_album`
--

DROP TABLE IF EXISTS `tbl_photo_album`;
CREATE TABLE `tbl_photo_album` (
  `albumid` int(11) NOT NULL auto_increment,
  `name` varchar(32) default NULL,
  `item` int(11) default '0',
  `amount` int(11) default '0',
  PRIMARY KEY  (`albumid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_photo_album`
--

LOCK TABLES `tbl_photo_album` WRITE;
/*!40000 ALTER TABLE `tbl_photo_album` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_photo_album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_photo_info`
--

DROP TABLE IF EXISTS `tbl_photo_info`;
CREATE TABLE `tbl_photo_info` (
  `photoid` int(11) NOT NULL auto_increment,
  `albumid` int(11) default '0',
  `title` varchar(64) default NULL,
  `addtime` datetime default NULL,
  `hits` int(11) default '0',
  `width` int(11) default '0',
  `height` int(11) default '0',
  `size` int(11) default '0',
  `url` varchar(32) default NULL,
  PRIMARY KEY  (`photoid`),
  KEY `albumid` (`albumid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_photo_info`
--

LOCK TABLES `tbl_photo_info` WRITE;
/*!40000 ALTER TABLE `tbl_photo_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_photo_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `userid` int(11) NOT NULL auto_increment,
  `username` varchar(32) default NULL,
  `password` varchar(32) default NULL,
  `addtime` datetime default NULL,
  `email` varchar(64) default NULL,
  `ip` varchar(15) default NULL,
  `os` varchar(255) default NULL,
  PRIMARY KEY  (`userid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `tbl_article_info`
--

/*!50001 DROP TABLE IF EXISTS `tbl_article_info`*/;
/*!50001 DROP VIEW IF EXISTS `tbl_article_info`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`ztemt`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `tbl_article_info` AS select `tbl_blog_category`.`name` AS `name`,`tbl_blog_article`.`articleid` AS `articleid`,`tbl_blog_article`.`catid` AS `catid`,`tbl_blog_article`.`autoid` AS `autoid`,`tbl_blog_article`.`subject` AS `subject`,`tbl_blog_article`.`content` AS `content`,`tbl_blog_article`.`addtime` AS `addtime`,`tbl_blog_article`.`hits` AS `hits`,`tbl_blog_article`.`comments` AS `comments` from (`tbl_blog_category` join `tbl_blog_article`) where (`tbl_blog_category`.`catid` = `tbl_blog_article`.`catid`) */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2008-09-10 10:04:47
