/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 提取ResultSet中的全部数据的接口定义。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public interface ResultSetExtractor {
  /**
   * 提取ResultSet中的全部数据。
   * @param rs ResultSet
   * @return ResultSet中的全部数据
   * @throws SQLException
   */
  public Object extractSet(ResultSet rs) throws SQLException;
}
