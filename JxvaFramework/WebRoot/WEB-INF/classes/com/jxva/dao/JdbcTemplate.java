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
 *
 */
package com.jxva.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.jxva.dao.jdbc.BatchPreparedStatementSetter;
import com.jxva.dao.jdbc.CallableStatementCallback;
import com.jxva.dao.jdbc.CallableStatementCreator;
import com.jxva.dao.jdbc.CollectionFactory;
import com.jxva.dao.jdbc.ColumnMapRowMapper;
import com.jxva.dao.jdbc.ConnectionCallback;
import com.jxva.dao.jdbc.ConnectionProxy;
import com.jxva.dao.jdbc.InterruptibleBatchPreparedStatementSetter;
import com.jxva.dao.jdbc.JdbcOperations;
import com.jxva.dao.jdbc.KeyHolder;
import com.jxva.dao.jdbc.NativeJdbcExtractor;
import com.jxva.dao.jdbc.ParameterDisposer;
import com.jxva.dao.jdbc.PreparedStatementCallback;
import com.jxva.dao.jdbc.PreparedStatementCreator;
import com.jxva.dao.jdbc.PreparedStatementSetter;
import com.jxva.dao.jdbc.ResultSetCallback;
import com.jxva.dao.jdbc.ResultSetCreator;
import com.jxva.dao.jdbc.ResultSetExtractor;
import com.jxva.dao.jdbc.ResultSetSupportingSqlParameter;
import com.jxva.dao.jdbc.RowCallbackHandler;
import com.jxva.dao.jdbc.RowMapper;
import com.jxva.dao.jdbc.RowMapperResultSetExtractor;
import com.jxva.dao.jdbc.SingleColumnRowMapper;
import com.jxva.dao.jdbc.SqlOutParameter;
import com.jxva.dao.jdbc.SqlParameter;
import com.jxva.dao.jdbc.SqlParameterValue;
import com.jxva.dao.jdbc.SqlProvider;
import com.jxva.dao.jdbc.SqlReturnResultSet;
import com.jxva.dao.jdbc.SqlReturnUpdateCount;
import com.jxva.dao.jdbc.SqlRowSet;
import com.jxva.dao.jdbc.SqlRowSetResultSetExtractor;
import com.jxva.dao.jdbc.SqlTypeValue;
import com.jxva.dao.jdbc.StatementCallback;
import com.jxva.dao.util.JdbcUtil;
import com.jxva.dao.util.StatementCreatorUtil;
import com.jxva.log.Logger;
/**
 * 
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-02 11:01:58 by Jxva
 */
public class JdbcTemplate implements JdbcOperations{
		
	private static final String RETURN_RESULT_SET_PREFIX = "#result-set-";

	private static final String RETURN_UPDATE_COUNT_PREFIX = "#update-count-";
	
	private static final Logger log=Logger.getLogger(JdbcTemplate.class);

	private NativeJdbcExtractor nativeJdbcExtractor;
	
	private boolean lazyInit = true;

	private boolean ignoreWarnings = true;

	private int fetchSize = 0;

	private int maxRows = 0;

	private int queryTimeout = 0;
	
	private boolean showSql=false;

	private boolean skipResultsProcessing = false;

	private boolean skipUndeclaredResults = false;

	private boolean resultsMapCaseInsensitive = false;


	private Connection connection;
	
	public JdbcTemplate(){
		
	}
		
	public JdbcTemplate(Connection connection) {
		this.connection=connection;
	}

