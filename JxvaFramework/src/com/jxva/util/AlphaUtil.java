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

import com.jxva.entity.Encoding;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-26 09:51:44 by Jxva
 */
public abstract class AlphaUtil {
	

	
	 //字母Z使用了两个标签，这里有２７个值,i, u, v都不做声母, 跟随前面的字母
    public static final char[] INITIAL_TABLE ={
                '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
                '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
                '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座',
            };
    
    public static final int[] GB2312_INITIAL_TABLE = new int[27];

    static {
        for (int i = 0; i < 27; ++i) {
        	 GB2312_INITIAL_TABLE[i] = getGb2312Value(INITIAL_TABLE[i]);
        }
    }
	

    /**
     * 输入字符,得到他的声母,英文字母返回对应的大写字母
     * 其他原样返回
     * @param ch
     * @return
     */
    public static char toAlpha(char ch) {
        if (ch >= 'a' && ch <= 'z')	return (char) (ch - 'a' + 'A');
        if (ch >= 'A' && ch <= 'Z')	return ch;
        int gb = getGb2312Value(ch);
        if (gb < GB2312_INITIAL_TABLE[0])return ch;
        int i;
        for (i = 0; i < 26; ++i) {
            if (match(i, gb))break;
        }
        return i>=26?ch:CharUtil.UPPER_CHAR_TABLE[i];
    }

    /**
     * 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
     * @param str
     * @return
     */
    public static String toAlpha(String str) {
        StringBuilder result = new StringBuilder();
        try {
            for (int i = 0; i <str.length(); i++) {
                result.append(toAlpha(str.charAt(i)));
            }
        } catch (Exception e) {
            result.append("");
        }
        return result.toString();
    }

    /**
     * 字母Z使用了两个标签
     * @param i
     * @param gb
     * @return
     */
    private static boolean match(int i, int gb) {
        if (gb < GB2312_INITIAL_TABLE[i])return false;
        int j = i + 1;
        while (j < 26 && (GB2312_INITIAL_TABLE[j] == GB2312_INITIAL_TABLE[i])){
            ++j;
        }
        return j==26?gb<=GB2312_INITIAL_TABLE[j]:gb<GB2312_INITIAL_TABLE[j];
    }

    /**
     * 取出汉字的编码
     * @param ch
     * @return
     */
    private static int getGb2312Value(char ch) {
        try {
            byte[] bytes =String.valueOf(ch).getBytes(Encoding.GB2312);
            return bytes.length < 2?0:(bytes[0] << 8 & 0xff00) + (bytes[1] &0xff);
        } catch (Exception e) {
            return 0;
        }
    }
}
