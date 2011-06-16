import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Trst {  
  
    public static void main(String[] args) throws UnsupportedEncodingException{ 
    	String myHexStr = "C3807280"; //some string in Russian, charset UTF-8
    	byte []  b = new BigInteger(myHexStr, 16).toByteArray(); //maybe other	constructor to implement other formats of pack()
    	String myStr = new String(b, "UTF-8").substring(1); //first char is sign
    	System.out.println(myStr);
          
    }
}  
  