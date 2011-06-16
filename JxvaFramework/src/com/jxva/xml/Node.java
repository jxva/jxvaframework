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
 * @version 2008-11-27 09:17:51 by Jxva
 */
public class Node {

    public static final int DOCUMENT = 0;
    public static final int ELEMENT = 2;
    public static final int TEXT = 4;
    public static final int CDSECT = 5;
    public static final int ENTITY_REF = 6;
    public static final int IGNORABLE_WHITESPACE = 7;
    public static final int PROCESSING_INSTRUCTION = 8;
    public static final int COMMENT = 9;
    public static final int DOCDECL = 10;

    protected List<Object> children;
    protected StringBuilder types;

    /**
     * inserts the given child object of the given type at the given index. 
     * @param index
     * @param type
     * @param child
     */
    public void addChild(int index, int type, Object child) throws XmlParseException{
        if (child == null){
            throw new XmlParseException("child node is null.");
        }
        if (children == null) {
            children = new ArrayList<Object>();
            types = new StringBuilder();
        }
        if (type == ELEMENT) {
            if (!(child instanceof Element))
                throw new XmlParseException("Element obj expected)");
            ((Element) child).setParent(this);
        }else if (!(child instanceof String)){
            throw new XmlParseException("String expected");
        }
        children.add(index,child);
        types.insert(index, (char) type);
    }

    /**
     * convenience method for addChild (getChildCount (), child)
     * @param type
     * @param child
     */
    public void addChild(int type, Object child) throws XmlParseException{
        addChild(getChildCount(), type, child);
    }

    public Element createElement(String namespace, String name) {
        Element e = new Element();
        e.namespace = namespace == null ? "" : namespace;
        e.name = name;
        return e;
    }

    public Object getChild(int index) {
        return children.get(index);
    }

    public int getChildCount() {
        return children == null ? 0 : children.size();
    }


    public Element getElement(int index) {
        Object child = getChild(index);
        return (child instanceof Element) ? (Element) child : null;
    }
    
    public String getElementText(int index){
    	return getElement(index).getText();
    }
    
    public Element getElement(String name){
    	return getElement(null,name);
    }
    
    public String getElementText(String name){
    	return getElement(name).getText();
    }
    
    public Element getElement(String namespace, String name) {

        int i = indexOf(namespace, name, 0);
        int j = indexOf(namespace, name, i + 1);

        if (i == -1 || j != -1){
        	return null;
        	/**
            throw new RuntimeException(
                "Element {"
                    + namespace
                    + "}"
                    + name
                    + (i == -1 ? " not found in " : " more than once in ")
                    + this);
            */    
        }
        return getElement(i);
    }

   
    /**
     * Returns the text node with the given index or null if the node
     * with the given index is not a text node.
     * @param index
     * @return
     */
    public String getText(int index) {
        return (isText(index)) ? (String) getChild(index) : null;
    }

    /**
     * Returns the type of the child at the given index. Possible 
     * types are ELEMENT, TEXT, COMMENT, and PROCESSING_INSTRUCTION
     * @param index
     * @return
     */
    public int getType(int index) {
        return types.charAt(index);
    }

    /** Convenience method for indexOf (getNamespace (), name,
        startIndex). 
    
    public int indexOf(String name, int startIndex) {
        return indexOf(getNamespace(), name, startIndex);
    }
    */

    public int indexOf(String namespace, String name, int startIndex) {
        int len = getChildCount();
        for (int i = startIndex; i < len; i++) {
            Element child = getElement(i);
            if (child != null
                && name.equals(child.getName())
                && (namespace == null || namespace.equals(child.getNamespace())))
                return i;
        }
        return -1;
    }

    public boolean isText(int i) {
        int t = getType(i);
        return t == TEXT || t == IGNORABLE_WHITESPACE || t == CDSECT;
    }


    public void parse(XmlParser parser)throws XmlParseException {
        boolean leave = false;
        do {
            int type = parser.getEventType();
   //       System.out.println(parser.getPositionDescription());
            switch (type) {
                case XmlParser.START_TAG :
                    {
                        Element child =createElement(parser.getNamespace(),parser.getName());
                        // child.setAttributes (event.getAttributes ());
                        addChild(ELEMENT, child);
                        // order is important here since 
                        // setparent may perform some init code!
                        child.parse(parser);
                        break;
                    }
                case XmlParser.END_DOCUMENT :
                case XmlParser.END_TAG :
                    leave = true;
                    break;
                default :
                    if (parser.getText() != null){
                        addChild(type == XmlParser.ENTITY_REF ? TEXT : type,parser.getText());
                    }else if (type == XmlParser.ENTITY_REF&& parser.getName() != null) {
                        addChild(ENTITY_REF, parser.getName());
                    }
                    parser.nextToken();
            }
        }
        while (!leave);
    }

    public void removeChild(int idx) {
        children.remove(idx);

        /***  Modification by HHS - start ***/
        //      types.deleteCharAt (index);
        /***/
        int n = types.length() - 1;

        for (int i = idx; i < n; i++)
            types.setCharAt(i, types.charAt(i + 1));

        types.setLength(n);

        /***  Modification by HHS - end   ***/
    }

    /* returns a valid XML representation of this Element including
    	attributes and children. 
    public String toString() {
        try {
            ByteArrayOutputStream bos =
                new ByteArrayOutputStream();
            XmlWriter xw =
                new XmlWriter(new OutputStreamWriter(bos));
            write(xw);
            xw.close();
            return new String(bos.toByteArray());
        }
        catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }
    */

    /** Writes this node to the given XmlWriter. For node and document,
        this method is identical to writeChildren, except that the
        stream is flushed automatically. */
    public void write(XmlSerializer writer) throws XmlParseException {
        writeChildren(writer);
		writer.flush();
    }

    /** Writes the children of this node to the given XmlWriter. */
    public void writeChildren(XmlSerializer writer) throws XmlParseException {
        if (children == null)return;
        int len = children.size();
    	for (int i = 0; i < len; i++) {
    		int type = getType(i);
            Object child = children.get(i);
            switch (type) {
                case ELEMENT :
                     ((Element) child).write(writer);
                    break;

                case TEXT :
                    writer.setText((String) child);
                    break;

                case IGNORABLE_WHITESPACE :
                    writer.ignorableWhitespace((String) child);
                    break;

                case CDSECT :
                    writer.cdsect((String) child);
                    break;

                case COMMENT :
                    writer.comment((String) child);
                    break;

                case ENTITY_REF :
                    writer.entityRef((String) child);
                    break;

                case PROCESSING_INSTRUCTION :
                    writer.processingInstruction((String) child);
                    break;

                case DOCDECL :
                    //writer.addDocType((String) child);
                    break;

                default :
                    throw new RuntimeException("Illegal type: " + type);
            }
        }
    }
}
