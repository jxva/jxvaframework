package study.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class MyScheduledTask implements Runnable {
	private String tname;

	public MyScheduledTask(String tname) {
		this.tname = tname;
	}

	public void run() {
		System.out.println(tname + "任务时延2秒执行！！！");
	}
}

/**
 * 使用延迟线程池可以指定任务在特定的时延之后执行
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 13:59:51 by Jxva
 */
public class sample17_4 {

	public static void main(String[] args) {
		ScheduledExecutorService scheduledThreadPool = Executors
				.newScheduledThreadPool(2);
		ScheduledExecutorService singScheduledThreadPool = Executors
				.newSingleThreadScheduledExecutor();
		MyScheduledTask mt1 = new MyScheduledTask("mt1");
		MyScheduledTask mt2 = new MyScheduledTask("mt2");
		// 以scheduledThreadPool启动mt1任务执行
		scheduledThreadPool.schedule(mt1, 2, TimeUnit.SECONDS);
		// 用singScheduledThreadPool启动mt2
		singScheduledThreadPool.schedule(mt2, 2000, TimeUnit.MILLISECONDS);
		scheduledThreadPool.shutdown();
		singScheduledThreadPool.shutdown();
	}
}