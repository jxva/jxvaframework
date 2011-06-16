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
<%
	request.setAttribute("a",34);
	request.setAttribute("b",9);
	request.setAttribute("name","fff");
%>
	<j:property value="#parameter.test"/>
	<j:if test="#parameter.a!=null">
		中国<j:property value="a"/>
	</j:if>
	<br/><br/>
	<j:if test="#request.a-#request.b<0">
		Ok1
	</j:if>
	<j:elseif test="#request.a==#request.b">
		Ok2
	</j:elseif>
	<j:elseif test="#request.a-#request.b==25">
		Ok3
	</j:elseif>
	<j:else>
		Error
	</j:else>
	<br/><br/>
	<j:if test="#request.name == 'Max'">
          Max's file here
    </j:if>
	<j:elseif test="#request.name == 'Scott'">
            Scott's file here
    </j:elseif>
    <j:elseif test="#request.name == 'fff'">
            Scott's file here
    </j:elseif>
	<j:else>
            Other's file here
    </j:else>
</body>
</html>