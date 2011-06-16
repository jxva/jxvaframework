package study;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jxva.util.DateUtil;

public class DateTest {
	public static void main(String[] args) throws ParseException {
		System.out.println(t("2009-03-02", "yyyy-MM-dd"));
		System.out.println(t("2009-03-02 12:12:12", "yyyy-MM-dd HH:mm:ss"));
		System.out.println(t("2009-03-02 12:12:12", "yyyy-MM-dd"));
		System.out.println(t("2009-03-02", "yyyy-MM-dd"));
		System.out.println(t("2009-03-04 12:14:15.0", "yyyy年MM月dd日 HH时mm分ss秒"));
		System.out.println(t("12:14:15", "HH时mm分ss秒"));
	}

	public static String t(String d, String f) throws ParseException {
		DateFormat df = new SimpleDateFormat(f);
		Date dd = DateUtil.parse(d);
		System.out.println(dd);
		return df.format(dd);
	}
}
