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

import java.util.Date;
import java.util.Random;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-15 11:45:42 by Jxva
 */
public abstract class RandomUtil {
	
    public static final Random RANDOM = new Random();
		
	/**
	 * 获取length长度的随机串,由随机表组成
	 * @param randomTables 随机表
	 * @param length 需要生成的随机串长度
	 * @return 随机串
	 */
	public static String getRandomString(char[] randomTable,int length){
		StringBuilder sb = new StringBuilder();
		int l=randomTable.length;
		for (int i = 0; i < length; i++) {
			sb.append(randomTable[RANDOM.nextInt(l)]);
		}
		return sb.toString();
	}
	
	/**
	 * 得到一个按日期格式的自动编号(长度为18位)<br>
	 * 如 yyyyMMddHHmmss+四位随机码
	 * @return String
	 */
	public static String getAutoId() {
		return DateUtil.DIGIT_YMDHMS.format(new Date())+ (Math.round(Math.random() * 8999) + 1000);
	}
	
	public static int getRandomNum(int max){
		int num=RANDOM.nextInt()%max;
		return num<0?-num:num;
	}
}
