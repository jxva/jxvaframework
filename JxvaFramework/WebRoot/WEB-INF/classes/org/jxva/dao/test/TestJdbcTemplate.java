package org.jxva.dao.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jxva.dao.jdbc.BatchPreparedStatementSetter;
import com.jxva.dao.jdbc.PreparedStatementSetter;

public class TestJdbcTemplate extends BaseTest {
	/*
	 * int update 的正在测试的类(String)
	 */
	public void testUpdateString() throws SQLException {
		assertEquals(2, template.update("update test set count=count+1"));
	}

	/*
	 * int update 的正在测试的类(String, Object[])
	 */
	public void testUpdateStringObjectArray() throws SQLException {
		Object[] args = { new Long(1) };
		assertEquals(1, template.update("update test set count=count+1 where id=?", args));
	}

	/*
	 * int update 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testUpdateStringPreparedStatementSetter() throws SQLException {
		assertEquals(1, template.update("update test set count=count+1 where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2);
					}
				}));
	}



	/*
	 * int[] batchUpdate 的正在测试的类(String, BatchPreparedStatementSetter)
	 */
	public void testBatchUpdateStringBatchPreparedStatementSetter() throws SQLException {
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
	 * int queryForInt 的正在测试的类(String)
	 */
	public void testQueryForIntString() throws SQLException {
		assertEquals(2, template.queryForInt("select count(*) from test"));
	}

	/*
	 * int queryForInt 的正在测试的类(String, Object[])
	 */
	public void testQueryForIntStringObjectArray() throws SQLException {
		Object[] args = { new Long(2) };
		assertEquals(2000, template.queryForInt("select size from test where id=?", args));
	}

	/*
	 * int queryForInt 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testQueryForIntStringPreparedStatementSetter() throws SQLException {
		assertEquals(2000, template.queryForInt("select size from test where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1,2);
					}
				}));
	}

	/*
	 * long queryForLong 的正在测试的类(String)
	 */
	public void testQueryForLongString() throws SQLException {
		assertEquals(2, template.queryForLong("select count(*) from test"));
	}

	/*
	 * long queryForLong 的正在测试的类(String, Object[])
	 */
	public void testQueryForLongStringObjectArray() throws SQLException {
		Object[] args = { new Long(2) };
		assertEquals(2000, template.queryForLong("select size from test where id=?", args));
	}

	/*
	 * long queryForLong 的正在测试的类(String, PreparedStatementSetter)
	 */
	public void testQueryForLongStringPreparedStatementSetter() throws SQLException {
		assertEquals(2000, template.queryForLong("select size from test where id=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)throws SQLException {
						ps.setLong(1, 2L);
					}
				}));
	}

}