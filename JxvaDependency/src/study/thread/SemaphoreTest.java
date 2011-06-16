package study.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量,实现方法的多线程限量使用。
 * 信号量(Semaphore),实现方法的并发限量使用 
 * 
 * Semaphore可以控制某个资源可被同时访问的个数
 * 
 * Semaphore维护了当前访问的个数，提供同步机制，控制同时访问的个数。
 * 在数据结构中链表可以保存“无限”的节点，用Semaphore可以实现有限大小的链表。
 * 另外重入锁ReentrantLock也可以实现该功能，但实现上要负责些，代码也要复杂些。
 */
public class SemaphoreTest extends Thread {
	public static void main(String[] args) {
		for (int i = 0; i <= 10; i++) {
			new SemaphoreTest().start();
		}
	}

	public void run() {
		int i = 2;
		OnlyTwo ot = new OnlyTwo();
		while (i-- > 0) {
			System.out.printf("[%3d]%d=%d\n", this.getId(), System.currentTimeMillis(), +ot.getSomthing());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ot.returnSomthing();
		}
	}
}

class OnlyTwo {
	private static final int MAX_AVAILABLE = 3;
	private static final Semaphore available = new Semaphore(MAX_AVAILABLE,false);
	private static int NUM = 1;

	/**
	 * 执行方法。
	 * 
	 * @return
	 */
	public int getSomthing() {
		try {
			//acquire()获取一个许可，如果没有就等待
			available.tryAcquire(60,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return NUM++;
	}

	/**
	 * 归还
	 */
	public void returnSomthing() {
		//acquire()
		available.release();
	}
}