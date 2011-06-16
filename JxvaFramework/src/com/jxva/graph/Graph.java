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
package com.jxva.graph;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import com.jxva.graph.watermark.WaterMarkLogo;
import com.jxva.graph.watermark.WaterMarkText;

/**
 * 高质量图形切割,缩放,旋转接口
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-11-27 09:10:44 by Jxva
 */
public interface Graph {
	
	/**
	 * 图形分割,将图形以rows行cols列进行分割<br>
	 * 保存的文件名以rowno_colno开头<br>
	 * 如: 4-3.jpg,表示分割图片中第4行第3列的图片
	 *  
	 * @param srcBi 源图形文件
	 * @param destDir 目标存放目录
	 * @param rows 分割行数
	 * @param cols 分割列数
	 * @return 分割成功:true 分割失败:false
	 * @throws GraphException
	 */
	public boolean compart(BufferedImage srcBi,File destDir,int rows,int cols)throws GraphException;
	
	/**
	 * 图形类型转换,支持如下格式:<br>
	 * GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 * @param srcBi 源图形文件
	 * @param dest 目录文件
	 * @return 转换成功:true 转换失败:false
	 * @throws GraphException
	 */
	public boolean convert(BufferedImage srcBi,File dest)throws GraphException;
	
	/**
	 * 切割图形,从原图中切割出子图形
	 * @param srcBi 源图形文件
	 * @param rectangle 切割的区域 
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage cut(BufferedImage srcBi,Rectangle rectangle)throws GraphException;
	
	/**
	 * 将图形按宽高指定容器大小进行缩放之后再切割(不会变型)<br>
	 * 目标图形的宽高由容器大小自动判断
	 * @param srcBi 源图形文件
	 * @param width 目标图形宽度
	 * @param height 目标图形高度
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage cut(BufferedImage srcBi,double width,double height)throws GraphException;
	
	/**
	 * 切割为正方形<br>
	 * 1. 图形原本为正方形将直接进行缩放,不进行切割操作;<br>
	 * 2. 图形非正方形将由系统在保证图形不变形的情况下自动智能切割为正方形
	 * @param srcBi 源图形文件
	 * @param size 正方形边长
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage cutToSquare(BufferedImage srcBi,double size)throws GraphException;
	
	/**
	 * 图形旋转,将图形以顺时针旋转radian弧度
	 * @param srcBi 源图形文件
	 * @param radian 弧度
	 * @param canvas 画布颜色,为null时,将默认为Color.WHITE
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage rotate(BufferedImage srcBi,double radian,Color canvas)throws GraphException;
	
	
	/**
	 * 将图形转换为Gray灰色(黑白照片)
	 * @param srcBi 源图形文件
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage toGray(BufferedImage srcBi)throws GraphException;
	/**
	 * 为图形加上图形水映
	 * @param srcBi 源图形文件
	 * @param logo 水映图形文件
	 * @param offsetX 偏移X轴
	 * @param offsetY 偏移Y轴
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage waterMarkLogo(BufferedImage srcBi,WaterMarkLogo waterMarkLogo)throws GraphException;
	
	/**
	 * 为图形加上文本水映
	 * @param srcBi 源图形文件
	 * @param logo 水映图形文件
	 * @param offsetX 偏移X轴
	 * @param offsetY 偏移Y轴
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage waterMarkText(BufferedImage srcBi,WaterMarkText waterMarkText)throws GraphException;
	
	
	/**
	 * 图形按比例进行缩放(不会变型)<br>
	 * 缩放比例转换矩阵为格式如下:
     * <pre>
     *		[   scale  0       0   ]
     *		[   0      scale   0   ]
     *		[   0      0       1   ]
     * </pre>
	 * @param srcBi 源图形文件
	 * @param scale 缩放比例,以百分比进行计算,如:50表示50%
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage zoom(BufferedImage srcBi,double scale)throws GraphException;
	
	/**
	 * 图形按宽高指定比例进行缩放(不会变型)<br>
	 * 缩放比例转换矩阵为格式如下:
     * <pre>
     *		[   sx     0       0   ]
     *		[   0      sy      0   ]
     *		[   0      0       1   ]
     * </pre>
	 * @param srcBi 源图形文件
	 * @param sx 宽缩放比例,,以百分比进行计算,如:50表示50%
	 * @param sy 高缩放比例,,以百分比进行计算,如:50表示50%
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage zoom(BufferedImage srcBi,double sx,double sy)throws GraphException;
	
	/**
	 * 缩放到指定高度,不进行切割操作(不会变型)<br>
	 * 在保证图形不变形的情况下自动智能缩放到指定高度,宽度将相应缩放
	 * @param srcBi 源图形文件
	 * @param height 目标图形高度
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage zoomToHeight(BufferedImage srcBi,double height)throws GraphException;
	
	/**
	 * 缩放到指定宽度,不进行切割操作(不会变型)<br>
	 * 在保证图形不变形的情况下自动智能缩放到指定宽度,高度将相应缩放
	 * @param srcBi 源图形文件
	 * @param width 目标图形宽度
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage zoomToWidth(BufferedImage srcBi,double width)throws GraphException;
	
	/**
	 * 拉伸将图形按宽高指定容器大小进行缩放(会变型)<br>
	 * 目标图形的宽高由容器大小自动判断
	 * @param srcBi 源图形文件
	 * @param width 目标图形宽度
	 * @param height 目标图形高度
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage zoomStretch(BufferedImage srcBi,double width,double height)throws GraphException;
	
	/**
	 * 不拉伸将图形按宽高指定容器大小进行缩放(不会变型)<br>
	 * 目标图形的宽高由容器大小自动判断
	 * @param srcBi 源图形文件
	 * @param width 目标图形宽度
	 * @param height 目标图形高度
	 * @return 目标图形文件
	 * @throws GraphException
	 */
	public BufferedImage zoomUnStretch(BufferedImage srcBi,double width,double height)throws GraphException;
	
	/**
	 * 将BufferedImage保存为文件
	 * @param srcBi 源图形文件
	 * @param dest 目标文件名
	 * @return 写成功:true 写失败:false
	 * @throws GraphException
	 */
	public boolean ioWrite(BufferedImage srcBi,File dest) throws GraphException;
	
	/**
	 * 将BufferedImage以指定质量保存为文件
	 * @param srcBi 源图形文件
	 * @param dest 目标文件名
	 * @param quality 图形质量
	 * @return 写成功:true 写失败:false
	 * @throws GraphException
	 */
	public boolean ioWrite(BufferedImage srcBi,File dest,float quality) throws GraphException;
}
