<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.jxva.com/tag" prefix="j"%>

<j:property value="#request.book.name"/><br/>
<j:property value="#request.book.name.substring(3)"/><br/>
<j:property value="#request.book.category.name"/><br/>
<j:property value="#request.book.press.pressType.name"/><br/>
<j:property value="#request['info']"/><br/>
<br/>
<j:property value="#request['num1']"/>
<br/>
<j:property value="#request.num2+#request['num1']"/>
<br/>
<j:property value="#request.book.quantity*#request['book'].price"/>