package com.ztemt.portal.util;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class JxlUtil {
	
	public static void main(String[] args) throws BiffException, IOException{
		File file=new File("c:/test.xls");
		String[][] s =readExcel(file,0);
		for(int i=0;i<s.length;i++){//行
			for(int j=0;j<s[i].length;j++){//列
				System.out.println(s[i][j]);
			}
		}
	}
	
	/**
	 * 读取excel
	 * @param file
	 * @param sheetIndex
	 * @throws IOException
	 * @throws BiffException
	 * @return String[][] 行/列
	 */
	public static String[][] readExcel(File file,int sheetIndex) throws BiffException, IOException {
		WorkbookSettings setting = new WorkbookSettings();
		// 避免乱码的设置
		//java.util.Locale locale = new java.util.Locale("zh", "CN");
		//setting.setLocale(locale);
		//setting.setEncoding("ISO-8859-1");
		Workbook book = Workbook.getWorkbook(file, setting);
		Sheet sheet = book.getSheet(sheetIndex);
		int rowNum=sheet.getRows();
		int colNum=sheet.getColumns();
		String[][] s=new String[rowNum][colNum];
		for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {// Excel第一行为表头,因此J初值设为1
			for (int colIndex = 0; colIndex < colNum; colIndex++) {// 只需从Excel中取出2列
				s[rowIndex][colIndex]=sheet.getCell(colIndex, rowIndex).getContents();
			}
		}
		// 【问题：如果在实际部署的时候没有写下面这句是否会导致不断消耗掉服务器的内存？
		// jxl里面有个ReadWrite.java没有关闭读的，只关闭了写的】
		book.close();
		return s;
	}
}
