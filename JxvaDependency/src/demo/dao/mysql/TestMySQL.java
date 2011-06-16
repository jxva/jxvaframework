package demo.dao.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

import demo.dao.mysql.model.Test;

public class TestMySQL {

	private static final DAOFactory factory=DAOFactory.getInstance("demo.dao.mysql.jxva");
	private static DAO dao;
	public static void main(String[] args) throws Exception {
		dao=factory.createDAO();
		Test tt=new Test();
		//tt.setAa(new Long(58));
		tt.setBb(new FileInputStream("d:/test.doc"));
		tt.setCc('x');
		tt.setDd('s');
		tt.setEe("fdsa");
		tt.setFf(new Date(System.currentTimeMillis()));
		tt.setGg(new BigDecimal(12.1));
		tt.setHh(new Double(12.54));
		tt.setIi(new Double(14.654));
		tt.setJj(new Double(15.14));
		tt.setKk(new Integer(156));
		tt.setLl(new Integer(5461));
		tt.setMm("mm");
		tt.setNn(new BigDecimal(1));
		tt.setOo(new BigDecimal(1));
		tt.setPp(new Double(1.1));
		tt.setQq(new Short((short)1));
		tt.setRr(new Time(System.currentTimeMillis()));
		tt.setSs(new Timestamp(System.currentTimeMillis()));
		tt.setTt("tttt");
		tt.setUu(new Long(58));
		dao.save(tt);
		Test test=dao.get(Test.class,"order by Aa desc");
		if(test!=null){
			InputStream is=test.getBb();
			File   file   =   new   File("d:/mysql_file.doc");  
			FileOutputStream   os   =   new   FileOutputStream(file);  
			byte[]   buffer   =   new   byte[1024];  
			int   length   =   -1;  
			while((length   =   is.read(buffer))>-1){  
			    os.write(buffer,0,length);  
			}  
			os.flush();  
			os.close();
		}
		
		List<Test> list=dao.find(Test.class);
		for(int i=0;i<list.size();i++){
			
			System.out.println(list.get(i).getAa());
		}
		dao.close();
	}

}
