<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.jxva.ognl.*" %>
<%

	OgnlUtil ognl=new OgnlUtil(request);

	List<Person> list=new ArrayList<Person>();
	
	for(int i=0;i<20;i++){
		Person p=new Person();
		p.setName("d"+i);
		list.add(p);
	}
	
	Person person=new Person();
	person.setName("测试");
	
	request.setAttribute("person",person);
		
	out.println(ognl.parse("#request['person'].name"));
	
	pageContext.setAttribute("email","jxva@msn.com");
		
	request.setAttribute("ok","哈我晕哈");
	int x=444;

	out.println(ognl.parse("#request.ok+'ssss'+#request['person'].name"));
	
	request.setAttribute("x",x);
	
	out.println(ognl.parse("#request['x']-54465"));
	
	request.setAttribute("list",list);
	
	out.println(ognl.parse("#request['list'][0]"));
	
	out.println(ognl.parse("#request['list'].size+5"));
		
	out.println(ognl.parse("6+#parameter['ok']"));
%>