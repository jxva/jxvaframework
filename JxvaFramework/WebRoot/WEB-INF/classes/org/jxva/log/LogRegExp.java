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
package org.jxva.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse an Apache log file with Regular Expressions
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-01-18 20:39:45 by Jxva
 */
public class LogRegExp {
	/** The number of fields that must be found. */
	public static final int NUM_FIELDS = 9;

	/** The sample log entry to be parsed. */
	public static final String logEntryLine = "123.45.67.89 - - [27/Oct/2000:09:27:09 -0400] \"GET /java/javaResources.html HTTP/1.0\" 200 10450 \"-\" \"Mozilla/4.6 [en] (X11; U; OpenBSD 2.8 i386; Nav)\"";

	public static void main(String argv[]) {

		String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";

		System.out.println("Using RE Pattern:");
		System.out.println(logEntryPattern);

		System.out.println("Input line is:");
		System.out.println(logEntryLine);

		Pattern p = Pattern.compile(logEntryPattern);
		Matcher matcher = p.matcher(logEntryLine);
		if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
			System.err.println("Bad log entry (or problem with RE?):");
			System.err.println(logEntryLine);
			return;
		}
		System.out.println("IP Address: " + matcher.group(1));
		System.out.println("Date&Time: " + matcher.group(4));
		System.out.println("Request: " + matcher.group(5));
		System.out.println("Response: " + matcher.group(6));
		System.out.println("Bytes Sent: " + matcher.group(7));
		if (!matcher.group(8).equals("-"))
			System.out.println("Referer: " + matcher.group(8));
		System.out.println("Browser: " + matcher.group(9));
	}
}
