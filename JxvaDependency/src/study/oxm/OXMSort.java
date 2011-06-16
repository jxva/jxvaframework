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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OXMSort {
	protected static void sort(List<Object> list,
			final Map<Object, Object> sortValues, final String type,
			final String orderby) {
		Comparator<Object> comparator = new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object o1, Object o2) {
				o1 = sortValues.get(o1);
				o2 = sortValues.get(o2);
				if (orderby.equals("desc")) {
					Object temp = o1;
					o1 = o2;
					o2 = temp;
				}
				if (type.equals("java.lang.Integer")) {
					return Integer.parseInt(o1.toString())
							- Integer.parseInt(o2.toString());
				}
				if (type.equals("java.lang.Short")) {
					return Short.parseShort(o1.toString())
							- Short.parseShort(o2.toString());
				}
				if (type.equals("java.lang.Long")) {
					if (Long.parseLong(o1.toString()) > Long.parseLong(o2
							.toString()))
						return 1;
					if (Long.parseLong(o1.toString()) < Long.parseLong(o2
							.toString()))
						return -1;
					return 0;
				}
				if (type.equals("java.lang.Double")) {
					if (Double.parseDouble(o1.toString()) > Double
							.parseDouble(o2.toString()))
						return 1;
					if (Double.parseDouble(o1.toString()) < Double
							.parseDouble(o2.toString()))
						return -1;
					return 0;
				}
				if (type.equals("java.sql.Timestamp")) {
					return Timestamp.valueOf(o1.toString()).compareTo(
							Timestamp.valueOf(o2.toString()));
				}
				if (type.equals("java.sql.Date")) {
					return Date.valueOf(o1.toString()).compareTo(
							Date.valueOf(o2.toString()));
				}
				if (type.equals("java.sql.Time")) {
					return Time.valueOf(o1.toString()).compareTo(
							Time.valueOf(o1.toString()));
				}
				if (o1 == o2)
					return 0;
				if (o1 instanceof Comparable) {
					Comparable<Object> c1 = (Comparable<Object>)o1;
					return c1.compareTo(o2);
				}
				if (o1 == null)
					return 1;
				if (o2 == null)
					return -1;
				else
					return o1.equals(o2) ? 0 : -1;
			}

		};
		Collections.sort(list, comparator);

	}

	/*
	 * @param list 要排序的Object List @param filds要排序的字段名 @param orderby asc升序 desc
	 * 降序
	 */

	public static void sort(List<Object> list, String filds, String orderby) {
		if (list != null && !list.isEmpty()) {
			String type = "";
			int size = list.size();
			HashMap<Object, Object> sortValues = new HashMap<Object, Object>(
					size);
			for (int i = 0; i < size; i++) {
				try {
					Object obj = list.get(i);
					Class<? extends Object> voClass = list.get(i).getClass();
					Field[] fs = voClass.getDeclaredFields();
					for (int j = 0; j < fs.length && i < 1; j++) {
						if (fs[j].getName() == filds) {
							type = fs[j].getType().getName();
						}
					}
					Method methInstance = voClass.getMethod("get"
							+ filds.substring(0, 1).toUpperCase()
							+ filds.substring(1, filds.length()),
							new Class[] {});
					String value = methInstance.invoke(obj, new Object[] {})
							.toString();
					sortValues.put(obj, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			sort(list, ((Map<Object, Object>) (sortValues)), type, orderby);
		}
	}

	public static void main(String arg[]) {
		List<Object> list = new ArrayList<Object>();
		Person person = new Person();
		person.setDate(Timestamp.valueOf("2007-12-29 14:06:35.0"));
		person.setEmail("wangjie@163.com");
		person.setMsgid(1);
		person.setQq("32485741");
		person.setSex("男");
		// person.setTemp(Long.valueOf("1231233"));
		person.setName("wangj");
		list.add(person);
		Person person1 = new Person();
		person1.setDate(Timestamp.valueOf("2003-12-29 14:06:35.0"));
		person1.setEmail("wangjie@163.com");
		person1.setMsgid(4);
		person1.setQq("32485741");
		person1.setSex("男");
		// person1.setTemp(Long.valueOf("3331233"));
		person1.setName("zqj");
		list.add(person1);
		Person person2 = new Person();
		person2.setDate(Timestamp.valueOf("2006-12-29 14:06:35.0"));
		person2.setEmail("wangjie@163.com");
		person2.setMsgid(100);
		person2.setQq("32485731");
		person2.setSex("male");
		// person2.setTemp(Long.valueOf("12332233"));
		person2.setName("jql");
		list.add(person2);
		sort(list, "msgid", "asc");
		for (Object pe : list) {
			Person p = (Person) pe;
			System.out.println("msgid:" + p.getMsgid() + "\tname:"
					+ p.getName() + "\tsex" + p.getSex() + "\temail:"
					+ p.getEmail() + "\tdate:" + p.getDate());
		}
	}

}
