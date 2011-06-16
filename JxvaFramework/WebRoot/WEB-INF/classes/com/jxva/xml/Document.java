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


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:17:37 by Jxva
 */
public class Document extends Node {

    private int rootIndex = -1;
    private String encoding;
    private Boolean standalone;

    public String getEncoding () {
        return encoding;
    }
    
    public void setEncoding(String enc) {
        this.encoding = enc;
    }
    
    public void setStandalone (Boolean standalone) {
        this.standalone = standalone;
    }
    
    public Boolean getStandalone() {
        return standalone;
    }


    public String getName() {
        return "#document";
    }


    public void addChild(int index, int type, Object child)throws XmlParseException {
        if (type == ELEMENT) {
         //   if (rootIndex != -1)
           //     throw new RuntimeException("Only one document root element allowed");
            rootIndex = index;
        }else if (rootIndex >= index){
            rootIndex++;
        }
        super.addChild(index, type, child);
    }

    public void parse(XmlParser parser)throws XmlParseException {
		parser.require(XmlParser.START_DOCUMENT, null, null);
		parser.nextToken ();        	
        encoding = parser.getInputEncoding();
        //add commet 2009-03-24
        //standalone = (Boolean)parser.getProperty ("http://xmlpull.org/v1/doc/properties.html#xmldecl-standalone");
        super.parse(parser);
        if (parser.getEventType() != XmlParser.END_DOCUMENT)throw new XmlParseException("Document end expected!");
    }

    public void removeChild(int index) {
        if (index == rootIndex)rootIndex = -1;
        else if (index < rootIndex)rootIndex--;
        super.removeChild(index);
    }

    public Element getRootElement() throws RuntimeException {
        if (rootIndex == -1) throw new RuntimeException("Document has no root element!");
        return (Element) getChild(rootIndex);
    }
    
    
    public void write(XmlSerializer writer)throws XmlParseException {
        writer.startDocument(encoding, standalone);
        writeChildren(writer);
        writer.endDocument();
    }
}