/*
 * Created on 2005-3-16
 *
 */
package org.javaresearch.jerch.test;

import org.apache.commons.dbcp.BasicDataSource;
import org.javaresearch.jerch.JDBCTemplate;
import org.javaresearch.jerch.JerchManager;

import junit.framework.TestCase;

/**
 * @author liumin
 *
 */
public class BaseTest extends TestCase {
  static BasicDataSource datasource=new BasicDataSource();
  JDBCTemplate template;
  static {
    datasource.setDriverClassName("com.mysql.jdbc.Driver");
    datasource.setUrl("jdbc:mysql://127.0.0.1:3306/jxva?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8");
    datasource.setUsername("ztemt");
    datasource.setPassword("ztemt");
    JerchManager.init(datasource);
  }
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    template=JerchManager.getTemplate();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
