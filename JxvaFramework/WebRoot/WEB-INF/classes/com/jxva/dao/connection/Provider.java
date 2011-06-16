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
package com.jxva.dao.connection;

import java.util.ArrayList;
import java.util.List;

import com.jxva.dao.ParseException;

/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-03-25 09:25:11 by Jxva
 */
public final class Provider {
	
    private static final List<Provider> known = new ArrayList<Provider>(6);
    
	public static final Provider JDBC = new Provider("jdbc",0);
	public static final Provider PROXOOL = new Provider("proxool",1);
	public static final Provider C3P0 = new Provider("c3p0",2);
	public static final Provider DBCP = new Provider("dbcp",3);
	public static final Provider JNDI = new Provider("jndi",4);
	public static final Provider BONECP=new Provider("bonecp",5);
	
	private final String name;
	private final int value;
	
	private Provider(String name,int value) {
		this.name = name;
		this.value=value;
		known.add(this);
	}
		
	public static Provider parse(String name)throws ParseException{
		try{
			for (int i = 0; i < known.size(); i++) {
				Provider provider = (Provider) known.get(i);
			    if (name.equalsIgnoreCase(provider.name.trim())) {
			    	return provider;
			    }
			}
		}catch(Exception e){}
		throw new ParseException();
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
    		Provider provider = (Provider)o;
    	    return (provider.value == this.value);
    	} catch (Exception e) {
    	    return false;
    	}
    }

    public int hashCode() {
    	return this.value;
    }
}
