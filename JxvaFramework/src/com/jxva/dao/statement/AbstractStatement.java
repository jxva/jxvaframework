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
package com.jxva.dao.statement;

import java.util.List;

import com.jxva.dao.Statement;
import com.jxva.dao.clause.FromClause;
import com.jxva.dao.entity.Mark;
import com.jxva.dao.entry.JqlEntry;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 18:10:18 by Jxva
 */
public abstract class AbstractStatement implements Statement{
	
	protected JqlEntry jqlEntry;
	protected String fromSection;
	protected String sql;
	
	private FromClause fromClause;
	
	protected void setFromClause(FromClause fromClause){
		this.fromClause=fromClause;
	}
	
	public String getModelName(){
		return fromSection.split("[ ,]")[0];
	}

	public FromClause getFromClause() {
		return this.fromClause;
	}
	
	protected String padSubStatement(String jql){
		StringBuilder sb=new StringBuilder(jql);
		List<String> subStatements=jqlEntry.getSubStatements();
		for(String s:subStatements){
			String subSql=new SubSelectStatement(jqlEntry,s).getSql();
			int pos=sb.indexOf(Mark.SUB_STATEMENT_MARK);
			if(pos>-1){
				sb.delete(pos,pos+2);
				sb.insert(pos,subSql);
			}
		}
		return sb.toString();
	}
	
	protected String padSingleQuoteParams(String jql){
		StringBuilder sb=new StringBuilder(jql);
		List<String> singleQuoteParams=jqlEntry.getSingleQuoteParams();
		for(String s:singleQuoteParams){
			int pos=sb.indexOf(Mark.SINGLE_QUOTE_MARK);
			if(pos>-1){
				sb.delete(pos,pos+2);
				sb.insert(pos,s);
			}
		}
		return sb.toString();
	}
	
	public String getSql() {
		return this.sql;
	}
	
	public String getJql(){
		return jqlEntry.getOriginJql();
	}
}
