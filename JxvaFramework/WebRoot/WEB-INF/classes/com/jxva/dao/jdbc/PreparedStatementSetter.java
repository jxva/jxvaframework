package com.jxva.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jxva.dao.JdbcTemplate;

/**
 * 
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-08 10:46:53 by Jxva
 * @see JdbcTemplate#update(String, PreparedStatementSetter)
 * @see JdbcTemplate#query(String, PreparedStatementSetter, ResultSetExtractor)
 */
public interface PreparedStatementSetter {

  public void setValues(PreparedStatement ps)  throws SQLException;
}
