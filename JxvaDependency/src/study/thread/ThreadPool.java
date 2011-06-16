package study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 线程池有两个，一种是固定尺寸的线程池和可变尺寸的线程池
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 13:48:25 by Jxva
 */
public class ThreadPool {
	public static void main(String[] args) {
		//
		//ExecutorService threadPool = Executors.newFixedThreadPool(2);
		//可变尺寸线程池可以根据任务的多少来自动调整待命线程的数量，优化执行性能。
		ExecutorService threadPool =Executors.newCachedThreadPool();
		MyTask mt1 = new MyTask("MT1");
		MyTask mt2 = new MyTask("MT2");
		MyTask mt3 = new MyTask("MT3");
		threadPool.execute(mt1);
		threadPool.execute(mt2);
		threadPool.execute(mt3);
		//如果希望程序在执行完所有任务后退出，需要调用ExecutorService接口中的shutdown方法来关闭线程池。
		 threadPool.shutdown();
	}
}