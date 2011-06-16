package demo.dao.other;

import java.sql.SQLException;

import org.jxva.dao.model.Author;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOException;
import com.jxva.dao.DAOFactory;
import com.jxva.dao.Transaction;

public class TestTrascation {

	private static DAOFactory factory =DAOFactory.getInstance();
	public static void main(String[] args) throws DAOException, SQLException {
		DAO dao=factory.createDAO();
		Transaction tx=null;
		try{
			System.out.println("AAAAAAAAAAA:"+dao.getJdbcTemplate().getConnection().getAutoCommit());
			
			tx=dao.beginTransaction();
			System.out.println("BBBBBBBBBBB:"+dao.getJdbcTemplate().getConnection().getAutoCommit());
			Author author=new Author();
			author.setName("fdsfsafsa");
			dao.save(author);
			//dao.update(author,"fdsfds");
			dao.find("from Author where named='d'");
			tx.commit();
			
		}catch(DAOException e){
			System.out.println("CCCCCCCCCCCC:"+dao.getJdbcTemplate().getConnection().getAutoCommit());
			tx.rollback();
			e.printStackTrace();
		}finally{
			tx=null;
			dao.close();
		}
	}

}
