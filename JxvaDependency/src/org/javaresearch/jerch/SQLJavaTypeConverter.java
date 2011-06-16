/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SQL和Java类型转换器类定义。
 * 最后更新日期:2005年3月29日
 * @author cherami
 */
public class SQLJavaTypeConverter {
  /**
   * 转换映射。
   */
  private static Map convertMap=new HashMap();
  /**
   * 初始化。
   */
  static {
    setDefaultJavaType();
  }
  /**
   * 根据数据库的类型常量定义得到对应的Java类型。
   * @param sqlType 数据库的类型常量定义
   * @return 对应的Java类型
   */
  public static Class getJavaType(int sqlType) {
    Class result=(Class)convertMap.get(new Integer(sqlType));
    if (result!=null) {
      return result;
    } else {
      return Object.class;
    }
  }
  /**
   * 设置缺省的映射关系。
   */
  public static void setDefaultJavaType() {
    convertMap.put(new Integer(Types.INTEGER),Integer.class);
    convertMap.put(new Integer(Types.VARCHAR),String.class);
    convertMap.put(new Integer(Types.BIGINT),Long.class);
    convertMap.put(new Integer(Types.BOOLEAN),Boolean.class);
    convertMap.put(new Integer(Types.DATE),Date.class);
    convertMap.put(new Integer(Types.TIMESTAMP),Timestamp.class);
    convertMap.put(new Integer(Types.TIME),Timestamp.class);
  }
  /**
   * 设置数据库的类型常量对应的Java类型。
   * @param sqlType 数据库的类型常量
   * @param javaType Java类型
   */
  public static void setJavaType(int sqlType,Class javaType) {
    convertMap.put(new Integer(sqlType),javaType);
  }
}
