package demo.util;

/**
 * 将中文转换成UTF8格式
 * 
 * @author The Jxva Framework
 * @since 1.0
 * @version 2008-11-28 16:29:51 by Jxva
 */
public class String2UTF8 {
	public String2UTF8() {
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		System.out.println(String2UTF8.toUtf8String("java 远程抓取"));
	}

}
