package org.jxva.dao.test;

import java.lang.reflect.InvocationTargetException;

import org.jxva.dao.model.Book;


public class TestMethodInvoke {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Book book=new Book();
		book.setBookId(1);
		Object obj=book.getClass().getMethod("getName").invoke(book);
		System.out.println(obj);
		System.out.println(book);
		Object obj1=book.getClass().getMethod("setName",java.lang.String.class).invoke(book, "dd");
		System.out.println(obj1);
		System.out.println(book);
	}

}
