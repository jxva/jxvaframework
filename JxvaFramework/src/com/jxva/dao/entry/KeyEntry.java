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


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-09 12:59:48 by Jxva
 */
public class KeyEntry{

	private int index;
	private String primaryKeyTableName;
	private String primaryKeyColumnName;
	private String foreignKeyTableName;
	private String foreignKeyColumnName;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getPrimaryKeyTableName() {
		return primaryKeyTableName;
	}
	public void setPrimaryKeyTableName(String primaryKeyTableName) {
		this.primaryKeyTableName = primaryKeyTableName;
	}
	public String getPrimaryKeyColumnName() {
		return primaryKeyColumnName;
	}
	public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
		this.primaryKeyColumnName = primaryKeyColumnName;
	}
	public String getForeignKeyTableName() {
		return foreignKeyTableName;
	}
	public void setForeignKeyTableName(String foreignKeyTableName) {
		this.foreignKeyTableName = foreignKeyTableName;
	}
	public String getForeignKeyColumnName() {
		return foreignKeyColumnName;
	}
	public void setForeignKeyColumnName(String foreignKeyColumnName) {
		this.foreignKeyColumnName = foreignKeyColumnName;
	}


}
