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

import java.util.Map;

import com.jxva.dao.annotation.Table;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-17 17:07:12 by Jxva
 */
public class TableEntry {
	
	private Table table;
	
	private String tableName;
	private String incrementColumn;
	private String firstPrimaryKeyColumn;
	
	private Class<?> model;
	private Map<String,ColumnEntry> columnEntrys;
	//user_id,user_name(以逗号分隔)
	private String columnNames;
	//userId|userName(以|分隔)
	private String specialFields;

	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}


	public void setModel(Class<?> model) {
		this.model = model;
	}

	public Class<?> getModel() {
		return model;
	}

	public String getIncrementColumn() {
		return incrementColumn;
	}

	public void setIncrementColumn(String incrementColumn) {
		this.incrementColumn = incrementColumn;
	}

	public String getFirstPrimaryKeyColumn() {
		return firstPrimaryKeyColumn;
	}

	public void setFirstPrimaryKeyColumn(String firstPrimaryKeyColumn) {
		this.firstPrimaryKeyColumn = firstPrimaryKeyColumn;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setSpecialFields(String specialFields) {
		this.specialFields = specialFields;
	}

	public String getSpecialFields() {
		return specialFields;
	}

	public void setColumnEntrys(Map<String,ColumnEntry> columnEntrys) {
		this.columnEntrys = columnEntrys;
	}

	public Map<String,ColumnEntry> getColumnEntrys() {
		return columnEntrys;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getColumnNames() {
		return columnNames;
	}


}
