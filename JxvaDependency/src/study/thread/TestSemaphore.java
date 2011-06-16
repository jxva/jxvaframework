package study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 下面的Demo中申明了一个只有5个许可的Semaphore，而有20个线程要访问这个资源，
 * 通过acquire()和release()获取和释放访问许可
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 14:33:11 by Jxva
 */
public class TestSemaphore {
  public static void main(String[] args) {
    // 线程池
    ExecutorService exec = Executors.newCachedThreadPool();
    // 只能5个线程同时访问
    final Semaphore semp = new Semaphore(5);
    // 模拟20个客户端访问
    for (int index = 0; index < 20; index++) {
      final int NO = index;
      Runnable run = new Runnable() {
        public void run() {
          try {
            // 获取许可
            semp.acquire();
            System.out.println("Accessing: " + NO);
            Thread.sleep((long) (Math.random() * 10000));
            // 访问完后，释放
            semp.release();
          } catch (InterruptedException e) {
          }
        }
      };
      exec.execute(run);
    }
    // 退出线程池
    exec.shutdown();
  }
}