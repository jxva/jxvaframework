package ready.reges;


public class Test {
	private PathMatcher pathMatcher = new AntPathMatcher();

	public void test1() {
		String srcUrl = "/secure/";
		String srcUrl2 = "/test/public/left.jsp?test=test1&test2=test2";

		System.out.println(pathMatcher.match("/**", srcUrl));
		System.out.println(pathMatcher.match("/secure/*.jsp", srcUrl));
		System.out.println(pathMatcher.match("/**/left.jsp*", srcUrl2));
		System.out.println(pathMatcher.match("/secure/*.jsp", srcUrl));
		
		System.out.println(pathMatcher.isPattern("/dfsa/*/"));

	}

	public void test2() {
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.test1();

	}
}
