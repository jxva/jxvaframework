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

import com.jxva.graph.GraphException;

/**
 * 图形处理平滑度算法
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2008-11-28 18:10:53 by Jxva
 */
public class SmoothGraph extends AbstractGraph {

	/**
	 * 图形缩放方法,由子类以不同策略实现
	 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
	 * 优先级比速度高 生成的图片质量比较好 但速度慢
	 * @param src	源图形文件
	 * @param dest  目标图形文件
	 * @param width 目标图形宽度
	 * @param height 目标图形高度
	 * @return 缩放成功:true 缩放失败:false
	 * @throws GraphException
	 */
	public BufferedImage resize(BufferedImage srcBi,double width,double height)throws GraphException {
		BufferedImage destBi = new BufferedImage((int) width, (int) height,BufferedImage.TYPE_INT_RGB);
		destBi.getGraphics().drawImage(srcBi.getScaledInstance((int) width, (int) height,Image.SCALE_SMOOTH), 0, 0, null);
		return destBi;
	}
}
