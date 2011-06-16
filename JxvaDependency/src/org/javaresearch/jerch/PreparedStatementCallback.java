/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PreparedStatement回调接口定义。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public interface PreparedStatementCallback {
  /**
   * 定义PreparedStatement中的执行内容。 
   * 执行前PreparedStatement的参数等内容都已经设置好了。
   * @param stmt PreparedStatement
   * @return 执行的结果
   * @throws SQLException
   */
  public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}
