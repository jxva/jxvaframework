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
package org.jxva;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jxva.entity.Encoding;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-08-12 18:11:35 by Jxva
 */
public class TestMDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url="jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\090805.mdb";
        //String url = "jdbc:odbc:helpdb";//helpdb为ODBC数据源名称

        Connection conn = null;
        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection(url , "" , "");
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select * from 130");

            while(rs.next()){
                System.out.println(new String (rs.getString(3).getBytes(Encoding.GBK),Encoding.ISO_8859_1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(conn!=null)
                    conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

	}

}
