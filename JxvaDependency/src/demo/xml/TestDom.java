package demo.xml;

import java.io.FileInputStream;

import com.jxva.xml.Document;
import com.jxva.xml.Element;
import com.jxva.xml.XmlParser;
import com.jxva.xml.XmlParserFactory;

public class TestDom {
	
	public static void main(String args[]) throws Exception {
		Document doc = new Document();
		 XmlParserFactory factory = XmlParserFactory.newInstance();
		 factory.setNamespaceAware(true);
		 XmlParser xpp = factory.newPullParser();
		 xpp.setInput(new FileInputStream("D:/eclipse/workspace/JxvaFramework/dev/demo/xml/sys.xml"),"UTF-8");
		 //xpp.setInput(TestDom.class.getResourceAsStream("sys.xml"),"UTF-8");
		 doc.parse(xpp);
		 
		 Element root = doc.getRootElement();
		// System.out.println(root.getElement(1).getName());
		 System.out.println(root.getElement("sysconfig").getName());
		 Element sysconfig=root.getElement("sysconfig");
		 System.out.println(sysconfig.getAttributeValue("encoding"));
		 System.out.println(root.getElement("sysconfig").getElement("error-page").getText());
		 Element mvc=root.getElement("mvc");
		 
		 System.out.println("==========================");

		 
         int child_count = mvc.getChildCount();
         System.out.println(child_count);
         for (int i = 0; i<child_count ; i++ ) {
        	 Element kid = mvc.getElement(i);
        	 if(kid!=null){
        		 System.out.println(kid.getText());
        	 }
         }
   	}
	
}
