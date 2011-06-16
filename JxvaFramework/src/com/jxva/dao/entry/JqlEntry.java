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
package com.jxva.dao.entry;

import java.util.List;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 14:51:33 by Jxva
 */
public class JqlEntry {
	
	private List<String> singleQuoteParams;//替换为??
	private List<String> subStatements;//替换为!!
	private List<String> namedParams;//替换为@@
	private String originJql;
	private String pureJql;
	private String finalJql;
	
	public JqlEntry(String originJql){
		this.originJql=originJql;
	}
	
	public List<String> getSingleQuoteParams() {
		return singleQuoteParams;
	}
	public void setSingleQuoteParams(List<String> singleQuoteParams) {
		this.singleQuoteParams = singleQuoteParams;
	}
	public List<String> getSubStatements() {
		return subStatements;
	}
	public void setSubStatements(List<String> subStatements) {
		this.subStatements = subStatements;
	}
	public List<String> getNamedParams() {
		return namedParams;
	}
	public void setNamedParams(List<String> namedParams) {
		this.namedParams = namedParams;
	}
	public String getPureJql() {
		return pureJql;
	}
	public void setPureJql(String pureJql) {
		this.pureJql = pureJql;
	}
	public String getFinalJql() {
		return finalJql;
	}
	public void setFinalJql(String finalJql) {
		this.finalJql = finalJql;
	}
	public void setOriginJql(String originJql) {
		this.originJql = originJql;
	}
	public String getOriginJql() {
		return originJql;
	}	
}
