/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;



/**
 * 自动向ValueConverterFactory注册转换类型的ValueConverter的抽象类定义。 
 * 最后更新日期:2005年3月28日
 * @author cherami
 */
public abstract class AutoRegisterValueConverter implements ValueConverter {
  /**
   * 转换器进行转换的原始值的类型。
   */
  protected Class originalType;
  /**
   * 构造方法。
   * @param originalType 转换器进行转换的原始值的类型
   */
  public AutoRegisterValueConverter(Class originalType) {
    this.originalType = originalType;
    ValueConverterFactory.registerValueConverter(originalType, this);
  }
}
