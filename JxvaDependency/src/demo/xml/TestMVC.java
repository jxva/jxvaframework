package demo.xml;

import java.io.FileInputStream;

import com.jxva.xml.Document;
import com.jxva.xml.Element;
import com.jxva.xml.XmlParser;
import com.jxva.xml.XmlParserFactory;

public class TestMVC {
	
	public static void main(String args[]) throws Exception {
		Document doc = new Document();
		 XmlParserFactory factory = XmlParserFactory.newInstance();
		 factory.setNamespaceAware(true);
		 XmlParser xpp = factory.newPullParser();
		 xpp.setInput(TestMVC.class.getResourceAsStream("mvc.xml"),"UTF-8");
		doc.parse(xpp);
		 Element root = doc.getRootElement();
		 		 
         int child_count = root.getChildCount();
         System.out.println(child_count);
         for (int i = 0; i<child_count ; i++ ) {
        	 Element kid = root.getElement(i);
        	 if(kid!=null){
        		 for(int j=0;j<kid.getChildCount();j++){
        			 Element pid=kid.getElement(j);
        			 if(pid!=null){
        				 System.out.println(pid.getAttributeName(0));
        			 }
        		 }
        	 }
         }
   	}
	
}
