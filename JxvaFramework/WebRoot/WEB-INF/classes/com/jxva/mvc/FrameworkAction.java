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
package com.jxva.mvc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.jxva.dao.DAOFactory;
import com.jxva.entity.Encoding;
import com.jxva.entity.HtmlBuilder;
import com.jxva.entity.Jscape;
import com.jxva.entity.Path;
import com.jxva.entity.RandomCode;
import com.jxva.entity.VerifyCode;
import com.jxva.mvc.entity.View;
import com.jxva.plugin.Plugin;
import com.jxva.plugin.PluginException;
import com.jxva.plugin.SysPlugin;

/**
 * 
 * Framework Visual Monitor and Manage
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:17:53 by Jxva
 */
public class FrameworkAction extends Action{
	
	private static final String START_TIME=new Timestamp(System.currentTimeMillis()).toString().substring(0,19);
	
	public String execute(){
		String urlExt=getUrlExt();
		StringBuilder sb=new StringBuilder();
		sb.append(" <a href='framework!dbPool").append(urlExt).append("'>Database Connection Pool</a> ");
		//sb.append(" <a href='framework!validCode").append(urlExt).append("'>Display ValidCode</a> ");
		//sb.append(" <a href='framework!reInitialize").append(urlExt).append("'>ReInitialize Configuration</a> ");
		return View.getInfoWithHeaderAndFooter(sb.toString());
	}
	
	public String dbPool(){
		String s="               startTime: "+START_TIME+'\n'+DAOFactory.getInstance().getConnectionProvider().getStatistics().toString();
		return View.getInfoWithHeaderAndFooter(s);
	}
	
	@Perform
	public void htmlBuilder(){
		HtmlBuilder.build(Jscape.unescape(request.getParameter("url")),new File(Path.APP_PATH+SysPlugin.CACHE_PATH.substring(1)+request.getParameter("file")),Encoding.UTF_8);
	}
	
	public String reInitialize() throws PluginException{
		new Plugin().reInitialize();
		return "reInitialize success";
	}
	
	@Perform
	public void verifyCodeNumber() throws IOException{
		response.setHeader("Pragma","No-cache");  
		response.setHeader("Cache-Control","no-cache");  
		response.setDateHeader("Expires", 0);  
		response.reset();  
		response.setContentType("image/jpeg");
		RandomCode randomCode=new RandomCode();
		ImageIO.write(randomCode.createNumber(),"JPEG",response.getOutputStream());  
		session.setAttribute("verifyCode",randomCode.getFlagInSession());
	}
	
	@Perform
	public void verifyCodeAlpha() throws IOException{
		response.setHeader("Pragma","No-cache");  
		response.setHeader("Cache-Control","no-cache");  
		response.setDateHeader("Expires", 0);  
		response.reset();  
		response.setContentType("image/jpeg");
		RandomCode randomCode=new RandomCode();
		ImageIO.write(randomCode.createAlpha(),"JPEG",response.getOutputStream());  
		session.setAttribute("verifyCode",randomCode.getFlagInSession());
	}
	
	@Perform
	public void verifyCodeChinese() throws IOException{
		response.setHeader("Pragma","No-cache");  
		response.setHeader("Cache-Control","no-cache");  
		response.setDateHeader("Expires", 0);  
		response.reset();  
		response.setContentType("image/jpeg");
		RandomCode randomCode=new RandomCode();
		ImageIO.write(randomCode.createChinese(),"JPEG",response.getOutputStream());  
		session.setAttribute("verifyCode",randomCode.getFlagInSession());
	}
	
	@Perform
	public void verifyOldCode(){
		VerifyCode.create(request, response,"verifyCode");
	}
	
	@Perform
	public void verifyCode(){
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        //图片宽高
        int width = 95;
        int height = 38;
        BufferedImage bimage = null;
        Graphics2D g = null;
        String value;
        try{
            bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        
            Random rand=new Random(System.currentTimeMillis());
            g = bimage.createGraphics();

            // 设置随机背景色
            Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
            //Color color = new Color(7, 147,77);

            // 填充深色背景
            g.setColor(color.darker());
            g.fillRect(0, 0, width, height);
            
            // 设置字体
            g.setFont(new Font("arial",Font.BOLD,36));
            
            // 随机生成字符,根据截取的位数决定产生的数字
            value = UUID.randomUUID().toString().replace("-","").substring(0,4);

            int w = (g.getFontMetrics()).stringWidth(value);
            int d = (g.getFontMetrics()).getDescent();
            int a = (g.getFontMetrics()).getMaxAscent();
            
            int x = 0, y =0;
            
            // 设置随机线条,10这个数值越大图片中线条越稀蔬
            for (int i = 0; i < height; i += 10){
               g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
               g.drawLine(x, y + i, width, y+i);
            }

            // reset x and y
            x=0;
            y=0;
            
            //设置随机线条,10这个数值越大图片中线条越稀蔬
            for (int i = 0; i < height; i += 10){
               g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
               g.drawLine(x, y + d - i, width + w, height + d - i);
            }
            
            //展示验证码中颜色,随机
            g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)).brighter());
            
            // 设置文字出现位置为中央
            x = width/2 - w/2;
            y = height/2 + a/2 - 2;
            
            //文字变形设置
            AffineTransform fontAT = new AffineTransform();
            int xp = x-2;
            // 每个文字都变形
            for (int c=0;c<value.length();c++){
                // 产生弧度
                int rotate = rand.nextInt(20);
                fontAT.rotate(rand.nextBoolean() ? Math.toRadians(rotate) : -Math.toRadians(rotate/2));
                Font fx = new Font("arial", Font.BOLD, 36).deriveFont(fontAT);
                g.setFont(fx);
                String ch = String.valueOf(value.charAt(c));
                int ht = rand.nextInt(3);
                // 打印字并移动位置
                g.drawString(ch, xp, y + (rand.nextBoolean()?-ht:ht));
                //移动指针.
                xp+=g.getFontMetrics().stringWidth(ch) + 2;
            }
            // 打印出图片    		
    		OutputStream os= response.getOutputStream();
    		ImageIO.write(bimage, "png",os);
    		os.flush();
    		os.close();
            //设置进当前会话
            session.setAttribute("verifyCode", value);

        }catch (IOException ex){
            ex.printStackTrace();
        }finally{
            if (g != null){
                g.dispose();
            }
        }
	}
	
	private String getUrlExt(){
		String url=request.getRequestURL().toString();
		return url.substring(url.lastIndexOf('.'));
	}
}
