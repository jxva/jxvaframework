/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jxva;

import java.io.File;
import java.io.FileInputStream;

public   class   TestEncoding   { 
	
	 public   static   String   guessCharSet(byte[]   b)   { 
	    	System.out.print(b.length+" "+b[0]+" "+b[1]+" "+b[2]+"    ");
	        String b1=Integer.toHexString(b[0]&0xFF);
	        String b2=Integer.toHexString(b[1]&0xFF);
	        String b3=Integer.toHexString(b[2]&0xFF);
	        System.out.print(b.length+" "+b1+" "+b2+" "+b3+"    ");
	        if(b[0]==-1&&b[1]==-2){
	        	return "Unicode";
	        }else if(b[0]==-2&&b[1]==-1){
	        	return "Unicode big endian";
	        }else if(b[0]==-17&&b[1]==-69&&b[2]==-65){
	        	return "UTF-8 with signature";
	        }else{
	        	int i=0,n=0;
	        	
	        	while(i<b.length){
	        		//System.out.print(" "+b[i]);
	        		if(b[i]>0){
	        			i+=1;  //ascii字符
	        			n+=1; 
	        		//}else if((b[i]&0xe0)==0xc0&&(b[i+1]&0xc0)==0x80){
	        		//	System.out.print("2");
	        		//	i+=2; //2个字节的utf8
	        		}else if((b[i]&0xf0)==0xe0&&(b[i+1]&0xc0)==0x80&&(b[i+2]&0xc0)==0x80){
	        			System.out.print("\n "+b[i]+" "+b[i+1]+" "+b[i+2]);
	        			//System.out.print("3");//输出多个3就表示有多少个汉字
	        			i+=3; //3个字节的utf8
	        		}else{
	        			//System.out.print(" "+(b[i]&0xf0)+" "+(0xe0)+" "+b[i+1]+" "+b[i+2]);
	        			System.out.print("未知原因");
	        			return "Ansi";
	        		}
	        	}
	        	if(n==b.length){
	        		System.out.print("到最后");
	        		return "Ansi";
	        	}
	        	return "UTF-8";
	        }
	    }  
    
    public   static   void getStringFromFile(String   _file)   throws   Exception   {  
//            ByteArrayOutputStream   os   =   new   ByteArrayOutputStream();  
//            InputStream   istream   =   new   FileInputStream(file);  
//            int   t   =   istream.read();  
//            while   (t   !=   -1)   {  
//                    os.write(t);  
//                    t   =   istream.read();  
//            }  
//            byte[]   b   =   os.toByteArray();  
//            //System.out.println("文件长度:"+b.length);
//            String   charset   =   guessCharSet(b);  
//            System.out.println(charset);
    		File file=new File(_file);
    		byte[] rawtext= new byte[(int)file.length()];
    		FileInputStream fis= new FileInputStream(file);
    		fis.read(rawtext);
    		System.out.println(guessCharSet(rawtext));
    		
    }  

    public   static   void   main(String[]   args)   throws   Exception   {  
            getStringFromFile("c:/test/test-ansi.txt");  
            getStringFromFile("c:/test/test-unicode.txt");  
            getStringFromFile("c:/test/test-big.txt"); 
            getStringFromFile("c:/test/test-utf8.txt");  
            getStringFromFile("c:/test/test-bom.txt");  
            System.out.println("\n\n");
            getStringFromFile("c:/test/test-ansi2.txt");  
            getStringFromFile("c:/test/test-unicode2.txt");  
            getStringFromFile("c:/test/test-big2.txt"); 
            getStringFromFile("c:/test/test-utf82.txt");  
            getStringFromFile("c:/db.sql"); 
            //getStringFromFile("E:/semc_java_me_cldc_sdk.2-5-0-2.zip");
            
            //			240		   224        192        128
    		System.out.println("\n ascii: "+(0xf0)+" "+(0xe0)+" "+(0xc0)+" "+(0x80));
  
    }  
}  

