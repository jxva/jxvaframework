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

import java.util.List;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-25 08:12:52 by Jxva
 */
public class AST {

	private List<String> subSelect;
	private List<String> params;
	private String select;
	private String from;
	private String condition;	
	private boolean isSubSelect;
	private boolean hasExistCache;
	private String jql;
	
	public List<String> getSubSelect() {
		return subSelect;
	}
	public void setSubSelect(List<String> subSelect) {
		this.subSelect = subSelect;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}

	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public void setIsSubSelect(boolean isSubSelect) {
		this.isSubSelect = isSubSelect;
	}
	public boolean isSubSelect() {
		return isSubSelect;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFrom() {
		return from;
	}
	public void setJql(String jql) {
		this.jql = jql;
	}
	public String getJql() {
		return jql;
	}
	public void setHasExistCache(boolean hasExistCache) {
		this.hasExistCache = hasExistCache;
	}
	public boolean hasExistCache() {
		return hasExistCache;
	}
}
