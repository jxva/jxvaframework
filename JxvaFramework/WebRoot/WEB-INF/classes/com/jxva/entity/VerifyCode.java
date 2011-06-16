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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 彩色验证码生成器
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-12-19 16:08:56 by Jxva
 */
public abstract class VerifyCode {
	
	/**
	 * 生成附加码
	 * @param request
	 * @param response
	 */
	public static void create(HttpServletRequest request,HttpServletResponse response,String flagInSession)throws EntityException{
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.reset();
		response.setContentType("image/jpeg");
		Color bg=Color.WHITE;
		Color border=new Color(163,213,255);
		Font font=new Font("Times New Roman",Font.BOLD,18);
		Color fontColor=new Color(0, 138,255);
		try {
			writeImage(flagInSession,request,response,bg,border,font,fontColor,60,20);
		} catch (IOException e) {
			throw new EntityException(e);
		}
	}
	
	/**
	 * 输出随机图片到指定输出流
	 * @param flagInSession
	 * @param request
	 * @param response
	 * @param bgColor
	 * @param borderColor
	 * @param font
	 * @param width
	 * @param height
	 * @throws IOException 
	 */
	private static void writeImage(String flagInSession,HttpServletRequest request,HttpServletResponse response,Color bgColor,Color borderColor,Font font,Color fontColor,int width,int height) throws IOException {
		//创建图片
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();//获取图形上下文
		
		Random random = new Random();//生成随机类
		graphics.setColor(bgColor);//设定背景色
		graphics.fillRect(0, 0, width, height);
		graphics.setFont(font);//设定字体
	
		//画边框
		graphics.setColor(borderColor);
		graphics.drawRect(0,0,width-1,height-1);
		//画边框 end
			
		//随机产生155条干扰线,使图象中的认证码不易被其它程序探测到
		graphics.setColor(getRandColor(200, 255));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width-4);
			int y = random.nextInt(height-4);
			graphics.drawLine(x, y, x + 2, y + 2);
		}//随机产生155条干扰线,使图象中的认证码不易被其它程序探测到 end
	
		//取随机产生的认证码(4位数字),显示到图象中
		StringBuilder validateCode =new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			validateCode.append(rand);
			graphics.setColor(fontColor);
			int fontSize=font.getSize();
			graphics.drawString(rand, (fontSize-3) * i + 4, font.getSize()-2);
		}//取随机产生的认证码(4位数字),显示到图象中 end
		HttpSession session=request.getSession(true);
		session.setAttribute(flagInSession,validateCode.toString());//将认证码存入SESSION
		graphics.dispose();//图象生效
	
		//输出图象到页面
		OutputStream os= response.getOutputStream();
		ImageIO.write(image, "JPEG",os);
		os.flush();
		os.close();
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param min
	 * @param max
	 * @return Color
	 */
	private static Color getRandColor(int min, int max) {
		if (min > 255) {
			min = 255;
		}
		if (max > 255) {
			max = 255;
		}
		Random random = new Random();
		int r = min + random.nextInt(max - min);
		int g = min + random.nextInt(max - min);
		int b = min + random.nextInt(max - min);
		return new Color(r, g, b);
	}
}