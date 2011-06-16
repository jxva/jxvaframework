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
package com.jxva.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2008-11-28 13:58:40 by Jxva
 */
public abstract class ShellUtil {


	/**
	 * 异步执行Shell命令
	 * 
	 * @param cmd
	 *            命令行
	 * @return 返回处理类
	 * @throws UtilException
	 */
	public static Process asynchRun(String cmd) throws UtilException {
		try {
			return Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			throw new UtilException(e);
		}
	}

	/**
	 * 同步执行Shell命令
	 * 
	 * @param cmd
	 *            命令行
	 * @return 返回处理类
	 * @throws UtilException
	 */
	public static Process synchRun(String cmd) throws UtilException {
		try {
			Process p = asynchRun(cmd);
			p.waitFor();
			return p;
		} catch (InterruptedException e) {
			throw new UtilException(e);
		}
	}

	/**
	 * 执行同步Shell命令并返回执行结果
	 * 
	 * @param cmd
	 *            命令行
	 * @return 返回执行结果
	 * @throws UtilException
	 */
	public static String getSynchRunResult(String cmd) throws UtilException {
		StringBuilder sb = new StringBuilder();
		InputStream in = synchRun(cmd).getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(isr);
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new UtilException(e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
			if (isr != null)
				try {
					isr.close();
				} catch (IOException e) {
				}
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}
		return sb.toString();
	}


	/**
	 * 检测程序。
	 * 
	 * @param processName
	 *            线程的名字，请使用准确的名字
	 * @return 找到返回true,没找到返回false
	 */
	public static boolean findProcess(String processName) {
		BufferedReader bufferedReader = null;
		try {
			Process proc = asynchRun("tasklist /FI \"IMAGENAME eq "+ processName + "\"");
			bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(processName)) {
					return true;
				}
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception ex) {
				}
			}
		}
	}
	
	public static void main(String[] args) {
	    System.out.println(findProcess("eclipse.exe"));  
		System.out.println(getSynchRunResult("ipconfig -all"));
	}

}
