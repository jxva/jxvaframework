/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

/**
 * 值转换接口定义。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public interface ValueConverter {
  /**
   * 将original的值转换为目标类型。
   * @param original 原始值
   * @param targetType 目标值的类型
   * @return 转换后的结果
   */
  public Object convert(Object original,Class targetType);
}
