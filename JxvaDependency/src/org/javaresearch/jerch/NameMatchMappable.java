/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

/**
 * 根据字段的名称进行自动匹配的Mappable实现。
 * 最后更新日期:2005年3月25日
 * @author cherami
 */
public class NameMatchMappable implements Mappable {
  /**
   * 得到字段对应的填充方法的方法名。
   * @param fieldName 数据库表的字段名
   * @return 前面加set，将字段名首字母大写。
   */
  public String getMapMethod(String fieldName) {
    return "set" + Character.toUpperCase(fieldName.charAt(0))
        + fieldName.substring(1);
  }
  
  /**
   * 得到字段对应的填充方法的参数类型。
   * 完全根据数据库的返回类型进行默认匹配。
   * @see SQLJavaTypeConverter#getJavaType(int sqlType) SQLJavaTypeConverter.getJavaType
   * @param fieldName 数据库表的字段名
   * @param dbType 数据库返回的类型常量定义
   * @return 数据库的返回类型对应的Java类型
   */
  public Class getMethodParameterType(String fieldName, int dbType) {
    return SQLJavaTypeConverter.getJavaType(dbType);
  }
}
