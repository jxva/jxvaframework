/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;



/**
 * 系统统一的异常定义。
 * 为了将底层的SQLException转换为一个运行时异常，使得使用这个类库的代码不用捕获SQLException，
 * 并且类库针对各种SQLException试图给出一个更好的异常描述，并且还会包含原来的异常信息。 
 * 最后更新日期:2005年3月25日
 * @author cherami
 */
public class JerchException extends RuntimeException {
  /**
   * 创建一个JerchException异常。
   */
  public JerchException() {
    super();
  }

  /**
   * 根据信息关键字messageKey创建一个JerchException异常。
   * @param messageKey 出错信息在资源文件中的关键字
   */
  public JerchException(String messageKey) {
    super(Utils.getMessage(messageKey));
  }
  /**
   * 根据底层异常创建一个JerchException异常。
   * @param e 底层异常。
   */
  public JerchException(Throwable e) {
    super(e);
  }
  /**
   * 根据信息关键字messageKey和底层异常创建一个JerchException异常。
   * @param messageKey 出错信息在资源文件中的关键字
   * @param e 底层异常
   */
  public JerchException(String messageKey,Throwable e) {
    super(Utils.getMessage(messageKey),e);
  }
}
