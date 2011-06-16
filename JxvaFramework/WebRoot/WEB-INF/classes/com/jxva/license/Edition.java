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
package com.jxva.license;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-28 16:03:38 by Jxva
 */
public final class Edition {
		
    private static final List<Edition> known = new ArrayList<Edition>(5);

	public static final Edition DEVELOPMENT = new Edition("Development",0);
	public static final Edition EVALUATION 	= new Edition("Evaluation", 1);
	public static final Edition PERSONAL 	= new Edition("Personal", 2);
	public static final Edition ENTERPRISE 	= new Edition("Enterprise", 3);
	public static final Edition UNLIMITED 	= new Edition("Unlimited", 4);

	private final String name;
	private final int value;
	
	private Edition(String name, int value) {
		this.name = name;
		this.value = value;
		known.add(this);
	}
	
	public static Edition parse(String name)throws LicenseException{
		try{
			for (int i = 0; i < known.size(); i++) {
			    Edition edition = (Edition) known.get(i);
			    if (name.equalsIgnoreCase(edition.name.trim())) {
			    	return edition;
			    }
			}
		}catch(Exception e){}
		throw new LicenseException();
	}
	
	public String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}

	public final int intValue() {
		return value;
	}
	
    public boolean equals(Object o) {
    	try {
    	    Edition edition = (Edition)o;
    	    return (edition.value == this.value);
    	} catch (Exception e) {
    	    return false;
    	}
    }

    public int hashCode() {
    	return this.value;
    }
    
}
