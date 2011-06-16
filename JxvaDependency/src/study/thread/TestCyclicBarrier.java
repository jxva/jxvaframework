package study.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在实际应用中，有时候需要多个线程同时工作以完成同一件事情，而且在完成过程中，
 * 往往会等待其他线程都完成某一阶段后再执行，等所有线程都到达某一个阶段后再统一执行。

比如有几个旅行团需要途经深圳、广州、韶关、长沙最后到达武汉。旅行团中有自驾游的，有徒步的，
有乘坐旅游大巴的；这些旅行团同时出发，并且每到一个目的地，都要等待其他旅行团到达此地后再同时出发，
直到都到达终点站武汉。

这时候CyclicBarrier就可以派上用场。CyclicBarrier最重要的属性就是参与者个数，另外最要方法是await()。
当所有线程都调用了await()后，就表示这些线程都可以继续执行，否则就会等待。
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 14:40:34 by Jxva
 */
public class TestCyclicBarrier {
	// 徒步需要的时间: Shenzhen, Guangzhou, Shaoguan, Changsha, Wuhan
	private static int[] timeWalk = { 5, 8, 15, 15, 10 };
	// 自驾游
	private static int[] timeSelf = { 1, 3, 4, 4, 5 };
	// 旅游大巴
	private static int[] timeBus = { 2, 4, 6, 6, 7 };

	static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date()) + ": ";
	}

	static class Tour implements Runnable {
		private int[] times;
		private CyclicBarrier barrier;
		private String tourName;

		public Tour(CyclicBarrier barrier, String tourName, int[] times) {
			this.times = times;
			this.tourName = tourName;
			this.barrier = barrier;
		}

		public void run() {
			try {
				Thread.sleep(times[0] * 1000);
				System.out.println(now() + tourName + " Reached Shenzhen");
				barrier.await();
				Thread.sleep(times[1] * 1000);
				System.out.println(now() + tourName + " Reached Guangzhou");
				barrier.await();
				Thread.sleep(times[2] * 1000);
				System.out.println(now() + tourName + " Reached Shaoguan");
				barrier.await();
				Thread.sleep(times[3] * 1000);
				System.out.println(now() + tourName + " Reached Changsha");
				barrier.await();
				Thread.sleep(times[4] * 1000);
				System.out.println(now() + tourName + " Reached Wuhan");
				barrier.await();
			} catch (InterruptedException e) {
			} catch (BrokenBarrierException e) {
			}
		}
	}

	public static void main(String[] args) {
		// 三个旅行团
		CyclicBarrier barrier = new CyclicBarrier(3);
		ExecutorService exec = Executors.newFixedThreadPool(3);
		exec.submit(new Tour(barrier, "WalkTour", timeWalk));
		exec.submit(new Tour(barrier, "SelfTour", timeSelf));
		exec.submit(new Tour(barrier, "BusTour", timeBus));
		exec.shutdown();
	}
}