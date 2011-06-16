package study;
import java.text.*;
import java.util.*;

public class DateUtil {
    /**
     * GMT 时间转换 ， 0.2版本功能， 正则表达式 ， 部分存在 bug ， 去掉 时区标志
     * 后续版本将会保留时区标志
     *
     * @param date
     *            指定时间字符串。
     * @return 指定日期的下一个星期
     */
    public static synchronized java.util.Date getRegtime(String time) {
        StringBuffer strb = new StringBuffer();
        for (int i = 0; time != null && i < time.length(); i++) {
            if (time.charAt(i) != '?')
                strb.append(time.charAt(i));
            else
                strb.append(" ");
        }
        if (time.indexOf("UTC") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("UTC")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if (time.indexOf("EDT") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("EDT")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if(time.indexOf("T")>0 && time.indexOf("Z")>0)
        {
            strb = new StringBuffer(strb.toString().replace('T',' ').replace('Z',' '))  ;
        }

        if (time.indexOf("CDT") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("CDT")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if (time.indexOf("GMT") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("GMT")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }

        if (time.indexOf("CST") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("CST")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if (time.indexOf("PDT") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("PDT")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }

        SimpleDateFormat format = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        if(time!=null && time.matches("\\d{1,4}\\-[a-zA-Z]{1,}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            format = new SimpleDateFormat(
                "yyyy-MMM-dd HH:mm:ss", Locale.US);
        }else if(time!=null && time.matches("\\d{1,4} [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{1,}"))
        {
            //10 Sep 2006 16:00:00 GMT
            format = new SimpleDateFormat(
                "dd MMM yyyy HH:mm:ss", Locale.US);
        }else if(time!=null && time.matches("\\d{8}"))
        {
            format = new SimpleDateFormat(
                "yyyyMMdd", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,},\\d{2} \\d{2} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            //Sat,23 09 2006 13:19:52
            format = new SimpleDateFormat(
                    "EEE,dd MM yyyy hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3}, \\d{2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}"))
        {
            //Sat, 23 Sep 2006 01:41
            format = new SimpleDateFormat(
                    "EEE, dd MMM yyyy hh:mm", Locale.US);
        }else if(time!=null && time.matches("\\d{1},\\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            //0,17 Sep 2006 22:30:16
            strb = new StringBuffer(time.substring(time.indexOf(",")+1,time.length())) ;
            format = new SimpleDateFormat(
                    "dd MMM yyyy hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("\\d{2}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}"))
        {
            //01/01/01 16:10:01
            format = new SimpleDateFormat(
                    "dd/MM/yy hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{2}"))
        {
            //11/3/2002 10:02:13 PM

            format = new SimpleDateFormat(
                    "MM/dd/yyyy hh:mm:ss a", Locale.US);
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{3}"))
        {
            //2006-9-13 15:17:25 GMT
            format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2}[a-zA-Z]{1}\\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            //2006-09-16T22:16:00
            strb = new StringBuffer(time.replace("T"," ")) ;
            format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3,}, \\d{2} [a-zA-Z]{3,} \\d{4} \\d{1,2}:\\d{1,2} [a-zA-Z]{3}"))
        {
            //Wednesday, 20 September 2006 00:00 GMT
            format = new SimpleDateFormat(
                    "EEE, dd MMM yyyy hh:mm", Locale.US);
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}-\\d{1,2}:\\d{1,2}"))
        {
            //2005-02-27T08:36:00-05:00
            strb = new StringBuffer(time.replace("T"," ").substring(0,time.lastIndexOf("-"))) ;
            format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("\\d{4}--\\d{1,2}-\\d{1,2}-T\\d{1,2}: \\d{1,2}:\\d{1,2}:-\\d{1,2}:\\d{1,2}"))
        {
            strb = new StringBuffer(time.replace("T"," ").replace(" ","").substring(0,time.lastIndexOf("-"))) ;
            format = new SimpleDateFormat(
                    "yyyy--MM-dd-hh:mm:ss", Locale.US);

        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
//            System.out.println("time:"+time);
            format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3} \\w{4} \\d{4} \\d{1,2}:\\d{1,2}[a-zA-Z]{2}"))
        {
            //Jul 31st 2006 10:08PM
//            System.out.println("time"+time);
            strb = new StringBuffer(time.replace("st","").replace("nd","").replace("th","")) ;
            format = new SimpleDateFormat(
                    "MMM dd yyyy hh:mma", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,2}, \\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} -\\d{4}"))
        {
            strb = new StringBuffer(time.substring(time.indexOf(",")+2,time.length())) ;
            format = new SimpleDateFormat(
                    "dd MMM yyyy HH:mm:ss Z", Locale.US);
        }else if(time!=null && time.matches("[1]\\d{9}"))
        {
            format = new SimpleDateFormat(
                    "dd MMM yyyy HH:mm:ss Z", Locale.US);
            strb = new StringBuffer(format.format(new Date(Long.parseLong(time)*1000)))  ;
        }else if(time!=null && time.matches("[a-zA-Z]{3,}, \\d{1,2} [a-zA-Z]{3,} \\d{4}, \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{2}ST"))
        {
            //Saturday, 23 September 2006, 11:32:29 SAST
            format = new SimpleDateFormat(
                    "EEE, dd MMM yyyy, HH:mm:ss Z", Locale.US);
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2} [^\\x00-\\xff]{1,} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            //2006-9-22 锟斤拷锟斤拷 08:40:18
            strb = new StringBuffer(time.replaceAll("[^\\x00-\\xff]{1,}","")) ;
            format = new SimpleDateFormat(
                    "yyyy-MM-dd  HH:mm:ss", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3}, \\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2} [a-zA-Z]{3,}"))
        {//Thu, 7 Sep 2006 22:23 GMT
            format = new SimpleDateFormat(
                    "EEE, dd MMM yyyy HH:mm", Locale.US);
        }else if(time!=null && time.matches("\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}"))
        {//2006/09/23 21:54
            format = new SimpleDateFormat(
                    "yyyy/MM/dd hh:mm", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3}, \\d{1,2} [a-zA-Z]{3} \\d{4}"))
        {//Tue, 19 Sep 2006
            format = new SimpleDateFormat(
                    "EEE, dd MMM yyyy", Locale.US);
        }else if(time!=null && time.matches("\\d{4}[^\\x00-\\xff]{1,}\\d{1,2}[^\\x00-\\xff]{1,}\\d{1,2}[^\\x00-\\xff]{1,}"))
        {
            strb = new StringBuffer(time.replaceAll("[^\\x00-\\xff]{1,}","-")) ;
            format = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,}, \\d{1,2} [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {//Sa, 23 Sep 2006 13:49:25
            if(time.indexOf(", ")>0)
                strb = new StringBuffer(time.substring(time.indexOf(", ")+2,time.length())) ;
            format = new SimpleDateFormat(
                    "dd MMM yyyy hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z ]{1,} \\d{4}"))
        {//Sat Sep 23 15:29:45 CD  2006
         //Wed Sep 14 13:14:23 U C 2005
//         System.out.println(time);
         if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{1,}  \\d{4}"))
         {
             //Sat Sep 23 15:29:45 CD  2006
             strb = new StringBuffer(time.replaceAll("[a-zA-Z]{1,}  ", " "));
             format = new SimpleDateFormat(
                    "EEE MMM dd hh:mm:ss yyyy", Locale.US);
         }else
         {
             //Wed Sep 14 13:14:23 U C 2005
//             strb = new StringBuffer(time.replace(" [a-zA-Z]{1} [a-zA-Z]{1} ", " "));
//             System.out.println(strb.toString());
             format = new SimpleDateFormat(
                    "EEE MMM dd hh:mm:ss 'U C' yyyy", Locale.US);
         }


        }else if(time!=null && time.matches("[a-zA-Z]{1,} \\d{1,2}, \\d{4}, \\d{1,2}:\\d{1,2}[a-zA-Z]{2}"))
        {//September 20, 2006, 11:56AM
            format = new SimpleDateFormat(
                    "MMM dd, yyyy, hh:mma", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,}, \\d{1,2} [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {//We, 26 Jul 2006 00:55:30
            strb = new StringBuffer(time.replace("[a-zA-Z]{1,}, ","")) ;
            format = new SimpleDateFormat(
                    "dd MMM yyyy hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("\\d{4}[^\\x00-\\xff]{1,}"))
        {
            format = new SimpleDateFormat(
                    "yyyy", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} \\d{4}"))
        {//Sat Sep 23 09:51:06 2006
            format = new SimpleDateFormat(
                    "EEE MMM dd hh:mm:ss yyyy", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,}, \\d{1,2}, [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} -\\d{1,}"))
        {//Fri, 22, Sept 2006 11:54:00 -0700
            if(time.split("[a-zA-Z]{4} ").length>1)
                strb = new StringBuffer(time.replaceAll("[a-zA-Z] "," ")) ;
//            System.out.println(strb);
            format = new SimpleDateFormat(
                    "EEE, dd, MMM yyyy hh:mm:ss z", Locale.US);
        }else if(time!=null && time.matches("\\d{1,2} [a-zA-Z]{3} \\d{4}"))
        {
            format = new SimpleDateFormat(
                    "dd MMM yyyy", Locale.US);
        }else if(time!=null && time.matches("\\d{1,2}/\\d{1,2}/\\d{4}"))
        {
            format = new SimpleDateFormat(
                    "dd/MM/yyyy", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3,} \\d{1,2} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}."))
        {//October 12 2005 16:01:49.
            format = new SimpleDateFormat(
                    "MMM dd yyyy hh:mm:ss.", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3,} \\d{1,2} \\d{4}"))
        {
            format = new SimpleDateFormat(
                    "MMM dd yyyy", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{3,}, [a-zA-Z]{3,} \\d{1,2} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} -\\d{1,}"))
        {//Thu, September 7 2006 12:40:02 -0700
            format = new SimpleDateFormat(
                    "EEE, MMM dd yyyy hh:mm:ss z", Locale.US);
        }else if(time!=null && time.matches("\\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {//28 Aug 2004 19:53:19
            format = new SimpleDateFormat(
                    "dd MMM yyyy hh:mm:ss", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2}, \\d{4} \\d{1,2}:\\d{1,2} [a-zA-Z]{2}"))
        {//Sat Sep 09, 2006 3:39 pm
            format = new SimpleDateFormat(
                    "EEE MMM dd, yyyy hh:mm a", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{2}  \\d{4}"))
        {//Sat Dec 03 12:54:26 CS  2005
            strb = new StringBuffer(time.replaceAll("[a-zA-Z]{2,}  "," ")) ;
//            System.out.println(time);
            format = new SimpleDateFormat(
                    "EEE MMM dd hh:mm:ss yyyy", Locale.US);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} \\d{1,2}, \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {//September 21, 2006 12:00:00
//            System.out.println("asfasljkf");
            format = new SimpleDateFormat(
                    "MMM dd, yyyy hh:mm:ss", Locale.US);
        }
        Date date = null;
        try {
           date = format.parse(strb.toString());
       } catch (ParseException ex) {
//            ex.printStackTrace();
           System.out.println(ex.getMessage());
           date = new Date(System.currentTimeMillis()-(long)1000*60*60*24*30);
       }
       return date;

    }

    /**
     * GMT 时间转换 , 0.1版本保留功能 有较多错误
     * @param time String
     * @return Date
     */
    public static synchronized java.util.Date getGMTime(String time) {
//        System.out.println("格式时间："+time);
        StringBuffer strb = new StringBuffer();
        for (int i = 0; time != null && i < time.length(); i++) {
            if (time.charAt(i) != '?')
                strb.append(time.charAt(i));
            else
                strb.append(" ");
        }
//        System.out.println(time);
        SimpleDateFormat format = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        if (time.indexOf("+") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("+")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if (strb.indexOf("-") > 0) {
            if(strb.toString().split("-").length==1 || strb.toString().split("-").length>3)
            {
                strb = new StringBuffer(strb.substring(0, strb.indexOf("-")));
                if (strb.charAt(strb.length() - 1) == ' ') {
                    strb = new StringBuffer(strb.substring(0, strb.length() - 1));
                }
            }
        }
        if (time.indexOf("UTC") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("UTC")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if (time.indexOf("EDT") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("EDT")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if(time.indexOf("T")>0 && time.indexOf("Z")>0)
        {
            strb = new StringBuffer(strb.toString().replace('T',' ').replace('Z',' '))  ;
        }

        if (time.indexOf("CDT") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("CDT")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if (time.indexOf("CST") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("CST")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }
        if (time.indexOf("PDT") > 0) {
            strb = new StringBuffer(strb.substring(0, strb.indexOf("PDT")));
            if (strb.charAt(strb.length() - 1) == ' ') {
                strb = new StringBuffer(strb.substring(0, strb.length() - 1));
            }
        }

//        if (java.lang.Character.isDigit(time.charAt(0))) {
//            strb = new StringBuffer(strb.substring(strb.indexOf(",") + 1,
//                    strb.length()));
//            format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US);
//            if (time.charAt(1) == ' ' && time.indexOf("GMT") > 0) {
//                strb = new StringBuffer(" ").append(strb.toString());
//            }
//        }
        if (Character.UnicodeBlock.of(strb.charAt(0)) ==
            Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
            if(Character.UnicodeBlock.of(strb.charAt(1)) ==
            Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
            {
                format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",
                                              Locale.SIMPLIFIED_CHINESE);
            }else
            {
                strb = new StringBuffer(strb.substring(strb.indexOf(",")+1)) ;
                format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss",
                                              Locale.SIMPLIFIED_CHINESE);
            }
        } else if (strb.indexOf("GMT") > 0) {
            if (strb.indexOf(", ") > 0) {
                format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z",
                                              Locale.US);
            } else if (strb.indexOf(", ") < 0 &&
                       strb.indexOf(",") > 0) {
                format = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss z",
                                              Locale.US);
            }

        } else {
            if (time.toUpperCase().indexOf("AM") > 0 ||
                time.toUpperCase().indexOf("PM") > 0) {
                strb = new StringBuffer(time);
                format = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
            }else if (strb.indexOf(", ") > 0) {
                format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",
                                              Locale.US);
            } else if (strb.indexOf(", ") < 0 &&
                       strb.indexOf(",") > 0) {
                format = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss",
                                              Locale.US);
            }else if (time != null && (time.indexOf("(") > 0 && time.indexOf(")") > 0)) {
                if(time.indexOf("星期")>0)
                {
                    strb = new StringBuffer(time.substring(0, time.indexOf("星期")));
                }
                format = new SimpleDateFormat("yyyy-MM-dd");
            }else if (time != null && (time.indexOf("T") > 0)) {
                if(time.indexOf("+")>0)
                {
                    strb = new StringBuffer(time.replace('T', ' ').substring(0,time.indexOf("+")));
                }else
                {
                    strb = new StringBuffer(time.replace('T', ' '));
                }
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                              Locale.US);
            }else if (time != null && (time.indexOf("-") > 0) && (time.length()>11)) {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                              Locale.US);
            }else if (time != null && (time.indexOf(".") > 0 && time.indexOf("/") > 0)) {
                format = new SimpleDateFormat("MM.dd yyyy / HH:mm",
                                              Locale.US);
            }else if (time != null && (time.length()<12)) {
                format = new SimpleDateFormat("yyyy-MM-dd");
            }else {
                format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss",
                                              Locale.US);
            }

        }

        Date date = null;
        try {
            date = format.parse(strb.toString());
        } catch (ParseException ex) {
//            ex.printStackTrace();
            System.out.println(ex.getMessage());
            date = new Date(System.currentTimeMillis()-(long)1000*60*60*24*30);
        }
        return date;
    }

    /**
     * 格式化时间
     * @param time String
     * @return Date
     */
    public static synchronized java.util.Date getFormatDate(String time) {
        SimpleDateFormat theDateTimeFormat = null;
        if (time == null || time.equals("")) {
            return new Date();
        } else {
            try {
                if (time.indexOf("-") > 0) {
                    if (time.indexOf("　") > 0) {
                        time = time.trim();
                    }
                    if(time.split(":").length>2 && time.indexOf(" ")<0)
                    {
                        theDateTimeFormat = new SimpleDateFormat(
                            "yyyy-MM-ddhh:mm:ss");
                    }if(time.split(":").length>2 && time.indexOf(" ")>0)
                    {
                        theDateTimeFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    }else
                    {
                        theDateTimeFormat = new SimpleDateFormat(
                                "yyyy-MM-ddhh:mm");
                    }
                } else if (time.indexOf("/") > 0) {
                    theDateTimeFormat = DATE_TIME_EXTENDED_FORMAT;
                } else if (time.indexOf("　") > 0) {
                    time = time.trim() ;
                    theDateTimeFormat = new SimpleDateFormat(
                            "yyyy-MM-ddhh:mm:ss");
                } else {
                    theDateTimeFormat = DATE_TIME_EXT_FORMAT;
                }

                return theDateTimeFormat.parse(time);
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
                return new Date(System.currentTimeMillis()-(long)1000*60*60*24*30);
            }
        }
    }

    /**
     * 取得指定日期的下一个星期
     *
     * @param date
     *            指定日期。
     * @return 指定日期的下一个星期
     */
    public static synchronized String getFormatString(String time) {
        SimpleDateFormat theDateTimeFormat = null;
        String returnStr = "";
        if (time == null || time.equals("")) {
            returnStr = DATE_TIME_EXTENDED_FORMAT.format(new Date());
//            System.out.println("格式之后的缺省时间："+returnStr);
            return returnStr;
        } else {
            {
                if (time.indexOf("-") > 0) {
                    theDateTimeFormat = DATE_TIME_EXT_FORMAT;
                } else if (time.indexOf("/") > 0) {
                    theDateTimeFormat = DATE_TIME_EXTENDED_FORMAT;
                } else if (time.indexOf("　") > 0) {
                    theDateTimeFormat = new SimpleDateFormat(
                            "yyyy-MM-dd　hh:mm:ss");
                } else {
                    theDateTimeFormat = DATE_TIME_EXT_FORMAT;
                }
                returnStr = theDateTimeFormat.format(getDate(time));
//                System.out.println("格式之前的时间："+time+"   格式之后的时间："+returnStr);
                return returnStr;
            }
        }
    }

    /**
     * 标准日期格式
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
            "MM/dd/yyyy");
    /**
     * 标准时间格式
     */
    private static final SimpleDateFormat DATE_TIME_FORMAT = new
            SimpleDateFormat(
                    "MM/dd/yyyy hh:mm");
    /**
     * 带时分秒的标准时间格式
     */
    private static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new
            SimpleDateFormat(
                    "yyyy/MM/dd hh:mm:ss");
    /**
     * 以横线格开的时间格式
     */
    private static final SimpleDateFormat DATE_TIME_EXT_FORMAT = new
            SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");

    /**
     * orA标准日期格式
     */
    private static final SimpleDateFormat orA_DATE_FORMAT = new
            SimpleDateFormat(
                    "yyyyMMdd");
    /**
     * orA标准时间格式
     */
    private static final SimpleDateFormat orA_DATE_TIME_FORMAT = new
            SimpleDateFormat(
                    "yyyyMMddhhmm");
    /**
     * 带时分秒的ORA标准时间格式
     */
    private static final SimpleDateFormat orA_DATE_TIME_EXTENDED_FORMAT = new
            SimpleDateFormat(
                    "yyyyMMddhhmmss");


    public static java.util.Date getDate(String time) {
    //        System.out.println("需要转换的时间：" + time);
        Date date = null;
        StringBuffer strb = new StringBuffer();
        for (int i = 0; time != null && i < time.length(); i++) {
            if (time.charAt(i) != '?' && time.charAt(i) != '　')
                strb.append(time.charAt(i));
            else
                strb.append(" ");
        }
        time = strb.toString() ;
        if(time!=null && time.matches("\\d{1,4}\\-[a-zA-Z]{1,}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("\\d{1,4} [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{1,}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("\\d{8}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("[a-zA-Z]{1,},\\d{2} \\d{2} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("[a-zA-Z]{3}, \\d{2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time); //
            //
        }else if(time!=null && time.matches("\\d{1},\\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("\\d{2}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}"))
        {
            date = getRegtime(time); //
            //
        }else if(time!=null && time.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{2}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{3}"))
        {
            date = getRegtime(time); //\\d{4}-\\d{1,2}-\\d{1,2}[a-zA-Z]{1}\\d{1,2}:\\d{1,2}:\\d{1,2}
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2}[a-zA-Z]{1}\\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("[a-zA-Z]{3,}, \\d{2} [a-zA-Z]{3,} \\d{4} \\d{1,2}:\\d{1,2} [a-zA-Z]{3}"))
        {
            //Wednesday, 20 September 2006 00:00 GMT
            date = getRegtime(time); //
            //
        }else if(time!=null && time.matches("[a-zA-Z]{3,}, \\d{2} [a-zA-Z]{3,} \\d{4} \\d{1,2}:\\d{1,2} [a-zA-Z]{3}"))
        {
            //Wednesday, 20 September 2006 00:00 GMT
            date = getRegtime(time); //
            //
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}-\\d{1,2}:\\d{1,2}"))
        {
            //Wednesday, 20 September 2006 00:00 GMT
            date = getRegtime(time); //
        }else if(time!=null && time.matches("\\d{4}--\\d{1,2}-\\d{1,2}-T\\d{1,2}: \\d{1,2}:\\d{1,2}:-\\d{1,2}:\\d{1,2}"))
        {
            //Wednesday, 20 September 2006 00:00 GMT
            date = getRegtime(time); //
            //\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {//Jul 31st 2006 10:08PM
            date = getRegtime(time); //
        }else if(time!=null && time.matches("[a-zA-Z]{3} \\w{4} \\d{4} \\d{1,2}:\\d{1,2}[a-zA-Z]{2}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,2}, \\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} -\\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[1]\\d{9}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{3,}, \\d{1,2} [a-zA-Z]{3,} \\d{4}, \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{2}ST"))
        {
            //澳大利亚时间
            date = getRegtime(time);
        }else if(time!=null && time.matches("\\d{4}-\\d{1,2}-\\d{1,2} [^\\x00-\\xff]{1,} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{3}, \\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2} [a-zA-Z]{3,}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{3}, \\d{1,2} [a-zA-Z]{3} \\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("\\d{4}[^\\x00-\\xff]{1,}\\d{1,2}[^\\x00-\\xff]{1,}\\d{1,2}[^\\x00-\\xff]{1,}"))
        {
            date = getRegtime(time); //
        }else if(time!=null && time.matches("[a-zA-Z]{1,}, \\d{1,2} [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z ]{1,} \\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} \\d{1,2}, \\d{4}, \\d{1,2}:\\d{1,2}[a-zA-Z]{2}"))
        {//
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,}, \\d{1,2} [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("\\d{4}[^\\x00-\\xff]{1,}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} \\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,}, \\d{1,2}, [a-zA-Z]{1,} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} -\\d{1,}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("\\d{1,2} [a-zA-Z]{3} \\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("\\d{1,2}/\\d{1,2}/\\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{3,} \\d{1,2} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}."))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{3,} \\d{1,2} \\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{3,}, [a-zA-Z]{3,} \\d{1,2} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} -\\d{1,}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("\\d{1,2} [a-zA-Z]{3} \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2}, \\d{4} \\d{1,2}:\\d{1,2} [a-zA-Z]{2}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} [a-zA-Z]{1,} \\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} [a-zA-Z]{2}  \\d{4}"))
        {
            date = getRegtime(time);
        }else if(time!=null && time.matches("[a-zA-Z]{1,} \\d{1,2}, \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}"))
        {
            date = getRegtime(time);
        }else if (time.indexOf("T") > 0 && time.indexOf("Z") > 0)
        {
            date = getGMTime(time); //
        }else if (time != null && time.indexOf("GMT") > 0) {
            date = getGMTime(time); //
        } else if (time != null && time.indexOf("0000") > 0) {
            date = getGMTime(time); //+00000800
        } else if (time != null && time.indexOf("0800") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.indexOf("+") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.indexOf(" -") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.indexOf("UTC") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.indexOf("EDT") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.indexOf("CDT") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.indexOf("CST") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.indexOf("PDT") > 0) {
            date = getGMTime(time); //+0800
        } else if (time != null && (time.trim().indexOf("AM") > 0 || time.trim().indexOf("PM")>0)) {
            date = getGMTime(time); //+0800
        } else if (time != null && time.length() > 0 &&
                   (Character.isLetter(time.charAt(0)) ||
                    Character.UnicodeBlock.of(time.charAt(0)) ==
                    Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)) {
            date = getGMTime(time); //+中文
        } else if (time != null && (time.indexOf("(") > 0 && time.indexOf(")") > 0)) {
            date = getGMTime(time); //+0800
        }else if (time != null && (time.indexOf(".") > 0 && time.indexOf("/") > 0)) {
            date = getGMTime(time); //+特殊的
        }else if (time != null && (time.length()<12) && time.indexOf("-")<0 && time.indexOf("/")<0) {

            date = getGMTime(time); //+特殊的
        }else if (time != null && (time.length()<12)) {
            date = getGMTime(time); //+特殊的
        } else {
            date = getFormatDate(time);
        }
//            System.out.println("传入的时间："+time +"  索引时间："+DATE_TIME_EXT_FORMAT.format(date));
        return date;
    }

}
