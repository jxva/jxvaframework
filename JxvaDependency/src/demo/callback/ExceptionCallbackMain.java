package demo.callback;

/**
 * xListener对象将监视ec对象，一旦活动的ec对象内部发生异常，
 * 将触发exceptionOccurred(x,source)的调用来处理异常。
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 10:40:36 by Jxva
 */
public class ExceptionCallbackMain implements ExceptionListener {

	private int exceptionCount;

	public ExceptionCallbackMain() {
		exceptionCount = 0;
	}

	public void exceptionOccurred(Exception x, Object source) {
		exceptionCount++;
		System.err.println("EXCEPTION #" + exceptionCount + ", source="+ source);
		x.printStackTrace();
	}

	public static void main(String[] args) {
		ExceptionListener xListener = new ExceptionCallbackMain();
		ExceptionCallback ec = new ExceptionCallback(xListener);
	}
}