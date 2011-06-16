/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;



import javax.sql.DataSource;

/**
 * 类库的初始化和管理类。
 * 最后更新日期:2005年3月25日
 * @author cherami
 */
public class JerchManager {
  /**
   * 数据源定义。
   */
  private DataSource datasource;
  /**
   * 唯一实例定义。
   */
  private static JerchManager instance;
  /**
   * 线程实例存储器
   */
  private static final ThreadLocal threadLocal = new ThreadLocal();
  /**
   * JDBCTemplate的实例个数的计数器。
   */
  private static int count;
  /**
   * 初始化类库。
   * 这个方法不能被使用两次，除非使用destroy方法消耗先前初始化的实例。
   * @param datasource 数据源
   */
  public static synchronized void init(DataSource datasource) {
    init(datasource,false);
  }
  /**
   * 初始化类库。
   * 这个方法不能被使用两次，除非使用destroy方法消耗先前初始化的实例。
   * @param datasource 数据源
   * @param debug 是否是调试模式
   */
  public static synchronized void init(DataSource datasource,boolean debug) {
    Utils.setDebuggable(debug);
    Utils.debug("JerchManager.START_INIT",null);
    if (instance != null) {
      throw new JerchException("JerchManager.HAVA_INITED");
    }
    instance = new JerchManager(datasource);
  }
  /**
   * 销毁实例以便重新设置。
   * 要在初始化后更换数据源必须使用本方法先销毁实例。
   */
  public static synchronized void destroy() {
    System.out.println("Template count:"+count+",Open count:"+Utils.getOpenConnectionCount()+",close count:"+Utils.getCloseConnectionCount());
    instance = null;
    count=0;
    threadLocal.set(null);
    if (Utils.getOpenConnectionCount()!=Utils.getCloseConnectionCount()) {
      Utils.debug("JerchManager.SOME_CONNECTION_NOT_CLOSED",null);
    }
    Utils.debug("JerchManager.DESTROYED",null);
  }
  /**
   * 得到JerchManager的实例。
   * 一般情况下没有必要使用此方法，可以直接使用getTemplate()得到需要使用的JDBCTemplate实例。
   * @return JerchManager实例。
   */
  public static JerchManager getInstance() {
    return instance;
  }
  /**
   * 私有构造方法，防止得到多个对象。
   * @param datasource 数据源
   */
  private JerchManager(DataSource datasource) {
    if (datasource==null) {
      throw new JerchException("JerchManager.DataSource_CAN_NOT_NULL");
    }
    this.datasource = datasource;
  }

  /**
   * 得到一个JDBCTemplate实例。
   * 对于当前线程，得到的是原来所使用的实例，对于一个新的线程，将创建一个新的实例。
   * 因此通过此方法得到并使用JDBCTemplate是线程安全的。
   * @return JDBCTemplate实例
   */
  public static JDBCTemplate getTemplate() {
    JDBCTemplate template = (JDBCTemplate) threadLocal.get();
    if (template==null) {
      if (instance==null) {
        throw new JerchException("JerchManager.HAVA_NOT_INITED");
      }
      template = createTemplate();
    }
    return template;
  }
  /**
   * 创建一个JDBCTemplate实例。
   * @return 新建的JDBCTemplate实例
   */
  protected static synchronized JDBCTemplate createTemplate() {
    JDBCTemplate template = new JDBCTemplate(instance.datasource);
    count++;
    threadLocal.set(template);
    return template;
  }
  
  /**
   * 得到JDBCTemplate实例的个数。
   * @return JDBCTemplate实例的个数
   */
  public static int getTemplateCount() {
    return count;
  }

}
