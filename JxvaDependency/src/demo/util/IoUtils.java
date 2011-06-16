/*
 * Copyright (C) The Apache Software Foundation. All rights reserved.
 *
 * This software is published under the terms of the Apache Software License
 * version 1.1, a copy of which has been included with this distribution in
 * the LICENSE file.
 */
package demo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Random;

/**
 * IO utility methods.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public class IoUtils {

	/**
	 * Random number generator to make unique file name
	 */
	private final static Random	RANDOM_GEN	=
																						new Random(System
																							.currentTimeMillis());

	/**
	 * Get a <code>BufferedInputStream</code>.
	 */
	public static BufferedInputStream getBufferedInputStream(InputStream in) {
		BufferedInputStream bin = null;
		if (in instanceof java.io.BufferedInputStream) {
			bin = (BufferedInputStream) in;
		}
		else {
			bin = new BufferedInputStream(in);
		}
		return bin;
	}

	/**
	 * Get a <code>BufferedOutputStream</code>.
	 */
	public static BufferedOutputStream getBufferedOutputStream(OutputStream out) {
		BufferedOutputStream bout = null;
		if (out instanceof java.io.BufferedOutputStream) {
			bout = (BufferedOutputStream) out;
		}
		else {
			bout = new BufferedOutputStream(out);
		}
		return bout;
	}

	/**
	 * Get <code>BufferedReader</code>.
	 */
	public static BufferedReader getBufferedReader(Reader rd) {
		BufferedReader br = null;
		if (br instanceof java.io.BufferedReader) {
			br = (BufferedReader) rd;
		}
		else {
			br = new BufferedReader(rd);
		}
		return br;
	}

	/**
	 * Get <code>BufferedWriter</code>.
	 */
	public static BufferedWriter getBufferedWriter(Writer wr) {
		BufferedWriter bw = null;
		if (wr instanceof java.io.BufferedWriter) {
			bw = (BufferedWriter) wr;
		}
		else {
			bw = new BufferedWriter(wr);
		}
		return bw;
	}

	/**
	 * Get unique file object.
	 */
	public static File getUniqueFile(File oldFile) {
		File newFile = oldFile;
		while (true) {
			if (!newFile.exists()) {
				break;
			}
			newFile =
				new File(oldFile.getAbsolutePath() + '.' +
					Math.abs(RANDOM_GEN.nextLong()));
		}
		return newFile;
	}

	/**
	 * No exception <code>InputStream</code> close method.
	 */
	public static InputStream close(InputStream is) {
		if (is != null) {
			try {
				is.close();
				is = null;
				// System.err.println("close InputStream");
			}
			catch (Exception ex) {
			}
		}
		return is;
	}

	/**
	 * No exception <code>OutputStream</code> close method.
	 */
	public static OutputStream close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
				os = null;
				// System.err.println("close OutputStream");
			}
			catch (Exception ex) {
			}
		}
		return os;
	}

	/**
	 * No exception <code>java.io.Reader</code> close method.
	 */
	public static void close(Reader rd) {
		if (rd != null) {
			try {
				rd.close();
			}
			catch (Exception ex) {
			}
		}
	}

	/**
	 * No exception <code>java.io.Writer</code> close method.
	 */
	public static void close(Writer wr) {
		if (wr != null) {
			try {
				wr.close();
			}
			catch (Exception ex) {
			}
		}
	}

	/**
	 * Get exception stack trace.
	 */
	public static String getStackTrace(Throwable ex) {
		String result = "";
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			pw.close();
			sw.close();
			result = sw.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
	 * 
	 * @param bufferSize
	 *          Size of internal buffer to use.
	 */
	public static void copy(Reader input, Writer output, int bufferSize)
		throws IOException {
		char buffer[] = new char[bufferSize];
		int n = 0;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
	}

	/**
	 * Copy chars from a <code>InputStream</code> to a <code>OutputStream</code>.
	 * 
	 * @param bufferSize
	 *          Size of internal buffer to use.
	 */
	public static void copy(InputStream input, OutputStream output, int bufferSize)
		throws IOException {
		byte buffer[] = new byte[bufferSize];
		int n = 0;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
	}

	/**
	 * Read fully from reader
	 */
	public static String readFully(Reader reader) throws IOException {
		StringWriter writer = new StringWriter();
		copy(reader, writer, 1024);
		return writer.toString();
	}

	/**
	 * Read fully from stream
	 */
	public static String readFully(InputStream input) throws IOException {
		StringWriter writer = new StringWriter();
		InputStreamReader reader = new InputStreamReader(input);
		copy(reader, writer, 1024);
		return writer.toString();
	}

}