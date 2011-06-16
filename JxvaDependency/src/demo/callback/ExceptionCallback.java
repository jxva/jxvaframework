package demo.callback;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ExceptionCallback{
	private Set<ExceptionListener> exceptionListeners;// 多个临听器的集合
	private Thread internalThread;// 内部线程，自运行对象，用来产生异常
	private volatile boolean noStopRequested;

	public ExceptionCallback(ExceptionListener[] initialGroup) {// 构造函数
		init(initialGroup);// 有一组临听器
	}

	public ExceptionCallback(ExceptionListener initialListener) {// 构造函数,添加一个临听器
		ExceptionListener[] group = new ExceptionListener[1];
		group[0] = initialListener;
		init(group);
	}

	public ExceptionCallback() {
		init(null);
	}

	private void init(ExceptionListener[] initialGroup) {
		System.out.println("in constructor - initializing...");
		exceptionListeners = Collections.synchronizedSet(new HashSet<ExceptionListener>());
		if (initialGroup != null) {
			for (int i = 0; i < initialGroup.length; i++) {
				addExceptionListener(initialGroup[i]);
			}
		}

		noStopRequested = true;
		Runnable r = new Runnable() {
			public void run() {
				try {
					runWork();// 产生异常的代码
				} catch (Exception x) {
					sendException(x);// 传速异常
				}
			}
		};
		internalThread = new Thread(r);// 内部线程
		internalThread.start();
	}

	private void runWork() {
		try {
			makeConnection(); // will throw an IOException
		} catch (IOException x) {
			sendException(x);// 传速异常
		}
		String str = null;
		int len = determineLength(str); // NullPointerException
	}

	private void makeConnection() throws IOException {
		String portStr = "j20";
		int port = 0;
		try {
			port = Integer.parseInt(portStr);// 产生一个异常
		} catch (NumberFormatException x) {
			sendException(x);// 传速异常
			port = 80;
		}
		connectToPort(port); // will throw an IOException
	}

	private void connectToPort(int portNum) throws IOException {
		throw new IOException("connection refused");
	}

	private int determineLength(String s) {
		return s.length();
	}

	public void stopRequest() {
		noStopRequested = false;
		internalThread.interrupt();
	}

	public boolean isAlive() {
		return internalThread.isAlive();
	}

	private void sendException(Exception x) {
		if (exceptionListeners.size() == 0) {// 如果没有临听器
			x.printStackTrace();
			return;
		}

		synchronized (exceptionListeners) {
			Iterator<ExceptionListener> iter = exceptionListeners.iterator();
			while (iter.hasNext()) {
				ExceptionListener l = iter.next();
				l.exceptionOccurred(x, this);// 处理异常
			}
		}
	}

	public void addExceptionListener(ExceptionListener l) {// 添加一个临听器
		if (l != null) {
			exceptionListeners.add(l);
		}
	}

	public void removeExceptionListener(ExceptionListener l) {// 删除一个临听器
		exceptionListeners.remove(l);
	}

	public String toString() {
		return getClass().getName() + "[isAlive()=" + isAlive() + "]";
	}
}