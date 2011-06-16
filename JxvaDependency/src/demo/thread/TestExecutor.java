package demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
    http://www.ibm.com/developerworks/cn/java/j-lo-scalbility/?ca=drs-tp4008
	加入轻量级的线程池—— Executor
	
	大多数并发应用程序是以执行任务（task）为基本单位进行管理的。通常情况下,我们会为每个任务单独创建一个线程来执行。
	这样会带来两个问题:
	一,大量的线程（>100）会消耗系统资源,使线程调度的开销变大,引起性能下降；
	二,对于生命周期短暂的任务,频繁地创建和消亡线程并不是明智的选择。
	因为创建和消亡线程的开销可能会大于使用多线程带来的性能好处。

	一种更加合理的使用多线程的方法是使用线程池（Thread Pool）。 
	java.util.concurrent 提供了一个灵活的线程池实现:Executor 框架。
	这个框架可以用于异步任务执行,而且支持很多不同类型的任务执行策略。
	它还为任务提交和任务执行之间的解耦提供了标准的方法,为使用 Runnable 描述任务提供了通用的方式。 
	Executor 的实现还提供了对生命周期的支持和 hook 函数,可以添加如统计收集、应用程序管理机制和监视器等扩展。
	
	在线程池中执行任务线程,可以重用已存在的线程,免除创建新的线程。这样可以在处理多个任务时减少线程创建、消亡的开销。
	同时,在任务到达时,工作线程通常已经存在,用于创建线程的等待时间不会延迟任务的执行,因此提高了响应性。
	通过适当的调整线程池的大小,在得到足够多的线程以保持处理器忙碌的同时,
	还可以防止过多的线程相互竞争资源,导致应用程序在线程管理上耗费过多的资源。
	
	Executor 默认提供了一些有用的预设线程池,可以通过调用 Executors 的静态工厂方法来创建。

    * newFixedThreadPool:提供一个具有最大线程个数限制的线程池。
    * newCachedThreadPool:提供一个没有最大线程个数限制的线程池。
    * newSingleThreadExecutor:提供一个单线程的线程池。保证任务按照任务队列说规定的顺序（FIFO,LIFO,优先级）执行。
    * newScheduledThreadPool:提供一个具有最大线程个数限制线程池,并支持定时以及周期性的任务执行。

 * @author Administrator
 *
 */

public class TestExecutor {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(100);
		for (int i = 0; i <100; i++) {
			pool.execute(new Runnable() {
				public void run() {
					//while (true) {
						// dosomething
					//}
					System.out.println(Thread.currentThread().getName());
				}
			});
		}
		pool.shutdown();
	}
}
