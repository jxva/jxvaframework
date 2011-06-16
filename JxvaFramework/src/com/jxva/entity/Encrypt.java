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
package com.jxva.entity;

import java.util.Random;
import com.jxva.util.StringUtil;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-31 09:52:53 by Jxva
 */
public abstract class Encrypt {
	
	public static void main(String[] args) {
		String[]e=Encrypt.encrypt("webmasterggfdgds#10000#zh_CN#Administrator#1000#中国人们fdslfkdssldafjlkdsjfl#fdsa");
		// System.out.println(e); 
		 System.out.println(e[0]+e[1]);
		 System.out.println((e[0]+e[1]).length());
		 String o=Encrypt.decrypt(e[0],e[1]);
		 System.out.println(o);
		 String[] s=o.split("#");
		 for(String t:s){
			 System.out.println(t);
		 }
	}
		
	private static final String[] numTags = new String[] { "ac", "gh", "dy", "df","xw", "tk", "aw", "pz", "rr", "vb" };

	/**
	 * 将字符串编码为带键值的密码组
	 * @param str 字符串
	 * @return 带键值的密码组
	 */
	public static String[] encrypt(String str) {
		if (StringUtil.isEmpty(str))return null;		
		/*
		 * 每个字符都加一个变化量,变成一个字母 以如下格式记录每次变化:pos*add|pos*add|pos*add
		 * pos表示哪个位置的字母作了改变,add表示改变量,|分隔各变化位置 然后对pos*add|pos*add|pos*add加密
		 * pos加随机数,add加随机数
		 * pos_随机数的解密串*add_随机数的解密|pos_随机数的解密*add_随机数的解密|pos_随机数的解密*add_随机数的解密
		 */
		Random ran = new Random();
		StringBuilder decryptKey = new StringBuilder();
		StringBuilder value = new StringBuilder(str);
		for (int i = 0; i < value.length(); i++) {
			char c = str.charAt(i);
			int ci = ran.nextInt('z' + 1);
			while (!(('A' <= ci && ci <= 'Z') || (('a' <= ci && ci <= 'z')))|| ci == (int) c) {
				ci = ran.nextInt('z' + 1);
			}
			int add = ci - (int) c;
			char newChar = (char) ci;
			value.setCharAt(i, newChar);
			decryptKey.append(i).append('*').append(add).append('|');
		}
		String key=decryptKey.toString();
		if (key.endsWith("|")) {
			key= key.substring(0, key.length() - 1);
			String[] tokens = StringUtil.split(key, "|");
			StringBuilder sb = new StringBuilder("");
			for (int j = 0; tokens != null && j < tokens.length; j++) {
				String[] subTokens = StringUtil.split(tokens[j], "*");
				int pos = Integer.parseInt(subTokens[0]);
				int add = Integer.parseInt(subTokens[1]);
				int posChange = ran.nextInt(50);
				String[] enPosChange = encrypt0(Integer.toString(posChange));
				int addChange = ran.nextInt(50);
				String[] enAddChange = encrypt0(Integer.toString(addChange));
				pos += posChange;
				add += addChange;
				sb.append(pos).append('_').append(enPosChange[0]).append('!').append(enPosChange[1]);
				sb.append('*').append(add).append('_').append(enAddChange[0]).append('!').append(enAddChange[1]).append('|');
			}
			key = sb.substring(0,sb.length() - 1);
		}
		return new String[] {key,value.toString()};
	}

	/**
	 * 解码带键值的密码组
	 * @param value 值 
	 * @param key 键
	 * @return
	 */
	public static String decrypt(String key,String value) {
		if (StringUtil.isEmpty(value))return null;
		if (StringUtil.isEmpty(key))return null;
		StringBuilder result = new StringBuilder(value);
		String[] tokens = StringUtil.split(key, "|");
		StringBuilder sb=new StringBuilder("");
		for (int j = 0; tokens != null && j < tokens.length; j++) {
			String[] subTokens = StringUtil.split(tokens[j], "*");
			String enPos = subTokens[0].substring(subTokens[0].indexOf("_") + 1);
			String enPosTokens[] = StringUtil.split(enPos, "!");
			String posStr = decrypt0(enPosTokens[0], enPosTokens[1]);
			int pos = Integer.parseInt(posStr);
			String enAdd = subTokens[1].substring(subTokens[1].indexOf("_") + 1);
			String enAddTokens[] = StringUtil.split(enAdd, "!");
			String addStr = decrypt0(enAddTokens[0], enAddTokens[1]);
			int add = Integer.parseInt(addStr);
			pos = Integer.parseInt(subTokens[0].substring(0, subTokens[0].indexOf("_")))- pos;
			add = Integer.parseInt(subTokens[1].substring(0, subTokens[1].indexOf("_")))- add;
			sb.append(pos).append('*').append(add).append('|');
		}
		String keyTemp = sb.substring(0, sb.length() - 1);
		tokens = StringUtil.split(keyTemp, "|");
		for (int i = 0; i < tokens.length; i++) {
			String[] subTokens = StringUtil.split(tokens[i], "*");
			int pos = Integer.parseInt(subTokens[0]);
			int change = Integer.parseInt(subTokens[1]);
			result.setCharAt(pos, (char) ((int) result.charAt(pos) - change));
		}
		return result.toString();
	}

