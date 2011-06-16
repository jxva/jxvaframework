package demo.log;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 在项目中记录日志是必须的,但是往往在记录日志的时候都是通过logger.debug("...")来记录的,
 * 程序一边运行一边记录日志,尤其在多线程或者Web应用中,同一时间可能有不同的日志记录到同一个
 * 文件中去,当出现错误的时候就不能确定在那些信息是你想关心的.因此如果我们在一个程序的开始的
 * 地方开始记录日志,但是这个日志不记录到日志文件中,而是保存在一个ThreadLocal中,在出错的时候
 * 就可以将这些日志全部输出到日志文件中去,就能方便查找了.
	代码如下:
 *
 * 在代码中,您可以调用 DebugLogger.put() 来保存您的程序正在做什么的信息,而且,稍后如果有
 * 必要（例如发生了一个错误）,您能够容易地检索与某个特定线程相关的调试信息。 与简单地把所有
 * 信息转储到一个日志文件,然后努力找出哪个日志记录来自哪个线程（还要担心线程争用日志纪录对象）
 * 相比,这种技术简便得多,也有效得多。 
 */

public class DebugLogger {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 测试使用,正式使用的时候可以在一个方法的开始new一个DebugLogger
		DebugLogger logger = new DebugLogger();
		// 模拟需要记录的十次日志信息,一次记录到DebugLogger里面去了
		for (int i = 0; i < 10; i++) {
			// 在需要记录日志的地方改为下面的代码
			logger.put("Log info " + i);
		}

		// 到了方法结束或者出现异常的时候,将刚才记录的日志取出来
		String[] strings = logger.get();
		// 将取出来的日志一次记录到日志文件里去
		for (int i = 0; i < strings.length; i++) {
			System.out.println(strings[i]);
		}

		// 请空DebugLogger里记录的日志内容
		logger.clear();
			String[] strings1 = logger.get();
			for (int i = 0; i < strings1.length; i++) {
				System.out.println(strings1[i]);
			}


	}

	// 内部静态类,继承至ThreadLocal
	private static class ThreadLocalList extends ThreadLocal {
		// 在调用get()方法的时候返回一个ArrayList对象
		public Object initialValue() {
			return new ArrayList();
		}

		// 将保存在ThreadLocal中的List返回
		public List getList() {
			return (List) super.get();
		}
	}

	private ThreadLocalList list = new ThreadLocalList();
	private static String[] stringArray = new String[0];

	// 清空记录的日志
	public void clear() {
		list.getList().clear();
	}

	// 将需要记录的日志内容保存下来
	public void put(String text) {
		list.getList().add(text);
	}

	// 返回需要记录的日志
	public String[] get() {
		return (String[]) list.getList().toArray(stringArray);
	}
}
