/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Statement回调接口定义。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public interface StatementCallback {
  /**
   * 定义Statement中的执行内容。 
   * @param stmt Statement
   * @return 执行的结果
   * @throws SQLException
   */
  public Object doInStatement(Statement stmt) throws SQLException;
}
