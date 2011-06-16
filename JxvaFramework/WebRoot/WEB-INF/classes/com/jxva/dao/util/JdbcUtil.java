/*
 * Copyright 2002-2008 the original author or authors.
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

package com.jxva.dao.util;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;

import com.jxva.dao.DataAccessException;

/**
 * Generic utility methods for working with JDBC. Mainly for internal use
 * within the framework, but also useful for custom JDBC access code.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 */
public abstract class JdbcUtil {


	/**
	 * Retrieve a JDBC column value from a ResultSet, using the specified value type.
	 * <p>Uses the specifically typed ResultSet accessor methods, falling back to
	 * {@link #getResultSetValue(java.sql.ResultSet, int)} for unknown types.
	 * <p>Note that the returned value may not be assignable to the specified
	 * required type, in case of an unknown type. Calling code needs to deal
	 * with this case appropriately, e.g. throwing a corresponding exception.
	 * @param rs is the ResultSet holding the data
	 * @param index is the column index
	 * @param requiredType the required value type (may be <code>null</code>)
	 * @return the value object
	 * @throws SQLException if thrown by the JDBC API
	 */
	public static Object getResultSetValue(ResultSet rs, int index, Class<?> requiredType) throws DataAccessException {
		try{
			if (requiredType == null) {
				return getResultSetValue(rs, index);
			}
	
			Object value = null;
			boolean wasNullCheck = false;
	
			// Explicitly extract typed value, as far as possible.
			if (String.class.equals(requiredType)) {
				value = rs.getString(index);
			}
			else if (boolean.class.equals(requiredType) || Boolean.class.equals(requiredType)) {
				value = rs.getBoolean(index);
				wasNullCheck = true;
			}
			else if (byte.class.equals(requiredType) || Byte.class.equals(requiredType)) {
				value = rs.getByte(index);
				wasNullCheck = true;
			}
			else if (short.class.equals(requiredType) || Short.class.equals(requiredType)) {
				value = rs.getShort(index);
				wasNullCheck = true;
			}
			else if (int.class.equals(requiredType) || Integer.class.equals(requiredType)) {
				value = rs.getInt(index);
				wasNullCheck = true;
			}
			else if (long.class.equals(requiredType) || Long.class.equals(requiredType)) {
				value = rs.getLong(index);
				wasNullCheck = true;
			}
			else if (float.class.equals(requiredType) || Float.class.equals(requiredType)) {
				value = rs.getFloat(index);
				wasNullCheck = true;
			}
			else if (double.class.equals(requiredType) || Double.class.equals(requiredType) ||
					Number.class.equals(requiredType)) {
				value = rs.getDouble(index);
				wasNullCheck = true;
			}
			else if (byte[].class.equals(requiredType)) {
				value = rs.getBytes(index);
			}
			else if (java.sql.Date.class.equals(requiredType)) {
				value = rs.getDate(index);
			}
			else if (java.sql.Time.class.equals(requiredType)) {
				value = rs.getTime(index);
			}
			else if (java.sql.Timestamp.class.equals(requiredType) || java.util.Date.class.equals(requiredType)) {
				value = rs.getTimestamp(index);
			}
			else if (BigDecimal.class.equals(requiredType)) {
				value = rs.getBigDecimal(index);
			}
			else if (Blob.class.equals(requiredType)) {
				value = rs.getBlob(index);
			}
			else if (Clob.class.equals(requiredType)) {
				value = rs.getClob(index);
			}
			else {
				// Some unknown type desired -> rely on getObject.
				value = getResultSetValue(rs, index);
			}
	
			// Perform was-null check if demanded (for results that the
			// JDBC driver returns as primitives).
			if (wasNullCheck && value != null && rs.wasNull()) {
				value = null;
			}
			return value;
		}catch(SQLException e){
			throw new DataAccessException(e);
		}
	}

	/**
	 * Retrieve a JDBC column value from a ResultSet, using the most appropriate
	 * value type. The returned value should be a detached value object, not having
	 * any ties to the active ResultSet: in particular, it should not be a Blob or
	 * Clob object but rather a byte array respectively String representation.
	 * <p>Uses the <code>getObject(index)</code> method, but includes additional "hacks"
	 * to get around Oracle 10g returning a non-standard object for its TIMESTAMP
	 * datatype and a <code>java.sql.Date</code> for DATE columns leaving out the
	 * time portion: These columns will explicitly be extracted as standard
	 * <code>java.sql.Timestamp</code> object.
	 * @param rs is the ResultSet holding the data
	 * @param index is the column index
	 * @return the value object
	 * @throws SQLException if thrown by the JDBC API
	 * @see java.sql.Blob
	 * @see java.sql.Clob
	 * @see java.sql.Timestamp
	 */
	public static Object getResultSetValue(ResultSet rs, int index) throws DataAccessException {
		try{
			Object obj = rs.getObject(index);
			String className = null;
			if (obj != null) {
				className = obj.getClass().getName();
			}
			if (obj instanceof Blob) {
				obj = rs.getBytes(index);
			}
			else if (obj instanceof Clob) {
				obj = rs.getString(index);
			}
			else if (className != null &&
					("oracle.sql.TIMESTAMP".equals(className) ||
					"oracle.sql.TIMESTAMPTZ".equals(className))) {
				obj = rs.getTimestamp(index);
			}
			else if (className != null && className.startsWith("oracle.sql.DATE")) {
				String metaDataClassName = rs.getMetaData().getColumnClassName(index);
				if ("java.sql.Timestamp".equals(metaDataClassName) ||
						"oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
					obj = rs.getTimestamp(index);
				}
				else {
					obj = rs.getDate(index);
				}
			}
			else if (obj != null && obj instanceof java.sql.Date) {
				if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
					obj = rs.getTimestamp(index);
				}
			}
			return obj;
		}catch(SQLException e){
			throw new DataAccessException(e);
		}
	}


	public static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws DataAccessException {
		try{
			String name = resultSetMetaData.getColumnLabel(columnIndex);
			if (name == null || name.length() < 1) {
				name = resultSetMetaData.getColumnName(columnIndex);
			}
			return name;
		}catch(SQLException e){
			throw new DataAccessException(e);
		}
	}
	

	public static <T> T requiredSingleResult(Collection<T> results) throws DataAccessException {
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			throw new DataAccessException("EmptyResultDataAccessException");
		}
		if (results.size() > 1) {
			throw new DataAccessException("IncorrectResultSizeDataAccessException");
		}
		return results.iterator().next();
	}
	
	public static boolean supportsBatchUpdates(Connection con) {
		try {
			DatabaseMetaData dbmd = con.getMetaData();
			if (dbmd != null) {
				if (dbmd.supportsBatchUpdates()) {
					System.out.println("JDBC driver supports batch updates");
					return true;
				}
				else {
					System.out.println("JDBC driver does not support batch updates");
				}
			}
		}
		catch (SQLException ex) {
			System.out.println("JDBC driver 'supportsBatchUpdates' method threw exception");
		}
		catch (AbstractMethodError err) {
			System.out.println("JDBC driver does not support JDBC 2.0 'supportsBatchUpdates' method");
		}
		return false;
	}
}
