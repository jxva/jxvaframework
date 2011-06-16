/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;



import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 批量更新时设置PreparedStatement的值的接口定义。 
 * 最后更新日期:2005年3月25日
 * @author cherami
 */
public interface BatchPreparedStatementSetter {
  /**
   * 得到批次的总次数。
   * @return 批次的总次数
   */
  public int getBatchSize();

  /**
   * 设置第i次的值。
   * 请注意它是针对每一次设置值的，
   * 如果所有的值不是保存在一个List类型的可以通过索引访问的集合中，
   * 那么这个方法的实现中可能需要使用switch或者if语句进行判断。
   * 你可以考虑使用PreparedStatementSetter一次性设置全部的批次。
   * @param ps PreparedStatement
   * @param i 执行批次
   * @throws SQLException
   */
  public void setValues(PreparedStatement ps, int i) throws SQLException;
}
