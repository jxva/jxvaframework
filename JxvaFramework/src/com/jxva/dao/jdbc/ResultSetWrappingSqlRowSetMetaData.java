/*
 * Copyright 2002-2005 the original author or authors.
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

package com.jxva.dao.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * Default implementation of Spring's SqlRowSetMetaData interface.
 * Used by ResultSetWrappingSqlRowSet.
 *
 * <p>This implementation wraps a <code>javax.sql.ResultSetMetaData</code>
 * instance, catching any SQLExceptions and translating them to the
 * appropriate Spring DataAccessException.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @since 1.2
 * @see ResultSetWrappingSqlRowSet#getMetaData
 */
public class ResultSetWrappingSqlRowSetMetaData implements SqlRowSetMetaData {

	private final ResultSetMetaData resultSetMetaData;

	private String[] columnNames;


	/**
	 * Create a new ResultSetWrappingSqlRowSetMetaData object
	 * for the given ResultSetMetaData instance.
	 * @param resultSetMetaData a disconnected ResultSetMetaData instance
	 * to wrap (usually a <code>javax.sql.RowSetMetaData</code> instance)
	 * @see java.sql.ResultSet#getMetaData
	 * @see javax.sql.RowSetMetaData
	 * @see ResultSetWrappingSqlRowSet#getMetaData
	 */
	public ResultSetWrappingSqlRowSetMetaData(ResultSetMetaData resultSetMetaData) {
		this.resultSetMetaData = resultSetMetaData;
	}


	public String getCatalogName(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getCatalogName(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}
	
	public String getColumnClassName(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getColumnClassName(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public int getColumnCount() throws SQLException {
		try {
			return this.resultSetMetaData.getColumnCount();
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public String[] getColumnNames() throws SQLException {
		if (this.columnNames == null) {
			this.columnNames = new String[getColumnCount()];
			for (int i = 0; i < getColumnCount(); i++) {
				this.columnNames[i] = getColumnName(i + 1);
			}
		}
		return this.columnNames;
	}

	public int getColumnDisplaySize(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getColumnDisplaySize(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public String getColumnLabel(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getColumnLabel(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public String getColumnName(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getColumnName(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public int getColumnType(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getColumnType(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public String getColumnTypeName(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getColumnTypeName(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public int getPrecision(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getPrecision(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public int getScale(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getScale(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public String getSchemaName(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getSchemaName(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public String getTableName(int column) throws SQLException {
		try {
			return this.resultSetMetaData.getTableName(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public boolean isCaseSensitive(int column) throws SQLException {
		try {
			return this.resultSetMetaData.isCaseSensitive(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public boolean isCurrency(int column) throws SQLException {
		try {
			return this.resultSetMetaData.isCurrency(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public boolean isSigned(int column) throws SQLException {
		try {
			return this.resultSetMetaData.isSigned(column);
		}
		catch (SQLException se) {
			throw new SQLException(se);
		}
	}

	public boolean isAutoIncrement(int column) throws SQLException {
		return this.resultSetMetaData.isAutoIncrement(column);
	}


	public int isNullable(int column) throws SQLException {
		return this.resultSetMetaData.isNullable(column);
	}
	
}
