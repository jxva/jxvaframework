package demo.dao.other;

import org.jxva.dao.model.Author;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

/**
 * 100个并发,总共1000次调用
 * @author jxva
 * @since 1.0.0
 *
 */
public class MultiThreadTestCreateDao extends Thread{

	private static DAOFactory factory =DAOFactory.getInstance();
	public static void main(String[] args) {
		System.gc();
		long s=System.currentTimeMillis();
		for(int i=0;i<200;i++){
			new MultiThreadTestCreateDao().start();
		}
		long e=System.currentTimeMillis();
		System.out.println(e-s);
	}
	
	public  void run(){
		for(int i=0;i<5;i++){
			DAO dao=factory.createDAO();
			//synchronized(dao){
				Author author=new Author();
				//author.setAuthorId(Long.valueOf(dao.getAutoId(Author.class)).intValue());
				author.setName("fdsfsafsa");
				dao.save(author);
			//}
			//System.out.println(Thread.currentThread().getName());
			//DAOUtil.close();
			dao.close();
		}
	}
}
