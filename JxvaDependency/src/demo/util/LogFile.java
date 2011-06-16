/*
 * Copyright (C) The Apache Software Foundation. All rights reserved.
 *
 * This software is published under the terms of the Apache Software License
 * version 1.1, a copy of which has been included with this distribution in
 * the LICENSE file.
 */
package demo.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log class to write log data. It uses <code>RandomAccessFile</code>. If the
 * log file size exceeds the limit, a new log file will be created.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public class LogFile {

	/**
	 * Line separator
	 */
	private final static String	LINE_SEP				=
																								System.getProperty(
																									"line.separator", "\n");

	/**
	 * Date format to backup old files.
	 */
	private DateFormat					LOG_FILE_DATE		=
																								new SimpleDateFormat(
																									"yyyy-MM-dd-hh-mm-ss");

	/**
	 * Debug log level - lowest priority.
	 */
	public static final int			DEBUG						= 0;

	/**
	 * Normal log level - print messages.
	 */
	public static final int			INFO						= 1;

	/**
	 * Warning message
	 */
	public static final int			WARN						= 2;

	/**
	 * Eror log level - print errors.
	 */
	public static final int			ERROR						= 3;

	private static final String	mstLogHeader[]	= { "DEB", "INF", "WAR", "ERR" };

	private DateFormat					mDateFmt				= null;
	private Writer							mOfs						= null;
	private File								mLogFile				= null;
	private int									miLogLevel			= INFO;
	private long								mlMaxSize				= -1;														// no
																																							// size
																																							// limit
	private long								mlSize					= 0;															// current
																																								// size
	private boolean							mbAutoFlush			= false;

	/**
	 * Constructor.
	 */
	public LogFile(File logFile) {

		if (logFile == null) {
			throw new NullPointerException("Log file is null.");
		}

		mDateFmt = new SimpleDateFormat("MM/dd HH:mm:ss");
		mLogFile = logFile;
		open();
	}

	/**
	 * Set log date format
	 */
	public void setDateFormat(DateFormat fmt) {
		mDateFmt = fmt;
	}

	/**
	 * Get the log date format.
	 */
	public DateFormat getDateFormat() {
		return mDateFmt;
	}

	/**
	 * Get auto flush
	 */
	public boolean getAutoFlush() {
		return mbAutoFlush;
	}

	/**
	 * Set auto flush
	 */
	public void setAutoFlush(boolean flush) {
		mbAutoFlush = flush;
	}

	/**
	 * Get log file.
	 */
	public File getFile() {
		return mLogFile;
	}

	/**
	 * Get max size.
	 */
	public long getMaxSize() {
		return mlMaxSize;
	}

	/**
	 * Set max file size. Set <code>max</code> less than or equal to zero to
	 * disable max size limitation.
	 */
	public synchronized void setMaxSize(long max) {
		mlMaxSize = max;
	}

	/**
	 * Get the current file size.
	 */
	public long getCurrentSize() {
		return mlSize;
	}

	/**
	 * Get current log level.
	 */
	public int getLogLevel() {
		return miLogLevel;
	}

	/**
	 * Set log level. If <code>level</code> is beypnd the range, it uses
	 * <code>LOG_NORMAL</code> to wtite log data.
	 */
	public synchronized void setLogLevel(int level) {
		miLogLevel = calculateLogLevel(level);
	}

	/**
	 * Open log file.
	 */
	private synchronized void open() {
		try {
			if (mLogFile != null) {
				mlSize = mLogFile.length();
				RandomAccessFile raf = new RandomAccessFile(mLogFile, "rw");
				raf.seek(raf.length());
				FileWriter fw = new FileWriter(raf.getFD());
				mOfs = new BufferedWriter(fw);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			mOfs = null;
		}
	}

	/**
	 * Print log data.
	 * 
	 * @param str -
	 *          log message
	 * @param level -
	 *          log level of this message.
	 */
	public synchronized void write(int level, String str) {

		level = calculateLogLevel(level);

		// write the string
		if (level >= miLogLevel) {
			String logStr =
				"[" + mDateFmt.format(new Date()) + " (" + mstLogHeader[level] + ")] " +
					str + LINE_SEP;
			long strLen = logStr.length();

			if (mOfs != null) {

				// save file if needed
				if ((mlMaxSize > 0) && ((mlSize + strLen) > mlMaxSize)) {
					save();
				}

				// now write it
				try {
					mOfs.write(logStr);
					if (mbAutoFlush) {
						mOfs.flush();
					}
					mlSize += strLen;
				}
				catch (Exception ex) {
					mOfs = null;
					ex.printStackTrace();
				}
			}
			else {
				System.out.print(logStr);
			}
		}
	}

	/**
	 * Print debug log. Here the log level is <code>DEBUG</code>.
	 */
	public void debug(String str) {
		write(DEBUG, str);
	}

	/**
	 * Print info log.
	 */
	public void info(String str) {
		write(INFO, str);
	}

	/**
	 * Print warning log.
	 */
	public void warn(String str) {
		write(WARN, str);
	}

	/**
	 * Print info log.
	 */
	public void error(String str) {
		write(ERROR, str);
	}

	/**
	 * Print debug log. Here the log level is <code>DEBUG</code>.
	 */
	public void debug(Throwable th) {
		write(DEBUG, IoUtils.getStackTrace(th));
	}

	/**
	 * Print info log.
	 */
	public void info(Throwable th) {
		write(INFO, IoUtils.getStackTrace(th));
	}

	/**
	 * Print warning log.
	 */
	public void warn(Throwable th) {
		write(WARN, IoUtils.getStackTrace(th));
	}

	/**
	 * Print info log.
	 */
	public void error(Throwable th) {
		write(ERROR, IoUtils.getStackTrace(th));
	}

	/**
	 * Log exception
	 */
	public synchronized void write(int level, Throwable th) {
		write(level, IoUtils.getStackTrace(th));
	}

	/**
	 * Set the log file and open a new log file. Returns the name of the saved log
	 * file.
	 */
	private synchronized void save() {
		if (mLogFile == null) {
			return;
		}

		try {
			dispose();
			File sf =
				new File(mLogFile.getAbsolutePath() + '.' +
					LOG_FILE_DATE.format(new Date()));
			sf = IoUtils.getUniqueFile(sf);
			mLogFile.renameTo(sf);
			open();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Calculate the log level
	 */
	private static int calculateLogLevel(int level) {
		int logLevel = level;

		if (level > ERROR) {
			logLevel = ERROR;
		}
		else if (level < DEBUG) {
			logLevel = DEBUG;
		}
		return logLevel;
	}

	/**
	 * Close the log file.
	 */
	public synchronized void dispose() {
		if (mOfs != null) {
			IoUtils.close(mOfs);
			mOfs = null;
		}
	}

}