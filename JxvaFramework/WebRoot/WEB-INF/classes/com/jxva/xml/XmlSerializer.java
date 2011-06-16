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

import java.io.OutputStream;
import java.io.Writer;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:18:59 by Jxva
 */
public interface XmlSerializer {

    public void setFeature(String name,boolean state)throws XmlSerializeException;
    
    public boolean getFeature(String name);
    
    public void setProperty(String name,Object value)throws XmlSerializeException;
    
    public Object getProperty(String name);

    public void setOutput (OutputStream os, String encoding)throws XmlSerializeException;
    
    public void setOutput (Writer writer)throws XmlSerializeException;
    
    public void startDocument (String encoding)throws XmlSerializeException;
    
    public void startDocument (String encoding, Boolean standalone)throws XmlSerializeException;
    
    public void endDocument ()throws XmlSerializeException;

    public void setPrefix (String prefix, String namespace)throws XmlSerializeException;
    
    public String getPrefix (String namespace, boolean generatePrefix)throws XmlSerializeException;
    
    public int getDepth();
    
    public String getNamespace ();
    
    public String getName();
    
    public XmlSerializer startTag (String name)throws XmlSerializeException;
    
    public XmlSerializer startTag (String namespace, String name)throws XmlSerializeException;
    
    public XmlSerializer addAttribute (String name, String value)throws XmlSerializeException;
    
    public XmlSerializer attribute (String namespace, String name, String value)throws XmlSerializeException;
    
    public XmlSerializer endTag (String name)throws XmlSerializeException;
    
    public XmlSerializer endTag (String namespace, String name)throws XmlSerializeException;
    
    public XmlSerializer setText (String text)throws XmlSerializeException;
        
    public XmlSerializer text (char [] buf, int start, int len)throws XmlSerializeException;
    
    public void cdsect (String text)throws XmlSerializeException;
    
    public void entityRef (String text)  throws XmlSerializeException;
    
    public void processingInstruction (String text)throws XmlSerializeException;
    
    public void comment (String text)throws XmlSerializeException;
    
    public void addDocType (String v1,String v2,String v3)throws XmlSerializeException;
    
    public void ignorableWhitespace (String text)throws XmlSerializeException;

    public void flush ()throws XmlSerializeException;
    
}

