/*
 * Created on 2005-3-16
 *
 */
package org.javaresearch.jerch.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javaresearch.jerch.BatchPreparedStatementSetter;
import org.javaresearch.jerch.Mappable;
import org.javaresearch.jerch.PartNameMatchMappable;
import org.javaresearch.jerch.PreparedStatementSetter;
import org.javaresearch.jerch.ResultRowExtractor;
import org.javaresearch.jerch.ResultSetExtractor;

/**
 * @author liumin
 */
public class TestJDBCTemplate extends BaseTest {
	/*
	 * int update 的正在测试的类(String)
	 */
	public void testUpdateString() {
		assertEquals(2, template.update("update test set count=count+1"));
	}

	/*
	 * int update 的正在测试的类(String, Object[])
	 */
	public void testUpdateStringObjectArray() {
		Object[] args = { new Long(1) };
		assertEquals(1, template.update("update test set count=count+1 where id=?", args));
	}

	/*
	 * int update 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testUpdateStringPreparedStatementSetter() {
		assertEquals(1, template.update("update test set count=count+1 where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}));
	}

	/*
	 * int[] batchUpdate 的正在测试的类(String, Object[][])
	 */
	public void testBatchUpdateStringObjectArrayArray() {
		Object[][] args = { { new Long(1) }, { new Long(2) } };
		int[] except = { 1, 1 };
		int[] result = template.batchUpdate("update test set count=count+1 where id=?", args);
		for (int i = 0; i < except.length; i++) {
			assertEquals(except[i], result[i]);
		}
	}

	/*
	 * int[] batchUpdate 的正在测试的类(String, BatchPreparedStatementSetter)
	 */
	public void testBatchUpdateStringBatchPreparedStatementSetter() {
		int[] except = { 1, 1 };
		int[] result = template.batchUpdate("update test set count=count+1 where id=?",
				new BatchPreparedStatementSetter() {
					public int getBatchSize() {
						return 2;
					}

					public void setValues(PreparedStatement ps, int i)throws SQLException {
						if (i == 0) {
							ps.setLong(1, 1);
						} else {
							ps.setLong(1, 2);
						}
					}

				});
		for (int i = 0; i < except.length; i++) {
			assertEquals(except[i], result[i]);
		}
	}

	/*
	 * int[] batchUpdate 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testBatchUpdateStringPreparedStatementSetter() {
		int[] except = { 1, 1 };
		int[] result = template.batchUpdate("update test set count=count+1 where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						for (int i = 0; i < 2; i++) {
							ps.setLong(1, i + 1);
							ps.addBatch();
						}
					}
				});
		for (int i = 0; i < except.length; i++) {
			assertEquals(except[i], result[i]);
		}
	}

	/*
	 * int queryForInt 的正在测试的类(String)
	 */
	public void testQueryForIntString() {
		assertEquals(2, template.queryForInt("select count(*) from test"));
	}

	/*
	 * int queryForInt 的正在测试的类(String, Object[])
	 */
	public void testQueryForIntStringObjectArray() {
		Object[] args = { new Long(2) };
		assertEquals(2000, template.queryForInt("select size from test where id=?", args));
	}

	/*
	 * int queryForInt 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testQueryForIntStringPreparedStatementSetter() {
		assertEquals(2000, template.queryForInt("select size from test where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}));
	}

	/*
	 * long queryForLong 的正在测试的类(String)
	 */
	public void testQueryForLongString() {
		assertEquals(2, template.queryForLong("select count(*) from test"));
	}

	/*
	 * long queryForLong 的正在测试的类(String, Object[])
	 */
	public void testQueryForLongStringObjectArray() {
		Object[] args = { new Long(2) };
		assertEquals(2000, template.queryForLong("select size from test where id=?", args));
	}

	/*
	 * long queryForLong 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testQueryForLongStringPreparedStatementSetter() {
		assertEquals(2000, template.queryForLong("select size from test where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}));
	}

	/*
	 * String queryString 的正在测试的类(String)
	 */
	public void testQueryStringString() {
		assertEquals("测试", template.queryString("select name from test where id=1"));
	}

