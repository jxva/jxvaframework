package net.jxva.ip;

public class Client {

	private static final IPSeeker ip=IPSeeker.getInstance();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(ip.getCountry("58.60.0.73"));
		System.out.println(ip.getAddress("218.75.63.12"));

	}

}
