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
package com.jxva.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.jxva.xml.Document;
import com.jxva.xml.XmlParser;
import com.jxva.xml.XmlParserFactory;
import com.jxva.xml.XmlParserImpl;
import com.jxva.xml.XmlSerializer;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:21:41 by Jxva
 */
public abstract class XmlUtil {
	
	public static Document getDocument(File file,String encoding) throws UtilException{
		try{
			XmlParserFactory factory = XmlParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlParser xpp = factory.newPullParser();
			xpp.setInput(new FileInputStream(file.getAbsolutePath()),encoding);
			Document doc = new Document();
			doc.parse(xpp);
			return doc;
		}catch(Exception e){
			throw new UtilException(e);
		}
	}
	
	public static Document getDocument(String str,String encoding)throws UtilException{
		try{
	        ByteArrayInputStream bin = new ByteArrayInputStream(str.getBytes());  
	        InputStreamReader in = new InputStreamReader(bin);  
	        XmlParser parser = new XmlParserImpl();  
	        parser.setInput(in);  
	        Document doc=new Document();
	        doc.parse(parser);
	        return doc;
		}catch(Exception e){
			throw new UtilException(e);
		}
	}
	
	public static XmlSerializer getXmlSerializer(File file,String encoding) throws UtilException {
		try{
			XmlParserFactory factory = XmlParserFactory.newInstance();
		    XmlSerializer serializer = factory.newSerializer();
		    Writer writer = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), true),encoding);
		    serializer.setOutput(writer);
		    serializer.startDocument(encoding);
		    return serializer;
		}catch(Exception e){
			throw new UtilException(e);
		}
	}
}
