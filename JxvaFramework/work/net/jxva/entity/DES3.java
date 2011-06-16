package net.jxva.entity;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DES3 {
	private static final String PASSWORD_CRYPT_KEY = "flxmtdes";
	private static final String IV = "flxmtIvS";
	public DES3(){
		
	}
	
	/**
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public String encrypt(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] b=cipher.doFinal(message.getBytes("UTF-8"));
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(b);
	}

	/**
	 * 解密
	 * 
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public String decrypt(String message) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytesrc = decoder.decodeBuffer(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); 
		DESKeySpec desKeySpec = new DESKeySpec(PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
		 
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);   
   
        byte[] retByte = cipher.doFinal(bytesrc);  
        return new String(retByte);
		
	}

	public static void main(String[] args) throws Exception {
		DES3 des = new DES3();
		System.out.println(des.encrypt("jxvaframework"));
		System.out.println(des.decrypt("pXkRxO+9q95MroOQnR1J8WU3v1J175aES+QzEo6zViKnoBPH/Adfe5KjDxC0U94H/Yizsr+omXAG5ovinrcBllgpwe39PECLJS8fHKVoU+VBa5ExHprEnAzr5bWDnupszX0qGJw5o07UEIIZhaUwHshImCIwpqb6N4qlZHseInxxQjE1DmaCuQzMT/YX9kZXDIdmxJGo6WPs6ZSNPv+JkWD2o6myMOq0b4qlvrDfJ34pgGU50pZFtLAnQ/iMB/8I7XPT3CGlrVrULPrGNNXwxwqEEYu/paVBKMdvse2QqroWAdqybgJ4/eDlNzEyCzW5vLoDBvOC8DX7YdmVAvDLNmQzrVNlHpHquGSV6UnvcpSU9j4Eu2s4190/2B9u+1kEyEHUcOJa15jAr4RGosx48ftEp/aqQ7+PPx/fqA+wVxQ3C1n4F+khVyUBu/qtgQ9xZrs7C/bAVTiXq1T556z29aLdivZ9T2r5nx1DHjJMI/C3soyb0uMBkx7zeWKNMdYSI456LM9DjLsQ8TKn9ajuxxSe4OvXJfczB1YCEIX+LI9geEP6InnvFWXUayshtMS8lWg4G3AYY815aQ55bY9dQd2VbuptiHif0rxBHU6bxRisE7bMjEnVJj8VI/roYLKIzLNjOHV/TUIxutljCReF48/QNlO4iZ3fFjN+Y9PS/jE87DJwj86nk+lN5vSQtKQDmXPAeva9PQOhBZXeXDW33Huhye42shMGyIFomdvA8mZvgm4G+I4vxg=="));
	}

}