	/*
	 * String queryString 的正在测试的类(String, Object[])
	 */
	public void testQueryStringStringObjectArray() {
		Object[] args = { new Long(1) };
		assertEquals("测试", template.queryString("select name from test where id=?", args));
	}

	/*
	 * String queryString 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testQueryStringStringPreparedStatementSetter() {
		assertEquals("测试", template.queryString("select name from test where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 1);
					}
				}));
	}

	/*
	 * Object queryObject 的正在测试的类(String, ResultRowExtractor)
	 */
	public void testQueryObjectStringResultRowExtractor() {
		assertEquals("测试", ((TestBean) template.queryObject(
				"select * from test where id=1", new ResultRowExtractor() {
					public Object extractRow(java.sql.ResultSet rs)throws SQLException {
						TestBean bean = new TestBean();
						bean.setId(new Long(rs.getLong("id")));
						bean.setName(rs.getString("name"));
						bean.setCreatetime(rs.getTimestamp("createtime"));
						bean.setTestdate(rs.getDate("testdate"));
						bean.setCount(new Integer(rs.getInt("count")));
						bean.setSize(new Integer(rs.getInt("size")));
						return bean;
					}
				})).getName());
	}

	/*
	 * Object queryObject 的正在测试的类(String, Object[], ResultRowExtractor)
	 */
	public void testQueryObjectStringObjectArrayResultRowExtractor() {
		Object[] args = { new Long(2) };
		assertEquals("Java", ((TestBean) template.queryObject("select * from test where id=?", args,
				new ResultRowExtractor() {
					public Object extractRow(java.sql.ResultSet rs)throws SQLException {
						TestBean bean = new TestBean();
						bean.setId(new Long(rs.getLong("id")));
						bean.setName(rs.getString("name"));
						bean.setCreatetime(rs.getTimestamp("createtime"));
						bean.setTestdate(rs.getDate("testdate"));
						bean.setCount(new Integer(rs.getInt("count")));
						bean.setSize(new Integer(rs.getInt("size")));
						return bean;
					}
				})).getName());
	}

