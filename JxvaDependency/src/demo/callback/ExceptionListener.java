package demo.callback;

/**
 * java程序发生异常时，我们一般用try,catch块来处理，但我们也可以实现自己的异常回调机制把发生的异常
 * (包括RuntimeException)通知到我们处理异常的对象。
    首先定义我们自己的处理异常的类(异常临听器），它实现ExceptionListener接口。
	public interface ExceptionListener {
		public void exceptionOccurred(Exception x, Object source);
	}
    第一个参数是发生的异常的引用，第二个参数是捕获该异常的类的引用。 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-10 10:38:12 by Jxva
 */
public interface ExceptionListener {
	public void exceptionOccurred(Exception x, Object source);
}