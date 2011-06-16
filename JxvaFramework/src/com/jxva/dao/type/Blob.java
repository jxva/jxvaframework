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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:53:02 by Jxva
 */
public class Blob implements java.sql.Blob, Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte bytes[];
	
	public Blob(byte _bytes[]) {
		this.bytes = _bytes;
	}

	public Blob(InputStream is) throws IOException {
		byte _bytes[] = new byte[1024];
		ByteArrayOutputStream os = new ByteArrayOutputStream(is.available());
		for (int i = -1; (i = is.read(_bytes)) > 0;){
			os.write(_bytes, 0, i);
		}
		os.flush();
		os.close();
		is.close();
		this.bytes = os.toByteArray();
	}

	public long length() throws SQLException {
		return (long) this.bytes.length;
	}

	public byte[] getBytes(long offset, int length) throws SQLException {
		byte _bytes[] = new byte[length];
		System.arraycopy(this.bytes, (int) offset, _bytes, 0, length);
		return _bytes;
	}

	public InputStream getBinaryStream() throws SQLException {
		return new ByteArrayInputStream(this.bytes);
	}

	public long position(byte abyte0[], long l) throws SQLException {
		throw new UnsupportedOperationException("Method position() not yet implemented.");
	}

	public long position(Blob blob, long l) throws SQLException {
		throw new UnsupportedOperationException("Method position() not yet implemented.");
	}

	public int setBytes(long l, byte abyte0[], int i, int j)throws SQLException {
		throw new UnsupportedOperationException("Method setBytes() not yet implemented.");
	}

	public int setBytes(long l, byte abyte0[]) throws SQLException {
		throw new UnsupportedOperationException("Method setBytes() not yet implemented.");
	}

	public OutputStream setBinaryStream(long l) throws SQLException {
		throw new UnsupportedOperationException("Method setBinaryStream() not yet implemented.");
	}

	public void truncate(long l) throws SQLException {
		throw new UnsupportedOperationException("Method truncate() not yet implemented.");
	}

	public long position(java.sql.Blob pattern, long start) throws SQLException {
		throw new UnsupportedOperationException("Method position() not yet implemented.");
	}

	public void free() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public InputStream getBinaryStream(long arg0, long arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}