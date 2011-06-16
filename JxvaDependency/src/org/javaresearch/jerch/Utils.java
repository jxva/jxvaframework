/**
 * Copyright: Copyright (c) 2005-2005
 * Company: JavaResearch(http://www.javaresearch.org)
 */
package org.javaresearch.jerch;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 工具类。
 * 最后更新日期:2005年5月7日
 * @author cherami
 */
public class Utils {
  /**
   * 是否是调试模式。
   */
  private static boolean debuggable = false;
  /**
   * 系统使用的国际化资源。
   */
  private static ResourceBundle resources=ResourceBundle.getBundle("org.javaresearch.jerch.jerch");

  /**
   * 日志记录。
   */
  private static final Logger logger = Logger.getLogger("jerch");
  
  private static long openConnectionCount=0;
  private static long closeConnectionCount=0;
 
  /**
   * 根据主键得到对应的国际化消息。
   * @param key 消息的主键
   * @return 国际化的消息
   */
  public static String getMessage(String key) {
    return resources.getString(key);
  }
  
  /**
   * 向日志中写入警告信息。
   * @param message 信息主键
   * @param th 相关的异常
   */
  public static void warn(String message, Throwable th) {
    logger.log(Level.WARNING, getMessage(message), th);
  }
  /**
   * 向日志中写入错误信息。
   * @param message 信息主键
   * @param th  相关的异常
   */
  public static void error(String message, Throwable th) {
    logger.log(Level.SEVERE, getMessage(message), th);
  }
  /**
   * 向日志中写入正常信息。
   * @param message 信息主键
   * @param th  相关的异常
   */
  public static void info(String message, Throwable th) {
    logger.log(Level.INFO, getMessage(message), th);
  }
  /**
   * 向日志中写入调试信息。
   * @param message 信息主键
   * @param th 相关的异常
   */
  public static void debug(String message, Throwable th) {
    if (debuggable) {
      if (th!=null) {
        logger.log(Level.INFO, getMessage(message),th);
      } else {
        logger.log(Level.INFO, getMessage(message));
      }
    }
  }
  /**
   * 设置是否处于调试状态。
   * @param debuggable 是否是调试状态
   */
  public static void setDebuggable(boolean debuggable) {
    Utils.debuggable = debuggable;
  }
  

  /**
   * 关闭JDBC的Statement并忽略任何异常。
   * @param stmt 要关闭的Statement
   */
  public static void closeStatement(Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      }
      catch (SQLException ex) {
        warn("Utils.CAN_NOT_CLOSE_STATEMENT", ex);
      }
    }
  }

  /**
   * 关闭JDBC的ResultSet并忽略任何异常。
   * @param rs 要关闭的ResultSet
   */
  public static void closeResultSet(ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      }
      catch (SQLException ex) {
        warn("Utils.CAN_NOT_CLOSE_RESULTSET", ex);
      }
    }
  }

  /**
   * 取得JDBC的Connection。
   * @param datasource 数据源
   * @return JDBC的Connection
   */
  public static synchronized Connection getConnection(DataSource datasource) {
    try {
      Connection connection  = datasource.getConnection();
      return connection;
    }
    catch (SQLException ex) {
      warn("Utils.CAN_NOT_GET_CONNECTION", ex);
      throw new JerchException("Utils.CAN_NOT_GET_CONNECTION",ex);
    }
  }

  /**
   * 关闭JDBC的Connection并忽略任何异常。
   * @param con 要关闭的Connection
   */
  public static void closeConnection(Connection con) {
    if (con != null) {
      try {
        con.close();
        closeConnectionCount++;
      }
      catch (SQLException ex) {
        warn("Utils.CAN_NOT_CLOSE_CONNECTION", ex);
      }
    }
  }
  
  
  /**
   * 得到Connection关闭的次数。
   * @return Connection关闭的次数。
   */
  public static long getCloseConnectionCount() {
    return closeConnectionCount;
  }
  /**
   * 得到Connection打开的次数。
   * @return Connection打开的次数。
   */
  public static long getOpenConnectionCount() {
    return openConnectionCount;
  }
}
