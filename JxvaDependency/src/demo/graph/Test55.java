/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
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
package demo.graph;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2008-11-27 16:59:50 by Jxva
 */
public class Test55 {
	
	private String fromdir;
	private String imgfile;
	private String todir;
	private String sysimgfile;

	/**
	 * 下面的程序使用jdk1.4中最新的ImageIO对图片进行读写.使用AffineTransform对图片进行缩放.
	 * @return
	 * @throws Exception
	 */
	public boolean CreateThumbnail() throws Exception {
		// ext是图片的格式 gif JPG 或png
		String ext = "";
		double Ratio = 0.0;
		File F = new File(fromdir, imgfile);
		if (!F.isFile())
			throw new Exception(F
					+ " is not image file error in CreateThumbnail!");

		// 首先判断上传的图片是gif还是JPG ImageIO只能将gif转换为png
		if (isJpg(imgfile)) {
			ext = "jpg";
		} else {
			ext = "png";
		}
		File ThF = new File(todir, sysimgfile + "." + ext);

		BufferedImage Bi = ImageIO.read(F);
		// 假设图片宽 高 最大为120 120
		Image Itemp = Bi.getScaledInstance(120, 120, Bi.SCALE_SMOOTH);

		if ((Bi.getHeight() > 120) || (Bi.getWidth() > 120)) {
			if (Bi.getHeight() > Bi.getWidth())
				Ratio = 120.0 / Bi.getHeight();
			else
				Ratio = 120.0 / Bi.getWidth();
		}

		AffineTransformOp op = new AffineTransformOp(AffineTransform
				.getScaleInstance(Ratio, Ratio), null);
		Itemp = op.filter(Bi, null);

		try {
			ImageIO.write((BufferedImage) Itemp, ext, ThF);
		} catch (Exception ex) {
			throw new Exception(" ImageIo.write error in CreatThum.: "
					+ ex.getMessage());
		}
		return (true);
	}

	/**
	 * @param imgfile2
	 * @return
	 */
	private boolean isJpg(String imgfile2) {
		// TODO Auto-generated method stub
		return false;
	}

}
