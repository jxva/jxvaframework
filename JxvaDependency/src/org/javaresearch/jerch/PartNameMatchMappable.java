/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;



import java.util.HashMap;
import java.util.Map;

/**
 * 部分根据字段的名称进行自动匹配的Mappable实现。 
 * 需要指定的是例外部分字段的相应方法和类型。 
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public class PartNameMatchMappable implements Mappable {
  private Map expectField;
  /**
   * 构造一个例外字段为空的PartNameMatchMappable实例。
   *
   */
  public PartNameMatchMappable() {
    this.expectField = new HashMap();
  }
  /**
   * 以指定参数构造一个PartNameMatchMappable实例。
   * @param expectField 例外的字段映射，主键为字段名，值为对象数组，第一个元素为方法名，第二个元素为类型。
   */
  public PartNameMatchMappable(Map expectField) {
    this.expectField = expectField;
  }
  /**
   * 设置字段对应的方法名和类型。
   * @param fieldName 数据库字段名
   * @param methodName 对应的方法
   * @param type 类型
   */
  public void setMapField(String fieldName,String methodName,Class type) {
    Object[] map={methodName,type};
    expectField.put(fieldName,map);
  }
  
  /**
   * 得到字段对应的填充方法的方法名。
   * @param fieldName 数据库表的字段名
   * @return 如果例外映射中有对应的方法则返回该方法，否则前面加set，将字段名首字母大写。
   */
  public String getMapMethod(String fieldName) {
    Object[] result = (Object[]) expectField.get(fieldName);
    if (result != null) {
      return (String) result[0];
    } else {
      return "set" + Character.toUpperCase(fieldName.charAt(0))
          + fieldName.substring(1);
    }
  }

  /**
   * 得到字段对应的填充方法的参数类型。
   * @see SQLJavaTypeConverter#getJavaType(int sqlType) SQLJavaTypeConverter.getJavaType
   * @param fieldName 数据库表的字段名
   * @param dbType 数据库返回的类型常量定义
   * @return 如果例外映射中有对应的类型则返回该类型，否则返回数据库的返回类型对应的Java类型
   */
  public Class getMethodParameterType(String fieldName, int dbType) {
    Object[] result = (Object[]) expectField.get(fieldName);
    if (result != null) {
      return (Class) result[1];
    } else {
      return SQLJavaTypeConverter.getJavaType(dbType);
    }
  }
}
