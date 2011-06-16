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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import com.jxva.graph.GraphException;

/**
 * 图形处理高质量算法
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-27 14:52:43 by Jxva
 */
public class HighQualityGraph extends AbstractGraph implements ImageObserver {

	public BufferedImage resize(BufferedImage srcBi,double width,double height)throws GraphException {		
		BufferedImage bi = new BufferedImage((int)width,(int)height,BufferedImage.TYPE_INT_RGB);
		bi.getGraphics().drawImage(srcBi,0,0,(int)width,(int)height,this);
		return bi;
	}
	
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return ((infoflags & ALLBITS) == 0);
	}
}
