/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
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
 *
 */
package study.oxm;
import java.sql.Timestamp;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
			OXMFactory oxmfactory=OXMFactory.getInstance("D:/eclipse/workspace/Security/src/com/jxva/oxm");
			OXM oxm=oxmfactory.createOXM();
			Person per =new Person();
			per.setMsgid(oxm.getAutoId(Person.class).intValue());
			per.setEmail("sadfdsf");
			per.setDate(Timestamp.valueOf("2007-02-03 12:20:20"));
			per.setName("jiang");
			per.setQq("82267524");
			per.setSex("male");
			oxm.insert(per);
			
			oxm.update(per, "msgid='7'");
			
			List<Object> list=oxm.find(Person.class,"msgid!='0'");
			for(Object obj:list){
				Person p=(Person)obj;
				System.out.println(p.getMsgid());
			}
			
			Object obj=oxm.findOrderBy(Person.class, " msgid!='0'", "msgid desc");
			System.out.println(obj);
			oxm.delete(Person.class, "msgid>29");

	}
	public static void print(List <Person>list){
		System.out.print("Msgid\tname\tsex\temail\t\t\tdate\t\t\tqq\t\n");
		for(Person person:list){
		System.out.println(person.getMsgid()+"\t"+person.getName()+"\t"+person.getSex()+"\t"+person.getEmail()+"\t"+person.getDate()+"\t"+person.getQq());		
		}
		
	}

}
