/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 创建PreparedStatement的接口定义。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public interface PreparedStatementCreator {
  /**
   * 从数据库连接中创建一个PreparedStatement。
   * @param con 数据库连接
   * @return 创建好的PreparedStatement
   * @throws SQLException
   */
  public PreparedStatement createPreparedStatement(Connection con) throws SQLException; 
}
