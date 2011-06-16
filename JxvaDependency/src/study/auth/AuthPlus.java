package study.auth;

import java.util.HashMap;

public class AuthPlus {

	private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	private static final String NUMS = "000000000001000010000011000100000101000110000111001000001001001010001011001100001101001110001111010000010001010010010011010100010101010110010111011000011001011010011011011100011101011110011111100000100001100010100011100100100101100110100111101000101001101010101011101100101101101110101111110000110001110010110011110100110101110110110111111000111001111010111011111100111101111110111111";
	
	private static final HashMap<Character,String> cn=new HashMap<Character,String>();
	private static final HashMap<String,Character> nc=new HashMap<String,Character>();
	static{
		for(int i=0;i<64;i++){
			cn.put(CODES.charAt(i),NUMS.substring(i*6,(i+1)*6));
			nc.put(NUMS.substring(i*6,(i+1)*6),CODES.charAt(i));
		}
	}
	private static final String[] spx = {"","0","00","000","0000","00000"};

	//测试数据
	private static final String d=Auth.getRight(new int[] { 1, 3, 4, 8, 9, 12, 15, 20 });
	
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
