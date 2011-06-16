package com.jxva.entity;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 <pre>
	major  minor Java platform version
	45       3           1.0
	45       3           1.1
	46       0           1.2
	47       0           1.3
	48       0           1.4
	49       0           1.5
	50       0           1.6 
  </pre>
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-06 12:38:20 by Jxva
 */
public class ClassParser {

    private int major;
    private int minor;
    
    public ClassParser(File file) throws IOException{
    	DataInputStream in = new DataInputStream(new FileInputStream(file.getAbsolutePath()));
    	int magic = in.readInt();
        if(magic != 0xcafebabe) {
        	System.out.println(file + " is not a valid class!");;
        }
        minor = in.readUnsignedShort();
        major = in.readUnsignedShort();
        in.close();
    }
    
    public int getMajor(){
    	return this.major;
    }
    
    public int getMinor(){
    	return this.minor;
    }
} 