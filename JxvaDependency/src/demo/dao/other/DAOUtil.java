package demo.dao.other;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

public class DAOUtil {

	public static final ThreadLocal<DAO> thread_var = new ThreadLocal<DAO>();
	
	private static final DAOFactory sessionFactory;
	static{
		sessionFactory=DAOFactory.getInstance();
	}

	public static DAO getDAO() {
		DAO s = thread_var.get();
		// 如果这个线程仍然是空,打开一个新线程
		if (s == null) {
			s = sessionFactory.createDAO();
			// 把Hibernate的Session放入thread_var变量中保存
			thread_var.set(s);
			System.out.println(Thread.currentThread().getName());
		}
		return s;
	}

	public static void colseDAO() {
		DAO s = thread_var.get();
		if (s != null) {
			s.close();
			thread_var.set(null);
			System.out.println(Thread.currentThread().getName());
		}
	}
}