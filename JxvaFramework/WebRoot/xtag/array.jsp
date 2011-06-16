<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.jxva.com/tag" prefix="j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>demo</title>
	<style type="text/css">
		td,body,input{font-size:12px;font-family:arial,sans-serif;line-height:180%;}
	</style>
</head>
<body>
	<table border="1" width="100%" bgcolor="#FFFFFF" cellpadding="1" cellspacing="1">
		<tr>
			<th>Index</th><th>No</th><th>Name1</th><th>Name2</th><th>Name3</th>
		</tr>
		<j:iterate name="list"> 
	    <tr>
	    	<td><j:property value="#index+'x'"/></td>
	         <td><j:property value="list[0]+100"/></td>
	        <td><j:property value="list[1]+'fda'+list[3]"/></td>
	        <td><j:property value="list[2]"/></td>
	         <td><j:property value="list[3]"/></td>
	    </tr>
	    </j:iterate>
	</table>
		<a href="xtag.jv">Index</a><br/>
</body>
</html>