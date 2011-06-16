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
package com.jxva.graph.watermark;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2008-11-28 17:52:14 by Jxva
 */
public class WaterMarkMaker implements ImageObserver {

	public BufferedImage make(BufferedImage srcBi,WaterMarkLogo waterMarkLogo) {
		int srcWidth = srcBi.getWidth();
		int srcHeight = srcBi.getHeight();
		int logoWidth = waterMarkLogo.getLogo().getWidth();
		int logoHeight = waterMarkLogo.getLogo().getHeight();

		BufferedImage tag = new BufferedImage(srcWidth, srcHeight,BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(srcBi, 0, 0, srcWidth, srcHeight, this);// 绘制大图
		int x = 0;
		int y = 0;
		int offsetX=waterMarkLogo.getOffsetX();
		int offsetY=waterMarkLogo.getOffsetY();
		switch (waterMarkLogo.getPosition()) {
		case LEFT_TOP:
			x = offsetX;
			y = offsetY;
			break;
		case LEFT_BOTTOM:
			x = offsetX;
			y = srcHeight - offsetY - logoHeight;
			break;
		case RIGHT_TOP:
			x = srcWidth - offsetX - logoWidth;
			y = offsetY;
			break;
		case RIGHT_BOTTOM:
			x = srcWidth - offsetX - logoWidth;
			y = srcHeight - offsetY - logoHeight;
			break;
		case CENTER_MIDDLE:
			x = (srcWidth - logoWidth) / 2 - offsetX;
			y = (srcHeight - logoHeight) / 2 - offsetY;
			break;
		}
		tag.getGraphics().drawImage(waterMarkLogo.getLogo(), x, y, logoWidth, logoHeight, this);// 绘制logo
		return tag;
	}

	public BufferedImage make(BufferedImage srcBi,WaterMarkText waterMarkText) {
		int srcWidth = srcBi.getWidth();
		int srcHeight = srcBi.getHeight();
		BufferedImage destBi = new BufferedImage(srcWidth, srcHeight,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = destBi.createGraphics();
		g.setColor(waterMarkText.getTextColor());
		g.setBackground(Color.white);
		g.drawImage(srcBi, 0, 0, null);
		int x = 0;
		int y = 0;
		int offsetX=waterMarkText.getOffsetX();
		int offsetY=waterMarkText.getOffsetY();
		switch (waterMarkText.getPosition()) {
		case LEFT_TOP:
			x = offsetX;
			y = offsetY;
			break;
		case LEFT_BOTTOM:
			x = offsetX;
			y = srcHeight - offsetY;
			break;
		case RIGHT_TOP:
			x = srcWidth - offsetX;
			y = offsetY;
			break;
		case RIGHT_BOTTOM:
			x = srcWidth - offsetX;
			y = srcHeight - offsetY;
			break;
		case CENTER_MIDDLE:
			x = srcWidth / 2 - offsetX;
			y = srcHeight/ 2 - offsetY;
			break;
		}
		g.drawString(waterMarkText.getText(),x,y);
		g.dispose();
		return destBi;
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y,int width, int height) {
		return ((infoflags & ALLBITS) == 0);
	}
}
