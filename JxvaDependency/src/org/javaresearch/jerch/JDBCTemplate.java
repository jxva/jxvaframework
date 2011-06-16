/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * JDBC的模板定义，包含所有支持的方法。 这个类是这个类库的核心，所有的实际功能差不多都是这个定义并完成的。 最后更新日期:2005年5月7日
 * 
 * @author cherami
 */
public class JDBCTemplate {
	/**
	 * 数据源。
	 */
	private DataSource datasource;

	/**
	 * 构造一个空的JDBCTemplate。
	 */
	protected JDBCTemplate() {

	}

	/**
	 * 以指定的数据源构造一个JDBCTemplate。
	 * 
	 * @param datasource
	 *            数据源
	 */
	protected JDBCTemplate(DataSource datasource) {
		this.datasource = datasource;
	}

	/**
	 * 设置当前JDBCTemplate的数据源。
	 * 
	 * @param datasource
	 *            数据源
	 */
	protected void setDataSource(DataSource datasource) {
		this.datasource = datasource;
	}

	/**
	 * 得到当前JDBCTemplate的数据源。
	 * 
	 * @return 当前JDBCTemplate的数据源
	 */
	protected DataSource getDataSource() {
		return datasource;
	}

	/**
	 * 使用psc创建PreparedStatement并使用pss设置相关的参数最后调用action执行。
	 * 
	 * @param psc
	 *            PreparedStatement的创建器
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @param action
	 *            PreparedStatement执行的回掉实现
	 * @return 执行后的结果
	 */
	public Object execute(PreparedStatementCreator psc,PreparedStatementSetter pss, PreparedStatementCallback action) {
		Connection con = Utils.getConnection(datasource);
		PreparedStatement ps = null;
		try {
			ps = psc.createPreparedStatement(con);
			pss.setValues(ps);
			Object result = action.doInPreparedStatement(ps);
			return result;
		} catch (SQLException ex) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", ex);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", ex);
		} finally {
			Utils.closeStatement(ps);
			Utils.closeConnection(con);
		}
	}

	/**
	 * 执行指定的sql并返回更新的记录数。
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 更新的记录数
	 */
	public int update(String sql) {
		return update(sql, nullPSS);
	}

	/**
	 * 执行指定的sql并返回更新的记录数。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @return 更新的记录数
	 */
	public int update(String sql, Object[] args) {
		return update(sql, new ArgPreparedStatementSetter(args));
	}

	/**
	 * 执行指定的sql并返回更新的记录数。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @return 更新的记录数
	 */
	public int update(String sql, Object[] args, int[] types) {
		return update(sql, new ArgTypePreparedStatementSetter(args, types));
	}

	/**
	 * 执行指定的sql并返回更新的记录数。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @return 更新的记录数
	 */
	public int update(String sql, PreparedStatementSetter pss) {
		Integer result = (Integer) execute(new SimplePreparedStatementCreator(sql), pss, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement stmt)throws SQLException {
				return new Integer(stmt.executeUpdate());
			}
		});
		return result.intValue();
	}

	/**
	 * 批量执行更新并返回每次的更新记录数
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            每次执行时的参数值
	 * @return 每次执行更新的记录数数组
	 */
	public int[] batchUpdate(String sql, Object[][] args) {
		return batchUpdate(sql, new ArgBatchPreparedStatementSetter(args));
	}

	/**
	 * 批量执行更新并返回每次的更新记录数
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            每次执行时的参数值
	 * @param types
	 *            参数类型
	 * @return 每次执行更新的记录数数组
	 */
	public int[] batchUpdate(String sql, Object[][] args, int[] types) {
		return batchUpdate(sql, new ArgTypeBatchPreparedStatementSetter(args,types));
	}

	/**
	 * 批量执行更新并返回每次的更新记录数
	 * 
	 * @param sql
	 *            SQL语句
	 * @param bpss
	 *            PreparedStatement的批量参数设置器
	 * @return 每次执行更新的记录数数组
	 */
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter bpss) {
		return batchUpdate(sql, new BatchPreparedStatementSetterConverter(bpss));
	}

	/**
	 * 批量执行更新并返回每次的更新记录数
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @return 每次执行更新的记录数数组
	 */
	public int[] batchUpdate(String sql, PreparedStatementSetter pss) {
		return (int[]) execute(new SimplePreparedStatementCreator(sql), pss,
				new PreparedStatementCallback() {
					public Object doInPreparedStatement(PreparedStatement stmt)throws SQLException {
						return stmt.executeBatch();
					}
				});
	}

	/**
	 * 查询一个整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public int queryForInt(String sql) {
		return queryForInt(sql, nullPSS);
	}

	/**
	 * 查询一个整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public int queryForInt(String sql, Object[] args) {
		return queryForInt(sql, new ArgPreparedStatementSetter(args));
	}

	/**
	 * 查询一个整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public int queryForInt(String sql, Object[] args, int[] types) {
		return queryForInt(sql, new ArgTypePreparedStatementSetter(args, types));
	}

	/**
	 * 查询一个整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @return 查询的第一行的第一个字段的整型值。
	 */
	public int queryForInt(String sql, PreparedStatementSetter pss) {
		Number result = (Number) queryObject(sql, pss,new ObjectResultRowExtractor());
		if (result == null) {
			return 0;
		}
		return result.intValue();
	}

	/**
	 * 查询一个长整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 查询的第一行的第一个字段的长整型值。
	 */
	public long queryForLong(String sql) {
		return queryForLong(sql, nullPSS);
	}

	/**
	 * 查询一个长整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @return 查询的第一行的第一个字段的长整型值。
	 */
	public long queryForLong(String sql, Object[] args) {
		return queryForLong(sql, new ArgPreparedStatementSetter(args));
	}

	/**
	 * 查询一个长整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @return 查询的第一行的第一个字段的长整型值。
	 */
	public long queryForLong(String sql, Object[] args, int[] types) {
		return queryForLong(sql,new ArgTypePreparedStatementSetter(args, types));
	}

	/**
	 * 查询一个长整型结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @return 查询的第一行的第一个字段的长整型值。
	 */
	public long queryForLong(String sql, PreparedStatementSetter pss) {
		Number result = (Number) queryObject(sql, pss,new ObjectResultRowExtractor());
		if (result == null) {
			return 0;
		}
		return result.longValue();
	}

	/**
	 * 查询一个字符串结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 查询的第一行的第一个字段的字符串值。
	 */
	public String queryString(String sql) {
		return queryString(sql, nullPSS);
	}

	/**
	 * 查询一个字符串结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @return 查询的第一行的第一个字段的字符串值。
	 */
	public String queryString(String sql, Object[] args) {
		return queryString(sql, new ArgPreparedStatementSetter(args));
	}

	/**
	 * 查询一个字符串结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @return 查询的第一行的第一个字段的字符串值。
	 */
	public String queryString(String sql, Object[] args, int[] types) {
		return queryString(sql, new ArgTypePreparedStatementSetter(args, types));
	}

	/**
	 * 查询一个字符串结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @return 查询的第一行的第一个字段的字符串值。
	 */
	public String queryString(String sql, PreparedStatementSetter pss) {
		return (String) queryObject(sql, pss, new StringResultRowExtractor());
	}

	/**
	 * 查询一个对象结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param rre
	 *            单行结果提取器
	 * @return 查询的第一行的结果并使用rre转换为结果对象。
	 */
	public Object queryObject(String sql, ResultRowExtractor rre) {
		return queryObject(sql, nullPSS, rre);
	}

	/**
	 * 查询一个对象结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param rre
	 *            单行结果提取器
	 * @return 查询的第一行的结果并使用rre转换为结果对象。
	 */
	public Object queryObject(String sql, Object[] args, ResultRowExtractor rre) {
		return queryObject(sql, new ArgPreparedStatementSetter(args), rre);
	}

	/**
	 * 查询一个对象结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @param rre
	 *            单行结果提取器
	 * @return 查询的第一行的结果并使用rre转换为结果对象。
	 */
	public Object queryObject(String sql, Object[] args, int[] types,ResultRowExtractor rre) {
		return queryObject(sql,new ArgTypePreparedStatementSetter(args, types), rre);
	}

	/**
	 * 查询一个对象结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @param rre
	 *            单行结果提取器
	 * @return 查询的第一行的结果并使用rre转换为结果对象。
	 */
	public Object queryObject(String sql, PreparedStatementSetter pss,final ResultRowExtractor rre) {
		Object result = (Object) execute(
				new SimplePreparedStatementCreator(sql), pss,
				new PreparedStatementCallback() {
					public Object doInPreparedStatement(PreparedStatement ps)throws SQLException {
						ResultSet rs = null;
						try {
							rs = ps.executeQuery();
							if (rs.next()) {
								return rre.extractRow(rs);
							} else {
								return null;
							}
						} catch (SQLException e) {
							Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
							throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
						} finally {
							Utils.closeResultSet(rs);
						}
					}
				});
		return result;
	}

	/**
	 * 查询一个对象列表结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param rre
	 *            单行结果提取器
	 * @return 查询所有结果并使用rre将每行结果转换为结果对象元素。
	 */
	public List query(String sql, ResultRowExtractor rre) {
		return (List) execute(new SimplePreparedStatementCreator(sql), nullPSS,
				new ResultRowListPreparedStatementCallback(rre));
	}

	/**
	 * 查询结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param rse
	 *            全部结果提取器。
	 * @return 查询结果并根据自定义的结果提取器提取数据并返回。
	 */
	public Object query(String sql, final ResultSetExtractor rse) {
		return (Object) execute(new SimplePreparedStatementCreator(sql),
				nullPSS, new ResultSetPreparedStatementCallback(rse));
	}

	/**
	 * 查询一个对象列表结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @param rre
	 *            单行结果提取器
	 * @return 查询所有结果并使用rre将每行结果转换为结果对象元素。
	 */
	public List query(String sql, Object[] args, int[] types,
			ResultRowExtractor rre) {
		return (List) execute(new SimplePreparedStatementCreator(sql),
				new ArgTypePreparedStatementSetter(args, types),
				new ResultRowListPreparedStatementCallback(rre));
	}

	/**
	 * 查询结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @param rse
	 *            全部结果提取器。
	 * @return 查询结果并根据自定义的结果提取器提取数据并返回。
	 */
	public Object query(String sql, Object[] args, int[] types,
			ResultSetExtractor rse) {
		return (Object) execute(new SimplePreparedStatementCreator(sql),
				new ArgTypePreparedStatementSetter(args, types),
				new ResultSetPreparedStatementCallback(rse));
	}

	/**
	 * 查询一个对象列表结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param rre
	 *            单行结果提取器
	 * @return 查询所有结果并使用rre将每行结果转换为结果对象元素。
	 */
	public List query(String sql, Object[] args, ResultRowExtractor rre) {
		return (List) execute(new SimplePreparedStatementCreator(sql),
				new ArgPreparedStatementSetter(args),
				new ResultRowListPreparedStatementCallback(rre));
	}

	/**
	 * 查询结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param rse
	 *            全部结果提取器。
	 * @return 查询结果并根据自定义的结果提取器提取数据并返回。
	 */
	public Object query(String sql, Object[] args, ResultSetExtractor rse) {
		return (Object) execute(new SimplePreparedStatementCreator(sql),
				new ArgPreparedStatementSetter(args),
				new ResultSetPreparedStatementCallback(rse));
	}

	/**
	 * 查询一个对象列表结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @param rre
	 *            单行结果提取器
	 * @return 查询所有结果并使用rre将每行结果转换为结果对象元素。
	 */
	public List query(String sql, PreparedStatementSetter pss,
			ResultRowExtractor rre) {
		return (List) execute(new SimplePreparedStatementCreator(sql), pss,
				new ResultRowListPreparedStatementCallback(rre));
	}

	/**
	 * 查询结果。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @param rse
	 *            全部结果提取器。
	 * @return 查询结果并根据自定义的结果提取器提取数据并返回。
	 */
	public Object query(String sql, PreparedStatementSetter pss,
			ResultSetExtractor rse) {
		return (Object) execute(new SimplePreparedStatementCreator(sql), pss,
				new ResultSetPreparedStatementCallback(rse));
	}

	/**
	 * 查询并返回第一行的结果并根据字段名自动转换为bean的对应属性。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param bean
	 *            结果Bean的类型
	 * @return 第一行的结果并转换为Bean实例。
	 */
	public Object queryForBean(String sql, Class bean) {
		try {
			Object obj = bean.newInstance();
			if (obj instanceof Mappable) {
				return queryObject(sql, nullPSS,
						new MappableResultRowExtractor(obj, (Mappable) obj));
			} else {
				return queryObject(sql, nullPSS,
						new MappableResultRowExtractor(obj,
								new NameMatchMappable()));
			}
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 查询并返回第一行的结果并根据字段名自动转换为bean的对应属性。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param bean
	 *            结果Bean的类型
	 * @return 第一行的结果并转换为Bean实例。
	 */
	public Object queryForBean(String sql, Object[] args, Class bean) {
		try {
			Object obj = bean.newInstance();
			if (obj instanceof Mappable) {
				return queryObject(sql, new ArgPreparedStatementSetter(args),
						new MappableResultRowExtractor(obj, (Mappable) obj));
			} else {
				return queryObject(sql, new ArgPreparedStatementSetter(args),
						new MappableResultRowExtractor(obj,
								new NameMatchMappable()));
			}
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 查询并返回第一行的结果并根据字段名自动转换为bean的对应属性。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @param bean
	 *            结果Bean的类型
	 * @return 第一行的结果并转换为Bean实例。
	 */
	public Object queryForBean(String sql, Object[] args, int[] types,
			Class bean) {
		try {
			Object obj = bean.newInstance();
			if (obj instanceof Mappable) {
				return queryObject(sql, new ArgTypePreparedStatementSetter(
						args, types), new MappableResultRowExtractor(obj,
						(Mappable) obj));
			} else {
				return queryObject(sql, new ArgTypePreparedStatementSetter(
						args, types), new MappableResultRowExtractor(obj,
						new NameMatchMappable()));
			}
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 查询并返回第一行的结果并根据字段名自动转换为bean的对应属性。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @param bean
	 *            结果Bean的类型
	 * @return 第一行的结果并转换为Bean实例。
	 */
	public Object queryForBean(String sql, PreparedStatementSetter pss,
			Class bean) {
		try {
			Object obj = bean.newInstance();
			if (obj instanceof Mappable) {
				return queryObject(sql, pss, new MappableResultRowExtractor(
						obj, (Mappable) obj));
			} else {
				return queryObject(sql, pss, new MappableResultRowExtractor(
						obj, new NameMatchMappable()));
			}
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 查询并返回第一行的结果并使用m将其转换为bean。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param bean
	 *            结果Bean的类型
	 * @param m
	 *            字段－属性映射器
	 * @return 第一行的结果并使用m将其转换为Bean实例。
	 */
	public Object queryForBean(String sql, Class bean, Mappable m) {
		try {
			return queryObject(sql, nullPSS, new MappableResultRowExtractor(
					bean.newInstance(), m));
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 查询并返回第一行的结果并使用m将其转换为bean。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param bean
	 *            结果Bean的类型
	 * @param m
	 *            字段－属性映射器
	 * @return 第一行的结果并使用m将其转换为Bean实例。
	 */
	public Object queryForBean(String sql, Object[] args, Class bean, Mappable m) {
		try {
			return queryObject(sql, new ArgPreparedStatementSetter(args),
					new MappableResultRowExtractor(bean.newInstance(), m));
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 查询并返回第一行的结果并使用m将其转换为bean。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param args
	 *            参数中的值
	 * @param types
	 *            参数类型
	 * @param bean
	 *            结果Bean的类型
	 * @param m
	 *            字段－属性映射器
	 * @return 第一行的结果并使用m将其转换为Bean实例。
	 */
	public Object queryForBean(String sql, Object[] args, int[] types,
			Class bean, Mappable m) {
		try {
			return queryObject(sql, new ArgTypePreparedStatementSetter(args,
					types), new MappableResultRowExtractor(bean.newInstance(),
					m));
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 查询并返回第一行的结果并使用m将其转换为bean。
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pss
	 *            PreparedStatement的参数设置器
	 * @param bean
	 *            结果Bean的类型
	 * @param m
	 *            字段－属性映射器
	 * @return 第一行的结果并使用m将其转换为Bean实例。
	 */
	public Object queryForBean(String sql, PreparedStatementSetter pss,
			Class bean, Mappable m) {
		try {
			return queryObject(sql, pss, new MappableResultRowExtractor(bean
					.newInstance(), m));
		} catch (Exception e) {
			Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
			throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
		}
	}

	/**
	 * 无任何参数的PreparedStatement设置器实例。
	 */
	private static final PreparedStatementSetter nullPSS = new PreparedStatementSetter() {
		public void setValues(PreparedStatement ps) {
		}
	};

	/**
	 * 将参数数组转换为PreparedStatement的设置器的简单适配器。
	 */
	private static class ArgPreparedStatementSetter implements
			PreparedStatementSetter {

		private final Object[] args;

		public ArgPreparedStatementSetter(Object[] args) {
			this.args = args;
		}

		public void setValues(PreparedStatement ps) throws SQLException {
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					ps.setObject(i + 1, this.args[i]);
				}
			}
		}
	}

	/**
	 * 将参数数组和其类型转换为PreparedStatement的设置器的简单适配器。
	 */
	private static class ArgTypePreparedStatementSetter implements
			PreparedStatementSetter {

		private final Object[] args;

		private final int[] types;

		public ArgTypePreparedStatementSetter(Object[] args, int[] types) {
			if ((args != null && types == null)
					|| (args == null && types != null)
					|| (args != null && args.length != types.length)) {
				throw new JerchException(
						"ArgTypePreparedStatementSetter.ARG_NOT_MATCH");
			}
			this.args = args;
			this.types = types;
		}

		public void setValues(PreparedStatement ps) throws SQLException {
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					ps.setObject(i + 1, this.args[i], this.types[i]);
				}
			}
		}
	}

	/**
	 * 将sql语句转换为PreparedStatement的创建器的简单适配器。
	 */
	private static class SimplePreparedStatementCreator implements
			PreparedStatementCreator {

		private final String sql;

		public SimplePreparedStatementCreator(String sql) {
			this.sql = sql;
		}

		public PreparedStatement createPreparedStatement(Connection con)
				throws SQLException {
			return con.prepareStatement(this.sql);
		}
	}

	/**
	 * 将第一个字段作为String类型提取的单行结果提取器。
	 */
	private static class StringResultRowExtractor implements ResultRowExtractor {
		public Object extractRow(java.sql.ResultSet rs) throws SQLException {
			return rs.getString(1);
		}
	}

	/**
	 * 将第一个字段作为Object类型提取的单行结果提取器。
	 */
	private static class ObjectResultRowExtractor implements ResultRowExtractor {
		public Object extractRow(java.sql.ResultSet rs) throws SQLException {
			return rs.getObject(1);
		}
	}

	/**
	 * 将BatchPreparedStatement设置其转换为PreparedStatement设置器的适配器。
	 */
	private static class BatchPreparedStatementSetterConverter implements
			PreparedStatementSetter {
		BatchPreparedStatementSetter bpss;

		public BatchPreparedStatementSetterConverter(
				BatchPreparedStatementSetter bpss) {
			this.bpss = bpss;
		}

		public void setValues(PreparedStatement ps) throws SQLException {
			for (int i = 0; i < bpss.getBatchSize(); i++) {
				bpss.setValues(ps, i);
				ps.addBatch();
			}
		}
	}

	/**
	 * 将批量参数数组转换为PreparedStatement设置器的适配器。
	 */
	private static class ArgBatchPreparedStatementSetter implements
			PreparedStatementSetter {

		private final Object[][] args;

		public ArgBatchPreparedStatementSetter(Object[][] args) {
			this.args = args;
		}

		public void setValues(PreparedStatement ps) throws SQLException {
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					Object[] arg = args[i];
					for (int j = 0; j < arg.length; j++) {
						ps.setObject(j + 1, arg[j]);
					}
					ps.addBatch();
				}
			}
		}
	}

	/**
	 * 将批量参数数组和对应类型转换为PreparedStatement设置器的适配器。
	 */
	private static class ArgTypeBatchPreparedStatementSetter implements
			PreparedStatementSetter {

		private final Object[][] args;

		private final int[] types;

		public ArgTypeBatchPreparedStatementSetter(Object[][] args, int[] types) {
			if ((args != null && types == null)
					|| (args == null && types != null)
					|| (args != null && args.length != types.length)) {
				throw new JerchException(
						"ArgTypePreparedStatementSetter.ARG_NOT_MATCH");
			}
			this.args = args;
			this.types = types;
		}

		public void setValues(PreparedStatement ps) throws SQLException {
			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					Object[] arg = args[i];
					for (int j = 0; j < arg.length; j++) {
						ps.setObject(j + 1, arg[j], types[j]);
					}
					ps.addBatch();
				}
			}
		}
	}

	/**
	 * 使用ResultRowExtractor提取单行结果并将全部结果添加到List的PreparedStatement回调实现。
	 */
	private static class ResultRowListPreparedStatementCallback implements
			PreparedStatementCallback {
		ResultRowExtractor rre;

		public ResultRowListPreparedStatementCallback(ResultRowExtractor rre) {
			this.rre = rre;
		}

		public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
			ResultSet rs = null;
			List result = new ArrayList();
			try {
				rs = ps.executeQuery();
				while (rs.next()) {
					result.add(rre.extractRow(rs));
				}
			} catch (SQLException e) {
				Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
				throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
			} finally {
				Utils.closeResultSet(rs);
			}
			return result;
		}
	}

	/**
	 * 使用ResultSetExtractor提取全部结果的PreparedStatement回调实现。
	 */
	private static class ResultSetPreparedStatementCallback implements
			PreparedStatementCallback {
		ResultSetExtractor rse;

		public ResultSetPreparedStatementCallback(ResultSetExtractor rse) {
			this.rse = rse;
		}

		public Object doInPreparedStatement(PreparedStatement ps)
				throws SQLException {
			ResultSet rs = null;
			try {
				rs = ps.executeQuery();
				return rse.extractSet(rs);
			} catch (SQLException e) {
				Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
				throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
			} finally {
				Utils.closeResultSet(rs);
			}
		}
	}

	/**
	 * 使用Mappable将单行结果转换为对象实例的ResultRow提取器实现。
	 */
	private static class MappableResultRowExtractor implements
			ResultRowExtractor {
		private Mappable map;

		private Object obj;

		public MappableResultRowExtractor(Object obj, Mappable map) {
			this.obj = obj;
			this.map = map;
		}

		public Object extractRow(ResultSet rs) throws SQLException {
			Class c = obj.getClass();
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			try {
				for (int i = 0; i < count; i++) {
					String fieldName = metaData.getColumnName(i + 1);
					int type = metaData.getColumnType(i + 1);
					Class[] types = { map.getMethodParameterType(fieldName,type) };
					Method m = c.getMethod(map.getMapMethod(fieldName), types);
					Object[] args = { ValueConverterFactory.convert(rs.getObject(fieldName), types[0]) };
					m.invoke(obj, args);
				}
			} catch (Exception e) {
				Utils.error("JDBCTemplate.EXECUTE_ERROR", e);
				throw new JerchException("JDBCTemplate.EXECUTE_ERROR", e);
			}
			return obj;
		}
	}
}
