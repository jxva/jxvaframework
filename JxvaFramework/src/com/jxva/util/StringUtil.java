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

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import com.jxva.entity.Encoding;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:26:56 by Jxva
 */
public abstract class StringUtil {
	
	public static boolean isEmpty(String str){
		return str==null||str.length()==0;
	}
		
	public static boolean isNull(String str){
		return str==null;
	}
	

	public static int length(String str){
		if(str==null)return 0;
		return replaceAll(str,"[\u4E00-\u9FA5]","--").length();
	}
	
	public static String substring(String str, int startIndex,int endIndex)throws UtilException{
		try {
			byte[] array = str.getBytes(Encoding.GBK);
			if(endIndex >= array.length||startIndex>endIndex)return str;
			int tmp=0;
			for(int i=startIndex;i<endIndex;i++){
			    if(array[i]<0){
			      tmp++;
			    }
			}
			return new String(array,0,tmp%2==0?endIndex:endIndex+1,Encoding.GBK);
		} catch (UnsupportedEncodingException e) {
			throw new UtilException(e);
		}
	}
	
	public static String firstCharLower(String str){
		return Character.toLowerCase(str.charAt(0))+str.substring(1);
	}
	
	public static String firstCharUpper(String str){
		return Character.toUpperCase(str.charAt(0))+str.substring(1);
	}
	

	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}


	public static boolean hasText(String str) {
		if (!hasLength(str))return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsWhitespace(String str) {
		if (!hasLength(str))return false;
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsUpperChar(String str){
		for(int i=0;i<str.length();i++){
			if(Character.isUpperCase(str.charAt(i))){
				return true;
			}
		} 
		return false;
	}
	

	public static String[] split(String src, String splitter){
		if (StringUtil.isEmpty(src) ||StringUtil.isEmpty(splitter)) {
			return null;
		}
		if (src.lastIndexOf(splitter) == -1) {
			return new String[] {src};
		}else {
			StringBuilder sb = new StringBuilder(src);
			int index = sb.indexOf(splitter);
			List<String> tokenList = new LinkedList<String>();
			while (index != -1) {
				tokenList.add(sb.substring(0, index));
				sb.delete(0, index + splitter.length());
				index = sb.indexOf(splitter);
			}
			tokenList.add(sb.toString());
			String[] ret = new String[tokenList.size()];
			tokenList.toArray(ret);
			return ret;
		}
	}
	
    public static String deleteWhitespace(String str) {
    	if (!hasLength(str))return str;
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        return count == sz?str:new String(chs, 0, count);
    }
    
	public static String trimWhitespace(String str) {
		if (!hasLength(str))return str;
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String ltrimWhitespace(String str) {
		if (!hasLength(str))return str;
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	public static String rtrimWhitespace(String str) {
		if (!hasLength(str))return str;
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String ltrimCharacter(String str, char leadingCharacter) {
		if (!hasLength(str))return str;
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}


	public static String rtrimCharacter(String str, char trailingCharacter) {
		if (!hasLength(str))return str;
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}


	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null)return false;
		if (str.startsWith(prefix))return true;
		if (str.length() < prefix.length())return false;
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null)return false;
		if (str.endsWith(suffix))return true;
		if (str.length() < suffix.length())return false;
		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	public static int indexOfIgnoreCase(String str,String s){
		if (str == null || s == null)return -1;
		if (str.length() < s.length())return -1;
		int pos=str.indexOf(s);
		if (pos>-1)return pos;
		return str.toLowerCase().indexOf(s.toLowerCase());
	}
	
	public static int lastIndexOfIgnoreCase(String str,String s){
		if (str == null || s == null)return -1;
		if (str.length() < s.length())return -1;
		int pos=str.indexOf(s);
		if (pos>-1)return pos;
		return str.toLowerCase().lastIndexOf(s.toLowerCase());
	}
	

	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}


	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0,pos=0,idx=0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}
	
	public static String replaceAll(String str, String from, String to) {
		if(str==null)return str;
		int index = str.indexOf(from);
		if(index==-1)return str;
		int pos = 0;
		final int fromLen = from.length();
		final StringBuilder sb = new StringBuilder();
		while (index >-1) {
			sb.append(str.substring(pos, index)).append(to);
			pos = index + fromLen;
			index = str.indexOf(from,pos);
		}
		sb.append(str.substring(pos));
		return sb.toString();
	}

	public static String deleteAny(String str, String charsToDelete) {
		if (!hasLength(str) || !hasLength(charsToDelete)) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String mark(String str,char c){
		return str == null ? null: c + str + c;
	}

	public static String mark(String str,String s){
		return str == null ? null: new StringBuilder(str.length()+s.length()*2).append(s).append(str).append(s).toString();
	}
}