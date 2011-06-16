/*
 * Created on 2005-3-16
 *
 */
package org.javaresearch.jerch.test;



import org.javaresearch.jerch.JerchManager;

/**
 * @author liumin
 */
public class TestJerchManager extends BaseTest {

  public void testGetInstance() {
    JerchManager instance = JerchManager.getInstance();
    assertNotNull(instance);
    JerchManager instance2 = JerchManager.getInstance();
    assertEquals(instance, instance2);
  }

  public void testGetTemplate() {
    assertNotNull(template);
  }

  public void testMultipleThread() {
    for (int i = 0; i < 5; i++) {
      TestThread thread = new TestThread();
      thread.start();
      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    assertTrue(JerchManager.getTemplateCount()==6);
  }

  public void testReset() {
    JerchManager.destroy();
    JerchManager.init(datasource);
    assertEquals(0, JerchManager.getTemplateCount());
    template = JerchManager.getTemplate();
    assertEquals(1, JerchManager.getTemplateCount());
  }

  class TestThread extends Thread {
    public void run() {
      for (int i = 0; i < 100; i++) {
        template = JerchManager.getTemplate();
        template.queryForInt("select count(*) from test");
      }
    }
  }

}