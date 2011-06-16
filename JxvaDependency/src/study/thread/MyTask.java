package study.thread;

class MyTask implements Runnable {// 类
	private String tname;

	public MyTask(String tname) {
		this.tname = tname;
	}

	public void run() {
		System.out.println("=========任务" + tname + "开始执行=======");
		for (int i = 0; i < 50; i++) {
			System.out.print("[" + tname + "_" + i + "] ");
		}
		System.out.println("=========任务" + tname + "开始结束=======");
	}
}
