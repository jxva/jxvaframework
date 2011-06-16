package demo.thread;

/**
	1000 次 new()   用时[共,平均]:15,0
	1000 次 clone() 用时[共,平均]:0,0
	
	10000 次 new()   用时[共,平均]:15,0
	10000 次 clone() 用时[共,平均]:16,0
	
	100000 次 new()   用时[共,平均]:15,0
	100000 次 clone() 用时[共,平均]:63,0
	
	1000000 次 new()   用时[共,平均]:62,0
	1000000 次 clone() 用时[共,平均]:625,0 
	不是clone效率多高,而是new代价太大。当new代价不大的时候,new的效率会高过clone。
 *
 */

class A implements Cloneable {
	String abc = "ttttttttttttttttttttttttttttt";
	String def = "sssssssssssssssssssssssssssss";

	void m1() {
		System.out.println(abc);
	}

	void m2() {
		System.out.println(def);
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private static A a = new A();

	public static A getInstance() {
		return (A) a.clone();
	}
}

public class CloneTest {
	public static void main(String[] args) throws Exception {
		long times = 100000;
		long time1 = 0;
		long time2 = 0;

		time1 = testNew(times);
		time2 = testClone(times);

		System.out.println(times + " 次 new()   用时[共,平均]:" + time1 + "," + time1
				/ times);
		System.err.println(times + " 次 clone() 用时[共,平均]:" + time2 + "," + time2
				/ times);
	}

	public static long testNew(long times) {
		long start = System.currentTimeMillis();
		for (long i = 0; i < times; i++) {
			new A();
		}
		long end = System.currentTimeMillis();
		return end - start;
	}

	public static long testClone(long times) {
		long start = System.currentTimeMillis();
		for (long i = 0; i < times; i++) {
			A.getInstance();
		}
		long end = System.currentTimeMillis();
		return end - start;
	}

}