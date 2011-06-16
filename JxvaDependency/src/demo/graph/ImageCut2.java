package demo.graph;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * import java.awt.geom.AffineTransform; AffineTransform t = new
 * AffineTransform() ; t.rotate(double d) ;
 * 
 * 这个类里面一进提供了基本上所有的关于图像平移，旋转，缩放等功能，自己看看去
 * 
 * @author The Jxva Framework
 * @since 1.0
 * @version 2008-11-27 16:56:38 by Jxva
 */
public class ImageCut2 {

	public static void scale(String srcImageFile, double standardWidth,
			double standardHeight) {
		try {
			System.out.println("scale&&&&&&&&&&&&&");
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件

			Image image = src.getScaledInstance((int) standardWidth,
					(int) standardHeight, Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage((int) standardWidth,
					(int) standardHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			ImageIO.write(tag, "JPG", new File(srcImageFile + "name.jpg"));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cut(String srcImageFile, double standardWidth,
			double standardHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			if (srcWidth > standardWidth && srcHeight > standardHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int w = 0;
				int h = 0;

				double wScale = srcWidth / standardWidth;
				double hScale = srcHeight / standardHeight;
				int srcWidth2;
				int srcHeight2;
				if (wScale > hScale) {
					srcWidth2 = (int) (standardWidth * hScale);
					w = (srcWidth - srcWidth2) / 2;
					srcWidth = srcWidth2;
					h = 0;
				} else {
					srcHeight2 = (int) (standardHeight * wScale);
					h = (srcHeight - srcHeight2) / 2;
					srcHeight = srcHeight2;
					w = 0;
				}
				cropFilter = new CropImageFilter(w, h, srcWidth, srcHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(srcWidth, srcHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null);
				g.dispose();
				ImageIO.write(tag, "JPEG", new File(srcImageFile));
				scale(srcImageFile, standardWidth, standardHeight);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放 - 参数指定目标图缩放比例。
	 * 
	 * @param srcImage
	 *            BufferedImage
	 * @param xscale
	 *            float
	 * @param yscale
	 *            float
	 * @param hints
	 *            RenderingHints
	 * @return BufferedImage
	 */
	public static BufferedImage scaleJ2D(BufferedImage srcImage, double xscale,
			double yscale, RenderingHints hints) {
		int width = (int) ((double) srcImage.getWidth() * xscale);
		int height = (int) ((double) srcImage.getHeight() * yscale);
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.scale(xscale, yscale);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, hints);
		BufferedImage dstImage = new BufferedImage(width, height, srcImage.getType());
		return affineTransformOp.filter(srcImage, dstImage);
	}

	public static final int J2D_ROTATE_90 = 90;
	public static final int J2D_ROTATE_180 = 180;
	public static final int J2D_ROTATE_270 = 270;

	/**
	 * 旋转 - 参数指定目标图旋转角度。
	 * 
	 * @param bufferedImage
	 *            BufferedImage
	 * @param radian
	 *            int
	 * @param hints
	 *            RenderingHints
	 * @return BufferedImage
	 */
	public static BufferedImage rotateJ2D(BufferedImage bufferedImage,int radian, RenderingHints hints) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		BufferedImage dstImage = null;
		AffineTransform affineTransform = new AffineTransform();
		if (radian == 180) {
			affineTransform.translate(width, height);
			dstImage = new BufferedImage(width, height, bufferedImage.getType());
		} else if (radian == 90) {
			affineTransform.translate(height, 0);
			dstImage = new BufferedImage(height, width, bufferedImage.getType());
		} else if (radian == 270) {
			affineTransform.translate(0, width);
			dstImage = new BufferedImage(height, width, bufferedImage.getType());
		}
		affineTransform.rotate(Math.toRadians(radian));
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, hints);
		return affineTransformOp.filter(bufferedImage, dstImage);
	}

	public static void main(String[] args) {
		cut("E:/test/2.jpg", 200, 125);
	}
}