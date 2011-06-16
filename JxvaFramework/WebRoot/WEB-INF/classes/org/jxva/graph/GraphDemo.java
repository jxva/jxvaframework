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
package org.jxva.graph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jxva.graph.Graph;
import com.jxva.graph.GraphFactory;
import com.jxva.graph.Position;
import com.jxva.graph.watermark.WaterMarkLogo;
import com.jxva.graph.watermark.WaterMarkText;


/**
 *
 *	Algorithm       ExcuteTime(ms)
 *	High Quality:   1328
 *	Filter:         7500
 *	Compress:       2750
 *	Affine:         1094
 *	Smooth:         2766
 *	Affine:         1078
 *	Compress:       2719
 *	Smooth:         2734
 *	High Quality:   1078
 *	Filter:         7344
 *	Smooth:         2687
 *	Filter:         7391
 *	Affine:         1078
 *	Compress:       2750
 *	High Quality:   1109
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-27 14:39:51 by Jxva
 */
public class GraphDemo {

	private static BufferedImage srcBi;
	private static BufferedImage logo;
	
	static{
		try {
			srcBi	=ImageIO.read(new File("E:/test/2.jpg"));
			logo	=ImageIO.read(new File("E:/test/1/jxva.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static File getDest(String str){
		return new File("E:/test/1/2_"+str+".jpg");
	}

	public static void main(String[] args) {
		
		//graph.ioWrite(graph.zoomUnStretch(srcBi,100,400),getDest("ok"),1);
		//graph.ioWrite(graph.zoomUnStretch(srcBi,400,100),getDest("ok2"),1);
		//withHighQuality();
		//withCompress();

	}
	
	public static void waterMark(){
		Graph graph=GraphFactory.createAffineGraph();
		WaterMarkLogo wML=new WaterMarkLogo();
		wML.setLogo(logo);
		wML.setOffsetX(10);
		wML.setOffsetY(10);
		wML.setPosition(Position.CENTER_MIDDLE);
		graph.ioWrite(graph.waterMarkLogo(srcBi,wML),getDest("okLogo"));
		
		WaterMarkText wMT=new WaterMarkText();
		wMT.setBgColor(Color.RED);
		wMT.setText("中国");
		wMT.setOffsetX(10);
		wMT.setOffsetY(10);
		wMT.setPosition(Position.LEFT_TOP);
		wMT.setBgColor(Color.WHITE);
		graph.ioWrite(graph.waterMarkText(srcBi,wMT),getDest("okText"));
	}
	
	public static void test(){
		System.out.printf("%-15s %s\n","Algorithm","ExcuteTime(ms)");
		
		System.out.printf("%-15s %s\n","High Quality:",withHighQuality());
		System.out.printf("%-15s %s\n","Filter:",withFilter());
		System.out.printf("%-15s %s\n","Compress:",withCompress());
		System.out.printf("%-15s %s\n","Affine:",withAffine());
		System.out.printf("%-15s %s\n","Smooth:",withSmooth());

		System.out.printf("%-15s %s\n","Affine:",withAffine());
		System.out.printf("%-15s %s\n","Compress:",withCompress());
		System.out.printf("%-15s %s\n","Smooth:",withSmooth());
		System.out.printf("%-15s %s\n","High Quality:",withHighQuality());
		System.out.printf("%-15s %s\n","Filter:",withFilter());
		
		System.out.printf("%-15s %s\n","Smooth:",withSmooth());
		System.out.printf("%-15s %s\n","Filter:",withFilter());
		System.out.printf("%-15s %s\n","Affine:",withAffine());
		System.out.printf("%-15s %s\n","Compress:",withCompress());
		System.out.printf("%-15s %s\n","High Quality:",withHighQuality());
	}
	
	public static long withHighQuality(){
		long s=System.currentTimeMillis();
		Graph graph=GraphFactory.createHighQualityGraph();
		graph.ioWrite(graph.cutToSquare(srcBi,200),getDest("hightQuality1"),0.6F);
		graph.ioWrite(graph.zoomToWidth(srcBi,200),getDest("hightQuality2"),0.8F);
		graph.ioWrite(graph.zoomToHeight(srcBi,200),getDest("hightQuality3"),1);
		return System.currentTimeMillis()-s;
	}
	
	public static long withFilter(){
		long s=System.currentTimeMillis();
		Graph graph=GraphFactory.createFilterGraph();
		graph.ioWrite(graph.cutToSquare(srcBi,200),getDest("filter1"));
		graph.ioWrite(graph.zoomToWidth(srcBi,200),getDest("filter2"));
		graph.ioWrite(graph.zoomToHeight(srcBi,200),getDest("filter3"));
		return System.currentTimeMillis()-s;
	}
	
	public static long withCompress(){
		long s=System.currentTimeMillis();
		Graph graph=GraphFactory.createCompressGraph();
		graph.ioWrite(graph.cutToSquare(srcBi,200),getDest("compress122"));
		graph.ioWrite(graph.zoomToWidth(srcBi,200),getDest("compress222"));
		graph.ioWrite(graph.zoomToHeight(srcBi,200),getDest("compress322"));
		return System.currentTimeMillis()-s;
	}
	
	public static long withAffine(){
		long s=System.currentTimeMillis();
		Graph graph=GraphFactory.createAffineGraph();
		graph.ioWrite(graph.cutToSquare(srcBi,200),getDest("affine1"));
		graph.ioWrite(graph.zoomToWidth(srcBi,200),getDest("affine2"));
		graph.ioWrite(graph.zoomToHeight(srcBi,200),getDest("affine3"));
		//graph.rotate(src,getDest("affine4"),20,null);
		//graph.zoom(src,getDest("affine5"),0.2);
		//graph.zoom(src,getDest("affine6"),0.2,0.1);
		return System.currentTimeMillis()-s;
	}
	
	public static long withSmooth(){
		long s=System.currentTimeMillis();
		Graph graph=GraphFactory.createCompressGraph();
		graph.ioWrite(graph.cutToSquare(srcBi,200),getDest("smooth1"));
		graph.ioWrite(graph.zoomToWidth(srcBi,200),getDest("smooth2"));
		graph.ioWrite(graph.zoomToHeight(srcBi,200),getDest("smooth3"));
		return System.currentTimeMillis()-s;
	}
}
