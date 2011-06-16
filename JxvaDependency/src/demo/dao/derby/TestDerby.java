package demo.dao.derby;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Clob;
import java.util.List;


import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

import demo.dao.derby.model.Derby;

public class TestDerby {

	private static final DAOFactory factory=DAOFactory.getInstance("test.dao.derby.jxva");
	private static DAO dao;
	public static void main(String[] args) throws Exception {
		dao=factory.createDAO();
		
		
//		Derby tt=new Derby();
//		tt.setA1(3);
//		tt.setA2(new Long(58));
//		tt.setA3(new Blob(new FileInputStream("d:/test.xls")));
//		tt.setA4('d');
//		tt.setA5(new FileInputStream("d:/test.txt"));
//		tt.setA6(new Clob(new FileInputStream("d:/test.txt")));
//		tt.setA7(new Date(System.currentTimeMillis()));
//		tt.setA8(new BigDecimal(141));
//		tt.setA9(new Double(15.14));
//		tt.setA10(new Double(15.14));
//		tt.setA11(new Double(15.14));
//		tt.setA12(54);
//		tt.setA13("d");
//		tt.setA14(new FileInputStream("d:/test.txt"));
//		tt.setA15(new BigDecimal(141));
//		tt.setA16(Float.valueOf("12.551"));
//		tt.setA17(new Short((short)3));
//		tt.setA18(new Time(System.currentTimeMillis()));
//		tt.setA19(new Timestamp(System.currentTimeMillis()));
//		tt.setA20("f");
//		tt.setA21(new FileInputStream("d:/test.txt"));
//		dao.insert(tt);
		
		List<Derby> list=dao.find(Derby.class);
		for(int i=0;i<list.size();i++){
			
			System.out.println(list.get(i).getA1());
		}
				
		Derby derby=dao.get(Derby.class,"order by a1 desc");
		if(derby!=null){
			//====A3 for Blob
			java.sql.Blob ablob = derby.getA3(); 
			InputStream bis = ablob.getBinaryStream() ; 
			OutputStream os = new FileOutputStream("d:/a3.xls");
			int b = bis.read(); 
			while (b != -1 ) {
				os.write((char)b);
				b = bis.read();
			}
			os.flush();
			os.close();
			bis.close();
			//====A6 for Clob
			InputStream aclob = derby.getA6().getAsciiStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(aclob));
			String line = null ;
			while(null != (line = br.readLine())){
				System.out.println(line); //将其输出至屏幕，实际你可以按照需要处理 
			}
			aclob.close();			
			//====A5
			InputStream is5=derby.getA5();
			File   file5   =   new   File("d:/a5.txt");  
			FileOutputStream   os5   =   new   FileOutputStream(file5);  
			byte[]   buffer5   =   new   byte[1024];  
			int   length5   =   -1;  
			while((length5   =   is5.read(buffer5))>-1){  
			    os5.write(buffer5,0,length5);  
			}  
			os5.flush();  
			os5.close();
			//====A14
			InputStream is14=derby.getA14();
			File   file14   =   new   File("d:/a14.txt");  
			FileOutputStream   os14   =   new   FileOutputStream(file14);  
			byte[]   buffer14   =   new   byte[1024];  
			int   length14   =   -1;  
			while((length14   =   is14.read(buffer14))>-1){  
			    os14.write(buffer14,0,length14);  
			}  
			os14.flush();  
			os14.close();
			//====A21
			InputStream is21=derby.getA21();
			File file21   =   new   File("d:/a21.txt");  
			FileOutputStream os21   =   new   FileOutputStream(file21);  
			byte[] buffer21   =   new   byte[1024];  
			int length21   =   -1;  
			while((length21   =   is21.read(buffer21))>-1){  
			    os21.write(buffer21,0,length21);  
			}  
			os21.flush();  
			os21.close();
		}
		dao.close();
	}

}
