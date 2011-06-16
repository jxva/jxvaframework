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
 * 图形文件处理策略,不同的策略对应不同的图片质量及处理速度与执行性能
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-28 11:02:26 by Jxva
 */
public interface GraphStrategy {
	
	/**
	 * 图形缩放方法,由子类以不同策略实现
	 * @param srcBi	源图形文件
	 * @param width 目标图形宽度
	 * @param height 目标图形高度
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage resize(BufferedImage srcBi, double width,double height)throws GraphException;
	
}
