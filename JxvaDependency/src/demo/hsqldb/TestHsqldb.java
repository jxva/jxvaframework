package demo.hsqldb;

import java.util.List;


import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

import demo.hsqldb.model.Text;


public class TestHsqldb {
	
	private static final DAOFactory factory=DAOFactory.getInstance("test.hsqldb.jxva");
	
	private static  void useDao(){
		DAO dao=factory.createDAO();
		Text t=new Text();
		t.setId(1);
		t.setName("dfd中国");
		dao.save(t);
		List<Text> list=dao.find(Text.class);
		for(Text text:list){
			System.out.println(text.getId()+"="+text.getName());
		}
		dao.close();
	}
	
	
	public static void main(String[] args)throws Exception {
		useDao();
	}
	

}
