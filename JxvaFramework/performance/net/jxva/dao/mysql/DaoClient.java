/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.jxva.dao.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.jxva.dao.model.Test;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.util.CharUtil;
import com.jxva.util.FileUtil;
import com.jxva.util.NumberUtil;
import com.jxva.util.RandomUtil;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 20:59:18 by Jxva
 */
public class DaoClient extends Thread{
	
	private static final DAOFactory factory=DAOFactory.getInstance();

	private static FileInputStream file;
	
	static{
		try{
			file=new FileInputStream("d:/test.doc");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		for(int i=0;i<1000;i++){
			new DaoClient().start();
		}
	}
	
	public void run(){
		DAO dao=factory.createDAO();
		for(int i=0;i<1;i++){
			save(dao);
			//System.out.println(Thread.currentThread().getId());
		}
		dao.close();
	}
	
	public static Test getAndSave(DAO dao) throws IOException{
		Test test=(Test)dao.get("from Test t where t.aa=?",1);
		if(test!=null){
			InputStream in=test.getBb();
			File   file   =   new   File("d:/mysql_file.doc");  
			FileOutputStream   out   =   new   FileOutputStream(file);
			FileUtil.copy(in, out);
//			byte[]   buffer   =   new   byte[4096];  
//			int   length   =   -1;  
//			while((length   =   is.read(buffer))>-1){  
//			    os.write(buffer,0,length);  
//			}  
//			os.flush();  
//			os.close();
		}
		return test;
	}

	public static int save(DAO dao){
		Test tt=new Test();
		//tt.setAa(new Long(58));
		tt.setBb(file);
		tt.setCc(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,1).charAt(0));
		tt.setDd(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,1).charAt(0));
		tt.setEe(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,32));
		tt.setFf(new Date(System.currentTimeMillis()));
		tt.setGg(BigDecimal.valueOf(12.1));
		tt.setHh(Double.valueOf(15435.14d));
		tt.setIi(Double.valueOf(1645.14d));
		tt.setJj(Float.valueOf(1235.14f));
		tt.setKk(Integer.valueOf(RandomUtil.getRandomString(NumberUtil.NUM_CHAR_TABLE,6)));
		tt.setLl(Integer.valueOf(RandomUtil.getRandomString(NumberUtil.NUM_CHAR_TABLE,4)));
		tt.setMm(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,64));
		tt.setNn(BigDecimal.valueOf(1676));
		tt.setOo(BigDecimal.valueOf(52676));
		tt.setPp(Double.valueOf(131.1d));
		tt.setQq(Short.valueOf((short)1));
		tt.setRr(new Time(System.currentTimeMillis()));
		tt.setSs(new Timestamp(System.currentTimeMillis()));
		tt.setTt(RandomUtil.getRandomString(CharUtil.CHAR_TABLE,128));
		tt.setUu(Long.valueOf(7258l));
		return dao.save(tt);
	}
}
