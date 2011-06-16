/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jxva.demo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jxva.tag.model.Book;
import org.jxva.tag.model.Category;
import org.jxva.tag.model.Press;
import org.jxva.tag.model.PressType;

import com.jxva.mvc.Action;
import com.jxva.mvc.Forward;
import com.jxva.mvc.Template;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-07-23 17:03:44 by Jxva
 */
public class MTagAction extends Action{
	
	@Template(value="/tpl/template.tpl",cache=60)
	public void template(){
		request.setAttribute("title","标题");
		request.setAttribute("content","内容");
		request.setAttribute("list",findBooks());
		System.out.println("执行..");
	}

	@Forward(value="/mvel/index.jsp",cache=60)
	public String execute(){
		return SUCCESS;
	}
	
	@Forward(value="/mvel/array.jsp",cache=60)
	public void array(){
		List<Object[]> list=new ArrayList<Object[]>();
		list.add(new Object[]{1,"Java","Php","Asp"});
		list.add(new Object[]{2,"MySQL","Access","SQLServer"});
		list.add(new Object[]{3,"VC++","Delphi","PB"});
		list.add(new Object[]{4,"Eclipse","NetBeans","JBuilder"});
		request.setAttribute("list",list);
	}
	
	@Forward("/mvel/condition.jsp")
	public void condition(){

	}

	@Forward(value="/mvel/iterate.jsp",cache=3)
	public void iterate(){
		request.setAttribute("list",findBooks());
	}
	
	@Forward("/mvel/property.jsp")
	public void property(){
		request.setAttribute("info","this is test info.");
		request.setAttribute("email","jxva@msn.com");
		request.setAttribute("num1",32.22);
		request.setAttribute("num2",23);
		request.setAttribute("book",findBooks().get(0));
	}
	
	@Forward(value="/mvel/form.jsp",cache=60)
	public void form(){
		int bookId=form.getIntParam("bookId");
		request.setAttribute("categoryList",findCategorys());
		request.setAttribute("pressList",findPress());
		request.setAttribute("book",findBooks().get(bookId-1));
	}
	
	private List<Category> findCategorys(){
		List<Category> list=new ArrayList<Category>();
		Category category=new Category();
		category.setCategoryId(1);
		category.setName("程序语言");
		category.setDescription("程序开发语言类");
		list.add(category);
		
		category=new Category();
		category.setCategoryId(2);
		category.setName("手机");
		category.setDescription("手机开发类");
		list.add(category);
		
		category=new Category();
		category.setCategoryId(3);
		category.setName("开发工具");
		category.setDescription("IDE开发工具");
		list.add(category);
		
		return list;
	}
	
	private List<Press> findPress(){
		List<Press> list=new ArrayList<Press>();
		List<PressType> pressTypes=findPressTypes();
		Press press=new Press();
		press.setPressId(1);
		press.setName("清华大学出版社");
		press.setPressTypeId(1);
		press.setPressType(pressTypes.get(0));
		list.add(press);
		
		press=new Press();
		press.setPressId(2);
		press.setName("科学技术出版社");
		press.setPressTypeId(2);
		press.setPressType(pressTypes.get(1));
		list.add(press);
		
		press=new Press();
		press.setPressId(3);
		press.setName("上海外文出版社");
		press.setPressTypeId(3);
		press.setPressType(pressTypes.get(2));
		list.add(press);
		
		return list;
	}
	
	private List<PressType> findPressTypes(){
		List<PressType> list=new ArrayList<PressType>();
		list.add(new PressType(1,"教育类"));
		list.add(new PressType(2,"人文类"));
		list.add(new PressType(3,"翻译类"));
		return list;
	}
	
	private List<Book> findBooks(){
		List<Press> presses=findPress();
		List<Category> categorys=findCategorys();
		
		List<Book> list=new ArrayList<Book>();
		Book book=new Book();
		book.setBookId(1);
		book.setName("Java 入门");
		
		book.setCategory(categorys.get(0));
		
		book.setCategoryId(1);
		book.setCreateTime(Timestamp.valueOf("2009-06-30 14:25:34"));
		book.setDescription("这书很不错!");
		book.setHitCount(256);
		book.setIsAvailable((short)1);
		book.setIsCommend((short)1);
		book.setIssuerDate(Date.valueOf("2009-07-01"));
		
		book.setPress(presses.get(0));
		
		book.setPressId(1);
		book.setPrice(BigDecimal.valueOf(45.24));
		book.setQuantity(10);
		list.add(book);
		
		book=new Book();
		book.setBookId(2);
		book.setName("Android 开发");
		

		book.setCategory(findCategorys().get(1));
		
		book.setCategoryId(2);
		book.setCreateTime(Timestamp.valueOf("2009-07-01 09:15:57"));
		book.setDescription("Google的书!");
		book.setHitCount(765);
		book.setIsAvailable((short)1);
		book.setIsCommend((short)0);
		book.setIssuerDate(Date.valueOf("2009-06-30"));
		
		book.setPress(presses.get(1));
		book.setPressId(2);
		
		book.setPrice(BigDecimal.valueOf(55.34));
		book.setQuantity(5);
		list.add(book);
		
		book=new Book();
		book.setBookId(3);
		book.setName("Eclipse精通");
		

		book.setCategory(findCategorys().get(2));
		book.setCategoryId(3);
		book.setCreateTime(Timestamp.valueOf("2009-07-02 21:15:37"));
		book.setDescription("开源IDE");
		book.setHitCount(64);
		book.setIsAvailable((short)0);
		book.setIsCommend((short)1);
		book.setIssuerDate(Date.valueOf("2009-07-02"));
		
		book.setPress(presses.get(2));
		book.setPressId(3);
		book.setPrice(BigDecimal.valueOf(78.56));
		book.setQuantity(20);
		list.add(book);
		
		return list;
	}

}
