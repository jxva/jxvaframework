package org.jxva.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.jxva.util.CharUtil;
import com.jxva.util.ChineseUtil;
import com.jxva.util.RandomUtil;

public class RandomCode {

	private String flagInSession;
	
	public String getFlagInSession() {
		return flagInSession;
	}
	
	public Color getRandColor(int min, int max) { // 给定范围获得随机颜色 
		Random random =new Random();
		if (min > 255)
			min = 255;
		if (max > 255)
			max = 255;
		int r = min + random.nextInt(max - min);
		int g = min + random.nextInt(max - min);
		int b = min + random.nextInt(max - min);
		return new Color(r, g, b);
	}

	public BufferedImage createNumber(){
		return createImage(true);
	}
	
	public BufferedImage createAlpha(){
		return createImage(false);
	}
	
	public BufferedImage createImage(boolean number) {
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(100, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		// 画边框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(number?random.nextInt(10):RandomUtil.getRandomString(CharUtil.UPPER_CHAR_TABLE,1));
			sb.append(rand);
			// 将认证码显示到图象中
			g.setColor(new Color(20 + random.nextInt(110),
			20 + random.nextInt(110),
			20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 13 * i + 6, 17);
		}
		g.dispose();
		flagInSession=sb.toString();
		return image;
	}

	public BufferedImage createChinese() {
		int width = 106, height = 28;
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// 设定图像背景色(因为是做背景，所以偏淡)
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 备选字体 宋体,新宋体,黑体,楷体,隶书
		String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53","\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
		int fontTypesLength = fontTypes.length;
		// 在图片背景上增加噪点
		g.setColor(getRandColor(160, 200));
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		for (int i = 0; i < 6; i++) {
			g.drawString("*********************************************", 0,
			5 * (i + 2));
		}
		Random random = new Random();
		// 取随机产生的认证码(4个汉字)
		// 保存生成的汉字字符串
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String rand = RandomUtil.getRandomString(ChineseUtil.CHINESE_TABLE,1);
			sb.append(rand);
			g.setColor(getRandColor(10, 150));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],Font.BOLD, 18 + random.nextInt(6)));
			g.drawString(rand, 24 * i +3+ random.nextInt(8), 22);
		}
		g.dispose();
		flagInSession=sb.toString();
		return image;
	}
}