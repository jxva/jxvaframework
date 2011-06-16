/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
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
 *
 */
package study.cache;


import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * A utility class which configures beans from XML, using reflection.
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:14:12 by Jxva
 */
public class Configurator {    
    public void configure(final Object bean, final InputStream inputStream) throws Exception {
        final SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        final BeanHandler handler = new BeanHandler(bean);
        parser.parse(inputStream, handler);
    }
}
