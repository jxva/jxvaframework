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
package net.jxva.dao.sqlparse.entry;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-26 09:06:30 by Jxva
 */
public class FromEntry {
	
	private String prefixName;
	private String modelName;
	private String aliasName;
	private int relation;
	private boolean isFetch;
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public int getRelation() {
		return relation;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public boolean isFetch() {
		return isFetch;
	}
	public void setIsFetch(boolean isFetch) {
		this.isFetch = isFetch;
	}
	
	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}
	public String getPrefixName() {
		return prefixName;
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("[ ");
		sb.append("prefixName=").append(prefixName).append(',');
		sb.append("modelName=").append(modelName).append(',');
		sb.append("aliasName=").append(aliasName).append(',');
		sb.append("relation=").append(relation).append(',');
		sb.append("isFetch=").append(isFetch).append(" ]");
		return sb.toString();
	}
}
