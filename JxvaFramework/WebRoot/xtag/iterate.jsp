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
			<th>Index</th><th>BookName</th><th>Press</th><th>PressType</th><th>Category</th><th>Description</th><th>Quantity</th><th>Price</th><th>Total</th><th>Hits</th><th>IssuerDate</th><th>CreateTime</th><th>IsCommend</th><th>IsAvailable</th><th>Edit</th><th>Del</th>
		</tr>
		<j:iterate name="list"> 
	    <tr>
	    	<td><j:property value="#index+4"/></td>
	        <td><a href='book!showBook.jv?bookId=<j:property value="bookId"/>'><j:property value="name"/></a></td>
	        <td><a href="press!showPress.jv?pressId=<j:property value="pressId"/>"><j:property value="press.name"/></a></td>
	        <td><a href="pressType!showPressType.jv?pressTypeId=<j:property value="press.pressTypeId"/>"><j:property value="press.pressType.name"/></a></td>
	        <td><a href="category!showCategory.jv?categoryId=<j:property value="categoryId"/>"><j:property value="category.name"/></a></td>
	        <td><j:property value="list.description"/></td>
	        <td><j:property value="quantity"/></td>
	        <td><j:property value="price"/></td>
	        <td><j:property value="list.quantity*list.price" /></td>
	        <td><j:property value="hitCount+4"/></td>
	        <td><j:date value="issuerDate" format="yyyy年MM月dd日"/></td>
	        <td><j:date value="createTime" format="yyyy年MM月dd日 HH时mm分ss秒"/></td>
	        <td>
	        	<j:if test="list.isCommend==0">
	        		否
	        	</j:if>
	        	<j:else>
	        		是
	        	</j:else>
	  
	        	</td>
	     	<td><j:property value="list.isAvailable==0?'否':'是'"/></td>
	        <td><a href='xtag!form.jv?bookId=<j:property value="bookId"/>'>Edit</a></td>
	        <td><a href='book!delBook.jv?bookId=<j:property value="bookId"/>'>Del</a></td>
	    </tr>
	    </j:iterate>
	</table>
		<a href="xtag.jv">Index</a><br/>
	<a href="book!addBook.jv">addBook</a>
</body>
</html>