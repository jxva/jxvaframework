/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 设置PreparedStatement的参数的接口定义。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public interface PreparedStatementSetter {
  /**
   * 设置PreparedStatement所需的全部参数。
   * @param ps PreparedStatement
   * @throws SQLException
   */
  public void setValues(PreparedStatement ps)  throws SQLException;
}
