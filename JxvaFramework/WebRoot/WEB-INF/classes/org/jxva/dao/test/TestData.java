package org.jxva.dao.test;

import java.util.ArrayList;
import java.util.List;

import com.jxva.dao.Parser;
import com.jxva.dao.Statement;
import com.jxva.dao.parser.JqlParser;

public class TestData {

	public static final List<String> jqls=new ArrayList<String>();
	public static final List<String> jqls2=new ArrayList<String>();

	
	static{
		jqls.add("from Book   b , Press u where b.pressId=u.pressId and  b.name like '%c%' order by b.bookId desc");
		jqls.add("from Book   b  where b.pressId=3 and  b.name like '%c%' order by b.bookId desc");
	    jqls.add("from Book   b  inner join fetch  b.press p where b.pressId=p.pressId and  b.name like '%c%' order by b.bookId desc");
		jqls.add("from Book   b right   join fetch  b.press p where b.pressId=p.pressId and  b.name like '%c%' order by b.bookId desc");
		jqls.add("from Book   right   join fetch  Press where Book.pressId=Press.pressId and  Book.name like '%c%' order by Book.bookId desc");
		jqls.add("from Book  b right   join fetch  Press p where b.pressId=p.pressId and  b.name like '%c%' order by b.bookId desc");
		
		jqls.add("from Book");
		jqls.add("from Book b");
	
		jqls.add("from Book order by bookId desc");
		jqls.add("from Book b order by b.bookId desc");
		jqls.add("from Book order by bookId desc");
		jqls.add("from Book b order by b.bookId desc");
		jqls.add("from Book order by bookId desc");
		jqls.add("from Book b order by b.bookId desc");
		jqls.add("from Book order by Book.bookId desc");
		
		jqls.add("select bookId from Book group by bookId");
		jqls.add("select b.bookId from Book b group by b.bookId");
		jqls.add("select Book.bookId from Book group by Book.bookId");
		
		jqls.add("select bookId from Book group by bookId order by bookId");
		jqls.add("select b.bookId from Book b group by b.bookId order by b.bookId");
		jqls.add("select Book.bookId from Book group by Book.bookId order by Book.bookId");
		
		jqls.add("from Book,Press where Book.pressId=3 and Press.pressId=3");
		jqls.add("from Book b,Press u where b.pressId=3 and u.pressId=3");
		
		jqls.add("from Book  where bookId=3");
		jqls.add("from Book  where Book.bookId=3");
		jqls.add("from Book b  where b.bookId=3");
		
		jqls.add("from Book   b left   join fetch  b.Press p where b.pressId in (select a.pressId from Press a where a.name like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' order by b.bookId desc");
		jqls.add("from Book   b left join fetch  b.Press p where b.pressId in (select a.pressId from Press a where a.name like '%t%') and  b.name like '%c%' order by b.bookId desc");
		jqls.add("from Book   b left join fetch  b.Press p where b.pressId in (1,2,4) and  b.name like '%c%' order by b.bookId desc");
		jqls.add("from Book   b left   join fetch  b.Press p where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) and a.name like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' order by b.bookId desc");
		
		jqls.add("from Book b left join b.Press right outer join b.category order by b.bookId desc");
		jqls.add("from Book b left join b.Press right outer join b.category where b.pressId in (select a.pressId from Press a where a.name like '%t%') order by b.bookId desc");
		jqls.add("from Book b left join b.Category,b.press order by b.bookId desc");
		
		jqls.add("from Book b cross join b.Press,b.category order by b.bookId desc");
		jqls.add("from Book b left join b.Press u,b.category p where u.name like '%s%' order by b.bookId desc");
		jqls.add("from Book b left join b.Press u,b.category p where u.name like '%s%' order by b.bookId desc");
	//	
	//	//mysql> select * from tbl_book b left outer join tbl_Press u on b.Press_id=u.Press_id union all (select * from tbl_book b0 right outer join tbl_Press u0 on b0.Press_id=u0.Press_id);
		
		jqls.add("select * from Book b left join Press u union all (select * from Book b0 right join Press u0)");
		jqls.add("from Book  left join Press  union (select * from Book  right join Press )");
		jqls.add("from Book b left join Press p union all (select * from Book b0 right join Press u0)");
//		
		
		jqls.add("from Book  left   join fetch  Press  where Book.pressId in (select pressId from Press  where pressId in (1,2,3,4) and name like '%t%') and  Book.bookId in (1,2,3,4) and Book.name like '%c%' order by Book.bookId desc");
		
		
		
		jqls.add("from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) and a.name like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) order by b.bookId desc");
		jqls.add("from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) and a.name like '%t%') and  b.bookId in (1,2,3,4) and b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) order by b.bookId desc");
		jqls.add("from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) and a.name like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 and b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) order by b.bookId desc");
		
		jqls.add("select b.bookId,b.name,p.pressId from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) and a.name like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 and b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) and b.bookId=2 order by b.bookId desc");

		jqls.add("delete b from  Book b  where b.name='fdsafdsa' ");
		jqls.add("update  Book b set b.name='dfdsa' ");
		//jqls.add("insert into Book (bookId,name) values (100,'ok')");
		
	
		jqls.add("from Category c join fetch c.book");
		jqls.add("from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) and a.name like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 and b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) and b.bookId=3 order by b.bookId desc");
		jqls.add("select b.bookId,b.name from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) and a.name like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 and b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) and b.bookId=6 order by b.bookId desc");
		
		jqls.add("delete b from  Book b  where b.name='dd ' ");
		jqls.add("update  Book b set b.bookId=1 where b.name=' dd ''d' ");
		
		jqls.add("delete b from  Book b  where b.name='dd ' ");
		jqls.add("from Book   b , Press u where b.pressId=u.pressId and  b.name like '%c%' order by b.bookId desc");
		jqls.add("delete b from  Book b  where b.name='dd ' ");
		//jqls2.add("insert into Book (bookId,name) values (102,'ok')");
		
		jqls.add("from Book b left join b.Press p  right outer join b.category c order by b.bookId desc");
		jqls.add("from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) or a.name like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 or b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) or b.bookId=2 order by b.bookId desc");

		
		//mysql> select b.book_id as c0,b.name as c1,b.category_id as c2,b.press_id as c3,b.issuer_date as c4,b.create_time as c5,b.quantity as c6,b.price as c7,b.hit_count as c8,b.is_commend as c9,b.is_available as c10,b.description as c11,p.press_id as c12,p.press_type_id as c13,p.name as c14,pt.press_type_id as c15,pt.name as c16 from tbl_book b left outer join tbl_press p on b.press_id=p.press_id left outer join tbl_press_type pt on p.press_type_id=pt.press_type_id where b.press_id in (1,2,3,4) order by b.book_id desc;
		jqls.add("from Book   b left   join fetch  b.Press p left join p.pressType pt where b.pressId in (1,2,3,4) order by b.bookId desc");
		jqls.add("from Book   b left   join fetch  b.Press p,b.category c where b.pressId in (select a.pressId from Press a where a.pressId in (1,2,3,4) or a.name like '%t%') and  b.bookId in (1,2,3,4) and b.bookId<10 and b.bookId>1 or b.name like '%c%' and p.pressId in (select sb.pressId from Book sb where sb.bookId in (2,3,5)) or b.bookId=2 order by b.bookId desc");

		jqls.add("delete b  from  Book b  where b.name='fdsafdsa' or b.bookId=99 ");
		jqls.add("delete  from  Book   where name='dd ' or bookId=99 ");
		jqls.add("update  Book b set b.name='dfdsa',b.bookId=101 where b.bookId=120");
		jqls.add("update  Book set name='dfdsa',bookId=102 where bookId=120");
		
		jqls.add("from Book order by bookId desc");
		//jqls.add("from Actor order by commendNum desc");
		
		jqls.add("from Book left join fetch Book.press p left join fetch Book.Category");
		jqls.add("from Book b left join fetch b.press left join fetch b.Category c");
		jqls.add("from Book b left join fetch b.press p left join fetch Press.pressType pt");
		
		jqls2.add("select bookId from B4ook group by bookId");
	}
	
	public static final List<String> getJqls(){
		return jqls;
	}
	
	private static final Parser parser=new JqlParser();
	
	public static void main(String[] args) {
		
		
		for(int i=0;i<jqls.size();i++){
			String jql=jqls.get(i);
			System.out.printf("%-15s %s\n","information:",i+"----------------------------------");
			System.out.printf("%-15s %s\n","jql:",jql);
			Statement s=parser.parse(jql);
			System.out.printf("%-15s %s\n","modelName:",s.getModelName());
			System.out.printf("%-15s %s\n\n\n","sql:",s.getSql());
			
		}
	}
}
