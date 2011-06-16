package demo.dao.sqlserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

import demo.dao.sqlserver.model.Sqlserver;

public class TestSQLServer {

	private static final DAOFactory factory=DAOFactory.getInstance("test.dao.sqlserver.jxva");
	private static DAO dao;
	public static void main(String[] args) throws Exception {
		dao=factory.createDAO();
		
		
		Sqlserver tt=new Sqlserver();
		tt.setA1(new Long(2));
		tt.setA2(new Long(58));
		tt.setA3(new FileInputStream("d:/test.txt")); //warning
		tt.setA4(Boolean.FALSE);
		tt.setA5('d');
		tt.setA6(new Timestamp(System.currentTimeMillis()));
		tt.setA7(new BigDecimal(1));
		tt.setA8(new Double(15.14));
		tt.setA9(new FileInputStream("d:/test.rar"));
		tt.setA10(34);
		tt.setA11(new BigDecimal(34));
		tt.setA12('f');
		tt.setA13("fds");
		tt.setA14(new BigDecimal(6));
		tt.setA15("3gfds43");
		tt.setA16("3ffgds4");
		tt.setA17(new Float(4324.3));
		tt.setA18(new Timestamp(System.currentTimeMillis()));
		tt.setA19(new Short((short)3));
		tt.setA20(new BigDecimal(16));
		tt.setA22("fdsa");
		//tt.setA23(new FileInputStream("d:/test.doc"));
		tt.setA24(new Short((short)3));
		//tt.setA25('g');
		tt.setA26(new FileInputStream("d:/test.txt")); //ok
		tt.setA27(new FileInputStream("d:/test.rar")); //ok
		tt.setA28("fddda");
		tt.setA29("hewqr");
		//tt.setA30("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>中国</root>");
		dao.save(tt);
		
		List<Sqlserver> list=dao.find(Sqlserver.class);
		for(int i=0;i<list.size();i++){
			
			System.out.println(list.get(i).getA1());
		}
				
		Sqlserver sqlserver=dao.get(Sqlserver.class,"order by a1 desc");
		if(sqlserver!=null){
			//====A3 for FileInputStream
			InputStream is3=sqlserver.getA3();
			File   file3   =   new   File("d:/a3.txt");  
			FileOutputStream   os3   =   new   FileOutputStream(file3);  
			byte[]   buffer3   =   new   byte[1024];  
			int   length3   =   -1;  
			while((length3   =   is3.read(buffer3))>-1){  
			    os3.write(buffer3,0,length3);  
			}  
			os3.flush();  
			os3.close();
			//====A9 for FileInputStream
			InputStream is9=sqlserver.getA9();
			File   file9   =   new   File("d:/a9.rar");  
			FileOutputStream   os9   =   new   FileOutputStream(file9);  
			byte[]   buffer9   =   new   byte[1024];  
			int   length9   =   -1;  
			while((length9   =   is9.read(buffer9))>-1){  
			    os9.write(buffer9,0,length9);  
			}  
			os9.flush();  
			os9.close();
			//====A23 for FileInputStream
			InputStream is23=sqlserver.getA23();
			File   file23   =   new   File("d:/a23.doc");  
			FileOutputStream   os23   =   new   FileOutputStream(file23);  
			byte[]   buffer23   =   new   byte[1024];  
			int   length23   =   -1;  
			while((length23   =   is23.read(buffer23))>-1){  
			    os23.write(buffer23,0,length23);  
			}  
			os23.flush();  
			os23.close();
			//====A26  for FileInputStream
			InputStream is26=sqlserver.getA26();
			File   file26   =   new   File("d:/a26.txt");  
			FileOutputStream   os26   =   new   FileOutputStream(file26);  
			byte[]   buffer26   =   new   byte[1024];  
			int   length26   =   -1;  
			while((length26   =   is26.read(buffer26))>-1){  
			    os26.write(buffer26,0,length26);  
			}  
			os26.flush();  
			os26.close();
			//====A27  for FileInputStream
			InputStream is27=sqlserver.getA27();
			File   file27   =   new   File("d:/a27.rar");  
			FileOutputStream   os27   =   new   FileOutputStream(file27);  
			byte[]   buffer27   =   new   byte[1024];  
			int   length27   =   -1;  
			while((length27   =   is27.read(buffer27))>-1){  
			    os27.write(buffer27,0,length27);  
			}  
			os27.flush();  
			os27.close();
		}
		dao.close();
	}

}
