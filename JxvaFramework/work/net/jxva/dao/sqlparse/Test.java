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
package net.jxva.dao.sqlparse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.jxva.dao.model.Book;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

/**
 * 

 		full outer join 和cross join 有什么区别?

　　答：交叉连接是属于内连接中的一种特殊的连接。根本不存在任何条件连接，应该说是范围最大的连接。
		就是笛卡尔乘积。 而全外连接是首先进行内连接，然后将所有没匹配上的记录也选出来，
		但不在对不匹配的进行笛卡尔乘积只是加进来。 而交叉连接是会将没匹配上的也要进行乘积。
 * 
 * 1.目前子查询中不能引用外部查询的别名,待改进(比较容易改进)
 * 如:
 * 	允  许:	from Book   b left join fetch  b.user u where b.userId in (select a.userId from User a where a.username like '%t%') and  b.name like '%c%' order by b.bookId desc
 *  不允许: from Book   b left join fetch  b.user u where b.userId in (select u.userId from u where u.username like '%t%') and  b.name like '%c%' order by b.bookId desc
 *       原因:子查询中的表名u引用了外部查询的别名
 *       
 * 2.不支持分层关联操作,待改进(还有一点困难)
 * 如: 
 * 	允  许: from Book b left join b.user u right join fetch b.publish p
 *  不允许: from Book b left join b.user u right join fetch u.publish p
 *  	原因:u.publish使用了上层b.user u中的别名u
 *  		 publish p只能使用首层Book b中的别名b
 *  
 * 3.union之后的联合查询语句需要使用()括号起来
 * 
 * 4.mysql不支持full outer join,可以转为left join union right join 的组合查询
 * 如:
 * 		from Book b full outer join User u
 * 
 * 可以改为:	select * from Book b left join User u union all (select * from Book b0 right join User u0)
 * 
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-25 08:58:29 by Jxva
 */
public class Test {

	public static void main(String[] args) {
		final SQLParser tree=new SQLParser();
		final List<String> jqls=new ArrayList<String>();
		
//		jqls.add("from Book   b , User u where b.userId=u.userId and  b.name like '%c%' order by b.bookId desc");
//		jqls.add("from Book   b  where b.userId=3 and  b.name like '%c%' order by b.bookId desc");
//	    jqls.add("from Book   b  inner join fetch  b.user u where b.userId=u.userId and  b.name like '%c%' order by b.bookId desc");
//		jqls.add("from Book   b right   join fetch  b.user u where b.userId=u.userId and  b.name like '%c%' order by b.bookId desc");
//		jqls.add("from Book   right   join fetch  User where Book.userId=User.userId and  Book.name like '%c%' order by Book.bookId desc");
//		jqls.add("from Book  b right   join fetch  User u where b.userId=u.userId and  b.name like '%c%' order by b.bookId desc");
//		
//		jqls.add("from Book");
//		jqls.add("from Book b");
//		
    	jqls.add("from Book order by bookId desc");
//		jqls.add("from Book b order by b.bookId desc");
//		jqls.add("from Book order by Book.bookId desc");
//		
//		jqls.add("select bookId from Book group by bookId");
//		jqls.add("select b.bookId from Book b group by b.bookId");
//		jqls.add("select Book.bookId from Book group by Book.bookId");
//		
//		jqls.add("select bookId from Book group by bookId order by bookId");
//		jqls.add("select b.bookId from Book b group by b.bookId order by b.bookId");
//		jqls.add("select Book.bookId from Book group by Book.bookId order by Book.bookId");
//		
//		jqls.add("from Book,User where Book.userId=3 and User.userId=3");
//		jqls.add("from Book b,User u where b.userId=3 and u.userId=3");
//		
//		jqls.add("from Book  where userId=3");
//		jqls.add("from Book  where Book.userId=3");
//		jqls.add("from Book b  where b.userId=3");
//		
//		jqls.add("from Book   b left   join fetch  b.user u where b.userId in (select a.userId from User a where a.username like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' order by b.bookId desc");
//		jqls.add("from Book   b left join fetch  b.user u where b.userId in (select a.userId from User a where a.username like '%t%') and  b.name like '%c%' order by b.bookId desc");
//		jqls.add("from Book   b left join fetch  b.user u where b.userId in (1,2,4) and  b.name like '%c%' order by b.bookId desc");
//		jqls.add("from Book   b left   join fetch  b.user u where b.userId in (select a.userId from User a where a.userId in (1,2,3,4) and a.username like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' order by b.bookId desc");
//		
//		jqls.add("from Book b left join b.user right outer join b.publish order by b.bookId desc");
//		jqls.add("from Book b left join b.user right outer join b.publish where b.userId in (select a.userId from User a where a.username like '%t%') order by b.bookId desc");
//		jqls.add("from Book b left join b.user,b.publish order by b.bookId desc");
//		
//		jqls.add("from Book b cross join b.user,b.publish order by b.bookId desc");
//		jqls.add("from Book b left join b.user u,b.publish p where u.username like '%s%' order by b.bookId desc");
//		jqls.add("from Book b left join b.user u,b.publish p where u.username like '%s%' order by b.bookId desc");
//		
//		//mysql> select * from tbl_book b left outer join tbl_user u on b.user_id=u.user_id union all (select * from tbl_book b0 right outer join tbl_user u0 on b0.user_id=u0.user_id);
		//jqls.add("select * from Book b left join User u union all (select * from Book b0 right join User u0)");
//		
//		jqls.add("from Book  left   join fetch  User  where Book.userId in (select userId from User  where userId in (1,2,3,4) and username like '%t%') and  Book.bookId in (1,2,3,4) and Book.name like '%c%' order by Book.bookId desc");
//		
//		
//		jqls.add("from Book  left join User  union (select * from Book  right join User )");
//		jqls.add("from Book b left join User u union all (select * from Book b0 right join User u0)");
//		
//		jqls.add("from Book   b left   join fetch  b.user u,b.publish p where b.userId in (select a.userId from User a where a.userId in (1,2,3,4) and a.username like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' and p.publishId in (select sb.publishId from Book sb where sb.bookId in (2,3,5)) order by b.bookId desc");
//		jqls.add("from Book   b left   join fetch  b.user u,b.publish p where b.userId in (select a.userId from User a where a.userId in (1,2,3,4) and a.username like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' and p.publishId in (select sb.publishId from Book sb where sb.bookId in (2,3,5)) order by b.bookId desc");
//		jqls.add("from Book   b left   join fetch  b.user u,b.publish p where b.userId in (select a.userId from User a where a.userId in (1,2,3,4) and a.username like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 and b.name like '%c%' and p.publishId in (select sb.publishId from Book sb where sb.bookId in (2,3,5)) order by b.bookId desc");
		
		Test test=new Test();
		//test.testDao(jqls, tree);
		//test.test(jqls,tree);
		//test.multiTest(jqls,tree);	
		//test.multiDaoTest(jqls,tree);
		
		final DAO dao=DAOFactory.getInstance().createDAO();
			//String sql=tree.parse(new JQLParser(),jqls.get(0));
			System.out.println("jql:"+jqls.get(0));
			//System.out.println("sql:"+sql);
			System.out.println();
			List<Book> list=(List<Book>)dao.find(jqls.get(0));
			for(Book book:list){
				System.out.print(book.toString());
				System.out.println();
			}
		dao.close();
		
//		FromEntry[] fromEntrys=tree.getFromEntrys();
//		for(FromEntry fromEntry:fromEntrys){
//			System.out.println(fromEntry);
//		}
	}
	
