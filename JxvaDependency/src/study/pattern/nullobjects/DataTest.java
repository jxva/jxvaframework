package study.pattern.nullobjects;

public class DataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client=new Client();
		System.out.println(client.getData().getUsername());
	}
}
