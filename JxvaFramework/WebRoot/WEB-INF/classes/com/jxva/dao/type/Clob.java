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
 *
 */
package com.jxva.dao.type;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:53:12 by Jxva
 */
public class Clob implements java.sql.Clob,Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private char chars[];
	
	public String getCharacters(){
		return new String(chars);
	}
	
	public String getCharacters(String encoding) throws UnsupportedEncodingException{
		String tmp=new String(chars);
		return new String(tmp.getBytes(),encoding);
	}

	/**
	 * 
	 * @param in
	 * @throws Exception
	 */
	public Clob(InputStream in) throws IOException {
		Reader reader=new InputStreamReader(in);
		char _chars[] = new char[1024];
		CharArrayWriter writer = new CharArrayWriter(0x500000);
		for (int i = -1; (i = reader.read(_chars)) > 0;){
			writer.write(_chars, 0, i);
		}
		writer.flush();
		writer.close();
		reader.close();
		this.chars = writer.toCharArray();
	}
	
	/**
	 * 
	 * @param in
	 * @param encoding
	 * @throws IOException 
	 * @throws Exception
	 */
	public Clob(InputStream in,String encoding) throws IOException {
		Reader reader=new InputStreamReader(in,encoding);
		char _chars[] = new char[1024];
		CharArrayWriter writer = new CharArrayWriter(0x500000);
		for (int i = -1; (i = reader.read(_chars)) > 0;){
			writer.write(_chars, 0, i);
		}
		writer.flush();
		writer.close();
		reader.close();
		this.chars = writer.toCharArray();
	}
	
	/**
	 * 
	 * @param reader
	 * @throws IOException
	 */
	public Clob(Reader reader) throws IOException {
		char _chars[] = new char[1024];
		CharArrayWriter writer = new CharArrayWriter(0x500000);
		for (int i = -1; (i = reader.read(_chars)) > 0;){
			writer.write(_chars, 0, i);
		}
		writer.flush();
		writer.close();
		reader.close();
		this.chars = writer.toCharArray();
	}


	public long length() throws SQLException {
		return (long) chars.length;
	}

	public String getSubString(long offset, int length) throws SQLException {
		return new String(chars, (int) offset, length);
	}

	public Reader getCharacterStream() throws SQLException {
		return new CharArrayReader(chars);
	}

	public InputStream getAsciiStream() throws SQLException {
		return new ByteArrayInputStream((new String(chars)).getBytes());
	}

	public long position(String s, long l) throws SQLException {
		throw new UnsupportedOperationException("Method position() not yet implemented.");
	}

	public long position(Clob clob, long l) throws SQLException {
		throw new UnsupportedOperationException("Method position() not yet implemented.");
	}

	public Writer setCharacterStream(long l) throws SQLException {
		throw new UnsupportedOperationException("Method setCharacterStream() not yet implemented.");
	}

	public int setString(long l, String s, int i, int j) throws SQLException {
		throw new UnsupportedOperationException("Method setString() not yet implemented.");
	}

	public int setString(long l, String s) throws SQLException {
		throw new UnsupportedOperationException("Method setString() not yet implemented.");
	}

	public void truncate(long l) throws SQLException {
		throw new UnsupportedOperationException("Method truncate() not yet implemented.");
	}

	public OutputStream setAsciiStream(long l) throws SQLException {
		throw new UnsupportedOperationException("Method setAsciiStream() not yet implemented.");
	}

	public long position(java.sql.Clob searchstr, long start) {
		throw new UnsupportedOperationException("Method position() not yet implemented.");
	}

	public void free() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public Reader getCharacterStream(long arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}