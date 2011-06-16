package study.auth;

import java.util.HashMap;


public class AuthHex {

	private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	private static final String[] NUMS = { "000000", "000001", "000010",
			"000011", "000100", "000101", "000110", "000111", "001000",
			"001001", "001010", "001011", "001100", "001101", "001110",
			"001111", "010000", "010001", "010010", "010011", "010100",
			"010101", "010110", "010111", "011000", "011001", "011010",
			"011011", "011100", "011101", "011110", "011111", "100000",
			"100001", "100010", "100011", "100100", "100101", "100110",
			"100111", "101000", "101001", "101010", "101011", "101100",
			"101101", "101110", "101111", "110000", "110001", "110010",
			"110011", "110100", "110101", "110110", "110111", "111000",
			"111001", "111010", "111011", "111100", "111101", "111110",
			"111111" };
	
	private static final HashMap<Character,String> cn=new HashMap<Character,String>();
	private static final HashMap<String,Character> nc=new HashMap<String,Character>();
	static{
		for(int i=0;i<64;i++){
			cn.put(CODES.charAt(i),NUMS[i]);
			nc.put(NUMS[i],CODES.charAt(i));
		}
	}
	private static final String[] spx = { "", "0", "00", "000","0000","00000"};

	//测试数据
	private static final String d=Auth.getRight(new int[] { 1, 3, 4, 8, 9, 12, 15, 20,1000,5000 });
	
	/**
	 * 将二进制字符串转为六十四进制
	 * @param bin
	 * @return
	 */
	public static String getHex(String bin){
		bin=bin+spx[6-bin.length()%6];
		StringBuilder	sb=new StringBuilder();
		for(int i=0;i<bin.length();i=i+6){
			sb.append(nc.get(bin.substring(i,i+6)));
		}
		return sb.toString();
	}
	
	public static String getBin(String hex){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<hex.length();i++){
			sb.append(cn.get(hex.charAt(i)));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(d);
		System.out.println(getHex(d));
		System.out.println(getBin(getHex(d)));
		System.out.println(Auth.getIds(d));
		System.out.println(Auth.getIds(getBin(getHex(d))));
	}
}
