package demo.callback;

/**
 * 1、普通异步
2、回调异步
3、单向异步
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 11:48:52 by Jxva
 */
public class Tools {

	/**
	 * 测试函数使用时间，通过定义CallBack接口的execute方法
	 * 
	 * @param callBack
	 */
	public void testTime(CallBack callBack) {
		long begin = System.currentTimeMillis();// 测试起始时间
		System.out.println("call begin");
		callBack.execute();// /进行回调操作
		long end = System.currentTimeMillis();// 测试结束时间
		System.out.println("call end");
		System.out.println("[use time]:" + (end - begin));// 打印使用时间
	}
	
	public void testTime1(){
		long begin = System.currentTimeMillis();// 测试起始时间
		System.out.println("call2 begin");
		new Thread(new Runnable(){ //异步
	           public void run(){
	        	   TestObject.testMethod();//如果出错,最好能够进行回调
	            } 
	       }, "testThread").start();		
		long end = System.currentTimeMillis();// 测试结束时间
		System.out.println("call2 end");
		System.out.println("[use2 time]:" + (end - begin));// 打印使用时间
	}

	public static void main(String[] args) {
		Tools tool = new Tools();
		tool.testTime(new CallBack() {
			// 定义execute方法
			public void execute() {
				// 这里可以加放一个或多个要测试运行时间的方法
				TestObject.testMethod();
			}
		});
		
		tool.testTime1();
		
		System.out.println("end");
	}

}
