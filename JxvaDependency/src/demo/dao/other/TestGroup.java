package demo.dao.other;

import java.util.List;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

public class TestGroup {
	private static final DAOFactory factory=DAOFactory.getInstance();
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			 System.out.println(Thread.currentThread().getId());
		DAO dao=factory.createDAO();
		List<Object[]> list=(List<Object[]>)dao.find("select range from Ring group by range");
		System.out.println(list.size());
		dao.close();
		}
	}
}
