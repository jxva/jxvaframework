package demo.xml;

import java.io.File;

import com.jxva.entity.Encoding;
import com.jxva.util.XmlUtil;
import com.jxva.xml.XmlSerializer;

public class TestWrite {
	 
	public static void main(String[] args) throws Exception{
		

	    XmlSerializer s =XmlUtil.getXmlSerializer(new File("c:/1.xml"),Encoding.UTF_8);
	    
	    s.addDocType("hibernate-configuration",
	            "-//Hibernate/Hibernate Configuration DTD 3.0//EN",
	            "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd");
	    s.startTag("poem");
	    
	    s.startTag("l").setText("Roses中文测试 are red,").endTag("l");
	    
	    XmlSerializer d=s.startTag("l").setText("fdsafdsa");
	    d.startTag("d").addAttribute("id","fdsa").setText("R2").endTag("d");
	    	    	    
	    s.endDocument();
	}
}
