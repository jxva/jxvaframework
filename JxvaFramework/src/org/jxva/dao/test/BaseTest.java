/*
 * Created on 2005-3-16
 *
 */
package org.jxva.dao.test;

import junit.framework.TestCase;

import com.jxva.dao.JdbcTemplate;
import com.jxva.dao.datasource.SimpleDataSource;

/**
 * @author liumin
 *
 */
public class BaseTest extends TestCase {
  static SimpleDataSource datasource=new SimpleDataSource();
  static JdbcTemplate template;
  static {
    datasource.setDriverClassName("com.mysql.jdbc.Driver");
    datasource.setUrl("jdbc:mysql://127.0.0.1:3306/jxva?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8");
    datasource.setUsername("ztemt");
    datasource.setPassword("ztemt");
  }
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
	template=new JdbcTemplate(datasource);
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
