package study.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 从名字可以看出，CountDownLatch是一个倒数计数的锁，当倒数到0时触发事件，也就是开锁，其他人就可以进入了。在一些应用场合中，需要等待某个条件达到要求后才能做后面的事情；同时当线程都完成后也会触发事件，以便进行后面的操作。

CountDownLatch最重要的方法是countDown()和await()，前者主要是倒数一次，后者是等待倒数到0，如果没有到达0，就只有阻塞等待了。

一个CountDouwnLatch实例是不能重复使用的，也就是说它是一次性的，锁一经被打开就不能再关闭使用了，如果想重复使用，请考虑使用CyclicBarrier。

下面的例子简单的说明了CountDownLatch的使用方法，模拟了100米赛跑，10名选手已经准备就绪，只等裁判一声令下。当所有人都到达终点时，比赛结束。

同样，线程池需要显式shutdown。
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 14:36:08 by Jxva
 */
public class TestCountDownLatch {
	public static void main(String[] args) throws InterruptedException {
		// 开始的倒数锁
		final CountDownLatch begin = new CountDownLatch(1);
		// 结束的倒数锁
		final CountDownLatch end = new CountDownLatch(10);
		// 十名选手
		final ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int index = 0; index < 10; index++) {
			final int NO = index + 1;
			Runnable run = new Runnable() {
				public void run() {
					try {
						begin.await();
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println("No." + NO + " arrived");
					} catch (InterruptedException e) {
					} finally {
						end.countDown();
					}
				}

			};
			exec.submit(run);
		}
		System.out.println("Game Start");
		begin.countDown();
		end.await();
		System.out.println("Game Over");
		exec.shutdown();
	}
}