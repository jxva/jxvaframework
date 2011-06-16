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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.dao.JdbcTemplate;
import com.jxva.util.UtilException;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-08-03 18:01:02 by Jxva
 */
public class TestFile {

	private static final DAOFactory factory=DAOFactory.getInstance();
	private static final Pattern pattern=Pattern.compile(",");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputStreamReader input=null;
		BufferedReader br =null;
		try {
			DAO dao=factory.createDAO();
			JdbcTemplate jdbc=dao.getJdbcTemplate();
			input = new InputStreamReader(new FileInputStream(new File("C:/2.txt")),"GBK");
			br = new BufferedReader(input);
			String line =null;
			while ((line = br.readLine()) != null) {
				String[] s=pattern.split(line,0);
				jdbc.execute("insert into tbl_mobile_area (start,city,province,mobile_type,carrier) values ('"+s[1]+"','"+s[3]+"','"+s[5]+"','"+s[6]+"','"+s[7]+"')");
			}
			dao.close();
		} catch (IOException e) {
			throw new UtilException(e);
		}finally{
			try {if(input!=null)input.close();} catch (IOException e) {}
			try {if(br!=null)br.close();} catch (IOException e) {}
		}

	}

}
