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
package com.jxva.dao.parser;


import com.jxva.dao.ParseException;
import com.jxva.dao.Parser;
import com.jxva.dao.Statement;
import com.jxva.dao.entry.JqlEntry;
import com.jxva.dao.statement.SubSelectStatement;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 14:18:17 by Jxva
 */
public class SubJqlParser implements Parser {

	public Statement parse(JqlEntry jqlEntry,String jql)throws ParseException {
		return new SubSelectStatement(jqlEntry,jql);
	}

	@Override
	public Statement parse(String jql) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
