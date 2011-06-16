package net.jxva.house;

import java.text.DecimalFormat;

/**
 *	等额本息计算
 *
 *  计算器: http://bj.house.sina.com.cn/bjjsq/index.html
 *  
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2010-02-05 14:17:22 by Jxva
 */
public class Rate {

	public static final double YEAR_RATE=0.0594;
	public static final double MONTH_RATE=YEAR_RATE/12;
	public static final int YEAR=20;
	public static final int TOTAL=450000;
	public static final int MONTHS=YEAR*12;
	public static final int MONTHLY_PAYMENT=TOTAL/MONTHS;
	
	public static final DecimalFormat  format=new DecimalFormat("#0.00");
		
	public static void main(String[] args) {
		double monthly=(TOTAL*MONTH_RATE*Math.pow(1+MONTH_RATE,MONTHS))/(Math.pow(1+MONTH_RATE,MONTHS)-1);
		System.out.println("每月还款:"+monthly);
		System.out.println("总共支付:"+(monthly*MONTHS));
		System.out.println("总利息数:"+(monthly*MONTHS-TOTAL));
	}

}
