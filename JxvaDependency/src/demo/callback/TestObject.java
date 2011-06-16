package demo.callback;

import com.jxva.util.ShellUtil;

public class TestObject {
	/**
	 * 一个用来被测试的方法，进行了一个比较耗时的循环
	 */
	public static void testMethod(){  
		System.out.println("test begin");
      //for(int i=0; i<1000000000; i++){
    	// ShellUtil.run("netstat -ao");
		System.out.println(ShellUtil.getSynchRunResult("E:\\test\\jdo -q 100 -rw 200 -rh 100 -op E:\\test\\4 E:\\test\\*.jpg"));
      //}
      System.out.println("end begin");
	}
	/**
	 * 一个简单的测试方法执行时间的方法
	 */
	public void testTime() {
		long begin = System.currentTimeMillis();// 测试起始时间
		testMethod();// 测试方法
		long end = System.currentTimeMillis();// 测试结束时间
		System.out.println("[use time]:" + (end - begin));// 打印使用时间
	}

	public static void main(String[] args) {
		TestObject test = new TestObject();
		test.testTime();
	}
}
