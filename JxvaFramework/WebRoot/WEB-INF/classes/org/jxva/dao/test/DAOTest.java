package org.jxva.dao.test;

import java.util.List;

import org.jxva.dao.model.Author;
import org.jxva.dao.model.Book;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.dao.PageBean;
import com.jxva.dao.Pager;

public class DAOTest {
	
	private static final DAOFactory factory=DAOFactory.getInstance();
	
	public static void main(String[] args){
		DAO dao=factory.createDAO();
		PageBean pageBean=new Pager(1,1);
		List<Book> list=dao.findPageBean("from Book b left join fetch b.press p left join fetch p.pressType pt left join fetch b.category c",pageBean);
		for(Book b:list){
			System.out.println(b.getPress());
		}
		//testAuthor(dao);
		//findByEntity(dao);
		//findByJQL(dao);
		dao.close();
	}

	public static void findBy(DAO dao){
		dao.findPaginated("from Book left join fetch Book.press p left join fetch Book.Category",1,20);
		dao.findPaginated("from Book b left join fetch b.press left join fetch b.Category c",1,20);
		dao.findPaginated("from Book b left join fetch b.press p left join fetch Press.pressType pt",1,7);
		
		dao.findPaginated("from Book b left join fetch b.press left join fetch Book.Category c",1,20);
	}
	
	
	public static void findByEntity(DAO dao){
		List<Book> list=(List<Book>)dao.find("from Book where bookId in (?,?,?) order by bookId desc",new String[]{"3","4","5"});
		for(Book book:list){
			System.out.print(book);
			System.out.print(book.getPress());
			System.out.println(book.getCategory());
		}
	}
	
	public static void findByJQL(DAO dao){
		List<Book> list=(List<Book>)dao.find("from Book b join fetch b.press p join fetch p.pressType c where b.bookId>?",4);
		//List<Book> list=(List<Book>)dao.find("from Book b join fetch b.press p where b.bookId>?",4);
		
		for(Book book:list){
			System.out.print(book);
			System.out.print(book.getPress());
			System.out.println(book.getCategory());
		}
	}
	
	public static void testAuthor(DAO dao){
//		dao.setAtomicity(true);
//		Author author=new Author();
//		author.setName("testdd");
//		System.out.println(dao.save(author));
//		dao.delete(Author.class,"authorId=?",36);
//		dao.find("from Author where authorddId='fdsa'");
		List<Author> list=(List<Author>)dao.find("from Author where authorId>?",30);
		for(Author a:list){
			//System.out.println(a);
		}
		
		Author as=(Author)dao.get("from Author where authorId=?",33);
		System.out.println(as);
		
		Object obj=dao.findUnique("select count(*) as total from Author");
		System.out.println(obj);
		
		PageBean pageBean=new Pager(2,5);
		List<Author> l=dao.findPageBean(Author.class,pageBean);
		for(Author a:l){
			//System.out.println(a);
		}
		System.out.println(pageBean.getTotalCount()+"="+pageBean.getTotalPage());
		
		PageBean pageBean1=new Pager(2,5);
		List<Author> ll=(List<Author>)dao.findPageBean("from Author",pageBean1);
		for(Author a:ll){
			//System.out.println(a);
		}
		System.out.println(pageBean1.getTotalCount()+"="+pageBean1.getTotalPage());
		

		List<Author> l3=(List<Author>)dao.find("from Author where authorId>?",20);
		for(Author a:l3){
			//System.out.println(a);
		}
	}
}