	private static String[] encrypt0(String value) {
		if (StringUtil.isEmpty(value))return null;
		StringBuilder sb = new StringBuilder(value);
		/*
		 * 每个字符都加一个变化量,变成一个字母 以如下格式记录每次变化: add^add^add add表示改变量,-分隔各变化位置
		 * 然后对add^add^add任取两个对换顺序,对换记录用pos$pos$pos$pos表示,其中相连两个为
		 * 一次对换,pos的每一位数字是用numTags对应位置的字母表示的 最后的解密串是add^add^add][pos$pos$pos$pos
		 */
		Random ran = new Random();
		String decryptKey = "";
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			int ci = ran.nextInt('z' + 1);
			while (!(('A' <= ci && ci <= 'Z') || (('a' <= ci && ci <= 'z')))
					|| ci == (int) c) {
				ci = ran.nextInt('z' + 1);
			}

			int add = ci - (int) c;
			char newChar = (char) ci;
			sb.setCharAt(i, newChar);
			decryptKey += add + "^";

		}

		// add^add^add任取两个对换顺序
		decryptKey = decryptKey.substring(0, decryptKey.length() - 1);
		String[] tokens = StringUtil.split(decryptKey, "^");

		String changeRecord = "";// 对换记录
		int tokensCnt = tokens.length;
		for (int j = 0; j < tokensCnt / 2; j++) {
			int onePos = ran.nextInt(tokensCnt);

			int twoPos = ran.nextInt(tokensCnt);

			if (onePos == twoPos) {
				j--;
				continue;
			}

			String oneStr = tokens[onePos];
			String twoStr = tokens[twoPos];

			tokens[onePos] = twoStr;
			tokens[twoPos] = oneStr;

			do {
				int digit = onePos % 10;
				onePos = onePos / 10;
				changeRecord += numTags[digit];
			} while (onePos > 0);
			changeRecord += "$";
			do {
				int digit = twoPos % 10;
				twoPos = twoPos / 10;
				changeRecord += numTags[digit];
			} while (twoPos > 0);
			changeRecord += "$";
		}

		// 重新构造解密串
		decryptKey = "";
		for (int j = 0; j < tokens.length; j++) {
			decryptKey += tokens[j] + "^";
		}
		decryptKey = decryptKey.substring(0, decryptKey.length() - 1);
		if (!changeRecord.equals("")) {
			decryptKey += "]["
					+ changeRecord.substring(0, changeRecord.length() - 1);
		} else {
			decryptKey += "][";
		}

		return new String[] { sb.toString(), decryptKey };
	}

	private static String decrypt0(String value, String key) {
		if (value == null || value.equals("")) {
			return null;
		}
		if (key == null || key.equals("")) {
			return null;
		}
		StringBuilder sb = new StringBuilder(value);
		// key 和key tokens位置交换记录
		String[] info = StringUtil.split(key, "][");
		String keyVal = info[0];
		String keyKey = info[1];
		String[] keyTokens = StringUtil.split(keyKey, "$");
		String[] keyValTokens = StringUtil.split(keyVal, "^");
		// 解密key
		if (keyTokens != null && keyTokens.length > 0) {
			for (int i = keyTokens.length - 1; i > 0;) {
				String onePosStr = keyTokens[i];
				String twoPosStr = keyTokens[i - 1];
				String onePosStr0 = "";
				String twoPosStr0 = "";
				int onePos = 0;
				int twoPos = 0;
				for (int j = onePosStr.length(); j > 1;) {
					String subPosStr = onePosStr.substring(j - 2, j);
					for (int k = 0; k < numTags.length; k++) {
						if (numTags[k].equals(subPosStr)) {
							onePosStr0 += k;
						}
					}
					j-=2;
				}
				for (int j = twoPosStr.length(); j > 1;) {
					String subPosStr = twoPosStr.substring(j - 2, j);
					for (int k = 0; k < numTags.length; k++) {
						if (numTags[k].equals(subPosStr)) {
							twoPosStr0 += k;
						}
					}
					j-=2;
				}

				onePos = Integer.parseInt(onePosStr0);
				twoPos = Integer.parseInt(twoPosStr0);
				String oneStr = keyValTokens[onePos];
				String twoStr = keyValTokens[twoPos];
				keyValTokens[onePos] = twoStr;
				keyValTokens[twoPos] = oneStr;
				i-=2;
			}
		}
		key = "";
		for (int i = 0; i < keyValTokens.length; i++) {
			key += keyValTokens[i] + "^";
		}
		/*
		 * 根据key对加密时的变动,还原真实值
		 */
		for (int i = 0; i < keyValTokens.length; i++) {
			int add = Integer.parseInt(keyValTokens[i]);
			sb.setCharAt(i, (char) ((int) sb.charAt(i) - add));
		}
		return sb.toString();
	}

}