	/*
	 * Object queryObject 的正在测试的类(String, PreparedStatementSetter,
	 * ResultRowExtractor)
	 */
	public void testQueryObjectStringPreparedStatementSetterResultRowExtractor() {
		assertEquals("Java", ((TestBean) template.queryObject(
				"select * from test where id=?", new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}, new ResultRowExtractor() {
					public Object extractRow(java.sql.ResultSet rs)throws SQLException {
						TestBean bean = new TestBean();
						setResultRow2Bean(rs, bean);
						return bean;
					}
				})).getName());
	}

	/*
	 * List query 的正在测试的类(String, ResultRowExtractor)
	 */
	public void testQueryStringResultRowExtractor() {
		assertEquals(2, template.query("select * from test",
				new ResultRowExtractor() {
					public Object extractRow(java.sql.ResultSet rs)throws SQLException {
						TestBean bean = new TestBean();
						setResultRow2Bean(rs, bean);
						return bean;
					}
				}).size());
	}

	/*
	 * Object query 的正在测试的类(String, ResultSetExtractor)
	 */
	public void testQueryStringResultSetExtractor() {
		assertEquals(2, ((List) template.query("select * from test",
				new ResultSetExtractor() {
					public Object extractSet(java.sql.ResultSet rs)throws SQLException {
						List result = new ArrayList();
						while (rs.next()) {
							TestBean bean = new TestBean();
							setResultRow2Bean(rs, bean);
							result.add(bean);
						}
						return result;
					}
				})).size());
	}

	/*
	 * List query 的正在测试的类(String, Object[], ResultRowExtractor)
	 */
	public void testQueryStringObjectArrayResultRowExtractor() {
		Object[] args = { new Long(2) };
		assertEquals("Java", ((TestBean) (template.query("select * from test where id=?", args,
				new ResultRowExtractor() {
					public Object extractRow(java.sql.ResultSet rs)throws SQLException {
						TestBean bean = new TestBean();
						setResultRow2Bean(rs, bean);
						return bean;
					}
				}).get(0))).getName());
	}

	/*
	 * Object query 的正在测试的类(String, Object[], ResultSetExtractor)
	 */
	public void testQueryStringObjectArrayResultSetExtractor() {
		Object[] args = { new Long(2) };
		assertEquals("Java", ((TestBean) ((List) template.query("select * from test where id=?", args,
				new ResultSetExtractor() {
					public Object extractSet(java.sql.ResultSet rs)throws SQLException {
						List result = new ArrayList();
						while (rs.next()) {
							TestBean bean = new TestBean();
							setResultRow2Bean(rs, bean);
							result.add(bean);
						}
						return result;
					}
				})).get(0)).getName());
	}

	/*
	 * List query 的正在测试的类(String, PreparedStatementSetter, ResultRowExtractor)
	 */
	public void testQueryStringPreparedStatementSetterResultRowExtractor() {
		assertEquals(1, ((List) template.query("select * from test where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}, new ResultSetExtractor() {
					public Object extractSet(java.sql.ResultSet rs)throws SQLException {
						List result = new ArrayList();
						while (rs.next()) {
							TestBean bean = new TestBean();
							setResultRow2Bean(rs, bean);
							result.add(bean);
						}
						return result;
					}
				})).size());
	}

	/*
	 * Object query 的正在测试的类(String, PreparedStatementSetter, ResultSetExtractor)
	 */
	public void testQueryStringPreparedStatementSetterResultSetExtractor() {
		assertEquals(1, ((List) template.query("select * from test where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}, new ResultSetExtractor() {
					public Object extractSet(java.sql.ResultSet rs)throws SQLException {
						List result = new ArrayList();
						while (rs.next()) {
							TestBean bean = new TestBean();
							setResultRow2Bean(rs, bean);
							result.add(bean);
						}
						return result;
					}
				})).size());
	}

	/*
	 * Object queryForBean 的正在测试的类(String, Class)
	 */
	public void testQueryForBeanStringClass() {
		assertEquals("测试", ((TestBean) template.queryForBean("select * from test where id=1", TestBean.class)).getName());
	}

	/*
	 * Object queryForBean 的正在测试的类(String, Object[], Class)
	 */
	public void testQueryForBeanStringObjectArrayClass() {
		Object[] args = { new Long(2) };
		assertEquals("Java", ((TestBean) template.queryForBean("select * from test where id=?", args, TestBean.class)).getName());
	}

	/*
	 * Object queryForBean 的正在测试的类(String, PreparedStatementSetter, Class)
	 */
	public void testQueryForBeanStringPreparedStatementSetterClass() {
		assertEquals("Java", ((TestBean) template.queryForBean("select * from test where id=?", new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}, TestBean.class)).getName());
	}

	/*
	 * Object queryForBean 的正在测试的类(String, Class, Mappable)
	 */
	public void testQueryForBeanStringClassMappable() {
		assertEquals("测试", ((TestBean) template.queryForBean("select * from test where id=1", TestBean.class,getPartNameMatchMappable())).getName());
	}

	/*
	 * Object queryForBean 的正在测试的类(String, Object[], Class, Mappable)
	 */
	public void testQueryForBeanStringObjectArrayClassMappable() {
		Object[] args = { new Long(2) };
		assertEquals("Java", ((TestBean) template.queryForBean("select * from test where id=?", args, TestBean.class,getPartNameMatchMappable())).getName());
	}

	/*
	 * Object queryForBean 的正在测试的类(String, PreparedStatementSetter, Class,
	 * Mappable)
	 */
	public void testQueryForBeanStringPreparedStatementSetterClassMappable() {
		assertEquals("Java", ((TestBean) template.queryForBean("select * from test where id=?", new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}, TestBean.class, getPartNameMatchMappable())).getName());
	}

	private void setResultRow2Bean(ResultSet rs, TestBean bean)throws SQLException {
		bean.setId(new Long(rs.getLong("id")));
		bean.setName(rs.getString("name"));
		bean.setCreatetime(rs.getTimestamp("createtime"));
		bean.setTestdate(rs.getDate("testdate"));
		bean.setCount(new Integer(rs.getInt("count")));
		bean.setSize(new Integer(rs.getInt("size")));
	}

	private Mappable getPartNameMatchMappable() {
		Map expectFields = new HashMap();
		Object[] result = { "setId", Long.class };
		expectFields.put("id", result);
		Mappable map = new PartNameMatchMappable(expectFields);
		return map;
	}

}