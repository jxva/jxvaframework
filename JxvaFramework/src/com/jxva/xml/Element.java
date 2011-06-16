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
package com.jxva.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:17:45 by Jxva
 */
public class Element extends Node {

    protected String namespace;
    protected String name;
    private List<String[]> attributes;
    private Node parent;
    private List<String[]> prefixes;

    public Element() {
    }

    public void init() {
    }


    /**
     * removes all children and attributes 
     */
    public void clear() {
        attributes = null;
        children = null;
    }


    public Element createElement(
        String namespace,
        String name) { 

        return (this.parent == null)? super.createElement(namespace, name): this.parent.createElement(namespace, name);
    }

    public int getAttributeCount() {
        return attributes == null ? 0 : attributes.size ();
    }

	public String getAttributeNamespace (int index) {
		return ((String []) attributes.get (index)) [0];
	}

	/**	public String getAttributePrefix (int index) {
		return ((String []) attributes.elementAt (index)) [1];
	}*/
	
	public String getAttributeName (int index) {
		return ((String []) attributes.get (index)) [1];
	}
	

	public String getAttributeValue (int index) {
		return ((String []) attributes.get (index)) [2];
	}
	
	public String getAttributeValue (String name) {
		return getAttributeValue(null,name);
	}
	
	public String getAttributeValue (String namespace, String name) {
		for (int i = 0; i < getAttributeCount (); i++) {
			if (name.equals (getAttributeName (i)) 
				&& (namespace == null || namespace.equals (getAttributeNamespace(i)))) {
				return getAttributeValue (i);
			}
		}						
		return null;			
	}

    public Node getRoot() {

        Element current = this;
        
        while (current.parent != null) {
            if (!(current.parent instanceof Element)) return current.parent;
            current = (Element) current.parent;
        }
        
        return current;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }
    
    public String getNamespaceUri (String prefix) {
    	int cnt = getNamespaceCount ();
		for (int i = 0; i < cnt; i++) {
			if (prefix == getNamespacePrefix (i) ||
				(prefix != null && prefix.equals (getNamespacePrefix (i))))
				return getNamespaceUri (i);	
		}
		return parent instanceof Element ? ((Element) parent).getNamespaceUri (prefix) : null;
    }

	public int getNamespaceCount () {
		return (prefixes == null ? 0 : prefixes.size ());
	}


	public String getNamespacePrefix (int i) {
		return ((String []) prefixes.get (i)) [0];
	}

	public String getNamespaceUri (int i) {
		return ((String []) prefixes.get (i)) [1];
	}


    public Node getParent() {
        return parent;
    }

    /**
     * Returns the parent element if available, null otherwise 

    public Element getParentElement() {
        return (parent instanceof Element)
            ? ((Element) parent)
            : null;
    }
    */

    public void parse(XmlParser parser)throws XmlParseException {

        for (int i = parser.getNamespaceCount (parser.getDepth () - 1);
        	i < parser.getNamespaceCount (parser.getDepth ()); i++) {
        	setPrefix (parser.getNamespacePrefix (i), parser.getNamespaceUri(i));
        }
        
        
        for (int i = 0; i < parser.getAttributeCount (); i++){
	        setAttribute (parser.getAttributeNamespace (i),
//	        			  parser.getAttributePrefix (i),
	        			  parser.getAttributeName (i),
	        			  parser.getAttributeValue (i));
        }

        //        if (prefixMap == null) throw new RuntimeException ("!!");

        init();

		if (parser.isEmptyElementTag()) 
			parser.nextToken ();
		else {
			parser.nextToken ();
	        super.parse(parser);
        	if (getChildCount() == 0)addChild(IGNORABLE_WHITESPACE, "");
		}
		
        parser.require(XmlParser.END_TAG,getNamespace(),getName());
        parser.nextToken ();
        
    }

	public void setAttribute (String namespace, String name, String value) {
		if (attributes == null) 
			attributes = new ArrayList<String[]> ();

		if (namespace == null) 
			namespace = "";
		
        for (int i = attributes.size()-1; i >=0; i--){
            String[] attribut = (String[]) attributes.get(i);
            if (attribut[0].equals(namespace) &&
				attribut[1].equals(name)){
					
				if (value == null) {
	                attributes.remove(i);
				}
				else {
					attribut[2] = value;
				}
	            return; 
			}
        }

		attributes.add(new String [] {namespace, name, value});
	}

	public void setPrefix (String prefix, String namespace) {
		if (prefixes == null) prefixes = new ArrayList<String[]> ();
		prefixes.add (new String [] {prefix, namespace});		
	}

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        if (namespace == null) throw new NullPointerException ("Use \"\" for empty namespace");
        this.namespace = namespace;
    }

    protected void setParent(Node parent) {
        this.parent = parent;
    }

    public String getText() {
    
        StringBuilder sb = new StringBuilder();
        int len = getChildCount();
    
        for (int i = 0; i < len; i++) {
            if (isText(i))
                sb.append(getText(i));
            else if (getType(i) == ELEMENT)
            	return null;
                //throw new RuntimeException("not text-only content!");
        }
    
        return sb.toString();
    }
    
    public void write(XmlSerializer writer)throws XmlParseException {
		if (prefixes != null) {
			for (int i = 0; i < prefixes.size (); i++) {
				writer.setPrefix (getNamespacePrefix (i), getNamespaceUri (i));
			}
		}
        writer.startTag(getNamespace(),getName());
        int len = getAttributeCount();
        for (int i = 0; i < len; i++) {
            writer.attribute(getAttributeNamespace(i),getAttributeName(i),getAttributeValue(i));
        }
        writeChildren(writer);
        writer.endTag(getNamespace (), getName ());
    }
}
