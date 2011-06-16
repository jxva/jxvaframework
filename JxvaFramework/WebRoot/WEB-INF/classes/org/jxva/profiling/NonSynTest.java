package org.jxva.profiling;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sun.misc.Unsafe;

public class NonSynTest {
	static class IncMan implements Runnable {
		private String fID;

		public IncMan(String id) {
			this.fID = id;

		}

		public void run() {
			try {
				NonSynTest.begin.await();
				for (int i = 0; i < 2000000; i++) {
					nosyn.incIndex();
				}
			} catch (InterruptedException e) {
			} finally {
				NonSynTest.end.countDown();
				System.out.println("Thread" + fID + " finished!");
			}
		}
	}

	static final int threadlen = 20;
	static CountDownLatch begin = new CountDownLatch(1);
	static CountDownLatch end = new CountDownLatch(threadlen);
	static NonSynTest nosyn = new NonSynTest();
	private static Unsafe unsafe = null;
	private static long valueOffset;
	static {
		try {
			unsafe = Unsafe.getUnsafe();
			valueOffset = unsafe.objectFieldOffset(NonSynTest.class.getDeclaredField("index"));
		} catch (Exception ex) {
			throw new Error(ex);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newFixedThreadPool(threadlen);
		for (int index = 0; index < threadlen; index++) {
			exec.submit(new IncMan(index + ""));
		}
		System.out.println("Game Start");
		begin.countDown();
		end.await();
		exec.shutdown();
		System.out.println("Game Over");
		System.out.println("Count: " + nosyn.getIndex());
	}

	private int index = 0;

	public int getIndex() {
		return index;
	}

	public void incIndex() {
		for (;;) {
			int current = index;
			int next = current + 1;
			if (unsafe.compareAndSwapInt(this, valueOffset, current, next))
				return;
		}
	}
}