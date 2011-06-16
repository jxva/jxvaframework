package demo.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import com.jxva.xml.Document;
import com.jxva.xml.Element;
import com.jxva.xml.XmlParser;
import com.jxva.xml.XmlParserImpl;

public class TestParseString {

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
	 
	public static void main(String[] args) throws Exception {
		Document doc=new Document();
        ByteArrayInputStream bin = new ByteArrayInputStream(xml.getBytes());  
        InputStreamReader in = new InputStreamReader(bin);  
        XmlParser parser = new XmlParserImpl();  
        parser.setInput(in);  
        doc.parse(parser);
        Element root = doc.getRootElement();
		System.out.println(root.getElement("card").getElement(0).getText());
	}

}
