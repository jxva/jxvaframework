package demo;

public class ExceptionTest {

	public static void main(String[] args) {
		try {
			byte[] a = args[0].getBytes();

		} catch (Exception ex) {

			ex.printStackTrace();
			StackTraceElement[] messages = ex.getStackTrace();
			int length = messages.length;
			for (int i = 0; i < length; i++) {
				System.out.println("ClassName:" + messages[i].getClassName());
				System.out.println("getFileName:" + messages[i].getFileName());
				System.out.println("getLineNumber:"
						+ messages[i].getLineNumber());
				System.out.println("getMethodName:"
						+ messages[i].getMethodName());
				System.out.println("toString:" + messages[i].toString());
			}
		}
	}

}
