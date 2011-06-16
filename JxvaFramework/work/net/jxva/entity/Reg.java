package net.jxva.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. A,B=A inner join B
 * 2. A a,B b=A a inner join B b
 * 3. A a inner join B b
 * 4. A a inner join fetch B b
 * 5. A a left join B b
 * 6. A a left join fetch B b
 * 7. A a right join B b
 * 8. A a right join fetch B b
 * 9.
 * inner join 
 * left join
 * right join
 * full join
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-23 16:19:59 by Jxva
 */
public class Reg {

	public static void main(String[] args) {
		String sql="from User u full outer join fetch u.book b where u.username = ' whe\"re is '' order by ' and u.userId =b.userId gRoup By u.username having count(*)>6 order by u.userId,u.username desc";
		sql=sql.replaceAll("'([^']|'')*'","?");
		sql=sql.replaceAll(" {2,}"," ");

		System.out.println(sql);
		Matcher m=Pattern.compile("(\\w|\\.)+( )?=").matcher(sql);
		while(m.find()){
			System.out.println(m.group());
		}
	}
}
