package com.jxva.demo;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.demo.model.Author;
import com.jxva.demo.model.Book;

public class DaoStudy {
	private static DAOFactory factory;
	public static void main(String[] args) {
		factory=DAOFactory.getInstance();
		DAO dao=factory.createDAO();
		//dao操作
		//插入操作
		Author author=new Author();
		//A使用主键自增
		author.setAuthorid(dao.getAutoid(Author.class).intValue());
		//B或者指定自增字段，指定递增自段为authorid
		//author.setAuthorid(dao.getAutoid(Author.class,"authorid").intValue());
		//C自定义自增字段值(不推荐)
		//author.setAuthorid(10); 
		author.setAuthorname("Martin Fowler");
		dao.insert(author);
		
		Book book=new Book();
		book.setBookid(3);
		book.setAmount(3);
		book.setAuthorid(10);
		book.setBookname("Refactoring");

	}
}
