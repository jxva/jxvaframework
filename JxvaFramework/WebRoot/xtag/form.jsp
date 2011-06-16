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
	<j:form action="book!saveBook.jv" method="post" id="save_form" bean="book">
		<j:hidden name="bookId"/>
		BookName: <j:input name="name" id="name"/><br/>
		Press: <j:select name="pressId" value="${book.pressId}">
			<j:iterate name="pressList">
				<j:option value="${pressList.pressId}"><j:property value="pressType.name"/> - <j:property value="name"/></j:option>
			</j:iterate>
		</j:select><br/>
		Category: <j:select name="categoryId" value="${book.categoryId}">
			<j:iterate name="categoryList">
				<j:option value="${categoryList.categoryId}"><j:property value="name"/></j:option>
			</j:iterate>
		</j:select><br/>
		Quantity: <j:input name="quantity"/><br/>
		Price: <j:input name="price"/><br/>
		Description: <j:textarea name="description" /><br/>
		IsCommend: 
			<j:radio name="isCommend" value="0" checked="true"/> No
			<j:radio name="isCommend" value="1"/> Yes <br/>
		IsAvailable: 
			<j:radio name="isAvailable" value="0" checked="true"/> No
			<j:radio name="isAvailable" value="1"/> Yes <br/>
		IssuerDate: <j:input name="issuerDate"/>
		<j:property value="#request.book.issuerDate"/>
		<br/>
		createTime: <j:property value="#request.book.createTime"/><br/>
		<input type="submit" value="Ok"/>
	</j:form>
	
	<a href="index.jsp">index</a><br/>
	<a href="book!listBook.jv">listBook</a><br/>
</body>
</html>