package net.jxva.house;

import java.text.DecimalFormat;



/**
 *	等额本金计算
 *  计算器: http://bj.house.sina.com.cn/bjjsq/index.html
 *  
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2010-02-05 11:25:59 by Jxva
 */
public class Principal {
	
	public static final double YEAR_RATE=5.94;
	public static final double MONTH_RATE=YEAR_RATE/12;
	public static final int YEAR=20;
	public static final int TOTAL=450000;
	public static final int MONTHS=YEAR*12;
	public static final int MONTHLY_PAYMENT=TOTAL/MONTHS;
	
	public static final DecimalFormat  format=new DecimalFormat("#0.00");

	public static void main(String[] args) {

		int mount=TOTAL;
		double total_rate=0;
		
		System.out.printf("%-20s%-20s%-20s%-20s\n","月数","本金","利息","小计");
		for(int i=1;i<MONTHS+1;i++){
			double monthly_rate=mount*MONTH_RATE/100;
			String mr=format.format(monthly_rate);
			System.out.printf("%-22s%-11d%-10s%-13s\n",i+"月",MONTHLY_PAYMENT,mr,format.format(MONTHLY_PAYMENT+monthly_rate));
			mount-=MONTHLY_PAYMENT;
			total_rate+=monthly_rate;
		}
		
		System.out.printf("\n%-20s %-20s\n","总还款:"+(TOTAL+total_rate),"利息:"+total_rate);
		
	}

}