	public void testDao(final List<String> jqls,final SQLParser t){
		final DAO dao=DAOFactory.getInstance().createDAO();
		for(String jql:jqls){
			String sql=t.parse(new JQLParser(),jql);
			System.out.println("jql:"+jql);
			System.out.println("sql:"+sql);
			System.out.println();
			List<Object[]> list=(List<Object[]>)dao.find(sql);
			for(Object[] objs:list){
				for(Object obj:objs){
					System.out.print((obj==null?"null":obj.toString())+' ');
				}
				System.out.println();
			}
		}
		dao.close();
	}
	
	public void test(final List<String> jqls,final SQLParser t){
		for(String jql:jqls){
			String sql=t.parse(new JQLParser(), jql);
			System.out.println("jql:"+jql);
			System.out.println("sql:"+sql);
			System.out.println("hasMainSelectClause:"+t.hasMainSelectClause());
			System.out.println();
		}
		
	}
	
	public void multiTest(final List<String> jqls,final SQLParser t){
		int threadCount=10;
		ExecutorService execute=Executors.newFixedThreadPool(threadCount);
		for(int i=0;i<threadCount;i++){
			execute.submit(new Runnable(){
				public void run(){
					for(String jql:jqls){
						String sql=t.parse(new JQLParser(), jql);
						System.out.println("jql:"+jql);
						System.out.println("sql:"+sql);
						System.out.println();
					}
				}
			});
		}
		execute.shutdown();
	}
	
	public void multiDaoTest(final List<String> jqls,final SQLParser t){
		final DAO dao=DAOFactory.getInstance().createDAO();
		int threadCount=10;
		ExecutorService execute=Executors.newFixedThreadPool(threadCount);
		for(int i=0;i<threadCount;i++){
			execute.submit(new Runnable(){
				public void run(){
					for(String jql:jqls){
						String sql=t.parse(new JQLParser(), jql);
						System.out.println("jql:"+jql);
						System.out.println("sql:"+sql);
						System.out.println();
						dao.find(sql);
					}
				}
			});
		}
		execute.shutdown();
		dao.close();
	}

}
