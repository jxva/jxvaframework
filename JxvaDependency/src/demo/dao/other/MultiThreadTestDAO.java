package demo.dao.other;

import java.util.List;

import org.jxva.dao.model.Book;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;



public class MultiThreadTestDAO extends Thread{

	private static DAOFactory factory =DAOFactory.getInstance();
	public static void main(String[] args) {
		for(int i=0;i<10;i++){ //100个线程并发对数据库进行操作
			new MultiThreadTestDAO().start();
		}
	}
	
	public void run() {
		for(int i=0;i<1;i++){
			DAO dao =factory.createDAO();
			List<Book> list=dao.find(Book.class);
			for(Book a:list){
				//System.out.println(a.getSubject());
			}
			dao.get(Book.class,2);
			//DAOUtil.colseDAO();
			List<Book> list1=(List<Book>)dao.find("from Book b join fetch b.press p");
			dao.close();
			//System.out.println(JDBCConnectionPool.getInstance(factory.getEnvironment()).getStatistics());
		}
	}
}