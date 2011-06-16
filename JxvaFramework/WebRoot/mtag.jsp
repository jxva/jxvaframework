<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.mvel2.MVEL,com.jxva.tag.model.*" %>
<%
	request.setAttribute("test","这里啥");

	
	List<Object[]> list=new ArrayList<Object[]>();
	list.add(new Object[]{1,"Java","Php","Asp"});
	list.add(new Object[]{2,"MySQL","Access","SQLServer"});
	list.add(new Object[]{3,"VC++","Delphi","PB"});
	list.add(new Object[]{4,"Eclipse","NetBeans","JBuilder"});
	request.setAttribute("list",list);
	
	out.println(MVEL.eval("getAttribute('test')",request));

	
	List<PressType> list2=new ArrayList<PressType>();
	list2.add(new PressType(1,"教育类"));
	list2.add(new PressType(2,"人文类"));
	list2.add(new PressType(3,"翻译类"));
	request.setAttribute("list2",list2);
	
	out.println(MVEL.eval("getAttribute('list')[3][1].substring(0,3).replaceAll('E','s')",request));
	
	out.println(MVEL.eval("getAttribute('list2')[0].name+getAttribute('test')",request));
%>