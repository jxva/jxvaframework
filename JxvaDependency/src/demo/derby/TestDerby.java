package demo.derby;

import java.util.List;

import javax.sql.rowset.JdbcRowSet;


import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.sun.rowset.JdbcRowSetImpl;

import demo.derby.model.Url;


public class TestDerby {

	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver"; //$NON-NLS-1$

	public static final String DERBY_CREATE = "jdbc:derby:TESTDB;create=true"; //$NON-NLS-1$

	public static final String DERBY_URL = "jdbc:derby://127.0.0.1/jxva"; //$NON-NLS-1$

	public static final String TABLE_CUSTOMERS = "CUSTOMERS"; //$NON-NLS-1$

	public static final String SQL_SELECT_CUSTOMERS = "SELECT * FROM TBL_URL"; //$NON-NLS-1$
    
    public static final String SQL_SELECT_ORDERS = "SELECT * FROM ORDERS"; //$NON-NLS-1$
	
	private static final DAOFactory factory=DAOFactory.getInstance("test.derby.jxva");
	
	private static  void useDao(){
		DAO dao=factory.createDAO();
		Url u=new Url();
		u.setUrlId(9);
		u.setHref("fdsa");
		u.setDescription("fdsafdsa");
		dao.save(u);
		List<Url> list=dao.find(Url.class);
		for(Url url:list){
			System.out.println(url.getHref()+"="+url.getDescription());
		}
		dao.close();
	}
	
	private static void useJDBC()throws Exception{
		JdbcRowSet jrs = new JdbcRowSetImpl();
		jrs.setCommand(SQL_SELECT_CUSTOMERS);
		jrs.setUrl(DERBY_URL);
		jrs.execute();

		while (jrs.next()) {
			for (int i = 1; i <= jrs.getMetaData().getColumnCount(); i++) {
				System.out.print(jrs.getObject(i));
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)throws Exception {
		useDao();
	}
	

}
