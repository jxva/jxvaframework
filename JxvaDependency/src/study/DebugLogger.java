package study;

import java.util.ArrayList;
import java.util.List;

public class DebugLogger {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DebugLogger logger = new DebugLogger();
		for (int i = 0; i < 10; i++) {
			logger.put("Log info " + i);
		}
		String[] strings = logger.get();
		for (int i = 0; i < strings.length; i++) {
			System.out.println(strings[i]);
		}
		logger.clear();
	}


	private static class ThreadLocalList extends ThreadLocal<ArrayList<String>> {
		public ArrayList<String> initialValue() {
			return new ArrayList<String>();
		}

		public List<String> getList() {
			return super.get();
		}
	}

	private ThreadLocalList list = new ThreadLocalList();
	private static String[] stringArray = new String[0];


	public void clear() {
		list.getList().clear();
	}


	public void put(String text) {
		list.getList().add(text);
	}


	public String[] get() {
		return (String[]) list.getList().toArray(stringArray);
	}
}