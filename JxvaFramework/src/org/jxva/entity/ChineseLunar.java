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
package org.jxva.entity;

/**
 * Chinese Lunar Calendar
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-02-13 17:03:53 by Jxva
 */
public abstract class ChineseLunar {
	
	public static final int[] MONTH_DAYS={31,28,31,30,31,30,31,31,30,31,30,31};
	public static final String[] CELESTIAL_STEM={"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
	public static final String[] EARTHLY_BRANCHES={"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
	public static final String[] ANIMAL={"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};
	public static final String[] SOLAR_TERM={"小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至"};
		
	public static String getYear(int year){
		StringBuilder sb=new StringBuilder();
		sb.append(CELESTIAL_STEM[(year-4)%10]);
		sb.append(EARTHLY_BRANCHES[(year-4)%12]);
		return sb.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getYear(2009));
	}

}
