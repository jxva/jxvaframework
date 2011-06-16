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

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.jxva.graph.GraphException;

/**
 * 图形处理仿射算法
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-28 10:48:45 by Jxva
 */
public class AffineGraph extends AbstractGraph{
	
	public BufferedImage resize(BufferedImage srcBi,double width,double height)throws GraphException {
		AffineTransform at = new AffineTransform();
		at.setToScale(width/srcBi.getWidth(),height/srcBi.getHeight());
		AffineTransformOp atOp = new AffineTransformOp(at,renderHints);
		return atOp.filter(srcBi, new BufferedImage((int)width, (int)height,srcBi.getType()));
	}	
}
