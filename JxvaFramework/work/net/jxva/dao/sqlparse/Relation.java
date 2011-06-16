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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-24 11:02:29 by Jxva
 */
public class Relation {
	public static final int CROSS=-1;
	public static final int DEFAULT=0;
	public static final int INNER=1;
	public static final int LEFT=2;
	public static final int RIGHT=3;
	public static final int FULL=4;
	public static final int UNION=6;
	
	private static final Map<Integer,String> relationMap=new HashMap<Integer,String>(5);
	static{
		relationMap.put(Relation.DEFAULT, ",");
		relationMap.put(Relation.INNER, " inner join ");
		relationMap.put(Relation.LEFT, " left outer join ");
		relationMap.put(Relation.RIGHT, " right outer join ");
		relationMap.put(Relation.FULL, " full outer join ");
		relationMap.put(Relation.CROSS," cross join ");
	}
	
	public static final String getValue(int relation){
		return relationMap.get(relation);
	}
	
	public static final int getValue(String relation){
		if(relation.equals("inner")){
			return INNER;
		}else if(relation.equals("left")){
			return LEFT;
		}else if(relation.equals("right")){
			return RIGHT;
		}else if(relation.equals("full")){
			return FULL;
		}else if(relation.equals("cross")){
			return CROSS;
		}else{
			return DEFAULT;
		}
	}
}
