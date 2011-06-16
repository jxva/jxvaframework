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

import java.io.IOException;
import java.util.List;

import org.jxva.dao.model.Test;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-04 21:19:23 by Jxva
 */
public class DaoUsage {
	private static final DAOFactory factory=DAOFactory.getInstance();
	private static DAO dao;
	
	public static void main(String[] args) throws IOException {
		dao=factory.createDAO();
		DaoClient.save(dao);
		Test test=(Test)dao.get("from Test t where t.aa =1 ");
		if(test!=null){
			System.out.println(test.getAa());
		}
		DaoClient.getAndSave(dao);
		//List<Test> list=dao.findPaginated("from Test order by aa desc",1,4);
		dao.close();
	}
	
	public static void test(DAO dao) throws Exception{
		
		
		List<Test> list=(List<Test>)dao.find("from Test",new Object[]{3,20});
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).getAa());
		}
		
		List<Test> list1=(List<Test>)dao.find("from Test t where t.aa<? and t.aa>?",new Object[]{1,40});
		for(int i=0;i<list1.size();i++){
			System.out.println(list1.get(i).getAa());
		}
	}
}
