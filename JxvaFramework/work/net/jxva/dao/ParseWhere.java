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
package net.jxva.dao;

import java.util.regex.Pattern;

/**
 *select top 10 a,b,c, t2.* from t1 left join t2 on right(t1.fa,5)=t2.fb
where (1=1) and ( (f1>3) or (f2>2) and (f3>4) or f5>1 and (f6>3 or f7<3) orf9=8 )
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-13 18:58:48 by Jxva
 */
public class ParseWhere {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String where="a.id in (1,2,3) and a.name='adsfas''df''adfasdf''''adfasdf''' or a.type='aas' or a.test1 exists(select * from b where b.id in (1,2,3,4)) and 1=1 and (a.id = 1 or a.type=2) and (c.dfafd=adsfasf and c.daat='adfafda') and a.id between 10 and 20 or a.name not like 'test%'";
		System.out.println(test(where));
	}
	
	public static String test(String sql){
		//sql=sql.replaceAll("\'\'","K");
		return Pattern.compile("'([^']|(''))*'").matcher(sql).replaceAll("?");
	}

}
