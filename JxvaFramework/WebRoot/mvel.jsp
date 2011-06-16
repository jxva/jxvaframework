<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.mvel2.MVEL,com.jxva.tag.model.*" %>
<%
	request.setAttribute("test","这里啥");
	session.setAttribute("test","哈哈");
	//pageContext.setAttribute("test","晕死");
	
	Map map=new HashMap();

	
	List<Object[]> list=new ArrayList<Object[]>();
	list.add(new Object[]{1,"Java","Php","Asp"});
	list.add(new Object[]{2,"MySQL","Access","SQLServer"});
	list.add(new Object[]{3,"VC++","Delphi","PB"});
	list.add(new Object[]{4,"Eclipse","NetBeans","JBuilder"});
	request.setAttribute("list",list);
	
	out.println(MVEL.eval("getAttribute('test')",request));
	out.println(MVEL.eval("getAttribute('test')",session));
	//out.println(MVEL.eval("getAttribute('test')",pageContext));
	
	map.put("request",request);
	map.put("session",session);
	//map.put("pageContext",pageContext);
	
	out.println(MVEL.eval("request.getAttribute('test')",map));
	out.println(MVEL.eval("session.getAttribute('test')",map));
	//out.println(MVEL.eval("pageContext.getAttribute('test')",map));
	
	List<PressType> list2=new ArrayList<PressType>();
	list2.add(new PressType(1,"教育类"));
	list2.add(new PressType(2,"人文类"));
	list2.add(new PressType(3,"翻译类"));
	request.setAttribute("list2",list2);
	
	out.println(MVEL.eval("request.getAttribute('list')[3][1].substring(0,3).replaceAll('E','s')",map));
	
	out.println(MVEL.eval("request.getAttribute('list2')[0].name",map));
%>