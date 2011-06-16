package dao;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

public class DaoTest extends TestCase {

	private static final DAOFactory factory=DAOFactory.getInstance("dao.jxva");
	
	protected DAO dao;

	@Before
	public void setUp() {
		dao=factory.createDAO();
	}

	@After
	public void tearDown() {
		dao.close();
	}
}
