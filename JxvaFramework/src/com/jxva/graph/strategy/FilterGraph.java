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

import java.awt.image.BufferedImage;

import com.jxva.graph.GraphException;

/**
 * 图形处理滤波算法
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-27 14:13:19 by Jxva
 */
public class FilterGraph extends AbstractGraph {

	public BufferedImage resize(BufferedImage srcBi,double width,double height)throws GraphException {
		return zoomToSize(srcBi,(int)width,(int)height);
	}
	
	
	private int nDots;
	private int nHalfDots;
	private double[] contrib;
	private double[] normContrib;
	private double[] tmpContrib;

	public BufferedImage zoomToSize(BufferedImage srcBi, int w, int h) {
		final double support = (double) 3.0;
		int width = srcBi.getWidth();
		int height = srcBi.getHeight();
		
		//决定图像尺寸
		double scaleH = (double) w / (double) width;
		double scaleV = (double) h / (double) height;
		// 需要判断一下scaleH,scaleV,不做放大操作
		if (scaleH >= 1.0 && scaleV >= 1.0){
			return srcBi;
		}
		
		//假设:同样的水平和垂直缩放因子
		nHalfDots = (int) ((double) width * support / (double) w);
		nDots = nHalfDots * 2 + 1;
		try {
			contrib = new double[nDots];
			normContrib = new double[nDots];
			tmpContrib = new double[nDots];
		} catch (Exception e) {
			System.out.println("init contrib,normContrib,tmpContrib" + e);
		}
		int center = nHalfDots;
		contrib[center] = 1.0;

		double weight = 0.0;
		int i = 0;
		for (i = 1; i <= center; i++) {
			contrib[center + i] = lanczos(i, width,w, support);
			weight += contrib[center + i];
		}

		for (i = center - 1; i >= 0; i--) {
			contrib[i] = contrib[center * 2 - i];
		}

		weight = weight * 2 + 1.0;

		for (i = 0; i <= center; i++) {
			normContrib[i] = contrib[i] / weight;
		}

		for (i = center + 1; i < nDots; i++) {
			normContrib[i] = normContrib[center * 2 - i];
		}
		
		BufferedImage pbOut = HorizontalFiltering(srcBi, w);
		BufferedImage pbFinalOut = VerticalFiltering(pbOut, h);
		return pbFinalOut;
	}
	


	private double lanczos(int i, int inWidth, int outWidth, double support){
		final double PI = (double) 3.14159265358978;
		double x = (double) i * (double) outWidth / (double) inWidth;
		return Math.sin(x * PI) / (x * PI) * Math.sin(x * PI / support)/ (x * PI / support);
	}

	/**
	 * 处理边缘
	 * @param start
	 * @param stop
	 */
	private void CalTempContrib(int start, int stop) {
		double weight = 0;
		int i = 0;
		for (i = start; i <= stop; i++) {
			weight += contrib[i];
		}
		for (i = start; i <= stop; i++) {
			tmpContrib[i] = contrib[i] / weight;
		}
	} 

	private int GetRedValue(int rgbValue) {
		return (rgbValue & 0x00ff0000)>>16;
	}

	private int GetGreenValue(int rgbValue) {
		return (rgbValue & 0x0000ff00)>>8;
	}

	private int GetBlueValue(int rgbValue) {
		return rgbValue & 0x000000ff;
	}

	private int ComRGB(int redValue, int greenValue, int blueValue) {
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}

	/**
	 * 行水平滤波
	 * @param bufImg
	 * @param startX
	 * @param stopX
	 * @param start
	 * @param stop
	 * @param y
	 * @param pContrib
	 * @return
	 */
	private int HorizontalFilter(BufferedImage bufImg, int startX, int stopX,
			int start, int stop, int y, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;

		for (i = startX, j = start; i <= stopX; i++, j++) {
			valueRGB = bufImg.getRGB(i, y);

			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}

		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen),Clip((int) valueBlue));
		return valueRGB;

	}

	/**
	 * 图片水平滤波
	 * @param bufImage
	 * @param iOutW
	 * @return
	 */
	private BufferedImage HorizontalFiltering(BufferedImage bufImage, int iOutW) {
		int dwInW = bufImage.getWidth();
		int dwInH = bufImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iOutW, dwInH,BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < iOutW; x++) {

			int startX;
			int start;
			int X = (int) (((double) x) * ((double) dwInW) / ((double) iOutW) + 0.5);
			int y = 0;

			startX = X - nHalfDots;
			if (startX < 0) {
				startX = 0;
				start = nHalfDots - X;
			} else {
				start = 0;
			}

			int stop;
			int stopX = X + nHalfDots;
			if (stopX > (dwInW - 1)) {
				stopX = dwInW - 1;
				stop = nHalfDots + (dwInW - 1 - X);
			} else {
				stop = nHalfDots * 2;
			}

			if (start > 0 || stop < nDots - 1) {
				CalTempContrib(start, stop);
				for (y = 0; y < dwInH; y++) {
					value = HorizontalFilter(bufImage, startX, stopX, start,
							stop, y, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (y = 0; y < dwInH; y++) {
					value = HorizontalFilter(bufImage, startX, stopX, start,
							stop, y, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		return pbOut;
	}

	/**
	 * 行垂直滤波
	 * @param pbInImage
	 * @param startY
	 * @param stopY
	 * @param start
	 * @param stop
	 * @param x
	 * @param pContrib
	 * @return
	 */
	private int VerticalFilter(BufferedImage pbInImage, int startY, int stopY,
			int start, int stop, int x, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;

		for (i = startY, j = start; i <= stopY; i++, j++) {
			valueRGB = pbInImage.getRGB(x, i);
			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}

		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen),Clip((int) valueBlue));
		return valueRGB;
	}

	/**
	 * 图片垂直滤波
	 * @param pbImage
	 * @param iOutH
	 * @return
	 */
	private BufferedImage VerticalFiltering(BufferedImage pbImage, int iOutH) {
		int iW = pbImage.getWidth();
		int iH = pbImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iW, iOutH,BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < iOutH; y++) {

			int startY;
			int start;
			int Y = (int) (((double) y) * ((double) iH) / ((double) iOutH) + 0.5);

			startY = Y - nHalfDots;
			if (startY < 0) {
				startY = 0;
				start = nHalfDots - Y;
			} else {
				start = 0;
			}

			int stop;
			int stopY = Y + nHalfDots;
			if (stopY > (int) (iH - 1)) {
				stopY = iH - 1;
				stop = nHalfDots + (iH - 1 - Y);
			} else {
				stop = nHalfDots * 2;
			}

			if (start > 0 || stop < nDots - 1) {
				CalTempContrib(start, stop);
				for (int x = 0; x < iW; x++) {
					value = VerticalFilter(pbImage, startY, stopY, start, stop,
							x, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (int x = 0; x < iW; x++) {
					value = VerticalFilter(pbImage, startY, stopY, start, stop,
							x, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}

		}
		return pbOut;
	}
	
	/**
	 * 拾色调整
	 * @param x
	 * @return
	 */
	private int Clip(int x){
		if (x<0)return 0;
		if (x>255)return 255;
		return x;
	}
}
