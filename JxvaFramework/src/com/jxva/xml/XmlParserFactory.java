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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:18:23 by Jxva
 */
public class XmlParserFactory {

    private List<Class<XmlParserImpl>> parserClasses;

    private List<Class<XmlSerializerImpl>> serializerClasses;


    // features are kept there
    private static final Map<String, Boolean> features = new HashMap<String, Boolean>();


    /**
     * Protected constructor to be called by factory implementations.
     */

    protected XmlParserFactory() {
    }



    /**
     * Set the features to be set when XML Pull Parser is created by this factory.
     * <p><b>NOTE:</b> factory features are not used for XML Serializer.
     *
     * @param name string with URI identifying feature
     * @param state if true feature will be set; if false will be ignored
     */

    public void setFeature(String name,boolean state) throws XmlParseException {
        features.put(name, new Boolean(state));
    }


    /**
     * Return the current value of the feature with given name.
     * <p><b>NOTE:</b> factory features are not used for XML Serializer.
     *
     * @param name The name of feature to be retrieved.
     * @return The value of named feature.
     *     Unknown features are <string>always</strong> returned as false
     */

    public boolean getFeature (String name) {
        Boolean value = features.get(name);
        return value != null ? value.booleanValue() : false;
    }

    /**
     * Specifies that the parser produced by this factory will provide
     * support for XML namespaces.
     * By default the value of this is set to false.
     *
     * @param awareness true if the parser produced by this code
     *    will provide support for XML namespaces;  false otherwise.
     */

    public void setNamespaceAware(boolean awareness) {
        features.put (XmlParser.FEATURE_PROCESS_NAMESPACES, new Boolean (awareness));
    }

    /**
     * Indicates whether or not the factory is configured to produce
     * parsers which are namespace aware
     * (it simply set feature XmlPullParser.FEATURE_PROCESS_NAMESPACES to true or false).
     *
     * @return  true if the factory is configured to produce parsers
     *    which are namespace aware; false otherwise.
     */

    public boolean isNamespaceAware() {
        return getFeature (XmlParser.FEATURE_PROCESS_NAMESPACES);
    }


    /**
     * Specifies that the parser produced by this factory will be validating
     * (it simply set feature XmlPullParser.FEATURE_VALIDATION to true or false).
     *
     * By default the value of this is set to false.
     *
     * @param validating - if true the parsers created by this factory  must be validating.
     */

    public void setValidating(boolean validating) {
        features.put (XmlParser.FEATURE_VALIDATION, new Boolean (validating));
    }

    /**
     * Indicates whether or not the factory is configured to produce parsers
     * which validate the XML content during parse.
     *
     * @return   true if the factory is configured to produce parsers
     * which validate the XML content during parse; false otherwise.
     */

    public boolean isValidating() {
        return getFeature (XmlParser.FEATURE_VALIDATION);
    }

    /**
     * Creates a new instance of a XML Pull Parser
     * using the currently configured factory features.
     *
     * @return A new instance of a XML Pull Parser.
     * @throws XmlParseException if a parser cannot be created which satisfies the
     * requested configuration.
     */

    public XmlParser newPullParser() throws XmlParseException {

        if (parserClasses == null) throw new XmlParseException
                ("Factory initialization was incomplete - has not tried ");

        if (parserClasses.size() == 0) throw new XmlParseException
                ("No valid parser classes found in ");

        final StringBuilder issues = new StringBuilder ();

        for (int i = 0; i < parserClasses.size (); i++) {
            final Class<XmlParserImpl> ppClass = parserClasses.get (i);
            try {
                final XmlParser pp = (XmlParser) ppClass.newInstance();
                //            if( ! features.isEmpty() ) {
                //Enumeration keys = features.keys();
                // while(keys.hasMoreElements()) {
                for(String key:features.keySet()){
                     final Boolean value = features.get(key);
                     if(value != null && value.booleanValue()) {
                         pp.setFeature(key, true);
                     }
                }
                return pp;

            } catch(Exception ex) {
                issues.append (ppClass.getName () + ": "+ ex.toString ()+"; ");
            }
        }

        throw new XmlParseException ("could not create parser: "+issues);
    }


    /**
     * Creates a new instance of a XML Serializer.
     *
     * <p><b>NOTE:</b> factory features are not used for XML Serializer.
     *
     * @return A new instance of a XML Serializer.
     * @throws XmlParseException if a parser cannot be created which satisfies the
     * requested configuration.
     */

    public XmlSerializer newSerializer() throws XmlParseException {

        if (serializerClasses == null) {
            throw new XmlParseException
                ("Factory initialization incomplete - has not tried ");
        }
        if(serializerClasses.size() == 0) {
            throw new XmlParseException
                ("No valid serializer classes found in ");
        }

        final StringBuilder issues = new StringBuilder ();

        for (int i = 0; i < serializerClasses.size (); i++) {
            final Class<?> ppClass = serializerClasses.get (i);
            try {
                final XmlSerializer ser = (XmlSerializer) ppClass.newInstance();

                //                for (Enumeration e = features.keys (); e.hasMoreElements ();) {
                //                    String key = (String) e.nextElement();
                //                    Boolean value = (Boolean) features.get(key);
                //                    if(value != null && value.booleanValue()) {
                //                        ser.setFeature(key, true);
                //                    }
                //                }
                return ser;

            } catch(Exception ex) {
                issues.append (ppClass.getName () + ": "+ ex.toString ()+"; ");
            }
        }

        throw new XmlParseException ("could not create serializer: "+issues);
    }



    public static XmlParserFactory newInstance ()throws XmlParseException {         
        return Factory.Singleton;
    }
    
    private static class Factory{
    	private static XmlParserFactory factory;
    	static{
    		if (factory == null) {
	    		 final List<Class<XmlParserImpl>> parserClasses = new ArrayList<Class<XmlParserImpl>> ();
	             final List<Class<XmlSerializerImpl>> serializerClasses = new ArrayList<Class<XmlSerializerImpl>> ();
	             factory = new XmlParserFactory ();
	             parserClasses.add(XmlParserImpl.class);
	             serializerClasses.add(XmlSerializerImpl.class);
	             factory.parserClasses = parserClasses;
	             factory.serializerClasses = serializerClasses;
    		}
    	}
    	static XmlParserFactory Singleton=factory;
    }
}


