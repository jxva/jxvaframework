package sql4j.util;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 4:06:35 AM)
 * @author: Jianguo Lu
 */

import java.io.*;
import org.w3c.dom.*;
import com.ibm.xml.parsers.DOMParser;
import javax.swing.*;

/**
 * Insert the type's description here.
 * Creation date: (10/20/00 4:06:35 AM)
 * @author Jianguo Lu
 */

public class DomUtil {

	/** search for a node based on the node name. 
	 **/
	public static Node findNode(Node node, String name) {
		if (node.getNodeName().equals(name))
			return node;
		if (node.hasChildNodes()) {
			NodeList list = node.getChildNodes();
			int size = list.getLength();
			for (int i = 0; i < size; i++) {
				Node found = findNode(list.item(i), name);
				if (found != null)
					return found;
			}
		}
		return null;
	}
	/**  all the child nodes that are instances of Elements.
		 to replace getChildNodes() in many cases. 
	 **/
	public static NodeList getChildElements(Node node) {
		if (node == null)
			return null;
		Node newNode = node;
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node subNode = nodes.item(i);
			if (!(subNode instanceof Element)) {
				newNode.removeChild(subNode);
			}
		}
		return newNode.getChildNodes();
	}
	/** get the attribute of a node if it is an Element.
	 **/
	public static String getNodeAttribute(Node node, String name) {
		if (node instanceof Element) {
			Element element = (Element) node;
			return element.getAttribute(name);
		}
		return null;
	}
	/** Normalizes the given string. */
	private static String normalize(String s) {
		return s;
		/**
		   StringBuffer str = new StringBuffer();
		   
		   int len = (s != null) ? s.length() : 0;
		   for (int i = 0; i < len; i++) {
		   char ch = s.charAt(i);
		   switch (ch) {
		   //case '<': {
		//    str.append("&lt;");
		//     break;
		// }
		//  case '>': {
		//    str.append("&gt;");
		//    break;
		// }
		//case '&': {
		//    str.append("&amp;");
		//    break;
		//}
		//case '"': {
		//    str.append("&quot;");
		//    break;
		//}
		
		case '\r':
		case '\n': {
		str.append("&#");
		str.append(Integer.toString(ch));
		str.append(';');
		break;
		}
		default: {
		str.append(ch);
		}
		}
		}
		
		return str.toString();
		**/
	} // normalize(String):String  
	/** Normalizes the given string. */
	private static String normalize(String s, String tab) {
		//return s;

		StringBuffer str = new StringBuffer();

		int len = (s != null) ? s.length() : 0;
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			switch (ch) {
				//case '<': {
				//    str.append("&lt;");
				//     break;
				// }
				//  case '>': {
				//    str.append("&gt;");
				//    break;
				// }
				//case '&': {
				//    str.append("&amp;");
				//    break;
				//}
				//case '"': {
				//    str.append("&quot;");
				//    break;
				//}

				case '\r' :
				case '\n' :
					{
						str.append(ch);
						str.append(tab);
						break;
					}
				default :
					{
						str.append(ch);
					}
			}
		}

		return str.toString();

	} // normalize(String):String       
	public static void printSubtree(PrintWriter writer, Node node) {
		printSubtree(writer, node, node);
	}
	public static void printSubtree(PrintWriter writer, Node root, Node node) {
		if (node instanceof Element) {
			if (node != root)
				writer.print("\n<" + node.getNodeName() + ">");
			if (node.hasChildNodes()) {
				NodeList list = node.getChildNodes();
				int size = list.getLength();
				for (int i = 0; i < size; i++) {
					printSubtree(writer, root, list.item(i));
				}
			}
			if (node != root)
				writer.print("</" + node.getNodeName() + ">");
		} else
			if (node instanceof Text) {
				writer.print(node.getNodeValue().trim());
			}
	}
	/** read an xml document from a file. 
		--com.ibm.xml.parser.Parser is the old version of parser. 
		--new version is the com.ibm.xml.parsers.DomParser, etc. 
		--changed to new version.
	 **/
	public static Document readDocument(String filename) {
		Document doc = null;
		try {
			DOMParser parser = new DOMParser();
			//Errors error = new Errors();
			//parser.setErrorHandler(error);
			parser.parse(filename);
			doc = parser.getDocument();
		} catch (Exception e) {
			e.printStackTrace();
			Misc.message("Exception in generating a Document from xml file " + filename);
		}
		if (doc == null)
			Misc.message(
				"DomUtil.java: Null Document produced. Fail to generate a Document from xml file "
					+ filename); 
		return doc;
	}
	/** get the xml Document from an xml String.
	 **/
	public static Document readDocumentFromString(String xmlString) {
		Document doc = null;
		try {

			DOMParser parser = new DOMParser();
			Errors errors = new Errors();
			parser.setErrorHandler(errors);
			parser.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlString)));
			doc = parser.getDocument();
		} catch (Exception e) {
		};
		if (doc == null)
			Misc.message("Fail to generate a document from xml text ==> see log file\n");
		return doc;
	}
	/** get the xml Document from an xml String.the first argument indicates
		whehter it is to be validated or not.
	  **/
	public static Document readDocumentFromString(
		boolean validate, 
		String xmlString) {
		Document doc = null;
		try {

			DOMParser parser = new DOMParser();
			if (validate) {
				Errors errors = new Errors();
				parser.setErrorHandler(errors);
			}
			parser.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlString)));
			doc = parser.getDocument();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return doc;
	}
	/** read an xml document from a file. 
		files of the form "../data/desc/amazonSearch.xml"
		can not be processed. maybe an error of xml4j.
		--com.ibm.xml.parser.Parser is the old version of parser. 
		--new version is the com.ibm.xml.parsers.DomParser, etc. 
		--changed to new version.
	**/
	public static Document readDocumentNoValidate(String filename) {
		Document doc = null;
		try {
			DOMParser parser = new DOMParser();
			parser.parse(filename);
			doc = parser.getDocument();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in generating a Document from xml file " + filename);
		}
		if (doc == null)
			System.out.println(
				"DomUtil.java: Null Document produced. Fail to generate a Document from xml file "
					+ filename); 
		return doc;
	}
	/** Returns a sorted list of attributes. */
	private static Attr[] sortAttributes(NamedNodeMap attrs) {
		int len = (attrs != null) ? attrs.getLength() : 0;
		Attr array[] = new Attr[len];
		for (int i = 0; i < len; i++) {
			array[i] = (Attr) attrs.item(i);
		}
		for (int i = 0; i < len - 1; i++) {
			String name = array[i].getNodeName();
			int index = i;
			for (int j = i + 1; j < len; j++) {
				String curName = array[j].getNodeName();
				if (curName.compareTo(name) < 0) {
					name = curName;
					index = j;
				}
			}
			if (index != i) {
				Attr temp = array[i];
				array[i] = array[index];
				array[index] = temp;
			}
		}
		return array;
	} // sortAttributes(NamedNodeMap):Attr[]                                                                              
	/** get the string representation of an Element.
	 **/
	public static String toString(Element elem) {
		if (elem == null)
			return "";
		String result = "<" + elem.getNodeName();
		Attr attrs[] = sortAttributes(elem.getAttributes());
		for (int i = 0; i < attrs.length; i++) {
			Attr attr = attrs[i];
			result = result + " " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\""; 
		}
		result = result + ">";
		NodeList children = elem.getChildNodes();
		if (children != null) {
			int len = children.getLength();
			for (int i = 0; i < len; i++) {
				result = result + toString(children.item(i));
			}
		}

		result = result + "</" + elem.getNodeName() + ">";
		return result;
	}
	/** transform the node into a string representation. 
	 **/
	public static String toString(Node node) {
		return toString(node, "");
	}
	public static String toString(Node node, String tab) {
		if (node == null)
			return "";
		String result = "";
		int type = node.getNodeType();
		switch (type) {
			case Node.DOCUMENT_NODE :
				{
					result = result + ((Document) node).getDocumentElement();
					break;
				}
			case Node.ELEMENT_NODE :
				{
					result = result + "<" + node.getNodeName();
					Attr attrs[] = sortAttributes(node.getAttributes());
					for (int i = 0; i < attrs.length; i++) {
						Attr attr = attrs[i];
						result = result + " " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\""; 
					}
					result = result + ">";
					NodeList children = node.getChildNodes();
					if (children != null) {
						int len = children.getLength();
						for (int i = 0; i < len; i++) {
							result = result + toString(children.item(i), tab);
						}
					}
					break;
				}
			case Node.ENTITY_REFERENCE_NODE :
				{
					result = result + "&" + node.getNodeName() + ";";
					break;
				}
			case Node.CDATA_SECTION_NODE :
			case Node.TEXT_NODE :
				{
					result = result + normalize(node.getNodeValue(), tab);
					break;
				}
		}

		if (type == Node.ELEMENT_NODE) {
			result = result + "</" + node.getNodeName() + ">";
		}
		return result;
	}
	public static String toString(NodeList nodes) {
		String result = "";
		if (nodes != null) {
			int len = nodes.getLength();
			for (int i = 0; i < len; i++) {
				result = result + "\n" + toString(nodes.item(i));
			}
		}
		return result;
	}
}