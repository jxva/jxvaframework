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

/**
 * 关键字(记号)都为小写
   主要关键字有:from,inner,join,left,right,full,fetch,outer,where,on,group by,order by
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-22 16:53:11 by Jxva
 */
public class Token {
	
	public static final String NULL=null;
	public static final String EMPTY="";
	public static final String WHITESPACE=" ";
	public static final String SELECT=" select ";
	public static final String FROM=" from ";
	public static final String INNER=" inner ";
	public static final String JOIN=" join ";
	public static final String LEFT=" left ";
	public static final String RIGHT=" right ";
	public static final String FULL=" full ";
	public static final String FETCH=" fetch ";
	public static final String OUTER=" outer ";
	public static final String WHERE=" where ";
	public static final String ON=" on ";
	public static final String ORDERBY=" order by ";
	public static final String GROUPBY=" group by ";
	public static final String CROSS=" cross ";
	
	public static final String SUM=" sum ";
	public static final String COUNT=" count ";
	
	public static final String SUB_CLAUSE_MARK="!!";
	//public static final String PARAMS_SEPARATOR="??";
	
}
