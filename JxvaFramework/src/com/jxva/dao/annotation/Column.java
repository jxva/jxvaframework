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
package com.jxva.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-10 12:52:21 by Jxva
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD})
public @interface Column {
	//column's name
	String name();
	//boolean unique() default false;
	//boolean nullable() default true; 
	//boolean insertable() default true;
	//boolean updatable() default true;
	//int length() default 255;
	//int precision() default 0; // decimal precision
	//int scale() default 0; // decimal scale 
	

}