	public JdbcTemplate(DataSource dataSource) throws DataAccessException {
		try {
			this.connection=dataSource.getConnection();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public JdbcTemplate(DataSource dataSource, boolean lazyInit) throws DataAccessException {
		this(dataSource);
		setLazyInit(lazyInit);
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public void setConnection(Connection connection){
		this.connection=connection;
	}
	
	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}

	public boolean isLazyInit() {
		return this.lazyInit;
	}
	

	public void setNativeJdbcExtractor(NativeJdbcExtractor extractor) {
		this.nativeJdbcExtractor = extractor;
	}

	public NativeJdbcExtractor getNativeJdbcExtractor() {
		return this.nativeJdbcExtractor;
	}

	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}

	public boolean isIgnoreWarnings() {
		return this.ignoreWarnings;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getFetchSize() {
		return this.fetchSize;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public int getMaxRows() {
		return this.maxRows;
	}

	public void setQueryTimeout(int queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	public int getQueryTimeout() {
		return this.queryTimeout;
	}

	public void setSkipResultsProcessing(boolean skipResultsProcessing) {
		this.skipResultsProcessing = skipResultsProcessing;
	}

	public boolean isSkipResultsProcessing() {
		return this.skipResultsProcessing;
	}

	public void setSkipUndeclaredResults(boolean skipUndeclaredResults) {
		this.skipUndeclaredResults = skipUndeclaredResults;
	}

	public boolean isSkipUndeclaredResults() {
		return this.skipUndeclaredResults;
	}

	public void setResultsMapCaseInsensitive(boolean resultsMapCaseInsensitive) {
		this.resultsMapCaseInsensitive = resultsMapCaseInsensitive;
	}

	public boolean isResultsMapCaseInsensitive() {
		return this.resultsMapCaseInsensitive;
	}


	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}

	public boolean getShowSql() {
		return showSql;
	}

	//-------------------------------------------------------------------------
	// Methods dealing with a plain java.sql.Connection
	//-------------------------------------------------------------------------

	public <T> T execute(ConnectionCallback<T> action) throws DataAccessException {
		//Assert.notNull(action, "Callback object must not be null");
		Connection conToUse = connection;
		if (this.nativeJdbcExtractor != null) {
			// Extract native JDBC Connection, castable to OracleConnection or the like.
			conToUse = this.nativeJdbcExtractor.getNativeConnection(connection);
		}else {
			// Create close-suppressing Connection proxy, also preparing returned Statements.
			conToUse = createConnectionProxy(connection);
		}
		return action.doInConnection(conToUse);
	}

	/**
	 * Create a close-suppressing proxy for the given JDBC Connection.
	 * Called by the <code>execute</code> method.
	 * <p>The proxy also prepares returned JDBC Statements, applying
	 * statement settings such as fetch size, max rows, and query timeout.
	 * @param con the JDBC Connection to create a proxy for
	 * @return the Connection proxy
	 * @see java.sql.Connection#close()
	 * @see #execute(ConnectionCallback)
	 * @see #applyStatementSettings
	 */
	protected Connection createConnectionProxy(Connection con) {
		return (Connection) Proxy.newProxyInstance(
				ConnectionProxy.class.getClassLoader(),
				new Class[] {ConnectionProxy.class},
				new CloseSuppressingInvocationHandler(con));
	}


	//-------------------------------------------------------------------------
	// Methods dealing with static SQL (java.sql.Statement)
	//-------------------------------------------------------------------------

	public <T> T execute(StatementCallback<T> action) throws DataAccessException {
		//Assert.notNull(action, "Callback object must not be null");
		Statement stmt = null;
		try {
			Connection conToUse = connection;
			if (this.nativeJdbcExtractor != null &&
					this.nativeJdbcExtractor.isNativeConnectionNecessaryForNativeStatements()) {
				conToUse = this.nativeJdbcExtractor.getNativeConnection(connection);
			}
			stmt = conToUse.createStatement();
			applyStatementSettings(stmt);
			Statement stmtToUse = stmt;
			if (this.nativeJdbcExtractor != null) {
				stmtToUse = this.nativeJdbcExtractor.getNativeStatement(stmt);
			}
			T result = action.doInStatement(stmtToUse);
			handleWarnings(stmt);
			return result;
		}catch(SQLException e){
			throw new DataAccessException(e);
		}finally {
			if(stmt!=null){try{stmt.close();stmt=null;}catch(SQLException e){}}
		}
	}
	
	public <T> T execute(ResultSetCreator rsc,ResultSetCallback<T> callback)throws DataAccessException{
		ResultSet rs=null;
		try {
			rs=rsc.createResultSet(connection);
			return callback.doInResultSet(rs);
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException e){}
		}
	}

	public void execute(final String sql) throws DataAccessException {
		class ExecuteStatementCallback implements StatementCallback<Object>, SqlProvider {
			public Object doInStatement(Statement stmt) throws SQLException, DataAccessException {
				if(showSql)log.info("SQL -> " + sql);
				stmt.execute(sql);
				return null;
			}
			public String getSql() {
				return sql;
			}
		}
		execute(new ExecuteStatementCallback());
	}

	public <T> T query(final String sql, final ResultSetExtractor<T> rse) throws DataAccessException {
		//Assert.notNull(sql, "SQL must not be null");
		//Assert.notNull(rse, "ResultSetExtractor must not be null");
		if(showSql)log.info("SQL -> " + sql);
		class QueryStatementCallback implements StatementCallback<T>, SqlProvider {
			public T doInStatement(Statement stmt) throws SQLException, DataAccessException {
				ResultSet rs = null;
				try {
					rs = stmt.executeQuery(sql);
					ResultSet rsToUse = rs;
					if (nativeJdbcExtractor != null) {
						rsToUse = nativeJdbcExtractor.getNativeResultSet(rs);
					}
					return rse.extractData(rsToUse);
				}
				finally {
					if(rs!=null){rs.close();rs=null;}
				}
			}
			public String getSql() {
				return sql;
			}
		}
		return execute(new QueryStatementCallback());
	}

	public void query(String sql, RowCallbackHandler rch) throws DataAccessException {
		query(sql, new RowCallbackHandlerResultSetExtractor(rch));
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return query(sql, new RowMapperResultSetExtractor<T>(rowMapper));
	}

	public Map<String, Object> queryForMap(String sql) throws DataAccessException {
		return queryForObject(sql, getColumnMapRowMapper());
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		List<T> results = query(sql, rowMapper);
		return JdbcUtil.requiredSingleResult(results);
	}

	public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {
		return queryForObject(sql, getSingleColumnRowMapper(requiredType));
	}

	public long queryForLong(String sql) throws DataAccessException {
		Number number = queryForObject(sql, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public int queryForInt(String sql) throws DataAccessException {
		Number number = queryForObject(sql, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
		return query(sql, getSingleColumnRowMapper(elementType));
	}

	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
		return query(sql, getColumnMapRowMapper());
	}

	/**
	 *  spring jdbcTemplate返回RS
		SqlRowSet rs=jdbcTemplate.queryForRowSet(sql);
		  int i=1;
		  while(rs.next())
		  {
		   question+=i+":"+rs.getString(2)+rs.getString(3)+"\n";
		   i++;
		  } 
	 */
	public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
		return query(sql, new SqlRowSetResultSetExtractor());
	}

	public int update(final String sql) throws DataAccessException {
		//Assert.notNull(sql, "SQL must not be null");
		class UpdateStatementCallback implements StatementCallback<Integer>, SqlProvider {
			public Integer doInStatement(Statement stmt) throws SQLException, DataAccessException {
				if(showSql)log.info("SQL -> " + sql);
				return stmt.executeUpdate(sql);
			}
			public String getSql() {
				return sql;
			}
		}
		return execute(new UpdateStatementCallback());
	}

	public int[] batchUpdate(final String[] sql) throws DataAccessException {
		//Assert.notEmpty(sql, "SQL array must not be empty");
		if(showSql)log.info("Executing SQL batch update of " + sql.length + " statements");
		class BatchUpdateStatementCallback implements StatementCallback<int[]>, SqlProvider {
			private String currSql;
			public int[] doInStatement(Statement stmt) throws SQLException, DataAccessException{
				int[] rowsAffected = new int[sql.length];
				if (JdbcUtil.supportsBatchUpdates(stmt.getConnection())) {
					for (String sqlStmt : sql) {
						this.currSql = sqlStmt;
						if(showSql)log.info("SQL -> " + currSql);
						stmt.addBatch(sqlStmt);
					}
					rowsAffected = stmt.executeBatch();
				}
				else {
					for (int i = 0; i < sql.length; i++) {
						this.currSql = sql[i];
						if(showSql)log.info("SQL -> " + currSql);
						if (!stmt.execute(sql[i])) {
							rowsAffected[i] = stmt.getUpdateCount();
						}else {
							throw new SQLException("Invalid batch SQL statement: " + sql[i]);
						}
					}
				}
				return rowsAffected;
			}
			public String getSql() {
				return this.currSql;
			}
		}
		return execute(new BatchUpdateStatementCallback());
	}


	//-------------------------------------------------------------------------
	// Methods dealing with prepared statements
	//-------------------------------------------------------------------------

	public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action)
			throws DataAccessException {

		//Assert.notNull(psc, "PreparedStatementCreator must not be null");
		//Assert.notNull(action, "Callback object must not be null");

		PreparedStatement ps = null;
		try {
			Connection conToUse = connection;
			if (this.nativeJdbcExtractor != null &&
					this.nativeJdbcExtractor.isNativeConnectionNecessaryForNativePreparedStatements()) {
				conToUse = this.nativeJdbcExtractor.getNativeConnection(connection);
			}
			ps = psc.createPreparedStatement(conToUse);
			applyStatementSettings(ps);
			PreparedStatement psToUse = ps;
			if (this.nativeJdbcExtractor != null) {
				psToUse = this.nativeJdbcExtractor.getNativePreparedStatement(ps);
			}
			T result = action.doInPreparedStatement(psToUse);
			handleWarnings(ps);
			return result;
		}catch(SQLException e){
			throw new DataAccessException(e);
		}finally {
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			if(ps!=null){try{ps.close();ps=null;}catch(SQLException e){}}
		}
	}

	public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {
		if(showSql)log.info("SQL -> " + sql);
		return execute(new SimplePreparedStatementCreator(sql), action);
	}

	/**
	 * Query using a prepared statement, allowing for a PreparedStatementCreator
	 * and a PreparedStatementSetter. Most other query methods use this method,
	 * but application code will always work with either a creator or a setter.
	 * @param psc Callback handler that can create a PreparedStatement given a
	 * Connection
	 * @param pss object that knows how to set values on the prepared statement.
	 * If this is null, the SQL will be assumed to contain no bind parameters.
	 * @param rse object that will extract results.
	 * @return an arbitrary result object, as returned by the ResultSetExtractor
	 * @throws DataAccessException if there is any problem
	 */
	protected <T> T query(
			PreparedStatementCreator psc, final PreparedStatementSetter pss, final ResultSetExtractor<T> rse)
			throws DataAccessException {

		//Assert.notNull(rse, "ResultSetExtractor must not be null");
		//System.out.println("Executing prepared SQL query");

		return execute(psc, new PreparedStatementCallback<T>() {
			public T doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ResultSet rs = null;
				try {
					if (pss != null) {
						pss.setValues(ps);
					}
					rs = ps.executeQuery();
					ResultSet rsToUse = rs;
					if (nativeJdbcExtractor != null) {
						rsToUse = nativeJdbcExtractor.getNativeResultSet(rs);
					}
					return rse.extractData(rsToUse);
				}
				finally {
					if(rs!=null){rs.close();rs=null;}
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
			}
		});
	}

	public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws DataAccessException {
		return query(psc, null, rse);
	}

	public <T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException {
		if(showSql)log.info("SQL -> " + sql);
		return query(new SimplePreparedStatementCreator(sql), pss, rse);
	}

	public <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse) throws DataAccessException {
		return query(sql, new ArgTypePreparedStatementSetter(args, argTypes), rse);
	}

	public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException {
		return query(sql, new ArgPreparedStatementSetter(args), rse);
	}

	public void query(PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException {
		query(psc, new RowCallbackHandlerResultSetExtractor(rch));
	}

	public void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {
		query(sql, pss, new RowCallbackHandlerResultSetExtractor(rch));
	}

	public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {
		query(sql, new ArgTypePreparedStatementSetter(args, argTypes), rch);
	}

	public void query(String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException {
		query(sql, new ArgPreparedStatementSetter(args), rch);
	}

	public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) throws DataAccessException {
		return query(psc, new RowMapperResultSetExtractor<T>(rowMapper));
	}

	public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
		return query(sql, pss, new RowMapperResultSetExtractor<T>(rowMapper));
	}

	public <T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
		return query(sql, args, argTypes, new RowMapperResultSetExtractor<T>(rowMapper));
	}

	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		return query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper));
	}

	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws DataAccessException {

		List<T> results = query(sql, args, argTypes, new RowMapperResultSetExtractor<T>(rowMapper, 1));
		return JdbcUtil.requiredSingleResult(results);
	}

	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		List<T> results = query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));
		return JdbcUtil.requiredSingleResult(results);
	}

	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
			throws DataAccessException {

		return queryForObject(sql, args, argTypes, getSingleColumnRowMapper(requiredType));
	}

	public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
		return queryForObject(sql, args, getSingleColumnRowMapper(requiredType));
	}

	public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		return queryForObject(sql, args, argTypes, getColumnMapRowMapper());
	}

	public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
		return queryForObject(sql, args, getColumnMapRowMapper());
	}

	public long queryForLong(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		Number number = queryForObject(sql, args, argTypes, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public long queryForLong(String sql, Object... args) throws DataAccessException {
		Number number = queryForObject(sql, args, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	public int queryForInt(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		Number number = queryForObject(sql, args, argTypes, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public int queryForInt(String sql, Object... args) throws DataAccessException {
		Number number = queryForObject(sql, args, Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException {
		return query(sql, args, argTypes, getSingleColumnRowMapper(elementType));
	}

	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {
		return query(sql, args, getSingleColumnRowMapper(elementType));
	}

	public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		return query(sql, args, argTypes, getColumnMapRowMapper());
	}

	public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {
		return query(sql, args, getColumnMapRowMapper());
	}

	public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		return query(sql, args, argTypes, new SqlRowSetResultSetExtractor());
	}

	public SqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException {
		return query(sql, args, new SqlRowSetResultSetExtractor());
	}

	protected int update(final PreparedStatementCreator psc, final PreparedStatementSetter pss) throws DataAccessException {
		return execute(psc, new PreparedStatementCallback<Integer>() {
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException {
				try {
					if (pss != null) {
						pss.setValues(ps);
					}
					return ps.executeUpdate();
				}finally {
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
			}
		});
	}

	public int update(PreparedStatementCreator psc) throws DataAccessException {
		return update(psc, (PreparedStatementSetter) null);
	}

	public int update(final PreparedStatementCreator psc, final KeyHolder generatedKeyHolder)
			throws DataAccessException {

		//Assert.notNull(generatedKeyHolder, "KeyHolder must not be null");
		//System.out.println("Executing SQL update and returning generated keys");

		return execute(psc, new PreparedStatementCallback<Integer>() {
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				int rows = ps.executeUpdate();
				List<Map<String, Object>> generatedKeys = generatedKeyHolder.getKeyList();
				generatedKeys.clear();
				ResultSet keys = ps.getGeneratedKeys();
				if (keys != null) {
					try {
						RowMapperResultSetExtractor<Map<String, Object>> rse =
								new RowMapperResultSetExtractor<Map<String, Object>>(getColumnMapRowMapper(), 1);
						generatedKeys.addAll(rse.extractData(keys));
					}finally {
						if(keys!=null){keys.close();keys=null;}
					}
				}

				//System.out.println("SQL update affected " + rows + " rows and returned " + generatedKeys.size() + " keys");
				return rows;
			}
		});
	}

	public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {
		if(showSql)log.info("SQL -> " + sql);
		return update(new SimplePreparedStatementCreator(sql), pss);
	}

	public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {
		return update(sql, new ArgTypePreparedStatementSetter(args, argTypes));
	}

	public int update(String sql, Object... args) throws DataAccessException {
		return update(sql, new ArgPreparedStatementSetter(args));
	}

	public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss) throws DataAccessException {

		if(showSql)log.info("Executing SQL batch update [" + sql + "]");

		return execute(sql, new PreparedStatementCallback<int[]>() {
			public int[] doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				try {
					int batchSize = pss.getBatchSize();
					InterruptibleBatchPreparedStatementSetter ipss =
							(pss instanceof InterruptibleBatchPreparedStatementSetter ?
							(InterruptibleBatchPreparedStatementSetter) pss : null);
					if (JdbcUtil.supportsBatchUpdates(ps.getConnection())) {
						for (int i = 0; i < batchSize; i++) {
							pss.setValues(ps, i);
							if (ipss != null && ipss.isBatchExhausted(i)) {
								break;
							}
							ps.addBatch();
						}
						return ps.executeBatch();
					}
					else {
						List<Integer> rowsAffected = new ArrayList<Integer>();
						for (int i = 0; i < batchSize; i++) {
							pss.setValues(ps, i);
							if (ipss != null && ipss.isBatchExhausted(i)) {
								break;
							}
							rowsAffected.add(ps.executeUpdate());
						}
						int[] rowsAffectedArray = new int[rowsAffected.size()];
						for (int i = 0; i < rowsAffectedArray.length; i++) {
							rowsAffectedArray[i] = rowsAffected.get(i);
						}
						return rowsAffectedArray;
					}
				}
				finally {
					if (pss instanceof ParameterDisposer) {
						((ParameterDisposer) pss).cleanupParameters();
					}
				}
			}
		});
	}


	//-------------------------------------------------------------------------
	// Methods dealing with callable statements
	//-------------------------------------------------------------------------

	public <T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action)
			throws DataAccessException {
		
		//Assert.notNull(csc, "CallableStatementCreator must not be null");
		//Assert.notNull(action, "Callback object must not be null");

		String sql1 = getSql(csc);
		if(showSql)log.info("Calling stored procedure" + (sql1 != null ? " [" + sql1  + "]" : ""));

		CallableStatement cs = null;
		try {
			Connection conToUse = connection;
			if (this.nativeJdbcExtractor != null) {
				conToUse = this.nativeJdbcExtractor.getNativeConnection(connection);
			}
			cs = csc.createCallableStatement(conToUse);
			applyStatementSettings(cs);
			CallableStatement csToUse = cs;
			if (this.nativeJdbcExtractor != null) {
				csToUse = this.nativeJdbcExtractor.getNativeCallableStatement(cs);
			}
			T result = action.doInCallableStatement(csToUse);
			handleWarnings(cs);
			return result;
		}catch(SQLException e){
			throw new DataAccessException(e);
		}finally {
			if (csc instanceof ParameterDisposer) {
				((ParameterDisposer) csc).cleanupParameters();
			}
			if(cs!=null){try{cs.close();cs=null;}catch(SQLException e){}}
		}
	}

	public <T> T execute(String callString, CallableStatementCallback<T> action) throws DataAccessException {
		return execute(new SimpleCallableStatementCreator(callString), action);
	}

	public Map<String, Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters)
			throws DataAccessException {

		final List<SqlParameter> updateCountParameters = new ArrayList<SqlParameter>();
		final List<SqlParameter> resultSetParameters = new ArrayList<SqlParameter>();
		final List<SqlParameter> callParameters = new ArrayList<SqlParameter>();
		for (SqlParameter parameter : declaredParameters) {
			if (parameter.isResultsParameter()) {
				if (parameter instanceof SqlReturnResultSet) {
					resultSetParameters.add(parameter);
				}
				else {
					updateCountParameters.add(parameter);					
				}
			}
			else {
				callParameters.add(parameter);
			}
		}
		return execute(csc, new CallableStatementCallback<Map<String, Object>>() {
			public Map<String, Object> doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				boolean retVal = cs.execute();
				int updateCount = cs.getUpdateCount();

				if(showSql)log.info("CallableStatement.execute() returned '" + retVal + "'");
				if(showSql)log.info("CallableStatement.getUpdateCount() returned " + updateCount);

				Map<String, Object> returnedResults = createResultsMap();
				if (retVal || updateCount != -1) {
					returnedResults.putAll(extractReturnedResults(cs, updateCountParameters, resultSetParameters, updateCount));
				}
				returnedResults.putAll(extractOutputParameters(cs, callParameters));
				return returnedResults;
			}
		});
	}

	/**
	 * Extract returned ResultSets from the completed stored procedure.
	 * @param cs JDBC wrapper for the stored procedure
	 * @param updateCountParameters Parameter list of declared update count parameters for the stored procedure
	 * @param resultSetParameters Parameter list of declared resturn resultSet parameters for the stored procedure
	 * @return Map that contains returned results
	 */
	protected Map<String, Object> extractReturnedResults(
			CallableStatement cs, List<SqlParameter> updateCountParameters, List<SqlParameter> resultSetParameters, int updateCount)
			throws SQLException {

		Map<String, Object> returnedResults = new HashMap<String, Object>();
		int rsIndex = 0;
		int updateIndex = 0;
		boolean moreResults;
		if (!skipResultsProcessing) {
			do {
				if (updateCount == -1) {
					if (resultSetParameters != null && resultSetParameters.size() > rsIndex) {
						SqlReturnResultSet<?> declaredRsParam = (SqlReturnResultSet<?>)resultSetParameters.get(rsIndex);
						returnedResults.putAll(processResultSet(cs.getResultSet(), declaredRsParam));
						rsIndex++;
					}
					else {
						if (!skipUndeclaredResults) {
							String rsName = RETURN_RESULT_SET_PREFIX + (rsIndex + 1);
							SqlReturnResultSet<?> undeclaredRsParam = new SqlReturnResultSet(rsName, new ColumnMapRowMapper());
							//System.out.println("Added default SqlReturnResultSet parameter named " + rsName);
							returnedResults.putAll(processResultSet(cs.getResultSet(), undeclaredRsParam));
							rsIndex++;
						}
					}
				}
				else {
					if (updateCountParameters != null && updateCountParameters.size() > updateIndex) {
						SqlReturnUpdateCount ucParam = (SqlReturnUpdateCount)updateCountParameters.get(updateIndex);
						String declaredUcName = ucParam.getName();
						returnedResults.put(declaredUcName, updateCount);
						updateIndex++;
					}
					else {
						if (!skipUndeclaredResults) {
							String undeclaredUcName = RETURN_UPDATE_COUNT_PREFIX + (updateIndex + 1);
							if(showSql)log.info("Added default SqlReturnUpdateCount parameter named " + undeclaredUcName);
							returnedResults.put(undeclaredUcName, updateCount);
							updateIndex++;
						}
					}
				}
				moreResults = cs.getMoreResults();
				updateCount = cs.getUpdateCount();
				if(showSql)log.info("CallableStatement.getUpdateCount() returned " + updateCount);
			}
			while (moreResults || updateCount != -1);
		}
		return returnedResults;
	}

	/**
	 * Extract output parameters from the completed stored procedure.
	 * @param cs JDBC wrapper for the stored procedure
	 * @param parameters parameter list for the stored procedure
	 * @return Map that contains returned results
	 */
	protected Map<String, Object> extractOutputParameters(CallableStatement cs, List<SqlParameter> parameters)
		throws SQLException {

		Map<String, Object> returnedResults = new HashMap<String, Object>();
		int sqlColIndex = 1;
		for (SqlParameter param : parameters) {
			if (param instanceof SqlOutParameter) {
				SqlOutParameter<?> outParam = (SqlOutParameter<?>) param;
				if (outParam.isReturnTypeSupported()) {
					Object out = outParam.getSqlReturnType().getTypeValue(
							cs, sqlColIndex, outParam.getSqlType(), outParam.getTypeName());
					returnedResults.put(outParam.getName(), out);
				}
				else {
					Object out = cs.getObject(sqlColIndex);
					if (out instanceof ResultSet) {
						if (outParam.isResultSetSupported()) {
							returnedResults.putAll(processResultSet((ResultSet) out, outParam));
						}
						else {
							String rsName = outParam.getName();
							SqlReturnResultSet<?> rsParam = new SqlReturnResultSet(rsName, new ColumnMapRowMapper());
							returnedResults.putAll(processResultSet(cs.getResultSet(), rsParam));
							if(showSql)log.info("Added default SqlReturnResultSet parameter named " + rsName);
						}
					}
					else {
						returnedResults.put(outParam.getName(), out);
					}
				}
			}
			if (!(param.isResultsParameter())) {
				sqlColIndex++;
			}
		}
		return returnedResults;
	}

	/**
	 * Process the given ResultSet from a stored procedure.
	 * @param rs the ResultSet to process
	 * @param param the corresponding stored procedure parameter
	 * @return Map that contains returned results
	 */
	protected <T> Map<String, Object> processResultSet(ResultSet rs, ResultSetSupportingSqlParameter<T> param) throws SQLException {
		if (rs == null) {
			return Collections.emptyMap();
		}
		Map<String, Object> returnedResults = new HashMap<String, Object>();
		try {
			ResultSet rsToUse = rs;
			if (this.nativeJdbcExtractor != null) {
				rsToUse = this.nativeJdbcExtractor.getNativeResultSet(rs);
			}
			if (param.getRowMapper() != null) {
				RowMapper<T> rowMapper = param.getRowMapper();
				Object result = (new RowMapperResultSetExtractor<T>(rowMapper)).extractData(rsToUse);
				returnedResults.put(param.getName(), result);
			}
			else if (param.getRowCallbackHandler() != null) {
				RowCallbackHandler rch = param.getRowCallbackHandler();
				(new RowCallbackHandlerResultSetExtractor(rch)).extractData(rsToUse);
				returnedResults.put(param.getName(), "ResultSet returned from stored procedure was processed");
			}
			else if (param.getResultSetExtractor() != null) {
				Object result = param.getResultSetExtractor().extractData(rsToUse);
				returnedResults.put(param.getName(), result);
			}
		}
		finally {
			if(rs!=null){rs.close();rs=null;}
		}
		return returnedResults;
	}


	//-------------------------------------------------------------------------
	// Implementation hooks and helper methods
	//-------------------------------------------------------------------------

	/**
	 * Create a new RowMapper for reading columns as key-value pairs.
	 * @return the RowMapper to use
	 * @see ColumnMapRowMapper
	 */
	protected RowMapper<Map<String, Object>> getColumnMapRowMapper() {
		return new ColumnMapRowMapper();
	}

	/**
	 * Create a new RowMapper for reading result objects from a single column.
	 * @param requiredType the type that each result object is expected to match
	 * @return the RowMapper to use
	 * @see SingleColumnRowMapper
	 */
	protected <T> RowMapper<T> getSingleColumnRowMapper(Class<T> requiredType) {
		return new SingleColumnRowMapper<T>(requiredType);
	}

	/**
	 * Create a Map instance to be used as results map.
	 * <p>If "isResultsMapCaseInsensitive" has been set to true, a linked case-insensitive Map
	 * will be created if possible, else a plain HashMap (see Spring's CollectionFactory).
	 * @return the results Map instance
	 * @see #setResultsMapCaseInsensitive
	 * @see com.jxva.dao.jdbc.CollectionFactory#createLinkedCaseInsensitiveMapIfPossible
	 */
	protected Map<String, Object> createResultsMap() {
		if (isResultsMapCaseInsensitive()) {
			return CollectionFactory.createLinkedCaseInsensitiveMapIfPossible(16);
		}else {
			return new LinkedHashMap<String, Object>();
		}
	}

	/**
	 * Prepare the given JDBC Statement (or PreparedStatement or CallableStatement),
	 * applying statement settings such as fetch size, max rows, and query timeout.
	 * @param stmt the JDBC Statement to prepare
	 * @throws DataAccessException if thrown by JDBC API
	 * @see #setFetchSize
	 * @see #setMaxRows
	 * @see #setQueryTimeout
	 * @see com.jxva.dao.jdbc.util.springframework.jdbc.datasource.DataSourceUtils#applyTransactionTimeout
	 */
	protected void applyStatementSettings(Statement stmt) throws SQLException {
		int fetchSize = getFetchSize();
		if (fetchSize > 0) {
			//System.out.println("fetchSize:"+fetchSize);
			stmt.setFetchSize(fetchSize);
		}
		int maxRows = getMaxRows();
		if (maxRows > 0) {
			//System.out.println("maxRows:"+maxRows);
			stmt.setMaxRows(maxRows);
		}
		//DataSourceUtils.applyTimeout(stmt, getDataSource(), getQueryTimeout());
		stmt.setQueryTimeout(getQueryTimeout());
	}

	/**
	 * Throw an SQLWarningException if we're not ignoring warnings,
	 * else log the warnings (at debug level).
	 * @param stmt the current JDBC statement
	 * @throws SQLWarningException if not ignoring warnings
	 * @see com.jxva.dao.jdbc.exception.springframework.jdbc.SQLWarningException
	 */
	protected void handleWarnings(Statement stmt) throws SQLException {
		if (isIgnoreWarnings()) {
			//if (logger.isDebugEnabled()) {
				SQLWarning warningToLog = stmt.getWarnings();
				while (warningToLog != null) {
					if(showSql)log.warn("SQLWarning ignored: SQL state '" + warningToLog.getSQLState() + "', error code '" +
							warningToLog.getErrorCode() + "', message [" + warningToLog.getMessage() + "]");
					warningToLog = warningToLog.getNextWarning();
				}
			//}
		}else {
			handleWarnings(stmt.getWarnings());
		}
	}

	/**
	 * Throw an SQLWarningException if encountering an actual warning.
	 * @param warning the warnings object from the current statement.
	 * May be <code>null</code>, in which case this method does nothing.
	 * @throws SQLWarningException in case of an actual warning to be raised
	 */
	protected void handleWarnings(SQLWarning warning) throws SQLException {
		if (warning != null) {
			throw new SQLException("Warning not ignored", warning);
		}
	}

	/**
	 * Determine SQL from potential provider object.
	 * @param sqlProvider object that's potentially a SqlProvider
	 * @return the SQL string, or <code>null</code>
	 * @see SqlProvider
	 */
	private static String getSql(Object sqlProvider) {
		if (sqlProvider instanceof SqlProvider) {
			return ((SqlProvider) sqlProvider).getSql();
		}
		else {
			return null;
		}
	}

	/**
	 * Invocation handler that suppresses close calls on JDBC COnnections.
	 * Also prepares returned Statement (Prepared/CallbackStatement) objects.
	 * @see java.sql.Connection#close()
	 */
	private class CloseSuppressingInvocationHandler implements InvocationHandler {

		private final Connection target;

		public CloseSuppressingInvocationHandler(Connection target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// Invocation on ConnectionProxy interface coming in...

			if (method.getName().equals("getTargetConnection")) {
				// Handle getTargetConnection method: return underlying Connection.
				return this.target;
			}
			else if (method.getName().equals("equals")) {
				// Only consider equal when proxies are identical.
				return (proxy == args[0]);
			}
			else if (method.getName().equals("hashCode")) {
				// Use hashCode of PersistenceManager proxy.
				return System.identityHashCode(proxy);
			}
			else if (method.getName().equals("close")) {
				// Handle close method: suppress, not valid.
				return null;
			}

			// Invoke method on target Connection.
			try {
				Object retVal = method.invoke(this.target, args);

				// If return value is a JDBC Statement, apply statement settings
				// (fetch size, max rows, transaction timeout).
				if (retVal instanceof Statement) {
					applyStatementSettings(((Statement) retVal));
				}

				return retVal;
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}
	}


	/**
	 * Simple adapter for PreparedStatementCreator, allowing to use a plain SQL statement.
	 */
	private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

		private final String sql;

		public SimplePreparedStatementCreator(String sql) {
			//Assert.notNull(sql, "SQL must not be null");
			this.sql = sql;
		}

		public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			return con.prepareStatement(this.sql);
		}

		public String getSql() {
			return this.sql;
		}
	}


	/**
	 * Simple adapter for CallableStatementCreator, allowing to use a plain SQL statement.
	 */
	private static class SimpleCallableStatementCreator implements CallableStatementCreator, SqlProvider {

		private final String callString;

		public SimpleCallableStatementCreator(String callString) {
			//Assert.notNull(callString, "Call string must not be null");
			this.callString = callString;
		}

		public CallableStatement createCallableStatement(Connection con) throws SQLException {
			return con.prepareCall(this.callString);
		}

		public String getSql() {
			return this.callString;
		}
	}


	/**
	 * Adapter to enable use of a RowCallbackHandler inside a ResultSetExtractor.
	 * <p>Uses a regular ResultSet, so we have to be careful when using it:
	 * We don't use it for navigating since this could lead to unpredictable consequences.
	 */
	private static class RowCallbackHandlerResultSetExtractor implements ResultSetExtractor<Object> {

		private final RowCallbackHandler rch;

		public RowCallbackHandlerResultSetExtractor(RowCallbackHandler rch) {
			this.rch = rch;
		}

		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			while (rs.next()) {
				this.rch.processRow(rs);
			}
			return null;
		}
	}
	
	private static class ArgPreparedStatementSetter implements PreparedStatementSetter, ParameterDisposer {

		private final Object[] args;


		/**
		 * Create a new ArgPreparedStatementSetter for the given arguments.
		 * @param args the arguments to set
		 */
		public ArgPreparedStatementSetter(Object[] args) {
			this.args = args;
		}


		public void setValues(PreparedStatement ps) throws SQLException {
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					Object arg = this.args[i];
					if (arg instanceof SqlParameterValue) {
						SqlParameterValue paramValue = (SqlParameterValue) arg;
						StatementCreatorUtil.setParameterValue(ps, i + 1, paramValue, paramValue.getValue());
					}else {
						StatementCreatorUtil.setParameterValue(ps, i + 1, SqlTypeValue.TYPE_UNKNOWN, arg);
					}
				}
			}
		}

		public void cleanupParameters() {
			StatementCreatorUtil.cleanupParameters(this.args);
		}

	}
	
	private static class ArgTypePreparedStatementSetter implements PreparedStatementSetter, ParameterDisposer {

		private final Object[] args;

		private final int[] argTypes;


		/**
		 * Create a new ArgTypePreparedStatementSetter for the given arguments.
		 * @param args the arguments to set
		 * @param argTypes the corresponding SQL types of the arguments
		 * @throws Exception 
		 */
		public ArgTypePreparedStatementSetter(Object[] args, int[] argTypes) throws DataAccessException {
			if ((args != null && argTypes == null) || (args == null && argTypes != null) ||
					(args != null && args.length != argTypes.length)) {
				throw new DataAccessException("args and argTypes parameters must match");
			}
			this.args = args;
			this.argTypes = argTypes;
		}


		public void setValues(PreparedStatement ps) throws DataAccessException {
			int argIndx = 1;
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					Object arg = this.args[i];
					if (arg instanceof Collection && this.argTypes[i] != Types.ARRAY) {
						Collection<?> entries = (Collection<?>) arg;
						for (Iterator<?> it = entries.iterator(); it.hasNext();) {
							Object entry = it.next();
							if (entry instanceof Object[]) {
								Object[] valueArray = ((Object[])entry);
								for (int k = 0; k < valueArray.length; k++) {
									Object argValue = valueArray[k];
									StatementCreatorUtil.setParameterValue(ps, argIndx++, this.argTypes[i], argValue);
								}
							}
							else {
								StatementCreatorUtil.setParameterValue(ps, argIndx++, this.argTypes[i], entry);
							}
						}
					}
					else {
						StatementCreatorUtil.setParameterValue(ps, argIndx++, this.argTypes[i], arg);
					}
				}
			}
		}

		public void cleanupParameters() {
			StatementCreatorUtil.cleanupParameters(this.args);
		}

	}

}