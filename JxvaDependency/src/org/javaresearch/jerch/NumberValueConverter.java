/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;


/**
 * 将Number类型的值转换为其他类型的转换器。 
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public class NumberValueConverter extends AutoRegisterValueConverter {
  /**
   * 构造方法。
   */
  public NumberValueConverter() {
    super(Number.class);
  }
  /**
   * 将original的值转换为目标类型。
   * @param original 原始值
   * @param targetType 目标值的类型
   * @return 转换后的结果
   */
  public Object convert(Object original, Class targetType) {
    Number value = (Number) original;
    if (targetType.equals(Long.class)) {
      return new Long(value.longValue());
    } else if (targetType.equals(Integer.class)) {
      return new Integer(value.intValue());
    } else if (targetType.equals(Character.class)) {
      return new Character((char)value.intValue());
    } else if (targetType.equals(Byte.class)) {
      return new Byte(value.byteValue());
    }
    return original;
  }
}
