/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
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
package demo;

/**
 * this is a regular expression example for study.
 * @author <a href="http://www.jxva.com">http://www.jxva.com/</a>
 * @version 1.0.0
 * @changelog
 */
public class Test {

	/**
	 * @param String[]
	 */
	public static void main(String[] args) {
		// = and or in like order by 
		String sql = "fdaf\"fdsa\"ds'af'fds'afds?afd%sas";
		String d=" order by userName desc ";
		String e="msgid = 5";
		
		String w=" userName = 'Jxva'fr\"am\"ew''o'rkd' and id=4 or msgid in (1,,2,3) and name like ' % jxva % ' order by jxva email ";
		System.out.println(stringSql(sql));

	}

    protected static String stringSql(String x) {
        // ' -> ''
        StringBuffer r = new StringBuffer(x.replaceAll("\\Q\'\\E", "\'\'"));
        final char q = '\'';
        r.insert(0, q); 
        r.append(q);
        return r.toString();
    }
}