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
package com.jxva.dao.dialect;

import com.jxva.dao.Dialect;
import com.jxva.log.Logger;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-11 09:37:36 by Jxva
 */
public class H2Dialect implements Dialect{

	private static final long serialVersionUID = 2430389688773498051L;
	private static final Logger log=Logger.getLogger(H2Dialect.class);
	
	public H2Dialect(){
		log.info("H2 Dialect initializing...");
	}
	
	public String getSQLWithRowSetLimit(String sql,int start, int end) {
		 return new StringBuffer(sql.length() + 20).
         append(sql).
         append(" limit start offset end").
         toString();
	}
	
	public boolean supportsLimit() {
		return true;
	}

	public boolean supportsLimitOffset() {
		return true;
	}
	
	public boolean supportsGetGeneratedKeys() {
		return true;
	}
	
	public static void main(String[] args)throws Exception{
		H2Dialect s=new H2Dialect();
		System.out.println(s.getSQLWithRowSetLimit("select * from hr_info where name='4' order by msgid",10,15));
	}

}
