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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public   class   TestEncoding1   {  
    static   char[]   base   =   {  
            '0',   '1',   '2',   '3',   '4',   '5',   '6',   '7',   '8',   '9',   'A',   'B',   'C',   'D',  
            'E',   'F'};  

    
    public   static   String   guessCharSet(byte[]   b)   { 
    	System.out.print(b.length+" "+Integer.toHexString(b[0]&0xFF)+" "+Integer.toHexString(b[1]&0xFF)+" "+Integer.toHexString(b[2]&0xFF)+"    ");
            if(b.length   >=   3   &&   b[0]   ==   -17   &&   b[1]   ==   -69   &&   b[2]   ==   -65)   {  
                    return "UTF8";
            }else{  
                    if   (b.length   >=   2   &&  
                            (   (b[0]   ==   -1   &&   b[1]   ==   -2)   ||   (b[0]   ==   -2   &&   b[1]   ==   -1)))   {  
                            return   "UTF16";  
                    }  
            }  
            return   "GBK";  
    }  

    public   static   String   getStringFromFile(String   file)   throws   Exception   {  
            ByteArrayOutputStream   os   =   new   ByteArrayOutputStream();  
            InputStream   istream   =   new   FileInputStream(file);  
            int   t   =   istream.read();  
            while   (t   !=   -1)   {  
                    os.write(t);  
                    t   =   istream.read();  
            }  
            byte[]   b   =   os.toByteArray();  
            byte[]   c;  
            String   charset   =   guessCharSet(b);  
            System.out.println(charset);
            String   s=null;  
             
            if   (charset.equals("UTF8"))   {  
                    c   =   new   byte[b.length   -   3];  
                    System.arraycopy(b,   3,   c,   0,   c.length);  
                    s=   new   String(c,   "UTF8");  
            }else   {  
                    s=   new   String(b,   charset);  
            }  
             
            char[]   ch=s.toCharArray();  
             
            s=new   String("".getBytes(),"GBK");  
            for   (char   m:ch){  
                    s+=m;  
            }  
            return   s;  
    }  

    public   static   void   main(String[]   args)   throws   Exception   {  
            String   s;
            s=getStringFromFile("c:/test/test-ansi.txt");  
            s=getStringFromFile("c:/test/test-unicode.txt");  
            s=getStringFromFile("c:/test/test-big.txt"); 
            s=getStringFromFile("c:/test/test-utf8.txt");  
            s=getStringFromFile("c:/test/test-bom.txt");  
            System.out.println("\n\n");
            s=getStringFromFile("c:/test/test-ansi2.txt");  
            s=getStringFromFile("c:/test/test-unicode2.txt");  
            s=getStringFromFile("c:/test/test-big2.txt"); 
            s=getStringFromFile("c:/test/test-utf82.txt");  
            s=getStringFromFile("c:/test/test-bom2.txt");  
  
    }  
}  

