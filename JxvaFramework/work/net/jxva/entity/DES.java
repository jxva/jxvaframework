package net.jxva.entity;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.jxva.util.StringUtil;
import com.jxva.util.UtilException;
/**
* DES加密的，文件中共有两个方法,加密、解密
* @author Lion
* @author www.lionsky.net
*/
public class DES {
     private static final String Algorithm = "DES";

     private static Cipher c;
     private static SecretKey  desKey;

     /**
      * 初始化 DESForCSharp 实例
      */
     public DES() {
         super();
     }

    static{
         Security.addProvider(new com.sun.crypto.provider.SunJCE());
         try {
        	 KeyGenerator  keygen = KeyGenerator.getInstance(Algorithm);
        	 DES.desKey= keygen.generateKey();
        	 DES.c = Cipher.getInstance(Algorithm);
          }
          catch(NoSuchAlgorithmException ex){
             ex.printStackTrace();
         }
          catch(NoSuchPaddingException ex){
             ex.printStackTrace();
         }
        }

     /**
      * 对 String 进行加密
      * @param str 要加密的数据
      * @return 返回加密后的 byte 数组
      */
      public static byte[] createEncryptor(String str) {
    	 try{
    		c.init(Cipher.ENCRYPT_MODE,desKey);
        	return c.doFinal(str.getBytes());
         }
         catch(Exception e){
        	 throw new UtilException(e);
         }
            
      }

     /**
      * 对 Byte 数组进行解密
      * @param buff 要解密的数据
      * @return 返回加密后的 String
      */
      public static String createDecryptor(byte[] buff) {
         try {
        	 c.init(Cipher.DECRYPT_MODE,desKey);
            return new String(c.doFinal(buff));
         }catch(Exception e){
        	 throw new UtilException(e);
         }
      }
      
      /**
       * F97B3E340C584148CE81221F7B5A207E
		 jxvaframework
		 Press any key to continue . . .
       * @param args
       */
      public static void main(String[] args){
    	  byte[] e=DES.createEncryptor("jxvaframework");
    	  System.out.println(StringUtil.encodeHex(e));
    	  String d=DES.createDecryptor(e);
    	  
    	  System.out.println(d);
    	  System.out.println(DES.createDecryptor("195d4d19c374c34431a2a289ab548ff9".toLowerCase().getBytes()));
      }
}