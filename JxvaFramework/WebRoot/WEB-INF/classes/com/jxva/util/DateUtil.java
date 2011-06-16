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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 
 			String[] patternExamples = {
                 "dd MMMMM yyyy",
                 "dd.MM.yy",
                 "MM/dd/yy",
                 "yyyy.MM.dd G ´at´ hh:mm:ss z",
                 "EEE, MMM d, ´´yy",
                 "h:mm a",
                 "H:mm:ss:SSS",
                 "K:mm a,z",
                 "yyyy.MMMMM.dd GGG hh:mm aaa"
                 };
 * 
 * d mon yyyy hh:mm:ss GMT == January 1, 1970, 00:00:00 GMT
 * 
 	yyyy-MM-dd HH:mm:ss zzzz
	EEE, MMM d, ''yy
	EEEE, MMMM dd, yyyy, hh:mm:ss a '('zzz')' 
	结果：
	2003-04-08 21:24:44 Pacific Daylight Time
	Tue, Apr 8, '03
	Tuesday, April 08, 2003, 09:24:44 PM (PDT) 
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:23:00 by Jxva
 */
public abstract class DateUtil {

	public static final int DAY_SECONDS = 86400;
	
	public static final DateFormat DATE_YMD = new SimpleDateFormat("yyyy-MM-dd");

	public static final DateFormat DATE_YMDH = new SimpleDateFormat("yyyy-MM-dd HH");

	public static final DateFormat DATE_YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
	public static final DateFormat DATETIME_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final DateFormat DATETIME_YMDHMSS_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	
	public static final DateFormat TIME_HMS = new SimpleDateFormat("HH:mm:ss");
	
	public static final DateFormat DIGIT_YMDHMS=new SimpleDateFormat("yyyyMMddHHmmss");
	
	//Thu Mar 12 15:57:22 CST 2009 or Wed Mar 11 00:00:00 UTC 2009
	public static final DateFormat CST=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
	
	//12 Mar 2009 09:09:47 GMT
	public static final DateFormat GMT=new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz",Locale.US);
	
	
	public static final String[] SHORT_CN_WEEK={"日","一","二","三","四","五","六"};
	
	public static final String[] SHORT_EN_WEEK={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
	
	public static final String[] EN_WEEK={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	
	public static final String[] SHORT_CN_MONTH={"一","二","三","四","五","六","七","八","九","十","十一","十二"};
	
	public static final String[] SHORT_EN_MONTH={"Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec"};
	
	public static final String[] EN_MONTH={"January","February","March","April","May","June","July","Aguest","September","October","November","December"};
	
	public static final Calendar CALENDAR = Calendar.getInstance();
	
	public static String getDate(){
		return new Timestamp(System.currentTimeMillis()).toString().substring(0,10);
	}
	
	public static String getDateTime(){
		return new Timestamp(System.currentTimeMillis()).toString().substring(0,19);
	}
	
	/**
	 * @param format
	 * @param date
	 * @return
	 */
	public static String format(String format,Date date){
		return new SimpleDateFormat(format).format(date);
//		CALENDAR.setTime(date);
//		int year = CALENDAR.get(Calendar.YEAR);
//		int month = CALENDAR.get(Calendar.MONTH);
//		int day = CALENDAR.get(Calendar.DAY_OF_MONTH);
//		int hour = CALENDAR.get(Calendar.HOUR_OF_DAY);
//		int min = CALENDAR.get(Calendar.MINUTE);
//		int sec = CALENDAR.get(Calendar.SECOND);
//		return year + "-" + (month < 10 ? "0" + month : "" + month) + "-"
//			+ (day < 10 ? "0" + day : "" + day)
//			+ (hour < 10 ? " 0" + hour : " " + hour) + ":"
//			+ (min < 10 ? "0" + min : "" + min) + ":"
//			+ (sec < 10 ? "0" + sec : "" + sec);
	}
		
	public static Date parse(DateFormat dateFormat,String str){
		Assert.hasText(str,"str must have text; it must not be null, empty, or blank");
		try {
			return dateFormat.parse(str);
		} catch (ParseException e) {
			throw new UtilException(e);
		}
	}
	
	/**
	 * TODO
	 * @param str
	 * @return
	 * @throws UtilException
	 */
	public static Date parse(String str) throws UtilException{
		Assert.hasText(str,"str must have text; it must not be null, empty, or blank");
		try {
			if(str.indexOf("CST")>-1){
				return CST.parse(str);
			}else if(str.indexOf("GMT")>-1){//12 Mar 2009 09:09:47 GMT
				return GMT.parse(str);
			}else if (str.length() > 10){
				return DATETIME_YMDHMS.parse(str);
			}else if (str.indexOf(':') == -1){
				return DATE_YMD.parse(str);
			}else{
				return TIME_HMS.parse(str);
			}
		} catch (ParseException e) {
			throw new UtilException(e);
		}
	}
		
	/**
	 * 在当前时间上加上指定偏移的秒数(seconds为负数则减去相应秒数),得到新的时间
	 * @param seconds
	 * @return java.util.Date
	 */
	public static Date getDateByOffsetSeconds(long seconds){
		Date date = new Date();
		long time = (date.getTime() / 1000) + seconds;
		date.setTime(time * 1000);
		return date;
	}	
	
	public static List<Date> getDateList(Date begin,Date end){
		int differ=getDifferDays(begin,end);
		List<Date> temp=new ArrayList<Date>(differ);
		for(int i=0;i<=differ;i++){
			CALENDAR.setTime(begin);
			CALENDAR.add(Calendar.DATE,i);
			temp.add(CALENDAR.getTime());
		}
		return temp;
	}
	
	public static int getDifferDays(Date begin,Date end){
		Long d=((begin.getTime()-end.getTime())/1000/DAY_SECONDS);
		return d.intValue();
	}

	public static int getDayOfWeek(Date date){
		CALENDAR.setTime(date);  
		return CALENDAR.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	
	public static Date getFirstDateOfWeek(){
		return getDateOfWeek(Calendar.SUNDAY);
	}
	
	public static Date getLastDateOfWeek(){
		return getDateOfWeek(Calendar.SATURDAY);
	}
		
	public static Date getDateOfWeek(int week){
		return getDateOfWeek(new Date(),week);
	}
	
	public static Date getDateOfWeek(Date date,int week){
		CALENDAR.setTime(date);  
        CALENDAR.set(Calendar.DAY_OF_WEEK,week);
		CALENDAR.set(Calendar.HOUR_OF_DAY,0);
		CALENDAR.set(Calendar.MINUTE, 0);
		CALENDAR.set(Calendar.SECOND, 0);
        return CALENDAR.getTime();  
	}

	public static Date getFirstDateOfMonth()throws UtilException{
		try {
			CALENDAR.setTime(DATE_YMD.parse(CALENDAR.get(Calendar.YEAR)+"-"+(CALENDAR.get(Calendar.MONTH)+1)+"-01"));
			return CALENDAR.getTime();
		} catch (ParseException e) {
			throw new UtilException(e);
		}
	}
	
	public static Date getLastDateOfMonth()throws UtilException{
		try {
			CALENDAR.setTime(DATE_YMD.parse(CALENDAR.get(Calendar.YEAR)+"-"+(CALENDAR.get(Calendar.MONTH)+1)+"-"+CALENDAR.getActualMaximum(Calendar.DAY_OF_MONTH)));
			return CALENDAR.getTime();
		} catch (ParseException e) {
			throw new UtilException(e);
		}
	}
}