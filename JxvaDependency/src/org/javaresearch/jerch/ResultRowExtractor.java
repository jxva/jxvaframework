/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.sql.SQLException;

/**
 * 提取ResultSet中的一行数据的接口定义。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public interface ResultRowExtractor {
  /**
   * 提取ResultSet中的当前行的数据。
   * @param rs ResultSet
   * @return ResultSet中的当前行的数据
   * @throws SQLException
   */
  public Object extractRow(java.sql.ResultSet rs) throws SQLException;
}
