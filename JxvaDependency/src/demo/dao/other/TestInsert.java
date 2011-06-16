package demo.dao.other;

import org.jxva.dao.model.Author;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

public class TestInsert {

	private static DAOFactory factory =DAOFactory.getInstance();
	public static void main(String[] args) {
		DAO dao=factory.createDAO();

		Author author=new Author();
		author.setName("fdsa");
		
		
		int is=dao.save(author);

			System.out.println(is);

		dao.close();
	}
	
	

}
