package demo.xml;
 

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jxva.xml.XmlParseException;
import com.jxva.xml.XmlParser;
import com.jxva.xml.XmlParserImpl;



public class TestParse {  

  

    //private static String xml = "<list><item>apple</item>"  

        //  + "<item>orange</item>" + "<item>pear</item></list>";  

  

    private static String xml ="<?xml version=\"1.0\"?>"  

    +"<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\" "   

    +"\"http://www.wapforum.org/DTD/wml_1.1.xml\">"  

    +"<wml>"  

    +"<card id=\"index\" title=\"天速\">"  

    +"<p align=\"center\">天速科技</p>"  

    +"<p align=\"center\"><a href=\"/t/main.a\"> 动漫天下 </a></p>"  

    +"<p align=\"center\"><a href=\"/t/main.a\"> 国色天香 </a></p>"  

    +"<p align=\"center\"><a href=\"/eb/main.a\">  风月书斋 </a></p>"  

  

    +"</card> </wml>";  

    public static void main(String args[])  

  

    throws XmlParseException, IOException {  

           

           

        ByteArrayInputStream bin = new ByteArrayInputStream(xml.getBytes());  

        InputStreamReader in = new InputStreamReader(bin);  

        XmlParser parser = new XmlParserImpl();  

        parser.setInput(in);  

  

        int eventType = parser.getEventType();  

        while (eventType != XmlParser.END_DOCUMENT) {  

            if (eventType == XmlParser.START_DOCUMENT) {  

                //System.out.println("Start document:");  

            } else if (eventType == XmlParser.END_DOCUMENT) {  

                //System.out.println("End document");  

            } else if (eventType == XmlParser.START_TAG) {  

                System.out.println("  " + parser.getName());  

                //System.out.println("Start Count: " + parser.getAttributeCount());  

                int  size = parser.getAttributeCount();  

                for(int i=0; i<size; i++){  

                    System.out.println("  " + parser.getAttributeName(i));  

                    System.out.println("  " + parser.getAttributeValue(i));  

                }  

                   

                  

            } else if (eventType == XmlParser.END_TAG) {  

                System.out.println(" " + parser.getName());  

            } else if (eventType == XmlParser.TEXT) {  

                System.out.println("  " + parser.getText());  

            }  

            eventType = parser.next();  

        }  

    }  

  

}  