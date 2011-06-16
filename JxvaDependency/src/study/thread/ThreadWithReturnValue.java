package study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

//实现了Callable接口的任务类
class MyCallableImpl implements Callable<String> {
	String taskName;

	public MyCallableImpl(String taskName) {
		this.taskName = taskName;
	}

	// 存放任务代码的run
	public String call()throws Exception {
		Thread.sleep(1000);
		System.out.println(taskName + "任务成功执行！");
		return "恭喜你，任务成功执行，我是返回消息";
	}
}

/**
 * 如果希望调用的线程不但能执行指定的任务，还想有返回值，
 * 这时我们就可以使用Callable与Future接口来实现。至于它的方法可以去查找相关API。
 * 从结果中可以看出，这个线程不但执行了任务代码，还带了返回值。
 * 
 * 
 * 
 * 可以使用Future接口或者FutureTask类从异步执行的线程中得到一个返回值。
 * Future接口提供了检查计算过程是否完成、检索计算结果或终止计算过程的一些方法。
 * FutureTask类提供了Future接口方法的基本实现（implementation）。
 * 只有计算过程完成以后才能检索结果；如果计算过程没有完成，get方法会被阻塞（block）。
 * 
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 13:54:01 by Jxva
 */
public class ThreadWithReturnValue {

	/**
	 * J2SE 5.0引入的Callable接口。不同于Runnable接口拥有run方法，
	 * Callable接口提供的是call方法，该方法可以返回一个Object对象，
	 * 或可返回任何一个在泛型化格式中定义了的特定类型的对象。
    public interface Callable<V> {
       V call() throws Exception;
    }
	因为你不可能把Callable对象传到Thread对象去执行，你可换用ExecutorService对象去执行Callable对象。
	该服务接受Callable对象，并经由submit方法去执行它。
    <T> Future<T> submit(Callable<T> task)
	如该方法的定义所示，提交一个Callable对象给ExecutorService会返回一个Future对象。
	然后，Future的get方法将会阻塞，直到任务完成。
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建线程
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		// 调用线程池的submit方法执行任务
		Future<String> future = threadPool.submit(new MyCallableImpl("taskA"));
		try {
			// 调用阻塞方法get获取任务的返回值
			System.out.println("EEEEEEEEE");
			System.out.println(future.get());
			System.out.println("DDDDD");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		threadPool.shutdown();
		
		
		//========================================================
		FutureTask<String> primeTask = new FutureTask<String>(new MyCallableImpl("taskB"));
		//Thread t = new Thread(primeTask);
       // t.start();
        try {

            // 假设现在做其它事情
            //Thread.sleep(5000);
            // 回来看看质数找好了吗
            if(primeTask.isDone()) {
                String primes = primeTask.get();//非阻塞方法
                System.out.println(primes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 

	}
}