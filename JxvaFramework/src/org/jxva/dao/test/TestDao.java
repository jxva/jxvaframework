package org.jxva.dao.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jxva.dao.model.Author;
import org.jxva.dao.model.Book;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

public class TestDao {

	private static final DAOFactory factory=DAOFactory.getInstance();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int threadCount=1;
		ExecutorService execute=Executors.newFixedThreadPool(threadCount);
		for(int i=0;i<threadCount;i++){
			execute.submit(new Runnable(){
				public void run(){
					try{
						DAO dao=factory.createDAO();
						System.out.println(dao.find(Author.class).size());
						//System.out.println(dao);
						//dao.close();
						//System.out.println("ddddddddddddddddd");
						DAO dao1=factory.createDAO();
					
						System.out.println(dao.find(Book.class).size());
						
						//System.out.println(dao1);
						dao1.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}
		execute.shutdown();
	}

}
