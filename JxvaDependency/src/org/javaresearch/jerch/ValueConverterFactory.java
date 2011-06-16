/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;



import java.util.HashMap;

/**
 * 值转换工厂。
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public class ValueConverterFactory {
  /**
   * 值类型转换映射。
   */
  private static HashMap converterMap = new HashMap();
  
  static {
    registerDefaultValueConverter();
  }
  /**
   * 值转换方法，将根据original的类型取得对应的类型的转换器并转换为目标类型。
   * @param original 原始值
   * @param targetType 目标类型
   * @param useSuper 找不到完全匹配的类型时是否使用父类的转换器
   * @return 转换后的值，如果没有对应的类型的转换器则直接返回原值。
   */
  public static Object convert(Object original, Class targetType,boolean useSuper) {
    ValueConverter converter = getValueConverter(original.getClass(),useSuper);
    if (converter != null) {
      return converter.convert(original, targetType);
    } else {
      return original;
    }
  }
  
  /**
   * 值转换方法，将根据original的类型取得对应的类型的转换器并转换为目标类型，
   * 没有完全匹配的类型时使用父类的类型进行转换。
   * @param original 原始值
   * @param targetType 目标类型
   * @return 转换后的值，如果没有对应的类型的转换器则直接返回原值。
   */
  public static Object convert(Object original, Class targetType) {
    return convert(original,targetType,true);
  }
  /**
   * 得到类型对应的转换器。
   * @param type 类型
   * @param useSuper 没有完全匹配的类型时是否使用父类的转换器
   * @return 对应的转换器，没有时返回null。
   */
  public static ValueConverter getValueConverter(Class type,boolean useSuper) {
    if (useSuper) {
      ValueConverter converter = (ValueConverter) converterMap.get(type);
      while (converter==null) {
        type=type.getSuperclass();
        if (type!=null) {
          converter = (ValueConverter) converterMap.get(type);
        } else {
          return null;
        }
      }
      return converter;
    } else {
      return (ValueConverter) converterMap.get(type);
    }
  }
  /**
   * 注册类型对应的转换器。
   * @param type 原值类型
   * @param valueConverter 转换器
   */
  public static void registerValueConverter(Class type,ValueConverter valueConverter) {
    converterMap.put(type, valueConverter);
  }
  /**
   * 注册系统缺省的转换器。
   */
  private static void registerDefaultValueConverter() {
    new NumberValueConverter();
  }
  
}
