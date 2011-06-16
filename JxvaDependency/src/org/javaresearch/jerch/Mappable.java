/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

/**
 * 用于JavaBean的填充所需的字段的对应的方法和类型的定义。
 * 最后更新日期:2005年3月25日
 * @author cherami
 */
public interface Mappable {
  /**
   * 得到字段对应的填充方法的方法名。
   * @param fieldName 数据库表的字段名
   * @return 进行填充的方法名。
   */
  public String getMapMethod(String fieldName);
  /**
   * 得到字段对应的填充方法的参数类型。
   * 数据库返回的值的类型和Java中的可能不是匹配的，或者JavaBean中自己定义为原始类型了，因此需要指定。
   * @param fieldName 数据库表的字段名
   * @param dbType 数据库返回的类型常量定义
   * @return 进行填充的方法的参数的类型
   */
  public Class getMethodParameterType(String fieldName,int dbType);
}
