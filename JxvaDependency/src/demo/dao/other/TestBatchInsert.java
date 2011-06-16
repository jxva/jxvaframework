package demo.dao.other;

import org.jxva.dao.model.Author;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

public class TestBatchInsert {

	private static DAOFactory factory =DAOFactory.getInstance();
	public static void main(String[] args) {
		DAO dao=factory.createDAO();
		Author[] authors=new Author[2];
		Author author1=new Author();
		author1.setName("fdsafsa");
		authors[0]=author1;
		Author author2=new Author();
		author2.setName("哈哈");
		authors[1]=author2;
		for(Author a:authors){
			System.out.println(a.getName());
		}
//		int[] is=dao.save(authors);
//		for(int i:is){
//			System.out.println(i);
//		}
		dao.close();
	}
	
	

}
