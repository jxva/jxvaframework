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
package com.jxva.graph.strategy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.ImageObserver;
import java.awt.image.Kernel;

import com.jxva.graph.GraphException;

/**
 * 图形处理压缩算法
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2008-11-27 15:14:13 by Jxva
 */
public class CompressGraph extends AbstractGraph  implements ImageObserver{
	
	public BufferedImage resize(BufferedImage srcBi,double width,double height)throws GraphException {	
		int imageWidth = srcBi.getWidth(this);
		int imageHeight = srcBi.getHeight(this);
		float scale = getRatio(imageWidth, imageHeight, 200, 150);
		imageWidth = (int) (scale * imageWidth);
		imageHeight = (int) (scale * imageHeight);
		Image image = srcBi.getScaledInstance(imageWidth, imageHeight,Image.SCALE_AREA_AVERAGING);
		// Make a BufferedImage from the Image.
		BufferedImage bi = new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white,null);
		g2.dispose();
		float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,-0.125f, -0.125f, -0.125f, -0.125f };
		Kernel kernel = new Kernel(3, 3, kernelData2);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		return cOp.filter(bi, null);
			
	}

	public float getRatio(int width, int height, int maxWidth,int maxHeight) {
		float radtio = 1.0f;
		float widthRatio = (float) maxWidth / width;
		float heightRatio = (float) maxHeight / height;
		if (widthRatio < 1.0 || heightRatio < 1.0) {
			radtio = widthRatio <= heightRatio ? widthRatio : heightRatio;
		}
		return radtio;
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y,int width, int height) {
		return((infoflags & ALLBITS) == 0);
	}	
}
