package org.jxva.dao.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Test {
    public static void main(String[] a)throws Exception {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        conn.createStatement().execute("create table if not exists test(username varchar(16),email varchar(32));");
        conn.createStatement().execute("insert into test (username,email) values ('jxva','jxva@msn.com');");
        ResultSet rs=conn.createStatement().executeQuery("select * from test");
        while(rs.next()){
        	System.out.print(rs.getString(1));
        	System.out.print('-');
        	System.out.println(rs.getString(2));
        }
        
        conn.close();
    }
}